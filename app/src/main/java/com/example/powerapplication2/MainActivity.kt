package com.example.powerapplication2

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.powerapplication2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    val binding by viewBinding(ActivityMainBinding::bind)
    val REQUEST_OVERLAY_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("tagPower","MainActivity")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivityForResult(intent, REQUEST_OVERLAY_PERMISSION)
        }
    }
}