package com.example.powerapplication2

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.powerapplication2.databinding.ActivityMainBinding

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


open class MainActivity : AppCompatActivity(R.layout.activity_main) {

    val binding by viewBinding(ActivityMainBinding::bind)
    private val TAG = "LocationActivity"
    var fusedClient: FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            //request permission.
            //However check if we need to show an explanatory UI first
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ) {
                showRationale()
            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_NETWORK_STATE
                    ), 2
                )
            }
        } else {
            //we already have the permission. Do any location wizardry now
            locationWizardry()
        }
    }

    @SuppressLint("MissingPermission")
    private fun locationWizardry() {
        fusedClient = LocationServices.getFusedLocationProviderClient(this)
        //Initially, get last known location. We can refine this estimate later
        fusedClient!!.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val loc =
                    location.provider + " ${formatLocation(location)}"
                binding.lastLocation.text = loc
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            2 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Thanks bud", Toast.LENGTH_SHORT).show()
                    locationWizardry()
                } else {
                    Toast.makeText(this, "C'mon man we really need this", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {}
        }
    }


    private fun showRationale() {
        val dialog: AlertDialog = AlertDialog.Builder(this).setMessage(
            "We need this, Just suck it up and grant us the" +
                    "permission :)"
        ).setPositiveButton("Sure") { dialogInterface, i ->
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                2
            )
            dialogInterface.dismiss()
        }
            .create()
        dialog.show()
    }

    @SuppressLint("DefaultLocale")
    private fun formatLocation(location: Location?): String? {
        return if (location == null) "" else "${location.latitude} ${location.longitude}"
    }
}