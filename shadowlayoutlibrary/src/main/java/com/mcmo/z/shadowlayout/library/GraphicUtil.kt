package com.mcmo.z.shadowlayout.library

import android.graphics.Path
import android.graphics.RectF

inline fun Path.addRoundRect(boundRect:RectF,topLeftRadius:Float,topRightRadius:Float,bottomLeftRadius:Float,bottomRightRadius:Float){
    addRoundRect(boundRect, floatArrayOf(topLeftRadius,topLeftRadius,topRightRadius,topRightRadius,bottomRightRadius,bottomRightRadius,bottomLeftRadius,bottomLeftRadius),Path.Direction.CW)
}