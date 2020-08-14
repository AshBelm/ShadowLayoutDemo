package com.mcmo.z.shadowlayoutdemo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import com.mcmo.z.shadowlayout.library.ShadowLayout
import java.lang.Exception
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min

class MainActivity : AppCompatActivity() {
    lateinit var sl: ShadowLayout
    lateinit var vColor: View
    lateinit var etColor: EditText
    lateinit var tvColorSymbol: TextView
    lateinit var sbAlpha: SeekBar
    lateinit var etAlpha: EditText
    lateinit var sbLength: SeekBar
    lateinit var etLength: EditText
    lateinit var sbTL: SeekBar
    lateinit var etTL: EditText
    lateinit var sbTR: SeekBar
    lateinit var etTR: EditText
    lateinit var sbBL: SeekBar
    lateinit var etBL: EditText
    lateinit var sbBR: SeekBar
    lateinit var etBR: EditText
    private val maxRoundRadius = 260
    private val maxLength = 60
    private val initColor = "FF0286FF"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sl = findViewById(R.id.sl)
        vColor = findViewById(R.id.v_color)
        etColor = findViewById(R.id.et_color)
        tvColorSymbol = findViewById(R.id.tv_color_symbol)
        sbAlpha = findViewById(R.id.sb_alpha)
        etAlpha = findViewById(R.id.et_alpha)
        sbLength = findViewById(R.id.sb_length)
        etLength = findViewById(R.id.et_length)
        sbTL = findViewById(R.id.sb_tl)
        sbTR = findViewById(R.id.sb_tr)
        sbBL = findViewById(R.id.sb_bl)
        sbBR = findViewById(R.id.sb_br)
        etLength = findViewById(R.id.et_length)
        etTL = findViewById(R.id.et_tl)
        etTR = findViewById(R.id.et_tr)
        etBL = findViewById(R.id.et_bl)
        etBR = findViewById(R.id.et_br)
        sbTL.max = maxRoundRadius
        sbTR.max = maxRoundRadius
        sbBL.max = maxRoundRadius
        sbBR.max = maxRoundRadius
        initColor()
        initAlpha()
        initLength()
        initCorner(etTL,sbTL,maxRoundRadius){sl.setRadius(tl = it.toFloat())}
        initCorner(etTR,sbTR,maxRoundRadius){sl.setRadius(tr = it.toFloat())}
        initCorner(etBL,sbBL,maxRoundRadius){sl.setRadius(bl = it.toFloat())}
        initCorner(etBR,sbBR,maxRoundRadius){sl.setRadius(br = it.toFloat())}

    }
    private inline fun initCorner(et:EditText, sb:SeekBar, maxRadius:Int, crossinline setCorner:(radius:Int)->Unit){
        et.setText("0")
        sb.max = maxRadius
        sb.progress = 0
        sb.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser){
                    et.setText(progress.toString())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
        et.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()){
                    sb.progress = 0
                    setCorner(0)
                }else{
                    val radius = s.toString().toIntOrNull()
                    radius?.run{
                        val r = max(0,this)
                        sb.progress = r
                        setCorner(r)
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    private fun initLength() {
        sbLength.max = maxLength
        sbLength.progress = 10
        sl.setShadowLength(10)
        etLength.setText("10")
        sbLength.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    etLength.setText(progress.toString())
    //                sl.setShadowLength(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
        etLength.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty()){
                    sbLength.progress = 0
                    sl.setShadowLength(0)
                }else{
                    val lengthInt = s.toString().toIntOrNull()
                    lengthInt?.apply {
                        val length = min(maxLength, max(this, 0))
                        sbLength.progress = length
                        sl.setShadowLength(length)
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    private fun initAlpha() {
        sbAlpha.max = 100
        sbAlpha.progress = 100
        etAlpha.setText(100.toString())
        sbAlpha.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    var alpha = ceil(progress * 2.55f).toInt()
                    changedAlpha(alpha)
                    etAlpha.setText(progress.toString())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
//        etAlpha.addTextChangedListener(object :TextWatcher{
//            override fun afterTextChanged(s: Editable?) {
//                if(s.isNullOrEmpty()){
//                    changedAlpha(0)
//
//                }else{
//
//                }
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//
//        })
    }

    private fun changedAlpha(alpha: Int) {
        var colorStr = etColor.text.toString()
        when (colorStr.length) {
            6 -> {
                colorStr = alpha.toString(16).appendZero(2) + colorStr
                etColor.setText(colorStr)
                //                            changeColor(colorStr)
            }
            8 -> {
                val ccc = alpha.toString(16).appendZero(2)
                colorStr = colorStr.replaceRange(0..1, ccc)
                etColor.setText(colorStr)
                //                            changeColor(colorStr)
            }
            else -> {
                //什么也不做
            }
        }
    }

    private fun initColor() {
        tvColorSymbol.setTextColor(0xFFFFFF)
        etColor.setText(initColor)
        changeColor(initColor)
        etColor.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val colorStr = s.toString()
                changeColor(colorStr)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    private fun changeColor(colorStr: String) {
        var color: Int? = null
        if (colorStr.length == 6 || colorStr.length == 8) {
            try {
                color = Color.parseColor("#$colorStr")
            } catch (e: Exception) {
            }
        }
        color?.also {
            vColor.setBackgroundColor(it)
            sl.setShadowColor(it)
        }
        tvColorSymbol.setTextColor(if (color == null) 0xFFFF00000.toInt() else 0xFF000000.toInt())
    }

    fun String.appendZero(num: Int): String {
        return if (length < num) {
            val count = num - length
            val sb = StringBuffer()
            for (i in 1..count) {
                sb.append("0")
            }
            sb.toString() + this
        } else {
            this
        }
    }
}