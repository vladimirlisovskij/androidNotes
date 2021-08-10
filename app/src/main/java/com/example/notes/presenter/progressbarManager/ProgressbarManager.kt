package com.example.notes.presenter.progressbarManager

import android.app.Activity
import android.app.Dialog
import com.example.notes.R
import javax.inject.Inject

class ProgressbarManager @Inject constructor(
    private val activity: Activity
) {
    private val dialog: Dialog = Dialog(activity).apply {
        setCancelable(false)
        setContentView(R.layout.item_circle_progress)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    fun show() {
        dialog.show()
    }

    fun dismiss() {
        dialog.dismiss()
    }
}