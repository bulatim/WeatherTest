package ru.bulat.weatherapplicationtest.ui.weather

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.map_weather_fragment.*
import ru.bulat.weatherapplicationtest.BuildConfig
import ru.bulat.weatherapplicationtest.R
import ru.bulat.weatherapplicationtest.base.BaseFragment
import ru.bulat.weatherapplicationtest.databinding.MapWeatherFragmentBinding
import ru.bulat.weatherapplicationtest.databinding.WeatherDialogBinding
import ru.bulat.weatherapplicationtest.utils.LocationListenerImpl
import ru.bulat.weatherapplicationtest.utils.REQUEST_CODE_PERMISSION_ACCESS_FINE_LOCATION
import javax.inject.Inject


class MapWeatherFragment : BaseFragment(), OnMapReadyCallback {
    lateinit var binding: MapWeatherFragmentBinding
    lateinit var bindingWeatherDialog: WeatherDialogBinding
    lateinit var viewModel: MapWeatherViewModel
    lateinit var builder: AlertDialog.Builder
    lateinit var locationAlertDialogBuilder: AlertDialog.Builder
    lateinit var alertDialog: AlertDialog
    lateinit var locationAlertDialog: AlertDialog
    lateinit var locationManager: LocationManager
    lateinit var locationListener: LocationListenerImpl
    lateinit var weatherDialog: WeatherDialog
    var mMap: GoogleMap? = null
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component().inject(this)
        locationManager = activity?.getSystemService(LOCATION_SERVICE) as LocationManager
        locationListener = LocationListenerImpl { location ->
            location?.let {
                mMap?.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            it.latitude,
                            it.longitude
                        ), 15f
                    )
                )
                locationManager.removeUpdates(locationListener)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        locationManager.removeUpdates(locationListener)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            MapWeatherFragmentBinding.inflate(
                inflater,
                container,
                false
            )
        bindingWeatherDialog = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.weather_dialog,
            null,
            false
        )
        bindingWeatherDialog.lifecycleOwner = this
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (childFragmentManager.findFragmentById(R.id.map)
                as SupportMapFragment).getMapAsync(this)
        builder = AlertDialog.Builder(requireContext())
        locationAlertDialogBuilder = AlertDialog.Builder(requireContext())
        myLocation.setOnClickListener {
            if (locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            ) {
                requestLocation()
            } else {
                if (!::locationAlertDialog.isInitialized) {
                    locationAlertDialogBuilder.setMessage("Определение местоположения выключено")
                    locationAlertDialogBuilder.setPositiveButton("Включить") { _, _ ->
                        startActivity(
                            Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS
                            )
                        )
                    }
                    locationAlertDialogBuilder.setNegativeButton("Отменить") { dialog, _ ->
                        dialog.dismiss()
                    }
                    locationAlertDialog = locationAlertDialogBuilder.create()
                }
                locationAlertDialog.show()
            }
        }
    }

    private fun requestLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_PERMISSION_ACCESS_FINE_LOCATION
            )
        } else {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000 * 10L, 10f, locationListener
            )
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 10L, 10f,
                locationListener
            )
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MapWeatherViewModel::class.java)
        binding.viewModel = viewModel
        bindingWeatherDialog.viewModel = viewModel
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_PERMISSION_ACCESS_FINE_LOCATION -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    requestLocation()
                } else {
                    if (isUserCheckNeverAskAgain()) {
                        val permissionDialog = AlertDialog.Builder(requireContext())
                        permissionDialog.setMessage(
                            "Для определения местоположения, необходимо предоставить разрешение " +
                                    "на определение местоположения в настройках приложения в разделе \"Права\""
                        )
                        permissionDialog.setPositiveButton("Перейти") { dialog, _ ->
                            val intent = Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                            )
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                        permissionDialog.setNegativeButton("Отменить") { dialog, _ ->
                            dialog.dismiss()
                        }
                        permissionDialog.show()
                    } else
                        showToast("Доступ к определению местоположения запрещен")
                }
                return
            }
            else -> {
            }
        }
    }

    private fun isUserCheckNeverAskAgain() =
        !ActivityCompat.shouldShowRequestPermissionRationale(
            requireActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap
        val latitude = sharedPreferences.getString("lastLatitude", "59.950015")!!.toDouble()
        val longtitude = sharedPreferences.getString("lastLongtitude", "30.316599")!!.toDouble()
        val zoom = sharedPreferences.getFloat("lastZoom", 7f)
        googleMap?.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    latitude,
                    longtitude
                ), zoom
            )
        )
        googleMap?.setOnMapClickListener {
            val marker = MarkerOptions().position(it)
            googleMap.addMarker(marker)
            if(!::weatherDialog.isInitialized)
                weatherDialog = WeatherDialog(bindingWeatherDialog) { googleMap.clear() }
            weatherDialog.show(
                parentFragmentManager,
                WeatherDialog::class.java.simpleName
            )
            viewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
                showToast(it)
                weatherDialog.dismiss()
            })
            viewModel.getWeatherByCoordinate(it.latitude, it.longitude)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPreferences.edit().putString(
            "lastLatitude",
            if (mMap?.cameraPosition?.target?.latitude != null) mMap?.cameraPosition?.target?.latitude.toString() else "59.950015"
        ).apply()
        sharedPreferences.edit()
            .putString(
                "lastLongtitude",
                if (mMap?.cameraPosition?.target?.longitude != null) mMap?.cameraPosition?.target?.longitude.toString() else "30.316599"
            ).apply()
        mMap?.cameraPosition?.zoom?.let { zoom ->
            sharedPreferences.edit().putFloat("lastZoom", zoom).apply()
        }
    }
}