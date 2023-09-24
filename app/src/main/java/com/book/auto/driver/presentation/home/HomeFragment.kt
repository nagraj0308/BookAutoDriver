package com.book.auto.driver.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.book.auto.driver.R
import com.book.auto.driver.databinding.FragmentHomeBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by activityViewModels()
    private var isNew: Boolean = true
    private var map: GoogleMap? = null
    private var cl: LatLng? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        MapsInitializer.initialize(requireContext(), MapsInitializer.Renderer.LATEST) {
            Log.v("NAGRAJ", it.name)
        }
        val root: View = binding.root
        val navController = findNavController()
        binding.btnUpdateLocation.setOnClickListener {
            activity?.let { it1 -> viewModel.updateLocation(it1) }
        }
        binding.btnAddAuto.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("is_new", isNew)
            navController.navigate(R.id.nav_add_edit_auto, bundle)
        }
        binding.btnEdit.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("is_new", isNew)
            navController.navigate(R.id.nav_add_edit_auto, bundle)
        }

        viewModel.readVehicle.observe(viewLifecycleOwner, Observer {
            setContentState()
        })

        //Maps View
        binding.mvCl.onCreate(savedInstanceState)
        binding.mvCl.getMapAsync {
            map = it
            viewModel.lat.observe(viewLifecycleOwner, Observer { it1 ->
                map!!.clear()
                cl = LatLng(it1, viewModel.lon.value!!)
                val update = CameraUpdateFactory.newLatLngZoom(cl!!, 10f)
                map!!.moveCamera(update)

                map!!.addMarker(
                    MarkerOptions().position(cl!!).title("Current Location")
                )
            })

            viewModel.lon.observe(viewLifecycleOwner, Observer { it1 ->
                map!!.clear()
                cl = LatLng(viewModel.lat.value!!, it1)
                val update = CameraUpdateFactory.newLatLngZoom(cl!!, 10f)
                map!!.moveCamera(update)

                map!!.addMarker(
                    MarkerOptions().position(cl!!).title("Current Location")
                )
            })
        }
        return root
    }


    override fun onStart() {
        super.onStart()
        binding.mvCl.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mvCl.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mvCl.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mvCl.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mvCl.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mvCl.onSaveInstanceState(outState)
    }

    private fun setContentState() {
        if (viewModel.readVehicle.value?._id == "") {
            isNew = true
            binding.btnAddAuto.visibility = View.VISIBLE
            binding.cvContent.visibility = View.GONE

        } else {
            isNew = false
            binding.btnAddAuto.visibility = View.GONE
            binding.cvContent.visibility = View.VISIBLE
            binding.tvAutoNo.text = viewModel.readVehicle.value!!.number
            binding.tvMobileNo.text = viewModel.readVehicle.value!!.mobileNo
            binding.tvLive.text =
                if (viewModel.readVehicle.value!!.deactivated || viewModel.readVehicle.value!!.verificationState != "A") "Not Live" else "Live"
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}