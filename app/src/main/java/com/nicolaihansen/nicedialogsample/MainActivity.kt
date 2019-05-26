package com.nicolaihansen.nicedialogsample

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.nicolaihansen.nicedialog.DialogShape
import com.nicolaihansen.nicedialog.NiceDialog
import com.nicolaihansen.nicedialog.DialogType
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


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

        custom.setOnClickListener {
            showDialog()
        }


    }

    private fun showDialog(dialogType: DialogType) {
        val dialog = NiceDialog.Builder(this)
            .setDialogType(dialogType)
            .setTitle("Nice dialog")
            .setMessage("This is a test of the NiceDialog library.")
            .setNegativeButton(null, object: NiceDialog.OnClickListener {
                override fun onClick(dialog: NiceDialog) {
                    dialog.dismiss()
                }
            })
            .create()
        dialog.show()
    }

    private fun showDialog() {
        val dialog = NiceDialog.Builder(this)
            .setDialogShape(DialogShape.ARC)
            .setTitle("Nice dialog")
            .setMessage("This is a test of the NiceDialog library.")
            .setBackgroundColor(ContextCompat.getColor(this, R.color.colorSuccess2))
            .setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
            .setIconOutlineColors(intArrayOf(Color.TRANSPARENT, Color.TRANSPARENT))
            .setPositiveButton(null, object: NiceDialog.OnClickListener {
                override fun onClick(dialog: NiceDialog) {
                    dialog.dismiss()
                }
            })
            .addInputField("Name", object: NiceDialog.OnInputListener {
                override fun onInput(text: String?) {
                    textView.text = text
                }

            })
            .create()
        dialog.show()
    }
}
