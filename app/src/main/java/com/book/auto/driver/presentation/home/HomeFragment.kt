package com.book.auto.driver.presentation.home

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.book.auto.driver.R
import com.book.auto.driver.data.remote.reqres.Vehicle
import com.book.auto.driver.databinding.FragmentHomeBinding
import com.book.auto.driver.presentation.base.BaseFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by activityViewModels()
    private var isNew: Boolean = true
    private var map: GoogleMap? = null
    private var cl: LatLng? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        MapsInitializer.initialize(requireContext(), MapsInitializer.Renderer.LATEST) {}
        val root: View = binding.root
        val navController = findNavController()
        binding.btnUpdateLocation.setOnClickListener {
            activity?.let { it1 ->
                viewModel.updateLocation(it1)
            }
        }
        binding.btnAddAuto.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("is_new", isNew)
            navController.navigate(R.id.nav_add_edit_auto, bundle)
        }

        binding.sbDeactivated.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateAutoActivation(isChecked)
        };

        binding.btnEdit.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("is_new", isNew)
            navController.navigate(R.id.nav_add_edit_auto, bundle)
        }

        viewModel.vehicle.observe(viewLifecycleOwner, Observer {
            setContentState(it)
        })

        //Maps View
        binding.mvCl.onCreate(savedInstanceState)
        binding.mvCl.getMapAsync {

            val circleDrawable = resources.getDrawable(R.drawable.ic_auto, null)
            val markerIcon: BitmapDescriptor = getMarkerIconFromDrawable(circleDrawable)
            map = it
            viewModel.lat.observe(viewLifecycleOwner, Observer { it1 ->
                map!!.clear()
                cl = LatLng(it1, viewModel.lon.value!!)
                val update = CameraUpdateFactory.newLatLngZoom(cl!!, 10f)
                map!!.moveCamera(update)
                map!!.addMarker(
                    MarkerOptions().position(cl!!).title("Current Location").icon(markerIcon)
                )
            })

            viewModel.lon.observe(viewLifecycleOwner, Observer { it1 ->
                map!!.clear()
                cl = LatLng(viewModel.lat.value!!, it1)
                val update = CameraUpdateFactory.newLatLngZoom(cl!!, 10f)
                map!!.moveCamera(update)
                map!!.addMarker(
                    MarkerOptions().position(cl!!).title("Current Location").icon(markerIcon)
                )
            })
        }
        return root
    }


    override fun onStart() {
        viewModel.getAutoDetails {

        }
        super.onStart()
        binding.mvCl.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mvCl.onResume()
    }


    override fun onStop() {
        super.onStop()
        binding.mvCl.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mvCl.onLowMemory()
    }


    private fun setContentState(vehicle: Vehicle?) {
        if (vehicle != null) {
            if (vehicle._id == "") {
                isNew = true
                binding.btnAddAuto.visibility = View.VISIBLE
                binding.cvContent.visibility = View.GONE

            } else {
                isNew = false
                binding.btnAddAuto.visibility = View.GONE
                binding.cvContent.visibility = View.VISIBLE
                binding.tvAutoNo.text = vehicle.number
                binding.tvMobileNo.text = vehicle.mobileNo
                binding.tvLive.text = getStatusMsg(vehicle.deactivated, vehicle.verificationState)
                binding.sbDeactivated.setChecked(vehicle.deactivated)

            }
        }

    }

    private fun getStatusMsg(deactivated: Boolean, verificationState: String): String {
        return if (verificationState == "U") {
            "System Verification Pending"
        } else if (verificationState == "R") {
            "Rejected in verification"
        } else {
            if (deactivated) {
                "Deactivated by you"
            } else {
                "Live"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getMarkerIconFromDrawable(drawable: Drawable): BitmapDescriptor {
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }


}