package com.book.gaadi.presentation.mygaadi

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.book.gaadi.R
import com.book.gaadi.data.remote.reqres.Vehicle
import com.book.gaadi.databinding.FragmentMyGaadiBinding
import com.book.gaadi.presentation.base.BaseFragment
import com.book.gaadi.presentation.home.HomeViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MyGaadiFragment : BaseFragment() {

    private var _binding: FragmentMyGaadiBinding? = null
    private val viewModel: HomeViewModel by activityViewModels()
    private var isNew: Boolean = true
    private var map: GoogleMap? = null
    private var cl: LatLng? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyGaadiBinding.inflate(inflater, container, false)
        MapsInitializer.initialize(requireContext(), MapsInitializer.Renderer.LATEST) {}
        val root: View = binding.root
        val navController = findNavController()
        binding.btnAddAuto.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("is_new", isNew)
            navController.navigate(R.id.nav_add_edit_gaadi, bundle)
        }



        binding.btnEdit.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("is_new", isNew)
            navController.navigate(R.id.nav_add_edit_gaadi, bundle)
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
                    MarkerOptions().position(cl!!).title(getString(R.string.your_location))
                        .icon(markerIcon)
                )
            })

            viewModel.lon.observe(viewLifecycleOwner, Observer { it1 ->
                map!!.clear()
                cl = LatLng(viewModel.lat.value!!, it1)
                val update = CameraUpdateFactory.newLatLngZoom(cl!!, 10f)
                map!!.moveCamera(update)
                map!!.addMarker(
                    MarkerOptions().position(cl!!).title(getString(R.string.your_location))
                        .icon(markerIcon)
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
            if (vehicle._id == "" || vehicle.number == "") {
                isNew = true
                binding.btnAddAuto.visibility = View.VISIBLE
                binding.cvContent.visibility = View.GONE

            } else {
                isNew = false
                binding.btnAddAuto.visibility = View.GONE
                binding.cvContent.visibility = View.VISIBLE
                binding.tvLive.text = getStatusMsg(vehicle.verificationState)
            }
        } else {
            binding.btnAddAuto.visibility = View.VISIBLE
            binding.cvContent.visibility = View.GONE
        }

    }

    private fun getStatusMsg(verificationState: String): String = if (verificationState == "U") {
        getString(R.string.system_verification_pending)
    } else if (verificationState == "R") {
        getString(R.string.rejected_in_verification)
    } else {
        getString(R.string.live)
    }

    override fun showProgress() {
        TODO("Not yet implemented")
    }

    override fun hideProgress() {
        TODO("Not yet implemented")
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