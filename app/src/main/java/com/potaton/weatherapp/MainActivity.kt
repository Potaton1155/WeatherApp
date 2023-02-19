package com.potaton.weatherapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.potaton.weatherapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        //LOCATION_CODE
        const val LOCATION_CODE = 100

        //permissions
        val permission_list = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //permissionチェック
        if (!hasPermission(this, *permission_list)) {
            ActivityCompat.requestPermissions(this, permission_list, LOCATION_CODE)
        }
    }


    private fun hasPermission(context: Context, vararg permissionList: String): Boolean = permissionList.all {
        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_CODE -> if (grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults.isEmpty()) {
                Toast.makeText(this, "アプリを使用するには、設定から位置情報をオンにしてください", Toast.LENGTH_LONG).show()
            }
        }
    }
}
