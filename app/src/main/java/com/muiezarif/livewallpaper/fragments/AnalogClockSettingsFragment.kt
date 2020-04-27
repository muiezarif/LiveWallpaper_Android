package com.muiezarif.livewallpaper.fragments

import android.app.Dialog
import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.muiezarif.livewallpaper.R
import com.muiezarif.livewallpaper.databinding.FontSelectDialogBinding
import com.muiezarif.livewallpaper.databinding.FragmentAnalogClockSettingsBinding
import com.muiezarif.livewallpaper.dialogs.ColorSelectDialog
import com.muiezarif.livewallpaper.helpers.AdHelper
import com.muiezarif.livewallpaper.listeners.ColorSelectDialogListener
import com.muiezarif.livewallpaper.services.AnalogClockWallpaperService
import com.muiezarif.livewallpaper.utils.ApplicationConstants
import com.muiezarif.livewallpaper.utils.ApplicationSharedPreference


/**
 * A simple [Fragment] subclass.
 */
class AnalogClockSettingsFragment : Fragment(), ColorSelectDialogListener {
    lateinit var dialog: ColorSelectDialog
    lateinit var sp: ApplicationSharedPreference
    lateinit var binding: FragmentAnalogClockSettingsBinding
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.fragment_analog_clock_settings,container,false)
        sp = ApplicationSharedPreference.getInstance(context)
        checkBoxChecks()
        inIt()
        return binding.root
    }
    private fun inIt() {
        setLiveWallpaperClick()
        changeBackgroundColor()
        changeTextColor()
        changeClockColor()
        changeClockSecArrowColor()
        changeClockMinArrowColor()
        changeClockHourArrowColor()
        hourFormat24()
        clockShadowEffect()
        clockShowSecArrow()
        clockShowDate()
        clockShowDay()
        clockShowMonth()
        clockShowDigitalClock()
        sizeOfClock()
        fontOfClock()
        positionOfClock()
        shapeOfClock()
        numberTypeOfClock()
        AdHelper.loadBannerAd(binding.adView)

    }
    private fun sizeOfClock() {
        if (!sp.readBool(ApplicationConstants.ANALOG_CLOCK_SIZE_OF_CLOCK1, true)) {
            ApplicationConstants.analogClockSizeCheck = sp.readInt(ApplicationConstants.ANALOG_CLOCK_SIZE_OF_CLOCK, -1)
        }
        binding.sbAnalogSize.max=10
        binding.sbAnalogSize.keyProgressIncrement=1
        binding.sbAnalogSize.progress= ApplicationConstants.analogClockSizeCheck
        binding.sbAnalogSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_SIZE_OF_CLOCK, progress)
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_SIZE_OF_CLOCK1, false)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }
    private fun fontOfClock() {
        binding.rlClockFont.setOnClickListener {
            val dialogBox = Dialog(context as Context)
            val dialog: FontSelectDialogBinding =DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.font_select_dialog,null,false)
            dialogBox.setContentView(dialog.root)
            if (!sp.readBool(ApplicationConstants.ANALOG_CLOCK_FONT1, true)) {
                ApplicationConstants.strAnalog = sp.readInt(ApplicationConstants.ANALOG_CLOCK_FONT,-1)
            }
            dialog.font0.typeface = Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_1))
            dialog.font1.typeface = Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_2))
            dialog.font2.typeface = Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_3))
            dialog.font3.typeface = Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_4))
            dialog.font4.typeface = Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_5))
            dialog.font5.typeface = Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_6))
            dialog.font6.typeface = Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_7))
            dialog.font7.typeface = Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_8))
            dialog.font8.typeface = Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_9))
            dialog.font9.typeface = Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_10))
            dialog.font10.typeface = Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_11))

            when (ApplicationConstants.strAnalog) {
                1 -> { dialog.font0.isChecked = true }
                2 -> { dialog.font1.isChecked = true }
                3 -> { dialog.font2.isChecked = true }
                4 -> { dialog.font3.isChecked = true }
                5 -> { dialog.font4.isChecked = true }
                6 -> { dialog.font5.isChecked = true }
                7 -> { dialog.font6.isChecked = true }
                8 -> { dialog.font7.isChecked = true }
                9 -> { dialog.font8.isChecked = true }
                10 -> { dialog.font9.isChecked = true }
                11-> { dialog.font10.isChecked = true }
            }

            dialog.radioGroupAnalogFont.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.font0 -> {
                        sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_FONT, 1)
                        sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_FONT1, false)
                        binding.tvAnalogClockFontCheck.typeface= Typeface.createFromAsset(context?.applicationContext?.assets, "customFont1.ttf")
                        dialogBox.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.font1 -> {
                        sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_FONT, 2)
                        sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_FONT1, false)
                        binding.tvAnalogClockFontCheck.typeface= Typeface.createFromAsset(context?.applicationContext?.assets, "customFont2.ttf")
                        dialogBox.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.font2 -> {
                        sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_FONT, 3)
                        sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_FONT1, false)
                        binding.tvAnalogClockFontCheck.typeface= Typeface.createFromAsset(context?.applicationContext?.assets, "customFont3.ttf")
                        dialogBox.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.font3 -> {
                        sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_FONT, 4)
                        sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_FONT1, false)
                        binding.tvAnalogClockFontCheck.typeface= Typeface.createFromAsset(context?.applicationContext?.assets, "customFont4.ttf")
                        dialogBox.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.font4 -> {
                        sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_FONT, 5)
                        sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_FONT1, false)
                        binding.tvAnalogClockFontCheck.typeface= Typeface.createFromAsset(context?.applicationContext?.assets, "customFont5.ttf")
                        dialogBox.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.font5 -> {
                        sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_FONT, 6)
                        sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_FONT1, false)
                        binding.tvAnalogClockFontCheck.typeface= Typeface.createFromAsset(context?.applicationContext?.assets, "customFont6.ttf")
                        dialogBox.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.font6 -> {
                        sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_FONT, 7)
                        sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_FONT1, false)
                        binding.tvAnalogClockFontCheck.typeface= Typeface.createFromAsset(context?.applicationContext?.assets, "customFont7.ttf")
                        dialogBox.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.font7 -> {
                        sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_FONT, 8)
                        sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_FONT1, false)
                        binding.tvAnalogClockFontCheck.typeface= Typeface.createFromAsset(context?.applicationContext?.assets, "customFont8.ttf")
                        dialogBox.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.font8 -> {
                        sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_FONT, 9)
                        sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_FONT1, false)
                        binding.tvAnalogClockFontCheck.typeface= Typeface.createFromAsset(context?.applicationContext?.assets, "customFont9.ttf")
                        dialogBox.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.font9 -> {
                        sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_FONT, 10)
                        sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_FONT1, false)
                        binding.tvAnalogClockFontCheck.typeface= Typeface.createFromAsset(context?.applicationContext?.assets, "customFont_10.ttf")
                        dialogBox.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.font10 -> {
                        sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_FONT, 11)
                        sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_FONT1, false)
                        binding.tvAnalogClockFontCheck.typeface= Typeface.createFromAsset(context?.applicationContext?.assets, "customFont_11.ttf")
                        dialogBox.dismiss()
                        return@setOnCheckedChangeListener
                    }
                }
            }
            dialogBox.show()
        }
    }
    private fun positionOfClock() {
        binding.rlClockPosition.setOnClickListener {
            val arrayOfString = context?.resources?.getStringArray(R.array.array_pos)
            if (!sp.readBool(ApplicationConstants.ANALOG_CLOCK_POSITION1, true)) {
                ApplicationConstants.positionClockCheck = sp.readInt(ApplicationConstants.ANALOG_CLOCK_POSITION, -1)
            }
            val builder = AlertDialog.Builder(context as Context)
            builder.setTitle(getString(R.string.dialog_set_position_of_clock))
            builder.setSingleChoiceItems(
                arrayOfString as Array<String>,
                ApplicationConstants.positionClockCheck
            ) { param2DialogInterface, param2Int ->
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_POSITION, param2Int)
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_POSITION1, false)
                if (sp.readInt(ApplicationConstants.ANALOG_CLOCK_POSITION,-1)==0){
                    binding.tvClockPositionChange.text=getString(R.string.custom_analogpos_top)
                }else if (sp.readInt(ApplicationConstants.ANALOG_CLOCK_POSITION,-1)==2){
                    binding.tvClockPositionChange.text=getString(R.string.custom_analogpos_bottom)
                }else{
                    binding.tvClockPositionChange.text=getString(R.string.custom_analogpos_center)
                }
                param2DialogInterface.dismiss()
            }
            builder.create().show()
        }
    }
    private fun numberTypeOfClock() {
        binding.rlClockNumberType.setOnClickListener {
            val arrayOfString = context?.resources?.getStringArray(R.array.array_num)
            if (!sp.readBool(ApplicationConstants.ANALOG_CLOCK_NUMBER_TYPE1, true)) {
                ApplicationConstants.numberTypeCheck = sp.readInt(ApplicationConstants.ANALOG_CLOCK_NUMBER_TYPE, -1)
            }
            val builder = AlertDialog.Builder(context as Context)
            builder.setTitle(getString(R.string.dialog_clock_number_type))
            builder.setSingleChoiceItems(
                arrayOfString as Array<String>,
                ApplicationConstants.numberTypeCheck
            ) { param2DialogInterface, param2Int ->
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_NUMBER_TYPE, param2Int)
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_NUMBER_TYPE1, false)
                if (sp.readInt(ApplicationConstants.ANALOG_CLOCK_NUMBER_TYPE, -1)==0){
                    binding.tvAnalogClockNumberTypeCheck.text=getString(R.string.custom_analognumber_numeric)
                }else{
                    binding.tvAnalogClockNumberTypeCheck.text=getString(R.string.custom_analognumber_roman)
                }
                param2DialogInterface.dismiss()
            }
            builder.create().show()
        }
    }
    private fun showCustomDialog() {
        try {
            dialog = ColorSelectDialog()
            dialog.show(childFragmentManager, "color dialog")
        } catch (e: Exception) {
        }
    }
    private fun shapeOfClock() {
        binding.rlClockShape.setOnClickListener {
            val arrayOfString =
                context?.resources?.getStringArray(R.array.array_shape)
            if (!sp.readBool(ApplicationConstants.ANALOG_CLOCK_SHAPE1, true)) {
                ApplicationConstants.shapeClockCheck = sp.readInt(ApplicationConstants.ANALOG_CLOCK_SHAPE, -1)
            }
            val builder = AlertDialog.Builder(context as Context)
            builder.setTitle(getString(R.string.dialog_select_shape_of_clock))
            builder.setSingleChoiceItems(
                arrayOfString as Array<String>,
                ApplicationConstants.shapeClockCheck
            ) { param2DialogInterface, param2Int ->
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_SHAPE, param2Int)
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_SHAPE1, false)
                if (sp.readInt(ApplicationConstants.ANALOG_CLOCK_SHAPE, -1)==0){
                    binding.tvAnalogShapeClockCheck.text=getString(R.string.custom_analogshape_circle)
                }else{
                    binding.tvAnalogShapeClockCheck.text=getString(R.string.custom_analogshape_rectangle)
                }
                param2DialogInterface.dismiss()
            }
            builder.create().show()
        }
    }
    private fun setLiveWallpaperClick() {
        binding.rlSetLiveAnalogClock.setOnClickListener {
            val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
            intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, context?.let { it1 -> ComponentName(it1, AnalogClockWallpaperService::class.java) })
            startActivity(intent)
        }
    }
    private fun changeBackgroundColor() {
        binding.ivAnalogClockBackgroundColor.setOnClickListener {
            ApplicationConstants.checkColorBoxAnalog = 0
            showCustomDialog()
        }
    }
    private fun changeTextColor() {
        binding.ivAnalogClockTextColor.setOnClickListener {
            ApplicationConstants.checkColorBoxAnalog = 1
            showCustomDialog()
        }
    }
    private fun changeClockColor() {
        binding.ivAnalogClockColor.setOnClickListener {
            ApplicationConstants.checkColorBoxAnalog = 2
            showCustomDialog()
        }
    }
    private fun changeClockSecArrowColor() {
        binding.ivAnalogClockSecArrowColor.setOnClickListener {
            ApplicationConstants.checkColorBoxAnalog = 3
            showCustomDialog()
        }
    }
    private fun changeClockMinArrowColor() {
        binding.ivAnalogClockMinArrowColor.setOnClickListener {
            ApplicationConstants.checkColorBoxAnalog = 4
            showCustomDialog()
        }
    }
    private fun changeClockHourArrowColor() {
        binding.ivAnalogClockHourArrowColor.setOnClickListener {
            ApplicationConstants.checkColorBoxAnalog = 5
            showCustomDialog()
        }
    }
    private fun hourFormat24() {
        binding.s24HourFormat.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_24_HOUR_FORMAT, true)
            } else {
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_24_HOUR_FORMAT, false)
            }
        }
    }
    private fun clockShadowEffect() {
        binding.sClockShadowEffect.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_SHADOW_EFFECT, true)
            } else {
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_SHADOW_EFFECT, false)
            }
        }
    }
    private fun clockShowSecArrow() {
        binding.sClockShowSecArrow.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_SHOW_SEC_ARROW, true)
            } else {
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_SHOW_SEC_ARROW, false)
            }
        }
    }
    private fun clockShowDate() {
        binding.sClockShowDate.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_SHOW_DATE, true)
            } else {
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_SHOW_DATE, false)
            }
        }
    }
    private fun clockShowDay() {
        binding.sClockShowDay.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_SHOW_DAY, true)
            } else {
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_SHOW_DAY, false)
            }
        }
    }
    private fun clockShowMonth() {
        binding.sClockShowMonth.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_SHOW_MONTH, true)
            } else {
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_SHOW_MONTH, false)
            }
        }
    }
    private fun clockShowDigitalClock() {
        binding.sClockShowDigitalClock.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_SHOW_DIGITAL_CLOCK, true)
            } else {
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_SHOW_DIGITAL_CLOCK, false)
            }
        }
    }
    private fun checkBoxChecks() {
        if (!sp.readBool(ApplicationConstants.ANALOG_CLOCK_BACKGROUND1, true)) {
            binding.ivAnalogClockBackgroundColor.setColorFilter(Color.parseColor(sp.readString(ApplicationConstants.ANALOG_CLOCK_BACKGROUND)))
        }else{
            binding.ivAnalogClockBackgroundColor.setColorFilter(Color.parseColor(getString(R.string.string_color_white)))
        }
        if (!sp.readBool(ApplicationConstants.ANALOG_CLOCK_TEXT_COLOR1, true)) {
            binding.ivAnalogClockTextColor.setColorFilter(Color.parseColor(sp.readString(ApplicationConstants.ANALOG_CLOCK_TEXT_COLOR)))
        }else{
            binding.ivAnalogClockTextColor.setColorFilter(Color.parseColor(getString(R.string.string_color_textArrow)))
        }
        if (!sp.readBool(ApplicationConstants.ANALOG_CLOCK_COLOR1, true)) {
            binding.ivAnalogClockColor.setColorFilter(Color.parseColor(sp.readString(ApplicationConstants.ANALOG_CLOCK_COLOR)))
        }else{
            binding.ivAnalogClockColor.setColorFilter(Color.parseColor(getString(R.string.string_color_clockColor)))
        }
        if (!sp.readBool(ApplicationConstants.ANALOG_CLOCK_SEC_ARROW_COLOR1, true)) {
            binding.ivAnalogClockSecArrowColor.setColorFilter(Color.parseColor(sp.readString(ApplicationConstants.ANALOG_CLOCK_SEC_ARROW_COLOR)))
        }else{
            binding.ivAnalogClockSecArrowColor.setColorFilter(Color.parseColor(getString(R.string.string_color_secArrow)))
        }
        if (!sp.readBool(ApplicationConstants.ANALOG_CLOCK_MIN_ARROW_COLOR1, true)) {
            binding.ivAnalogClockMinArrowColor.setColorFilter(Color.parseColor(sp.readString(ApplicationConstants.ANALOG_CLOCK_MIN_ARROW_COLOR)))
        }else{
            binding.ivAnalogClockMinArrowColor.setColorFilter(Color.parseColor(getString(R.string.string_color_minArrow)))
        }
        if (!sp.readBool(ApplicationConstants.ANALOG_CLOCK_HOUR_ARROW_COLOR1, true)) {
            binding.ivAnalogClockHourArrowColor.setColorFilter(Color.parseColor(sp.readString(ApplicationConstants.ANALOG_CLOCK_HOUR_ARROW_COLOR)))
        }else{
            binding.ivAnalogClockHourArrowColor.setColorFilter(Color.parseColor(getString(R.string.string_color_hourArrow)))
        }
        if (sp.readInt(ApplicationConstants.ANALOG_CLOCK_POSITION,-1)==0){
            binding.tvClockPositionChange.text=getString(R.string.custom_analogpos_top)
        }else if (sp.readInt(ApplicationConstants.ANALOG_CLOCK_POSITION,-1)==2){
            binding.tvClockPositionChange.text=getString(R.string.custom_analogpos_bottom)
        }else{
            binding.tvClockPositionChange.text=getString(R.string.custom_analogpos_center)
        }
        if (!sp.readBool(ApplicationConstants.ANALOG_CLOCK_FONT1, true)) {
            ApplicationConstants.strAnalog = sp.readInt(ApplicationConstants.ANALOG_CLOCK_FONT,-1)
            when (ApplicationConstants.strAnalog) {
                1 -> { binding.tvAnalogClockFontCheck.typeface=Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_1)) }
                2 -> { binding.tvAnalogClockFontCheck.typeface=Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_2)) }
                3 -> { binding.tvAnalogClockFontCheck.typeface=Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_3)) }
                4 -> { binding.tvAnalogClockFontCheck.typeface=Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_4)) }
                5 -> { binding.tvAnalogClockFontCheck.typeface=Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_5)) }
                6 -> { binding.tvAnalogClockFontCheck.typeface=Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_6)) }
                7 -> { binding.tvAnalogClockFontCheck.typeface=Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_7)) }
                8 -> { binding.tvAnalogClockFontCheck.typeface=Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_8)) }
                9 -> { binding.tvAnalogClockFontCheck.typeface=Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_9)) }
                10 -> { binding.tvAnalogClockFontCheck.typeface=Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_10)) }
                11-> { binding.tvAnalogClockFontCheck.typeface=Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_11)) }
            }
        }
        if (!sp.readBool(ApplicationConstants.ANALOG_CLOCK_SHAPE1, true)) {
            if (sp.readInt(ApplicationConstants.ANALOG_CLOCK_SHAPE, -1)==0){
                binding.tvAnalogShapeClockCheck.text=getString(R.string.custom_analogshape_circle)
            }else{
                binding.tvAnalogShapeClockCheck.text=getString(R.string.custom_analogshape_rectangle)
            }
        }else{
            binding.tvAnalogShapeClockCheck.text=getString(R.string.custom_analogshape_circle)
        }
        if (!sp.readBool(ApplicationConstants.ANALOG_CLOCK_NUMBER_TYPE1, true)) {
            if (sp.readInt(ApplicationConstants.ANALOG_CLOCK_NUMBER_TYPE, -1)==0){
                binding.tvAnalogClockNumberTypeCheck.text=getString(R.string.custom_analognumber_numeric)
            }else{
                binding.tvAnalogClockNumberTypeCheck.text=getString(R.string.custom_analognumber_roman)
            }
        }else{
            binding.tvAnalogClockNumberTypeCheck.text=getString(R.string.custom_analognumber_numeric)
        }
        binding.s24HourFormat.isChecked = sp.readBool(ApplicationConstants.ANALOG_CLOCK_24_HOUR_FORMAT, true)
        binding.sClockShadowEffect.isChecked = sp.readBool(ApplicationConstants.ANALOG_CLOCK_SHADOW_EFFECT, true)
        binding.sClockShowSecArrow.isChecked = sp.readBool(ApplicationConstants.ANALOG_CLOCK_SHOW_SEC_ARROW, true)
        binding.sClockShowDate.isChecked = sp.readBool(ApplicationConstants.ANALOG_CLOCK_SHOW_DATE, true)
        binding.sClockShowDay.isChecked = sp.readBool(ApplicationConstants.ANALOG_CLOCK_SHOW_DAY, true)
        binding.sClockShowMonth.isChecked = sp.readBool(ApplicationConstants.ANALOG_CLOCK_SHOW_MONTH, true)
        binding.sClockShowDigitalClock.isChecked = sp.readBool(ApplicationConstants.ANALOG_CLOCK_SHOW_DIGITAL_CLOCK, true)
    }
    override fun getColor(color: String) {
        when (ApplicationConstants.checkColorBoxAnalog) {
            0 -> {
                binding.ivAnalogClockBackgroundColor.setColorFilter(Color.parseColor(color))
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_BACKGROUND, color)
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_BACKGROUND1, false)
                dialog.dismiss()
            }
            1 -> {
                binding.ivAnalogClockTextColor.setColorFilter(Color.parseColor(color))
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_TEXT_COLOR, color)
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_TEXT_COLOR1, false)
                dialog.dismiss()
            }
            2 -> {
                binding.ivAnalogClockColor.setColorFilter(Color.parseColor(color))
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_COLOR, color)
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_COLOR1, false)
                dialog.dismiss()
            }
            3 -> {
                binding.ivAnalogClockSecArrowColor.setColorFilter(Color.parseColor(color))
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_SEC_ARROW_COLOR, color)
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_SEC_ARROW_COLOR1, false)
                dialog.dismiss()
            }
            4 -> {
                binding.ivAnalogClockMinArrowColor.setColorFilter(Color.parseColor(color))
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_MIN_ARROW_COLOR, color)
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_MIN_ARROW_COLOR1, false)
                dialog.dismiss()
            }
            5 -> {
                binding.ivAnalogClockHourArrowColor.setColorFilter(Color.parseColor(color))
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_HOUR_ARROW_COLOR, color)
                sp.savePreferences(ApplicationConstants.ANALOG_CLOCK_HOUR_ARROW_COLOR1, false)
                dialog.dismiss()
            }
        }
    }

}
