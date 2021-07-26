package com.ankh.jomax.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.ankh.jomax.R
import com.ankh.jomax.databinding.ActivityMainBinding
import com.ankh.jomax.databinding.BottomSheetBehaviorBinding
import com.ankh.jomax.util.DrawingColors
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainActivity : AppCompatActivity() {
    private val permissions = Manifest.permission.WRITE_EXTERNAL_STORAGE
    private lateinit var bottomSheetBehaviorBinding: BottomSheetBehaviorBinding
    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)


        bottomSheetBehaviorBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.bottom_sheet_behavior,
            mainBinding.root as ViewGroup,
            true
        )

        mainBinding.recyclerviewColors.apply{
            adapter = ColorsAdapter(
                this@MainActivity,
                DrawingColors.getColors(),
                ::changePenColors)
            layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
        }

        changePenColors(DrawingColors.getColors()[0].value)

        bottomSheetBehaviorBinding.faBtnScreenCapture.setOnClickListener {
            Toast.makeText(this,"Saved",Toast.LENGTH_LONG).show()
            mainBinding.viewMyCanvas.clearCanvas()
            //binding.viewMyCanvas.screenShot("test.jpg")
        }

        bottomSheetBehaviorBinding.skBarPenSize.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mainBinding.viewMyCanvas.changePenSize(seekBar!!.progress.toFloat())
            }
        })

        val bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet))
        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                //bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }

        })

        bottomSheetBehaviorBinding.viewBottomSheetDrag.setOnClickListener {
            if(bottomSheetBehavior.state==BottomSheetBehavior.STATE_COLLAPSED){
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }else{
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        checkPermission()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== PERMISSION_REQUEST_CODE){
            checkPermission()
        }
    }

    private fun changePenColors(color: String){
        mainBinding.viewMyCanvas.changeDrawingColor(color)
        bottomSheetBehaviorBinding.skBarPenSize.thumb.setTint(Color.parseColor(color))
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