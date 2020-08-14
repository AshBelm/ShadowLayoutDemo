package com.mcmo.z.shadowlayout.library

import android.graphics.*
import android.view.View
import androidx.core.graphics.alpha
import kotlin.math.max

/**
 * 请关闭硬件加速
 * view级别关闭代码：view.setLayerType(View.LAYER_TYPE_SOFTWARE,null)
 */
class ShadowPainter {
    private val dstoutXfremode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
    private val paint = Paint()
    var shadowRadius: Float = 0f
    var topLeftRadius: Float = 0f
    var topRightRadius: Float = 0f
    var bottomLeftRadius: Float = 0f
    var bottomRightRadius: Float = 0f
    var shadowcolor: Int = Color.GRAY
    var shadowDx: Float = 0f
    var shadowDy: Float = 0f

    private val path = Path()
    private val boundRect = RectF()
    private val radiusRect = RectF()
    var forceClipChild = true

    init {
        paint.isAntiAlias = true
        paint.strokeWidth = 1.0f
        paint.style = Paint.Style.FILL
    }

    fun setBound(left: Float, top: Float, right: Float, bottom: Float) {
        boundRect.set(left, top, right, bottom)
    }

    fun drawShadow(canvas: Canvas?) {
        canvas?.run {
            canvas.save()
            generatePath()
            paint.color = shadowcolor
            paint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowcolor)
            paint.style = Paint.Style.FILL
            drawPath(path, paint)
            paint.color = Color.WHITE//这里需要一个不透明的颜色（DST_OUT:只在源图像和目标图像不相交的地方绘制【目标图像】，在相交的地方根据源图像的alpha进行过滤，源图像完全不透明则完全过滤，完全透明则不过滤）
            paint.xfermode = dstoutXfremode
            paint.style = Paint.Style.FILL
            paint.clearShadowLayer()
            drawPath(path, paint)
            paint.xfermode = null
            canvas.restore()
        }
    }

    private fun generatePath() {
        path.reset()
        path.addRoundRect(
            boundRect,
            topLeftRadius,
            topRightRadius,
            bottomLeftRadius,
            bottomRightRadius
        )
    }
}