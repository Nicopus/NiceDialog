package com.nicolaihansen.nicedialogsample

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.nicolaihansen.nicedialog.DialogShape
import com.nicolaihansen.nicedialog.NiceDialog
import com.nicolaihansen.nicedialog.DialogType
import com.nicolaihansen.nicedialog.ImageDialog
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

        custom1.setOnClickListener {
            showDialogWithArc()
        }

        custom2.setOnClickListener {
            showDialogWithInput()
        }

        custom3.setOnClickListener {
            showDialogWithCustomIcon()
        }

        custom4.setOnClickListener {
            showDialogWithBackground()
        }

        custom5.setOnClickListener {
            showDialogWithBanner()
        }


    }

    private fun showDialog(dialogType: DialogType) {
        val dialog = NiceDialog.Builder(this)
            .setDialogType(dialogType)
            .setTitle("Nice dialog")
            .setMessage("This is a test of the NiceDialog library.")
            .setNegativeButton(null, object : NiceDialog.OnClickListener {
                override fun onClick(dialog: NiceDialog) {
                    dialog.dismiss()
                }
            })
            .create()
        dialog.show()
    }

    private fun showDialogWithArc() {
        val dialog = NiceDialog.Builder(this)
            .setDialogShape(DialogShape.ARC)
            .setDialogType(DialogType.SUCCESS)
            .setTitle("Nice dialog")
            .setMessage("This is a test of the NiceDialog library.")
            .setPositiveButton(null, object : NiceDialog.OnClickListener {
                override fun onClick(dialog: NiceDialog) {
                    dialog.dismiss()
                }
            })
            .create()
        dialog.show()
    }

    private fun showDialogWithInput() {
        val dialog = NiceDialog.Builder(this)
            .setDialogType(DialogType.SUCCESS)
            .setTitle("Nice dialog")
            .setMessage("This is a test of the NiceDialog library.")
            .setPositiveButton(null, object : NiceDialog.OnClickListener {
                override fun onClick(dialog: NiceDialog) {
                    dialog.dismiss()
                }
            })
            .addInputField("Enter random text", object : NiceDialog.OnInputListener {
                override fun onInput(text: String?) {
                    inputText.text = text
                }
            })
            .create()
        dialog.show()
    }

    private fun showDialogWithCustomIcon() {
        val dialog = NiceDialog.Builder(this)
            .setDialogType(DialogType.SUCCESS)
            .setTitle("Nice dialog")
            .setMessage("This is a test of the NiceDialog library.")
            .setIcon(R.drawable.ic_android)
            .setPositiveButton(null, object : NiceDialog.OnClickListener {
                override fun onClick(dialog: NiceDialog) {
                    dialog.dismiss()
                }
            })
            .create()
        dialog.show()
    }

    private fun showDialogWithBackground() {
        val dialog = NiceDialog.Builder(this)
            .setDialogShape(DialogShape.ARC)
            .setDialogType(DialogType.SUCCESS)
            .setTitle("Nice dialog")
            .setMessage("This is a test of the NiceDialog library.")
            .setBackgroundColor(ContextCompat.getColor(this, R.color.colorSuccess2))
            .setIconColor(Color.WHITE)
            .setTextColor(Color.WHITE)
            .setIconOutlineColor(Color.TRANSPARENT)
            .setPositiveButton(null, object : NiceDialog.OnClickListener {
                override fun onClick(dialog: NiceDialog) {
                    dialog.dismiss()
                }
            })
            .setButtonColor(Color.WHITE)
            .create()
        dialog.show()
    }

    private fun showDialogWithBanner() {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.def_banner)
        val dialog = ImageDialog.Builder(this, bitmap)
            .setImageHeight(60)
            .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            .setTitle("Nice dialog")
            .setMessage("This is a test of the NiceDialog library.")
            .setPositiveButton(null, object : ImageDialog.OnClickListener {
                override fun onClick(dialog: ImageDialog) {
                    dialog.dismiss()
                }
            })
            .create()
        dialog.show()
    }
}
