package com.muiezarif.livewallpaper.fragments


import android.app.Dialog
import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import com.google.firebase.analytics.FirebaseAnalytics
import com.muiezarif.livewallpaper.R
import com.muiezarif.livewallpaper.databinding.DateFormatDialogBinding
import com.muiezarif.livewallpaper.databinding.FontSelectDialogBinding
import com.muiezarif.livewallpaper.databinding.FragmentDigitalClockSettingsBinding
import com.muiezarif.livewallpaper.dialogs.ColorSelectDialog
import com.muiezarif.livewallpaper.helpers.AdHelper
import com.muiezarif.livewallpaper.listeners.ColorSelectDialogListener
import com.muiezarif.livewallpaper.services.DigitalClockWallpaperService
import com.muiezarif.livewallpaper.utils.ApplicationConstants
import com.muiezarif.livewallpaper.utils.ApplicationSharedPreference
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class DigitalClockSettingsFragment : Fragment(), ColorSelectDialogListener {
    lateinit var sp: ApplicationSharedPreference
    private lateinit var dialog: ColorSelectDialog
    private var cal: Calendar = Calendar.getInstance()
    private lateinit var day: Date
    private lateinit var binding: FragmentDigitalClockSettingsBinding
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.fragment_digital_clock_settings, container, false)
        sp= ApplicationSharedPreference.getInstance(context)
        inIt()
        checkCheckBox()
        return binding.root
    }
    private fun inIt(){
        day=cal.time
        cal.time= Date()
        setDigitalClockLive()
        digitalClockStyle()
        digitalClockColor()
        digitalClockBackground()
        digitalClockSize()
        digitalClockVerticalPosition()
        digitalClockHorizontalPosition()
        digitalDisplayDateFormat()
        digitalUse24HourFormat()
        digitalShowSeconds()
        digitalShowDate()
        digitalShowDayOfWeek()
        digitalShowGlow()
        AdHelper.loadBannerAd(binding.adView)
    }
    private fun showCustomDialog() {
        try {
            this.dialog = ColorSelectDialog()
            this.dialog.show(childFragmentManager, "color dialog")
        } catch (e: Exception) {
        }
    }

    private fun setDigitalClockLive(){
        binding.rlDigitalClockSetLive.setOnClickListener {
            val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
            intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, context?.let { it1 -> ComponentName(it1, DigitalClockWallpaperService::class.java) })
            startActivity(intent)
        }
    }
    private fun digitalClockStyle(){
        binding.rlDigitalClockSetStyle.setOnClickListener {
            val dialogBox = Dialog(context as Context)
            val dialog: FontSelectDialogBinding =DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.font_select_dialog,null,false)
            dialogBox.setContentView(dialog.root)
            if (!sp.readBool(ApplicationConstants.DIGITAL_CLOCK_STYLE1, true)) {
                ApplicationConstants.strDigital = sp.readInt(ApplicationConstants.DIGITAL_CLOCK_STYLE,-1)
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

            when (ApplicationConstants.strDigital) {
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
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_STYLE, 1)
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_STYLE1, false)
                        binding.tvDigitalClockStyleCheck.typeface= Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_1))
                        dialogBox.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.font1 -> {
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_STYLE, 2)
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_STYLE1, false)
                        binding.tvDigitalClockStyleCheck.typeface= Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_2))
                        dialogBox.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.font2 -> {
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_STYLE, 3)
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_STYLE1, false)
                        binding.tvDigitalClockStyleCheck.typeface= Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_3))
                        dialogBox.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.font3 -> {
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_STYLE, 4)
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_STYLE1, false)
                        binding.tvDigitalClockStyleCheck.typeface= Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_4))
                        dialogBox.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.font4 -> {
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_STYLE, 5)
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_STYLE1, false)
                        binding.tvDigitalClockStyleCheck.typeface= Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_5))
                        dialogBox.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.font5 -> {
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_STYLE, 6)
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_STYLE1, false)
                        binding.tvDigitalClockStyleCheck.typeface= Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_6))
                        dialogBox.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.font6 -> {
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_STYLE, 7)
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_STYLE1, false)
                        binding.tvDigitalClockStyleCheck.typeface= Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_7))
                        dialogBox.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.font7 -> {
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_STYLE, 8)
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_STYLE1, false)
                        binding.tvDigitalClockStyleCheck.typeface= Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_8))
                        dialogBox.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.font8 -> {
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_STYLE, 9)
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_STYLE1, false)
                        binding.tvDigitalClockStyleCheck.typeface= Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_9))
                        dialogBox.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.font9 -> {
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_STYLE, 10)
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_STYLE1, false)
                        binding.tvDigitalClockStyleCheck.typeface= Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_10))
                        dialogBox.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.font10 -> {
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_STYLE, 11)
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_STYLE1, false)
                        binding.tvDigitalClockStyleCheck.typeface= Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_11))
                        dialogBox.dismiss()
                        return@setOnCheckedChangeListener
                    }
                }
            }
            dialogBox.show()
        }
    }
    private fun digitalClockColor(){
        binding.rlDigitalClockColor.setOnClickListener {
            ApplicationConstants.checkColorBoxDigital =0
            showCustomDialog()
        }
    }
    private fun digitalClockBackground(){
        binding.rlDigitalClockBackground.setOnClickListener {
            ApplicationConstants.checkColorBoxDigital =1
            showCustomDialog()
        }
    }
    private fun digitalClockSize(){
        if (!sp.readBool(ApplicationConstants.DIGITAL_CLOCK_SIZE1, true)) {
            ApplicationConstants.digitalClockSizeCheck = sp.readInt(ApplicationConstants.DIGITAL_CLOCK_SIZE, -1)
        }
        binding.sbClockSize.max = 10
        binding.sbClockSize.keyProgressIncrement = 1
        binding.sbClockSize.progress = ApplicationConstants.digitalClockSizeCheck
        binding.sbClockSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_SIZE, progress)
                sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_SIZE1, false)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }
    private fun digitalClockVerticalPosition(){
        if (!sp.readBool(ApplicationConstants.DIGITAL_CLOCK_POSITION_VERTICAL1, true)) {
            ApplicationConstants.clockPositionVerticalCheck = sp.readInt(ApplicationConstants.DIGITAL_CLOCK_POSITION_VERTICAL, -1)
        }
        binding.sbClockPositionVertical.max = 185
        binding.sbClockPositionVertical.keyProgressIncrement = 1
        binding.sbClockPositionVertical.progress = ApplicationConstants.clockPositionVerticalCheck
        binding.sbClockPositionVertical.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_POSITION_VERTICAL, progress)
                sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_POSITION_VERTICAL1, false)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }
    private fun digitalClockHorizontalPosition(){
        if (!sp.readBool(ApplicationConstants.DIGITAL_CLOCK_POSITION_HORIZONTAL1, true)) {
            ApplicationConstants.clockPositionHorizontalCheck = sp.readInt(ApplicationConstants.DIGITAL_CLOCK_POSITION_HORIZONTAL, -1)
        }
        binding.sbClockPositionHorizontal.max = 200
        binding.sbClockPositionHorizontal.keyProgressIncrement = 1
        binding.sbClockPositionHorizontal.progress =
            ApplicationConstants.clockPositionHorizontalCheck
        binding.sbClockPositionHorizontal.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_POSITION_HORIZONTAL, progress)
                sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_POSITION_HORIZONTAL1, false)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }
    private fun digitalDisplayDateFormat() {
        binding.rlDigitalDisplayDateFormat.setOnClickListener {
            val dialogBoxDate = Dialog(context as Context)
            val dialogDate: DateFormatDialogBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.date_format_dialog,
                null,
                false
            )
            dialogBoxDate.setContentView(dialogDate.root)
            if (!sp.readBool(ApplicationConstants.DIGITAL_CLOCK_DISPLAY_DATE_FORMAT1, true)) {
                ApplicationConstants.strFormat = sp.readString(ApplicationConstants.DIGITAL_CLOCK_DISPLAY_DATE_FORMAT)
            }
            dialogDate.format0.text = SimpleDateFormat(getString(R.string.dd_MMMM_yyyy), Locale.ENGLISH).format(day.time)
            dialogDate.format1.text = SimpleDateFormat(getString(R.string.MMMM_dd_yyyy), Locale.ENGLISH).format(day.time)
            dialogDate.format2.text = SimpleDateFormat(getString(R.string.dd_MMM_yyyy), Locale.ENGLISH).format(day.time)
            dialogDate.format3.text = SimpleDateFormat(getString(R.string.MMM_dd_yyyy), Locale.ENGLISH).format(day.time)
            dialogDate.format4.text = SimpleDateFormat(getString(R.string.MM_dd_yyyy), Locale.ENGLISH).format(day.time)
            dialogDate.format5.text = SimpleDateFormat(getString(R.string.dd_MM_yyyy), Locale.ENGLISH).format(day.time)
            dialogDate.format6.text = SimpleDateFormat(getString(R.string.yyyy_dd_MM), Locale.ENGLISH).format(day.time)
            dialogDate.format7.text = SimpleDateFormat(getString(R.string.MM_dd_yyyy2), Locale.ENGLISH).format(day.time)
            dialogDate.format8.text = SimpleDateFormat(getString(R.string.dd_MM_yyyy2), Locale.ENGLISH).format(day.time)
            dialogDate.format9.text = SimpleDateFormat(getString(R.string.yyyy_MM_dd), Locale.ENGLISH).format(day.time)


            when (ApplicationConstants.strFormat) {
                getString(R.string.dd_MMMM_yyyy) -> { dialogDate.format0.isChecked = true }
                getString(R.string.MMMM_dd_yyyy) -> { dialogDate.format1.isChecked = true }
                getString(R.string.dd_MMM_yyyy) -> { dialogDate.format2.isChecked = true }
                getString(R.string.MMM_dd_yyyy) -> { dialogDate.format3.isChecked = true }
                getString(R.string.MM_dd_yyyy) -> { dialogDate.format4.isChecked = true }
                getString(R.string.dd_MM_yyyy) -> { dialogDate.format5.isChecked = true }
                getString(R.string.yyyy_dd_MM) -> { dialogDate.format6.isChecked = true }
                getString(R.string.MM_dd_yyyy2) -> { dialogDate.format7.isChecked = true }
                getString(R.string.dd_MM_yyyy2) -> { dialogDate.format8.isChecked = true }
                getString(R.string.yyyy_MM_dd) -> { dialogDate.format9.isChecked = true }
            }
            dialogDate.radioGroupDigitalDateFormat.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId){
                    R.id.format0 -> {
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_DISPLAY_DATE_FORMAT, getString(R.string.dd_MMMM_yyyy))
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_DISPLAY_DATE_FORMAT1, false)
                        binding.tvDigitalDateFormatCheck.text=
                            SimpleDateFormat(getString(R.string.dd_MMMM_yyyy), Locale.ENGLISH).format(day.time)
                        dialogBoxDate.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.format1 -> {
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_DISPLAY_DATE_FORMAT, getString(R.string.MMMM_dd_yyyy))
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_DISPLAY_DATE_FORMAT1, false)
                        binding.tvDigitalDateFormatCheck.text=
                            SimpleDateFormat(getString(R.string.MMMM_dd_yyyy), Locale.ENGLISH).format(day.time)
                        dialogBoxDate.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.format2 -> {
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_DISPLAY_DATE_FORMAT, getString(R.string.dd_MMM_yyyy))
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_DISPLAY_DATE_FORMAT1, false)
                        binding.tvDigitalDateFormatCheck.text=
                            SimpleDateFormat(getString(R.string.dd_MMM_yyyy), Locale.ENGLISH).format(day.time)
                        dialogBoxDate.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.format3 -> {
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_DISPLAY_DATE_FORMAT, getString(R.string.MMM_dd_yyyy))
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_DISPLAY_DATE_FORMAT1, false)
                        binding.tvDigitalDateFormatCheck.text=
                            SimpleDateFormat(getString(R.string.MMM_dd_yyyy), Locale.ENGLISH).format(day.time)
                        dialogBoxDate.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.format4 -> {
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_DISPLAY_DATE_FORMAT, getString(R.string.MM_dd_yyyy))
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_DISPLAY_DATE_FORMAT1, false)
                        binding.tvDigitalDateFormatCheck.text=
                            SimpleDateFormat(getString(R.string.MM_dd_yyyy), Locale.ENGLISH).format(day.time)
                        dialogBoxDate.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.format5 -> {
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_DISPLAY_DATE_FORMAT, getString(R.string.dd_MM_yyyy))
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_DISPLAY_DATE_FORMAT1, false)
                        binding.tvDigitalDateFormatCheck.text=
                            SimpleDateFormat(getString(R.string.dd_MM_yyyy), Locale.ENGLISH).format(day.time)
                        dialogBoxDate.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.format6 -> {
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_DISPLAY_DATE_FORMAT, getString(R.string.yyyy_dd_MM))
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_DISPLAY_DATE_FORMAT1, false)
                        binding.tvDigitalDateFormatCheck.text=
                            SimpleDateFormat(getString(R.string.yyyy_dd_MM), Locale.ENGLISH).format(day.time)
                        dialogBoxDate.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.format7 -> {
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_DISPLAY_DATE_FORMAT, getString(R.string.MM_dd_yyyy2))
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_DISPLAY_DATE_FORMAT1, false)
                        binding.tvDigitalDateFormatCheck.text=
                            SimpleDateFormat(getString(R.string.MM_dd_yyyy2), Locale.ENGLISH).format(day.time)
                        dialogBoxDate.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.format8 -> {
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_DISPLAY_DATE_FORMAT, getString(R.string.dd_MM_yyyy2))
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_DISPLAY_DATE_FORMAT1, false)
                        binding.tvDigitalDateFormatCheck.text=
                            SimpleDateFormat(getString(R.string.dd_MM_yyyy2), Locale.ENGLISH).format(day.time)
                        dialogBoxDate.dismiss()
                        return@setOnCheckedChangeListener
                    }
                    R.id.format9 -> {
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_DISPLAY_DATE_FORMAT, getString(R.string.yyyy_MM_dd))
                        sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_DISPLAY_DATE_FORMAT1, false)
                        binding.tvDigitalDateFormatCheck.text=
                            SimpleDateFormat(getString(R.string.yyyy_MM_dd), Locale.ENGLISH).format(day.time)
                        dialogBoxDate.dismiss()
                        return@setOnCheckedChangeListener
                    }
                }
            }
            dialogBoxDate.show()
        }
    }
    private fun digitalUse24HourFormat(){
        binding.cbUse24HourFormat.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_24_HOUR_FORMAT, true)
            }else{
                sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_24_HOUR_FORMAT, false)
            }
        }
    }
    private fun digitalShowSeconds(){
        binding.cbDigitalShowSeconds.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_SHOW_SECONDS, true)
            }else{
                sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_SHOW_SECONDS, false)
            }
        }

    }
    private fun digitalShowDate(){
        binding.cbDigitalShowDate.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_SHOW_DATE, true)
            }else{
                sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_SHOW_DATE, false)
            }
        }
    }
    private fun digitalShowDayOfWeek(){
        binding.cbDigitalShowDayOfWeek.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_DAY_OF_WEEK, true)
            }else{
                sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_DAY_OF_WEEK, false)
            }
        }
    }
    private fun digitalShowGlow(){
        binding.cbShowGlow.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_SHOW_GLOW, true)
            }else{
                sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_SHOW_GLOW, false)
            }
        }
    }
    private fun checkCheckBox(){
        if (!sp.readBool(ApplicationConstants.DIGITAL_COLOR_BACKGROUND1, true)) {
            binding.ivDigitalClockBackgroundColor.setColorFilter(Color.parseColor(sp.readString(ApplicationConstants.DIGITAL_COLOR_BACKGROUND)))
        }else{
            binding.ivDigitalClockBackgroundColor.setColorFilter(Color.parseColor(getString(R.string.string_color_white)))
        }
        if (!sp.readBool(ApplicationConstants.DIGITAL_CLOCK_COLOR1, true)) {
            binding.ivDigitalClockColor.setColorFilter(Color.parseColor(sp.readString(ApplicationConstants.DIGITAL_CLOCK_COLOR)))
        }else{
            binding.ivDigitalClockColor.setColorFilter(Color.parseColor(getString(R.string.string_color_black)))
        }
        if (!sp.readBool(ApplicationConstants.DIGITAL_CLOCK_DISPLAY_DATE_FORMAT1, true)) {
            binding.tvDigitalDateFormatCheck.text=SimpleDateFormat(sp.readString(ApplicationConstants.DIGITAL_CLOCK_DISPLAY_DATE_FORMAT), Locale.ENGLISH).format(day.time)
        }else{
            binding.tvDigitalDateFormatCheck.text=SimpleDateFormat(getString(R.string.default_date_format), Locale.ENGLISH).format(day.time)
        }
        if (!sp.readBool(ApplicationConstants.DIGITAL_CLOCK_STYLE1,true)){
            when(sp.readInt(ApplicationConstants.DIGITAL_CLOCK_STYLE,-1)){
                1 -> { binding.tvDigitalClockStyleCheck.typeface=Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_1)) }
                2 -> { binding.tvDigitalClockStyleCheck.typeface=Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_2)) }
                3 -> { binding.tvDigitalClockStyleCheck.typeface=Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_3)) }
                4 -> { binding.tvDigitalClockStyleCheck.typeface=Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_4)) }
                5 -> { binding.tvDigitalClockStyleCheck.typeface=Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_5)) }
                6 -> { binding.tvDigitalClockStyleCheck.typeface=Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_6)) }
                7 -> { binding.tvDigitalClockStyleCheck.typeface=Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_7)) }
                8 -> { binding.tvDigitalClockStyleCheck.typeface=Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_8)) }
                9 -> { binding.tvDigitalClockStyleCheck.typeface=Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_9)) }
                10 -> { binding.tvDigitalClockStyleCheck.typeface=Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_10)) }
                11-> { binding.tvDigitalClockStyleCheck.typeface=Typeface.createFromAsset(context?.applicationContext?.assets, getString(R.string.custom_font_11)) }
            }
        }else{
            binding.tvDigitalClockStyleCheck.typeface=Typeface.createFromAsset(context?.applicationContext?.assets, "customFont1.ttf")
        }
        binding.cbShowGlow.isChecked=sp.readBool(ApplicationConstants.DIGITAL_CLOCK_SHOW_GLOW, true)
        binding.cbDigitalShowDayOfWeek.isChecked=sp.readBool(ApplicationConstants.DIGITAL_CLOCK_DAY_OF_WEEK, true)
        binding.cbDigitalShowDate.isChecked=sp.readBool(ApplicationConstants.DIGITAL_CLOCK_SHOW_DATE, true)
        binding.cbDigitalShowSeconds.isChecked=sp.readBool(ApplicationConstants.DIGITAL_CLOCK_SHOW_SECONDS, true)
        binding.cbUse24HourFormat.isChecked=sp.readBool(ApplicationConstants.DIGITAL_CLOCK_24_HOUR_FORMAT, true)
    }

    override fun getColor(color: String) {
        when(ApplicationConstants.checkColorBoxDigital) {
            0-> {
                sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_COLOR, color)
                sp.savePreferences(ApplicationConstants.DIGITAL_CLOCK_COLOR1, false)
                binding.ivDigitalClockColor.setColorFilter(Color.parseColor(color))
                dialog.dismiss()
            }
            1->{
                sp.savePreferences(ApplicationConstants.DIGITAL_COLOR_BACKGROUND, color)
                sp.savePreferences(ApplicationConstants.DIGITAL_COLOR_BACKGROUND1, false)
                binding.ivDigitalClockBackgroundColor.setColorFilter(Color.parseColor(color))
                dialog.dismiss()
            }
        }
    }


}
