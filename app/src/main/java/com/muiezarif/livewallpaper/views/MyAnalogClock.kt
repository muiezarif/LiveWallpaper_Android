package com.muiezarif.livewallpaper.views

import android.content.Context
import android.graphics.*
import android.util.TypedValue
import android.view.View
import com.muiezarif.livewallpaper.R
import com.muiezarif.livewallpaper.utils.ApplicationConstants
import com.muiezarif.livewallpaper.utils.ApplicationSharedPreference
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.cos
import kotlin.math.sin


class MyAnalogClock(context: Context?) : View(context) {
    private var cal: Calendar = Calendar.getInstance()
    private var sp: ApplicationSharedPreference = ApplicationSharedPreference.getInstance(context)
    private lateinit var paint: Paint
    private lateinit var paintClock: Paint
    private var colors: Int = 0
    private val numbers = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    private val romanNumbers = arrayOf("I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII")
    private var xx: Float = 0.0f
    private var yy: Float = 0.0f
    private var radius = 0
    private var sizeScaled = -1
    private val rect = Rect()
    private var fontSize = 0
    private lateinit var day: Date
    private var positionOfClock = 1
    private var clockSize = 9
    private var bgColor = 0
    private var textColor = 0
    private var clockColor = 0
    private var clockSecArrowColor = 0
    private var clockHourArrowColor = 0
    private var clockMinArrowColor = 0
    private var hour = 0f
    private var min = 0f
    private var sec = 0f
    private var statusBarHeight=0



    fun config(xx: Float, yy: Float, size: Int, date: Date, paint: Paint?, colors: String) {

        var resourceId=resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }
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
            this.paint = paint
        }
        if (sp.readBool(ApplicationConstants.ANALOG_CLOCK_SHADOW_EFFECT, true)) {
            this.paint.setShadowLayer(3f, 0f, 0f, Color.BLACK)
            setLayerType(LAYER_TYPE_SOFTWARE, this.paint)
        }else{
            this.paint.setShadowLayer(0f, 0f, 0f, Color.BLACK)
            setLayerType(LAYER_TYPE_SOFTWARE, this.paint)
        }
        if (!sp.readBool(ApplicationConstants.ANALOG_CLOCK_BACKGROUND1, true)) {
            bgColor = Color.parseColor(sp.readString(ApplicationConstants.ANALOG_CLOCK_BACKGROUND))
        } else {
            bgColor = Color.parseColor(context.getString(R.string.string_color_white))
        }
        if (!sp.readBool(ApplicationConstants.ANALOG_CLOCK_TEXT_COLOR1, true)) {
            textColor = Color.parseColor(sp.readString(ApplicationConstants.ANALOG_CLOCK_TEXT_COLOR))
        } else {
            textColor = Color.parseColor(context.getString(R.string.string_color_textArrow))
        }
        if (!sp.readBool(ApplicationConstants.ANALOG_CLOCK_COLOR1, true)) {
            clockColor = Color.parseColor(sp.readString(ApplicationConstants.ANALOG_CLOCK_COLOR))
        } else {
            clockColor = Color.parseColor(context.getString(R.string.string_color_clockColor))
        }
        if (!sp.readBool(ApplicationConstants.ANALOG_CLOCK_SEC_ARROW_COLOR1, true)) {
            clockSecArrowColor =
                Color.parseColor(sp.readString(ApplicationConstants.ANALOG_CLOCK_SEC_ARROW_COLOR))
        } else {
            clockSecArrowColor = Color.parseColor(context.getString(R.string.string_color_secArrow))
        }
        if (!sp.readBool(ApplicationConstants.ANALOG_CLOCK_MIN_ARROW_COLOR1, true)) {
            clockMinArrowColor =
                Color.parseColor(sp.readString(ApplicationConstants.ANALOG_CLOCK_MIN_ARROW_COLOR))
        } else {
            clockMinArrowColor = Color.parseColor(context.getString(R.string.string_color_minArrow))
        }
        if (!sp.readBool(ApplicationConstants.ANALOG_CLOCK_HOUR_ARROW_COLOR1, true)) {
            clockHourArrowColor =
                Color.parseColor(sp.readString(ApplicationConstants.ANALOG_CLOCK_HOUR_ARROW_COLOR))
        } else {
            clockHourArrowColor = Color.parseColor(context.getString(R.string.string_color_hourArrow))
        }

        if (size != sizeScaled) {
            if (!sp.readBool(ApplicationConstants.ANALOG_CLOCK_SIZE_OF_CLOCK1, true)) {
                clockSize = sp.readInt(ApplicationConstants.ANALOG_CLOCK_SIZE_OF_CLOCK, -1)
                if (clockSize==10){
                    clockSize--
                }
                radius = ((size * (((clockSize + 1) / 11.0f))) / 2).toInt()
            } else {
                radius = ((size * (((clockSize + 1) / 11.0f))) / 2).toInt()
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(bgColor)
        paint.color = colors
        sec = cal[Calendar.SECOND].toFloat()
        min = cal[Calendar.MINUTE].toFloat()
        hour = cal[Calendar.HOUR_OF_DAY].toFloat()
        if (!sp.readBool(ApplicationConstants.ANALOG_CLOCK_POSITION1, true)) {
            positionOfClock = sp.readInt(ApplicationConstants.ANALOG_CLOCK_POSITION, -1)
            if (positionOfClock == 0) {
                yy =xx+statusBarHeight
                drawShape(canvas)
            } else if (positionOfClock == 1) {
                drawShape(canvas)
            } else if (positionOfClock == 2) {
                yy =(yy*2)-(xx)
                drawShape(canvas)
            }
        } else {
            drawShape(canvas)
        }
        drawMinHand(canvas)
        drawNumbers(canvas)
        if (sp.readBool(ApplicationConstants.ANALOG_CLOCK_SHOW_DIGITAL_CLOCK, true)) {
            drawDigitalClock(canvas)
        }
        drawMonthDate(canvas)
        if (sp.readBool(ApplicationConstants.ANALOG_CLOCK_SHOW_DAY, true)) {
            drawDayName(canvas)
        }
        drawHourHand(canvas)
        if (sp.readBool(ApplicationConstants.ANALOG_CLOCK_SHOW_SEC_ARROW, true)) {
            drawSecHand(canvas)
        }
    }

    private fun drawSecHand(canvas: Canvas) {
        paint.color = clockSecArrowColor
        paint.strokeWidth = 9.0f
        canvas.drawLine(xx, yy, (xx + radius * 0.7f * cos(Math.toRadians(sec / 60.0f * 360.0f - 90f.toDouble()))).toFloat(), (yy + radius * 0.7f * sin(Math.toRadians(sec / 60.0f * 360.0f - 90f.toDouble()))).toFloat(), paint)
        canvas.drawCircle(this.xx, this.yy, ((this.radius / 50).toFloat()), this.paint)
        paint.color = Color.parseColor(context.getString(R.string.string_color_black))
        canvas.drawCircle(this.xx, this.yy, ((this.radius / 90).toFloat()), this.paint)
    }

    private fun drawMinHand(canvas: Canvas) {
        paint.color = clockMinArrowColor
        paint.strokeWidth = 15.0f
        canvas.drawLine(xx, yy, (xx + radius * 0.6f * cos(Math.toRadians(min / 60.0f * 360.0f - 90f.toDouble()))).toFloat(), (yy + radius * 0.6f * sin(Math.toRadians(min / 60.0f * 360.0f - 90f.toDouble()))).toFloat(), paint)
        canvas.drawCircle(this.xx, this.yy, ((this.radius / 40).toFloat()), this.paint)
        canvas.save()
    }

    private fun drawHourHand(canvas: Canvas) {
        paint.color = clockHourArrowColor
        paint.style=Paint.Style.STROKE
        paint.strokeWidth = 20.0f
        canvas.drawLine(xx, yy, (xx + (radius * 0.5f) * cos(Math.toRadians((hour / 12.0f * 360.0f - 90f).toDouble()))).toFloat(), (yy + (radius * 0.5f) * sin(Math.toRadians((hour / 12.0f * 360.0f - 90f).toDouble()))).toFloat(), paint)
        canvas.drawCircle(this.xx, this.yy, ((this.radius / 30).toFloat()), this.paint)
        canvas.save()
    }

    private fun drawShape(canvas: Canvas) {
        paint.color = clockColor
        paint.style=Paint.Style.FILL_AND_STROKE
        if (!sp.readBool(ApplicationConstants.ANALOG_CLOCK_SHAPE1, true)) {
            val shapeCheck = sp.readInt(ApplicationConstants.ANALOG_CLOCK_SHAPE, -1)
            if (shapeCheck == 1) {
                canvas.drawRoundRect(RectF(xx - this.radius, yy - this.radius, xx + this.radius, yy + this.radius), (this.radius / 5).toFloat(), (this.radius / 5).toFloat(), this.paint)
            } else if (shapeCheck == 0) {
                canvas.drawCircle(xx, yy, ((radius).toFloat()), this.paint)
            }

        } else {
            canvas.drawCircle(xx, yy, ((radius).toFloat()), this.paint)
        }
    }

    private fun drawNumbers(canvas: Canvas) {
        checkFonts()
        paint.textSize = (radius / 8).toFloat()
        if (!sp.readBool(ApplicationConstants.ANALOG_CLOCK_NUMBER_TYPE1, true)) {
            val numType = sp.readInt(ApplicationConstants.ANALOG_CLOCK_NUMBER_TYPE, -1)
            if (numType == 0) {
                for (number in numbers) {
                    val tmp = number.toString()
                    paint.getTextBounds(tmp, 0, tmp.length, rect)
                    val angle = Math.PI / 6 * (number - 3)
                    val x = (xx + cos(angle) * (radius * 0.9) - rect.width() / 2)
                    val y = (yy + sin(angle) * (radius * 0.9) + rect.height() / 2)
                    canvas.drawText(tmp, x.toFloat(), y.toFloat(), paint)
                }
            } else if (numType == 1) {
                var i = 1
                for (number in romanNumbers) {
                    paint.getTextBounds(number, 0, number.length, rect)
                    val angle = Math.PI / 6 * (i - 3)
                    val x = (xx + cos(angle) * (radius * 0.9) - rect.width() / 2)
                    val y = (yy + sin(angle) * (radius * 0.9) + rect.height() / 2)
                    canvas.drawText(number, x.toFloat(), y.toFloat(), paint)
                    i++
                }
            }
        } else {
            for (number in numbers) {
                val tmp = number.toString()
                paint.getTextBounds(tmp, 0, tmp.length, rect)
                val angle = Math.PI / 6 * (number - 3)
                val x = (xx + cos(angle) * (radius * 0.9) - rect.width() / 2)
                val y = (yy + sin(angle) * (radius * 0.9) + rect.height() / 2)
                canvas.drawText(tmp, x.toFloat(), y.toFloat(), paint)
            }
        }
        this.paint.maskFilter = null
    }

    private fun drawDigitalClock(canvas: Canvas) {
        checkFonts()
        paint.textSize = (radius / 10).toFloat()
        val angle = Math.PI / 6 * (9)
        val x = (xx + cos(angle) * (radius * 0.7) - rect.width() / 2)
        val y = (yy + sin(angle) * (radius * 0.7) + rect.height() / 2)
        if (sp.readBool(ApplicationConstants.ANALOG_CLOCK_24_HOUR_FORMAT, true)) {
            canvas.drawText(SimpleDateFormat("HH:mm", Locale.ENGLISH).format(day.time), x.toFloat(), y.toFloat(), paint)
        } else {
            canvas.drawText(SimpleDateFormat("h:mm",Locale.ENGLISH).format(day.time), x.toFloat(), y.toFloat(), paint)
        }
    }

    private fun drawMonthDate(canvas: Canvas) {
        checkFonts()
        paint.textSize = (radius / 10).toFloat()
        val angle1 = Math.PI / 6 * (0)
        val x1 = ((xx-50) + cos(angle1) * (radius * 0.6) - rect.width() / 2)
        val y1 = (yy + sin(angle1) * (radius * 0.6) + rect.height() / 2)
        if (sp.readBool(ApplicationConstants.ANALOG_CLOCK_SHOW_DATE, true) && sp.readBool(ApplicationConstants.ANALOG_CLOCK_SHOW_MONTH, true)) {
            canvas.drawText(SimpleDateFormat("MMM dd",Locale.ENGLISH).format(day.time), x1.toFloat(), y1.toFloat(), paint)
        } else if (sp.readBool(ApplicationConstants.ANALOG_CLOCK_SHOW_DATE, true)) {
            canvas.drawText(SimpleDateFormat("dd",Locale.ENGLISH).format(day.time), x1.toFloat(), y1.toFloat(), paint)
        } else if (sp.readBool(ApplicationConstants.ANALOG_CLOCK_SHOW_MONTH, true)) {
            canvas.drawText(SimpleDateFormat("MMM",Locale.ENGLISH).format(day.time), x1.toFloat(), y1.toFloat(), paint)
        }
    }

    private fun drawDayName(canvas: Canvas) {
        checkFonts()
        paint.textSize = (radius / 10).toFloat()
        val angle2 = Math.PI / 6 * (3)
        val x2 = (xx + cos(angle2) * (radius * 0.7) - rect.width() / 2)
        val y2 = (yy + sin(angle2) * (radius * 0.7) + rect.height() / 2)
        canvas.drawText(SimpleDateFormat("EE", Locale.ENGLISH).format(day.time), x2.toFloat(), y2.toFloat(), paint)
    }

    private fun checkFonts() {
        paint.color = textColor
        paint.style=Paint.Style.FILL_AND_STROKE
        if (!sp.readBool(ApplicationConstants.ANALOG_CLOCK_FONT1, true)) {
            when (sp.readInt(ApplicationConstants.ANALOG_CLOCK_FONT,-1)) {
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