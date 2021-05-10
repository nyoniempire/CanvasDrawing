package com.ankh.jomax.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.ankh.jomax.R
import com.ankh.jomax.data.PenColors
import com.ankh.jomax.databinding.ActivityMainBinding
import com.ankh.jomax.util.DrawingColors

class MainActivity : AppCompatActivity() {
    private val permissions = Manifest.permission.WRITE_EXTERNAL_STORAGE
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.recyclerviewColors.apply{
            adapter = ColorsAdapter(
                this@MainActivity,
                DrawingColors.getColors(),
                ::changePenColors)
            layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
        }

        binding.faBtnScreenCapture.setOnClickListener {
            Toast.makeText(this,"Saved",Toast.LENGTH_LONG).show()
            binding.viewMyCanvas.clearCanvas()
            //binding.viewMyCanvas.screenShot("test.jpg")
        }

        binding.skBarPenSize.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                binding.viewMyCanvas.changePenSize(seekBar!!.progress.toFloat())
            }
        })
        checkPermission()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== PERMISSION_REQUEST_CODE){
            checkPermission()
        }
    }

    private fun changePenColors(color: String){
        binding.viewMyCanvas.changeDrawingColor(color)
        binding.skBarPenSize.thumb.setTint(Color.parseColor(color))
    }

    private fun checkPermission(){
        if (ContextCompat.checkSelfPermission(
                this,
                permissions
            ) == PackageManager.PERMISSION_GRANTED
        ){
            return
        }else{
            requestForPermissions()
        }
    }

    private fun requestForPermissions(){
        ActivityCompat.requestPermissions(this, arrayOf(permissions), PERMISSION_REQUEST_CODE)
    }
}

const val PERMISSION_REQUEST_CODE = 1001