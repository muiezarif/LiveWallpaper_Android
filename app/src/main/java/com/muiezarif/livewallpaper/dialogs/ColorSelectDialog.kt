package com.muiezarif.livewallpaper.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import com.muiezarif.livewallpaper.R
import com.muiezarif.livewallpaper.listeners.ColorSelectDialogListener
import com.skydoves.colorpickerview.ColorPickerView
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener

class ColorSelectDialog : AppCompatDialogFragment(), View.OnClickListener {

    lateinit var select: ImageView
    private var colorSelectDialogListener: ColorSelectDialogListener? = null
    lateinit var color: String
    lateinit var builder: AlertDialog.Builder
    lateinit var colorPickerView: ColorPickerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
//            colorSelectDialogListener = context as ColorSelectDialogListener
        parentFragment?.let { onAttachToParentFragment(it) }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        builder = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.layout_custom_color_select_dialog, null)
        builder.setView(view)
        if (view != null) {
            select = view.findViewById(R.id.ivColorSelect)
            colorPickerView = view.findViewById(R.id.cpvColorPicker)
        }
        colorPickerView.setColorListener(ColorEnvelopeListener { envelope, _ ->
            color = "#" + envelope.hexCode
        })
        select.setOnClickListener(this)
        return builder.create()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivColorSelect ->
                colorSelectDialogListener?.getColor(color)
            else -> {

            }
        }
    }
    fun onAttachToParentFragment(fragment: Fragment) {
        try {
            colorSelectDialogListener = fragment as ColorSelectDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                "$fragment must implement OnPlayerSelectionSetListener"
            )
        }
    }

}