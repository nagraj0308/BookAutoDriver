package com.rent.house.presentation.home

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.rent.house.R
import com.rent.house.data.remote.reqres.House
import com.rent.house.databinding.FragmentHomeBinding
import com.rent.house.presentation.base.BaseFragment


class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by activityViewModels()
    private var map: GoogleMap? = null
    private var cl: LatLng? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        MapsInitializer.initialize(requireContext(), MapsInitializer.Renderer.LATEST) {}
        val root: View = binding.root
        binding.btnRefresh.setOnClickListener {
            activity?.let { it1 ->
                viewModel.updateLocation(it1) {}
            }
        }


        //Maps View
        binding.mvCl.onCreate(savedInstanceState)
        binding.mvCl.getMapAsync()
        {

            val circleDrawable = resources.getDrawable(R.drawable.user, null)
            val markerIcon: BitmapDescriptor = getMarkerIconFromDrawable(circleDrawable)
            map = it


            viewModel.houses.observe(viewLifecycleOwner) { list ->
                map!!.clear()
                cl = LatLng(viewModel.lat.value!!, viewModel.lon.value!!)
                val update = CameraUpdateFactory.newLatLngZoom(cl!!, 10f)
                map!!.moveCamera(update)
                map!!.addMarker(
                    MarkerOptions().position(cl!!).title(getString(R.string.your_location))
                        .icon(markerIcon)
                )

                for (au in list) {
                    map!!.addMarker(
                        MarkerOptions().position(LatLng(au.lat, au.lon)).title(au.rate)
                            .icon(
                                getMarkerIconFromDrawable(
                                    resources.getDrawable(
                                        getDrawable(),
                                        null
                                    )
                                )
                            ).snippet(au._id)
                    )
                }

                map!!.setOnMarkerClickListener { marker ->
                    val auto = getAuto(marker.snippet, list)
                    if (auto != null) {
                        showDetails(auto)
                        true
                    } else {
                        false

                    }
                }

            }

        }
        return root
    }

    private fun getAuto(id: String?, list: List<House>): House? {
        for (au in list) {
            if (au._id == id) {
                return au
            }
        }
        return null
    }

    private fun showDetails(auto: House) {
        val bundle = Bundle()
        bundle.putSerializable("item", auto)
        findNavController().navigate(R.id.nav_view_house, bundle)
    }

    private fun getDrawable(): Int {
        return R.drawable.car
    }


    override fun onStart() {
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showProgress() {
        TODO("Not yet implemented")
    }

    override fun hideProgress() {
        TODO("Not yet implemented")
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