package com.book.auto.presentation.home

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.book.auto.R
import com.book.auto.data.remote.reqres.Auto
import com.book.auto.databinding.FragmentHomeBinding
import com.book.auto.presentation.base.BaseFragment
import com.book.auto.utils.Constants
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
//                viewModel.updateLocation(it1)
                showDetails(Auto())
            }
        }


//            if (it != null) {
//                val bundle = Bundle()
////                bundle.putSerializable("item", mList[position])
//                navController.navigate(R.id.nav_view, bundle)
//            }


//        binding.btnEdit.setOnClickListener {
//            val bundle = Bundle()
//            bundle.putBoolean("is_new", isNew)
////            navController.navigate(R.id.nav_add_edit_auto, bundle)
//        }


        //Maps View
        binding.mvCl.onCreate(savedInstanceState)
        binding.mvCl.getMapAsync()
        {

            val circleDrawable = resources.getDrawable(R.drawable.user, null)
            val markerIcon: BitmapDescriptor = getMarkerIconFromDrawable(circleDrawable)
            map = it


            viewModel.autos.observe(viewLifecycleOwner) { list ->
                map!!.clear()
                cl = LatLng(viewModel.lat.value!!, viewModel.lon.value!!)
                val update = CameraUpdateFactory.newLatLngZoom(cl!!, 10f)
                map!!.moveCamera(update)
                map!!.addMarker(
                    MarkerOptions().position(cl!!).title("Current Location").icon(markerIcon)
                )

                for (au in list) {
                    map!!.addMarker(
                        MarkerOptions().position(LatLng(au.lat, au.lon)).title(au.number)
                            .icon(
                                getMarkerIconFromDrawable(
                                    resources.getDrawable(
                                        getDrawable(au.type),
                                        null
                                    )
                                )
                            )
                    )
                }

            }
            map.setOnMarkerClickListener {
                it.
            }
        }
        return root
    }

    private fun showDetails(auto: Auto) {
        val bundle = Bundle()
        bundle.putSerializable("item", auto)
        findNavController().navigate(R.id.nav_view_auto_details, bundle)
    }

    private fun getDrawable(type: String): Int {
        return when (type) {
            Constants.autoTypes[0] -> {
                R.drawable.w4
            }

            Constants.autoTypes[1] -> {
                R.drawable.w3
            }

            Constants.autoTypes[2] -> {
                R.drawable.w2
            }

            else -> {
                R.drawable.wo
            }
        }
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