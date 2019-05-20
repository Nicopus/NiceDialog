package com.nicolaihansen.nicedialog

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.Window
import android.view.WindowManager.LayoutParams
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.nicolaihansen.nicedialog.R
import com.nicolaihansen.nicedialog.DialogType.*


class NiceDialog private constructor(
    context: Context,
    private var dialogType: DialogType?,
    private var iconOutlineColor: Int?,
    private var iconOutlineColors: IntArray?,
    private var title: String,
    private var message: String,
    private var iconRes: Int?,
    private var iconColor: Int?,
    private var positiveText: String?,
    private var positiveListener: View.OnClickListener?,
    private var negativeText: String?,
    private var negativeListener: View.OnClickListener?
) : AlertDialog(context) {

    private lateinit var titleTextView: TextView
    private lateinit var messageTextView: TextView
    private lateinit var iconOutlineView: View
    private lateinit var iconView: ImageView
    private lateinit var positiveButton: TextView
    private lateinit var negativeButton: TextView
    private lateinit var inputView: EditText

    data class Builder(
            private val context: Context
    ) {

        private var dialogType: DialogType? = null
        private var iconOutlineColor: Int? = null
        private var iconOutlineColors: IntArray? = null
        private var title: String = ""
        private var message: String = ""
        private var iconRes: Int? = null
        private var iconColor: Int? = null
        private var positiveText: String? = null
        private var positiveListener: View.OnClickListener? = null
        private var negativeText: String? = null
        private var negativeListener: View.OnClickListener? = null


        fun setDialogType(dialogType: DialogType): Builder {
            this.dialogType = dialogType
            return this
        }

        fun setIconOutlineColor(iconColor: Int): Builder {
            this.iconOutlineColor = iconColor
            return this
        }

        fun setIconOutlineColors(iconColors: IntArray): Builder {
            this.iconOutlineColors = iconColors
            return this
        }

        fun setIcon(iconRes: Int): Builder {
            this.iconRes = iconRes
            return this
        }

        fun setIconColor(color: Int): Builder {
            this.iconColor = color
            return this
        }

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setMessage(message: String): Builder {
            this.message = message
            return this
        }

        fun setPositiveButton(text: String? = null, listener: View.OnClickListener): Builder {
            this.positiveText = text
            this.positiveListener = listener
            return this
        }

        fun setNegativeButton(text: String? = null, listener: View.OnClickListener): Builder {
            this.negativeText = text
            this.negativeListener = listener
            return this
        }

        fun create() = NiceDialog(
            context, dialogType, iconOutlineColor, iconOutlineColors,
            title, message, iconRes, iconColor, positiveText, positiveListener,
            negativeText, negativeListener
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_icon_mid)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.clearFlags(
                LayoutParams.FLAG_NOT_FOCUSABLE
                        or LayoutParams.FLAG_ALT_FOCUSABLE_IM
        )

        titleTextView = findViewById(R.id.title)
        titleTextView.text = title
        messageTextView = findViewById(R.id.message)
        messageTextView.text = message
        iconOutlineView = findViewById(R.id.icon_outline)
        iconOutlineView.background = getIconOutlineDrawable()
        iconView = findViewById(R.id.icon)
        iconView.setImageDrawable(getIconDrawable())
        iconView.setColorFilter(getIconColor())
        inputView = findViewById(R.id.input)
        positiveButton = findViewById(R.id.ok)
        negativeButton = findViewById(R.id.cancel)
        prepareButtons()
    }

    private fun prepareButtons() {
        if (positiveListener != null) {
            positiveButton.setOnClickListener {
                positiveListener!!.onClick(it)
                dismiss()
            }
            positiveButton.visibility = View.VISIBLE
            if (positiveText != null) {
                positiveButton.text = positiveText
            }
        }

        if (negativeListener != null) {
            negativeButton.setOnClickListener {
                negativeListener!!.onClick(it)
                dismiss()
            }
            negativeButton.visibility = View.VISIBLE
            if (negativeText != null) {
                negativeButton.text = negativeText
            }
        }
    }

    private fun getIconOutlineDrawable(): Drawable {
        val drawable: GradientDrawable
        when {
            iconOutlineColors != null -> {
                drawable = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, iconOutlineColors!!)
                drawable.shape = GradientDrawable.OVAL
            }
            iconOutlineColor != null -> {
                drawable = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(iconOutlineColor!!))
                drawable.shape = GradientDrawable.OVAL
            }
            else -> {
                drawable = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, getDialogTypeColors())
                drawable.shape = GradientDrawable.OVAL
            }
        }
        return drawable
    }

    private fun getDialogTypeColors(): IntArray {
        val startColor: Int
        val endColor: Int
        when (dialogType) {
            ERROR -> {
                startColor = ContextCompat.getColor(context, R.color.colorError1)
                endColor = ContextCompat.getColor(context, R.color.colorError2)
            }
            WARNING -> {
                startColor = ContextCompat.getColor(context, R.color.colorWarning1)
                endColor = ContextCompat.getColor(context, R.color.colorWarning2)
            }
            INFO -> {
                startColor = ContextCompat.getColor(context, R.color.colorInfo1)
                endColor = ContextCompat.getColor(context, R.color.colorInfo2)
            }
            else -> {
                startColor = ContextCompat.getColor(context, R.color.colorSuccess1)
                endColor = ContextCompat.getColor(context, R.color.colorSuccess2)
            }
        }
        return intArrayOf(startColor, endColor)
    }

    private fun getIconDrawable(): Drawable? {
        return if (iconRes != null) {
            ContextCompat.getDrawable(context, iconRes!!)
        } else {
            getDialogTypeIconDrawable()
        }
    }

    private fun getDialogTypeIconDrawable(): Drawable? {
        return when (dialogType) {
            ERROR -> {
                ContextCompat.getDrawable(context, R.drawable.ic_error)
            }
            WARNING -> {
                ContextCompat.getDrawable(context, R.drawable.ic_warning)
            }
            INFO -> {
                ContextCompat.getDrawable(context, R.drawable.ic_info)
            }
            else -> {
                ContextCompat.getDrawable(context, R.drawable.ic_success)
            }
        }
    }

    private fun getIconColor(): Int {
        return when {
            iconColor != null -> {
                iconColor!!
            }
            else -> {
                getDialogTypeIconColor()
            }
        }
    }

    private fun getDialogTypeIconColor(): Int {
        return when (dialogType) {
            WARNING -> {
                ContextCompat.getColor(context, R.color.colorIconDark)
            }
            else -> {
                ContextCompat.getColor(context, R.color.colorIconLight)
            }
        }
    }
}