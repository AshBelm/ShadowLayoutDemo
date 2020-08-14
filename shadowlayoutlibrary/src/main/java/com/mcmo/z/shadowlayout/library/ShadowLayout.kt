package com.mcmo.z.shadowlayout.library

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout

class ShadowLayout(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    FrameLayout(context, attrs, defStyleAttr) {
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null, 0)

    private val painter = ShadowPainter()

    init {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)// TODO: 2020/8/13 如何检测这个硬件加速影响的手机型号和系统版本？
        val ta = context.obtainStyledAttributes(attrs, R.styleable.ShadowLayout)
        painter.shadowRadius = ta.getDimension(R.styleable.ShadowLayout_sl_shadow_length, 0f)
        painter.topLeftRadius = ta.getDimension(R.styleable.ShadowLayout_sl_topLeft_radius, 0f)
        painter.topRightRadius = ta.getDimension(R.styleable.ShadowLayout_sl_topRight_radius, 0f)
        painter.bottomLeftRadius = ta.getDimension(R.styleable.ShadowLayout_sl_bottomLeft_radius, 0f)
        painter.bottomRightRadius = ta.getDimension(R.styleable.ShadowLayout_sl_bottomRight_radius, 0f)
        painter.shadowcolor = ta.getColor(R.styleable.ShadowLayout_sl_shadow_color, Color.GRAY)
        painter.shadowDx = ta.getDimension(R.styleable.ShadowLayout_sl_shadow_dx, 0f)
        painter.shadowDy = ta.getDimension(R.styleable.ShadowLayout_sl_shadow_dy, 0f)
        ta.recycle()
    }


    override fun dispatchDraw(canvas: Canvas?) {
        painter.setBound(
            paddingLeft.toFloat(),
            paddingTop.toFloat(),
            width - paddingRight.toFloat(),
            height - paddingBottom.toFloat()
        )
        painter.drawShadow(canvas)
        super.dispatchDraw(canvas)
    }

    fun setShadowColor(color: Int) {
        painter.shadowcolor = color
        invalidate()
    }

    fun setShadowLength(length: Int) {
        painter.shadowRadius = length.toFloat()
        invalidate()
    }

    fun setRadius(tl: Float? = null, tr: Float? = null, bl: Float? = null, br: Float? = null) {
        tl?.also { painter.topLeftRadius = it }
        tr?.also { painter.topRightRadius = it }
        bl?.also { painter.bottomLeftRadius = it }
        br?.also { painter.bottomRightRadius = it }
        invalidate()
    }
}

