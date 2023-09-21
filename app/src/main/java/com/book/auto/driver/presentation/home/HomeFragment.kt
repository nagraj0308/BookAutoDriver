package com.book.auto.driver.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.book.auto.driver.R
import com.book.auto.driver.databinding.FragmentHomeBinding
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class HomeFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentHomeBinding? = null

    private var mLocationRequest: LocationRequest? = null
    private val UPDATE_INTERVAL = (10 * 1000).toLong()  /* 10 secs */
    private val FASTEST_INTERVAL: Long = 2000 /* 2 sec */
    private val viewModel: HomeViewModel by activityViewModels()


    private lateinit var mGoogleMap: GoogleMap

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val navController = findNavController()
        binding.btnUpdateLocation.setOnClickListener {
            activity?.let { it1 -> viewModel.updateLocation(it1) }
        }
        binding.btnAddAuto.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("is_add", true)
            navController.navigate(R.id.nav_add_edit_auto, bundle)
        }
        binding.btnEdit.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("is_add", false)
            navController.navigate(R.id.nav_add_edit_auto, bundle)
        }

//        val mapFragment = binding.mvCl as SupportMapFragment
//        mapFragment.getMapAsync(this)

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.addMarker(
            MarkerOptions().position(
                LatLng(
                    viewModel.readState.value!!.lat,
                    viewModel.readState.value!!.lon
                )
            ).title("Current Location")
        )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}