package com.ankh.jomax.view

import android.content.Context
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.ankh.jomax.R

class ClippedView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, defStyle: Int = 0):
    View(context,attr,defStyle) {

        private val paint = Paint().apply{
            isAntiAlias = true
            strokeWidth = resources.getDimension(R.dimen.strokeWidth)
            textSize = resources.getDimension(R.dimen.textSize)
        }


    private val path = Path()
}