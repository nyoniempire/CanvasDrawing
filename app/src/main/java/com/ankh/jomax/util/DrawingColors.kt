package com.ankh.jomax.util

import com.ankh.jomax.data.PenColors

object DrawingColors {
    fun getColors(): ArrayList<PenColors>{
        return arrayListOf(
            PenColors("White","#FFFFFF"),
            PenColors("Red","#FF0000"),
            PenColors("Green","#00FF00"),
            PenColors("Black","#FF000000"),
            PenColors("Cyan","#CCFFFF"),
            PenColors("Yellow","#FFFFCC"),
            PenColors("Lavender","#CCCCFF"),
            PenColors("Rose","#FFCCCC"),
            PenColors("PaleTurquoise","#99FFFF"),
            PenColors("Gainsboro","#CCCCCC"),
            PenColors("NavajoWhite","#FFCC99"),
            PenColors("PaleGoldenRod","#FFFF99"),
            PenColors("Aquamarine","#66FFCC"),
            PenColors("LightGrey","#CCCCCC"),
            PenColors("LightBlue","#99CCFF"),
            PenColors("PaleGreen","#99FF99"),
            PenColors("LightSteelBlue","#99CCCC"),
            PenColors("Aqua","#00FFFF")
        )
    }
}