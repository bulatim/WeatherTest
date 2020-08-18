package ru.bulat.weatherapplicationtest.ui.weather

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ru.bulat.weatherapplicationtest.databinding.WeatherDialogBinding


class WeatherDialog(
    private val bindingWeatherDialog: WeatherDialogBinding,
    private val callback: () -> Unit
) :
    DialogFragment() {
    lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        callback.invoke()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (!::alertDialog.isInitialized)
            alertDialog = AlertDialog.Builder(requireActivity())
                .setView(bindingWeatherDialog.root)
                .create()
        return alertDialog
    }
}