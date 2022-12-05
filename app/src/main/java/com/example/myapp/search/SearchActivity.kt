package com.example.myapp.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.myapp.R
import com.example.myapp.databinding.ActivitySearchBinding
import com.example.myapp.viewmodel.SearchViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(),
    OnMapReadyCallback,
    TimePicker.OnTimeChangedListener,
    DatePicker.OnDateChangedListener
{
    private lateinit var map: GoogleMap
    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewmodel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        viewmodel = ViewModelProvider(this)[SearchViewModel::class.java]

        val mapFragment = SupportMapFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.map_container, mapFragment)
            .commit()

        setContentView(binding.root)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setMinZoomPreference(6.0f)
        map.setMaxZoomPreference(14.0f)

        val sogangUniv = LatLng(37.551088534404556, 126.94008951593834)
        map.addMarker(
            MarkerOptions()
                .position(sogangUniv)
                .title("Marker")
        )
        map.moveCamera(CameraUpdateFactory.newLatLng(sogangUniv))
        map.moveCamera(CameraUpdateFactory.zoomTo(20.0f))
    }

    override fun onDateChanged(view: DatePicker?, year: Int, month: Int, day: Int) {
        viewmodel.setDate(year, month, day)
    }

    override fun onTimeChanged(view: TimePicker?, hour: Int, minute: Int) {
        viewmodel.setTime(hour, minute)
    }
}