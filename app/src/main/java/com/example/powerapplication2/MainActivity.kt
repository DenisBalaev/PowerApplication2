package com.example.powerapplication2

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.powerapplication2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    val binding by viewBinding(ActivityMainBinding::bind)

    private val requestOverlayPermission = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            requestOverlayPermission.launch(intent)
        }
    }
}