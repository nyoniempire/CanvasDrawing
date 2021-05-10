package com.ankh.jomax.view

import android.content.ContentValues
import android.content.Context
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat
import com.ankh.jomax.R
import com.ankh.jomax.util.Constants
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

private const val STROKE_WIDTH = 12f
class MyCanvasView: View {

    private val drawColor = ResourcesCompat.getColor(resources,R.color.colorPaint,null)
    private val bitmapColor = ResourcesCompat.getColor(resources, R.color.colorBackground,null)

    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap

    private val paint = Paint().apply{
        color = drawColor
        isAntiAlias = true
        isDither = true
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = STROKE_WIDTH
    }

    private var path = Path()
    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f
    private var currentX = 0f
    private var currentY = 0f

    constructor(context: Context):super(context)
    constructor(context: Context,attributeSet: AttributeSet): super(context,attributeSet)
    constructor(context: Context,attributeSet: AttributeSet,defSty: Int): super(context,attributeSet,defSty)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if(::extraBitmap.isInitialized) extraBitmap.recycle()

        extraBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)

        extraCanvas.drawColor(bitmapColor)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(extraBitmap,0f,0f,null)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX = event.x
        motionTouchEventY = event.y

        when(event.action){
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }

        return true
    }

    private fun touchStart(){
        path.reset()
        path.moveTo(motionTouchEventX,motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY

    }

    private fun touchMove(){
        val dx = Math.abs(motionTouchEventX-currentX)
        val dy = Math.abs(motionTouchEventY-currentY)
        //if(dx >= touchTolerance || dy >= touchTolerance){
            path.quadTo(currentX,currentY,(motionTouchEventX+currentX)/2, (motionTouchEventY+currentY)/2)
            currentX = motionTouchEventX
            currentY = motionTouchEventY

            extraCanvas.drawPath(path,paint)

        //}
        invalidate()
    }

    private fun touchUp(){
        path.reset()
    }

    fun changeDrawingColor(newColor:String){
        paint.color = Color.parseColor(newColor)
    }

    fun changePenSize(newPenSize: Float) {
        paint.strokeWidth = newPenSize
    }

    fun screenShot(name: String){
        var outputStream: OutputStream? = null //= FileOutputStream(imgFile)
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q){
            context?.contentResolver?.also { resolver->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME,name)
                    put(MediaStore.MediaColumns.MIME_TYPE,"image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues)
                outputStream = imageUri?.let { resolver.openOutputStream(it) }
            }
        }else{
            val imageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imageDir,name)
            outputStream = FileOutputStream(image)
        }

        outputStream?.use {
            extraBitmap.compress(Bitmap.CompressFormat.JPEG,100, it)
        }
    }

    fun clearCanvas(){
        path.reset()
    }
}