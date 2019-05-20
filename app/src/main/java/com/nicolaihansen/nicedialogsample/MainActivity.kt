package com.nicolaihansen.nicedialogsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.nicolaihansen.nicedialog.NiceDialog
import com.nicolaihansen.nicedialog.DialogType
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        error.setOnClickListener {
            showDialog(DialogType.ERROR)
        }

        warning.setOnClickListener {
            showDialog(DialogType.WARNING)
        }

        success.setOnClickListener {
            showDialog(DialogType.SUCCESS)
        }

        info.setOnClickListener {
            showDialog(DialogType.INFO)
        }
    }

    private fun showDialog(dialogType: DialogType) {
        val dialog = NiceDialog.Builder(this)
                .setDialogType(dialogType)
                .setTitle("Nice dialog")
                .setMessage("This is a test of the NiceDialog library.")
                .setNegativeButton(null, this)
                .create()
        dialog.show()
    }

    override fun onClick(v: View?) {

    }
}
