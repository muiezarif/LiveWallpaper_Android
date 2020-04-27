package com.muiezarif.livewallpaper.views

import android.content.Context
import android.graphics.*
import android.widget.TextView
import java.util.*
import android.util.TypedValue
import android.view.View
import com.muiezarif.livewallpaper.R
import com.muiezarif.livewallpaper.utils.ApplicationConstants
import com.muiezarif.livewallpaper.utils.ApplicationSharedPreference
import java.text.SimpleDateFormat
import kotlin.math.cos
import kotlin.math.sin

class MyDigitalClock(context: Context?) : TextView(context) {
    private var cal: Calendar = Calendar.getInstance()
    private var sp: ApplicationSharedPreference = ApplicationSharedPreference.getInstance(context)
    private lateinit var paint: Paint
    private var bgColor = 0
    private var textColorFont = 0
    private var xx: Float = 0.0f
    private var yy: Float = 0.0f
    private var colors: Int = 0
    private var clockSize = 9
    private lateinit var day: Date
    private var fontSize = 0
    private var radius = 0
    private var sizeScaled = -1
    private val rect = Rect()


    fun config(xx: Float, yy: Float, size: Int, date: Date, paint: Paint?, colors: String) {
        this.xx = xx
        this.yy = yy
        this.colors = Color.parseColor(colors)
        day = cal.time
        cal.time = date
        fontSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13f, resources.displayMetrics).toInt()
        if (paint != null) {
            paint.isAntiAlias = true
            paint.style = Paint.Style.FILL_AND_STROKE
            paint.strokeWidth = 5.0f
            paint.strokeCap = Paint.Cap.ROUND
            paint.strokeJoin = Paint.Join.ROUND
            paint.color=Color.parseColor(context.getString(R.string.string_color_black))
            this.paint = paint
        }

        if (!sp.readBool(ApplicationConstants.DIGITAL_COLOR_BACKGROUND1, true)) {
            bgColor = Color.parseColor(sp.readString(ApplicationConstants.DIGITAL_COLOR_BACKGROUND))
        } else {
            bgColor = Color.parseColor(context.getString(R.string.string_color_white))
        }
        if (!sp.readBool(ApplicationConstants.DIGITAL_CLOCK_COLOR1, true)) {
            textColorFont = Color.parseColor(sp.readString(ApplicationConstants.DIGITAL_CLOCK_COLOR))
        } else {
            textColorFont = Color.parseColor(context.getString(R.string.string_color_black))
        }
        if (sp.readBool(ApplicationConstants.DIGITAL_CLOCK_SHOW_GLOW,true)){
            this.paint.setShadowLayer(8f, 0f, 0f, textColorFont)
            setLayerType(View.LAYER_TYPE_SOFTWARE, this.paint)
        }else{
            this.paint.setShadowLayer(0f, 0f, 0f, textColorFont)
            setLayerType(View.LAYER_TYPE_SOFTWARE, this.paint)
        }
        if (!sp.readBool(ApplicationConstants.DIGITAL_CLOCK_POSITION_HORIZONTAL1, true)) {
            if (sp.readInt(ApplicationConstants.DIGITAL_CLOCK_POSITION_HORIZONTAL, -1) > 100) {
                var bb = ((sp.readInt(
                    ApplicationConstants.DIGITAL_CLOCK_POSITION_HORIZONTAL,
                    -1
                ))).toFloat()
                bb /= 100
                when (sp.readInt(ApplicationConstants.DIGITAL_CLOCK_SIZE, -1)) {
                    0 -> {
                        if (bb < 1.83) {
                            this.xx *= bb
                        } else {
                            this.xx *= 1.82f
                        }
                    }
                    1 -> {
                        if (bb < 1.78) {
                            this.xx *= bb
                        } else {
                            this.xx *= 1.77f
                        }
                    }
                    2 -> {
                        if (bb < 1.73) {
                            this.xx *= bb
                        } else {
                            this.xx *= 1.72f
                        }
                    }
                    3 -> {
                        if (bb < 1.68) {
                            this.xx *= bb
                        } else {
                            this.xx *= 1.67f
                        }
                    }
                    4 -> {
                        if (bb < 1.63) {
                            this.xx *= bb
                        } else {
                            this.xx *= 1.62f
                        }
                    }
                    5 -> {
                        if (bb < 1.58) {
                            this.xx *= bb
                        } else {
                            this.xx *= 1.57f
                        }
                    }
                    6 -> {
                        if (bb < 1.53) {
                            this.xx *= bb
                        } else {
                            this.xx *= 1.52f
                        }
                    }
                    7 -> {
                        if (bb < 1.48) {
                            this.xx *= bb
                        } else {
                            this.xx *= 1.47f
                        }
                    }
                    8 -> {
                        if (bb < 1.43) {
                            this.xx *= bb
                        } else {
                            this.xx *= 1.42f
                        }
                    }
                    9 -> {
                        if (bb < 1.38) {
                            this.xx *= bb
                        } else {
                            this.xx *= 1.37f
                        }
                    }
                    10 -> {
                        if (bb < 1.33) {
                            this.xx *= bb
                        } else {
                            this.xx *= 1.32f
                        }
                    }
                    else->{
                        if (bb < 1.38) {
                            this.xx *= bb
                        } else {
                            this.xx *= 1.37f
                        }
                    }
                }
            } else if (sp.readInt(
                    ApplicationConstants.DIGITAL_CLOCK_POSITION_HORIZONTAL,
                    -1
                ) < 100
            ) {
                var cc = (sp.readInt(
                    ApplicationConstants.DIGITAL_CLOCK_POSITION_HORIZONTAL,
                    -1
                )).toFloat()
                cc /= 100
                when (sp.readInt(ApplicationConstants.DIGITAL_CLOCK_SIZE, -1)) {
                    0 -> {
                        if (cc > 0.11) {
                            this.xx *= cc
                        } else {
                            this.xx *= 0.1f
                        }
                    }
                    1 -> {
                        if (cc > 0.15) {
                            this.xx *= cc
                        } else {
                            this.xx *= 0.12f
                        }
                    }
                    2 -> {
                        if (cc > 0.19) {
                            this.xx *= cc
                        } else {
                            this.xx *= 0.15f
                        }
                    }
                    3 -> {
                        if (cc > 0.21) {
                            this.xx *= cc
                        } else {
                            this.xx *= 0.19f
                        }
                    }
                    4 -> {
                        if (cc > 0.26) {
                            this.xx *= cc
                        } else {
                            this.xx *= 0.22f
                        }
                    }
                    5 -> {
                        if (cc > 0.27) {
                            this.xx *= cc
                        } else {
                            this.xx *= 0.25f
                        }
                    }
                    6 -> {
                        if (cc > 0.30) {
                            this.xx *= cc
                        } else {
                            this.xx *= 0.3f
                        }
                    }
                    7 -> {
                        if (cc > 0.31) {
                            this.xx *= cc
                        } else {
                            this.xx *= 0.35f
                        }
                    }
                    8 -> {
                        if (cc > 0.35) {
                            this.xx *= cc
                        } else {
                            this.xx *= 0.4f
                        }
                    }
                    9 -> {
                        if (cc > 0.38) {
                            this.xx *= cc
                        } else {
                            this.xx *= 0.45f
                        }
                    }
                    10 -> {
                        if (cc > 0.4) {
                            this.xx *= cc
                        } else {
                            this.xx *= 0.5f
                        }
                    }
                    else->{
                        if (cc > 0.38) {
                            this.xx *= cc
                        } else {
                            this.xx *= 0.45f
                        }
                    }

                }
            }
        }
        if(!sp.readBool(ApplicationConstants.DIGITAL_CLOCK_POSITION_VERTICAL1,true)){
            if(sp.readInt(ApplicationConstants.DIGITAL_CLOCK_POSITION_VERTICAL,-1)>100) {
                var  bb=((sp.readInt(ApplicationConstants.DIGITAL_CLOCK_POSITION_VERTICAL, -1))).toFloat()
                bb /= 100
                this.yy*=bb
            }else if(sp.readInt(ApplicationConstants.DIGITAL_CLOCK_POSITION_VERTICAL,-1)<100){
                var cc=(sp.readInt(ApplicationConstants.DIGITAL_CLOCK_POSITION_VERTICAL, -1)).toFloat()
                cc/=100
                if (cc>0.35) {
                    this.yy *= cc
                }else{
                    this.yy*=0.36.toFloat()
                }
            }
        }


        if (size != sizeScaled) {
            if (!sp.readBool(ApplicationConstants.DIGITAL_CLOCK_SIZE1, true)) {
                clockSize = sp.readInt(ApplicationConstants.DIGITAL_CLOCK_SIZE, -1)
                radius = ((size * (((clockSize + 1) / 11.0f))) / 2).toInt()
            } else {
                radius = ((size * (((clockSize + 1) / 11.0f))) / 2).toInt()
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(bgColor)
        drawTime(canvas)
        drawSeconds(canvas)
        drawAmPm(canvas)
        drawDayName(canvas)
        drawDateFormat(canvas)
    }

    private fun drawTime(canvas: Canvas?){
        checkFonts()
        paint.textSize = (radius/2).toFloat()
        paint.textAlign= Paint.Align.CENTER
        if (sp.readBool(ApplicationConstants.DIGITAL_CLOCK_24_HOUR_FORMAT, true)) {
            canvas?.drawText(SimpleDateFormat("HH:mm", Locale.ENGLISH).format(day.time), xx/2, yy/2, paint)
        } else {
            canvas?.drawText(SimpleDateFormat("h:mm", Locale.ENGLISH).format(day.time), xx/2, yy/2, paint)
        }
    }

    private fun drawSeconds(canvas: Canvas?){
        if (sp.readBool(ApplicationConstants.DIGITAL_CLOCK_SHOW_SECONDS,true)) {
            checkFonts()
            paint.textSize = (radius / 10).toFloat()
            val angle1 = Math.PI / 6 * (0)
            val x1 = ((xx / 2) + cos(angle1) * (radius * 0.63) - rect.width() / 2)
            val y1 = ((yy / 2) + sin(angle1) * (radius * 0.63) + rect.height() / 2)
            canvas?.drawText(
                SimpleDateFormat("ss", Locale.ENGLISH).format(day.time),
                x1.toFloat(),
                y1.toFloat(),
                paint
            )
        }
    }
    private fun drawAmPm(canvas: Canvas?){
        if (!sp.readBool(ApplicationConstants.DIGITAL_CLOCK_24_HOUR_FORMAT, true)) {
            checkFonts()
            paint.textSize = (radius / 10).toFloat()
            val angle2 = Math.PI / 6 * (-0.8)
            val x2 = ((xx / 2) + cos(angle2) * (radius * 0.68) - rect.width() / 2)
            val y2 = ((yy / 2) + sin(angle2) * (radius * 0.68) + rect.height() / 2)
            canvas?.drawText(
                SimpleDateFormat("a", Locale.ENGLISH).format(day.time),
                x2.toFloat(),
                y2.toFloat(),
                paint
            )
        }
    }
    private fun drawDayName(canvas: Canvas?){
        if (sp.readBool(ApplicationConstants.DIGITAL_CLOCK_DAY_OF_WEEK,true)) {
            checkFonts()
            paint.textSize = (radius / 9).toFloat()
            val angle3 = Math.PI / 6 * (9)
            val x3 = ((xx / 2) + cos(angle3) * (radius * 0.5) - rect.width() / 2)
            val y3 = ((yy / 2) + sin(angle3) * (radius * 0.5) + rect.height() / 2)
            canvas?.drawText(
                SimpleDateFormat("EEEE", Locale.ENGLISH).format(day.time),
                x3.toFloat(),
                y3.toFloat(),
                paint
            )
        }
    }
    private fun drawDateFormat(canvas: Canvas?){
        if (sp.readBool(ApplicationConstants.DIGITAL_CLOCK_SHOW_DATE,true)) {
            checkFonts()
            paint.textSize = (radius / 9).toFloat()
            val angle4 = Math.PI / 6 * (3)
            val x4 = ((xx / 2) + cos(angle4) * (radius * 0.2) - rect.width() / 2)
            val y4 = ((yy / 2) + sin(angle4) * (radius * 0.2) + rect.height() / 2)
            if (!sp.readBool(ApplicationConstants.DIGITAL_CLOCK_DISPLAY_DATE_FORMAT1, true)) {
                canvas?.drawText(
                    SimpleDateFormat(
                        sp.readString(ApplicationConstants.DIGITAL_CLOCK_DISPLAY_DATE_FORMAT),
                        Locale.ENGLISH
                    ).format(day.time), x4.toFloat(), y4.toFloat(), paint
                )
            } else {
                canvas?.drawText(
                    SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH).format(day.time),
                    x4.toFloat(),
                    y4.toFloat(),
                    paint
                )
            }
        }


    }

    private fun checkFonts() {
        paint.color = textColorFont
        if (!sp.readBool(ApplicationConstants.DIGITAL_CLOCK_STYLE1, true)) {
            when (sp.readInt(ApplicationConstants.DIGITAL_CLOCK_STYLE,-1)) {
                1 -> {
                    val plain = Typeface.createFromAsset(context.assets, context.getString(R.string.custom_font_1))
                    paint.typeface = plain
                }
                2 -> {
                    val plain = Typeface.createFromAsset(context.assets, context.getString(R.string.custom_font_2))
                    paint.typeface = plain
                }
                3 -> {
                    val plain = Typeface.createFromAsset(context.assets, context.getString(R.string.custom_font_3))
                    paint.typeface = plain
                }
                4 -> {
                    val plain = Typeface.createFromAsset(context.assets, context.getString(R.string.custom_font_4))
                    paint.typeface = plain
                }
                5 -> {
                    val plain = Typeface.createFromAsset(context.assets, context.getString(R.string.custom_font_5))
                    paint.typeface = plain
                }
                6 -> {
                    val plain = Typeface.createFromAsset(context.assets, context.getString(R.string.custom_font_6))
                    paint.typeface = plain
                }
                7 -> {
                    val plain = Typeface.createFromAsset(context.assets, context.getString(R.string.custom_font_7))
                    paint.typeface = plain
                }
                8 -> {
                    val plain = Typeface.createFromAsset(context.assets, context.getString(R.string.custom_font_8))
                    paint.typeface = plain
                }
                9 -> {
                    val plain = Typeface.createFromAsset(context.assets, context.getString(R.string.custom_font_9))
                    paint.typeface = plain
                }
                10 -> {
                    val plain = Typeface.createFromAsset(context.assets, context.getString(R.string.custom_font_10))
                    paint.typeface = plain
                }
                11->{
                    val plain = Typeface.createFromAsset(context.assets, context.getString(R.string.custom_font_11))
                    paint.typeface = plain
                }
            }
        } else {
            val plain = Typeface.createFromAsset(context.assets, context.getString(R.string.custom_font_1))
            paint.typeface = plain
        }
        paint.strokeWidth = 2f
    }



}