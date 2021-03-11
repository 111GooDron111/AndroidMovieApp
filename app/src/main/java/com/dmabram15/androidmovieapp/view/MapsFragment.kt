package com.dmabram15.androidmovieapp.view

import android.app.Activity
import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.dmabram15.androidmoviesapp.R
import com.dmabram15.androidmoviesapp.databinding.FragmentMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private lateinit var binding: FragmentMapsBinding
    private val maxResult = 1
    private val defaultZoom = 15f
    private val mapFragment by lazy {
        childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
    }
    val sydney = LatLng(-34.0, 151.0)
    var currentFoundAddress: LatLng? = null

    private val callback = OnMapReadyCallback { googleMap ->

        googleMap.setOnMapLongClickListener {
            currentFoundAddress = it
            moveCameraToLonLng(googleMap, it)
            setAddressToAddressTextView(it)
        }

        currentFoundAddress?.let {
            moveCameraToLonLng(googleMap, it)
        }
    }

    private fun moveCameraToLonLng(googleMap: GoogleMap, latLng: LatLng) {
        googleMap.addMarker(MarkerOptions().position(latLng))
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(latLng, defaultZoom)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment?.getMapAsync(callback)

        initializeFields()
        setListeners()
    }

    private fun initializeFields() {
        binding.root.post {
            setAddressToAddressTextView(currentFoundAddress)
        }
    }

    private fun setAddressToAddressTextView(address: LatLng?) {
        var textAddress: String
        if (address != null) {
            address.let {
                textAddress = getAddressAsync(it.latitude, it.longitude)
            }
        } else {
            textAddress = getAddressAsync(sydney.latitude, sydney.longitude)
        }
        binding.currentSiteTextView.text = textAddress
    }

    private fun setListeners() {
        binding.searchAddressButton.setOnClickListener {
            currentFoundAddress = getLatLng(
                binding.addressEditText.text.toString()
            )
            mapFragment?.getMapAsync(callback)
            hideSoftKeyboard()
            setAddressToAddressTextView(currentFoundAddress)
        }
    }

    private fun getAddressAsync(lat: Double, long: Double): String {
        val addresses = Geocoder(context).getFromLocation(lat, long, maxResult)
        return if (addresses.isNotEmpty()) {
            addresses[0].getAddressLine(0)
        } else "Address not found"
    }

    private fun getLatLng(address: String): LatLng {
        var addresses: MutableList<Address>? = null
        if (address.isNotEmpty()) {
            addresses = Geocoder(context).getFromLocationName(address, maxResult)
        }
        addresses?.let {
            return when {
                addresses.isNotEmpty() -> {
                    LatLng(addresses[0].latitude, addresses[0].longitude)
                }
                else -> {
                    sydney
                }
            }
        }
        view?.post {
            binding.addressEditText.showSnackbar("Address $address not found")
        }
        return sydney
    }

    private fun hideSoftKeyboard() {
        activity?.let {
            (it.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
                .toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        }
    }

    companion object {
        fun newInstance() = MapsFragment()
    }
}