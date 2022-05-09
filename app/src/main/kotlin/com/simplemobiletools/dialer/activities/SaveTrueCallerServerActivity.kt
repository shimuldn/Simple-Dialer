package com.simplemobiletools.dialer.activities

import android.annotation.SuppressLint
import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.simplemobiletools.commons.extensions.setupDialogStuff
import com.simplemobiletools.commons.extensions.showKeyboard
import com.simplemobiletools.commons.extensions.value
import com.simplemobiletools.dialer.R
import com.simplemobiletools.dialer.extensions.config
import kotlinx.android.synthetic.main.dialog_set_truecaller_server.view.*

@SuppressLint("InflateParams")
class SaveTrueCallerServerActivity(val activity: Activity, val callback: () -> Unit)  {

    init {

        val originalServer:String? = activity.config.getTrueCallerServer()
        val view = activity.layoutInflater.inflate(R.layout.dialog_set_truecaller_server, null).apply {
            if (originalServer?.isNotEmpty() == true) {
                set_truecaller_server_edittext.setText(originalServer)
            }
        }

        AlertDialog.Builder(activity)
            .setPositiveButton(com.simplemobiletools.commons.R.string.ok, null)
            .setNegativeButton(com.simplemobiletools.commons.R.string.cancel, null)
            .create().apply {
                activity.setupDialogStuff(view, this) {
                    showKeyboard(view.set_truecaller_server_edittext)
                    getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                        val newTrueCallerServer = view.set_truecaller_server_edittext.value
                        activity.config.saveTrueCallerServer(newTrueCallerServer)
                        callback()
                        dismiss()
                    }
                }
            }
    }
}
