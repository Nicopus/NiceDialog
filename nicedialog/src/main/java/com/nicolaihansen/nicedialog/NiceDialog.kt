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
import android.widget.*
import com.nicolaihansen.nicedialog.DialogType.*


class NiceDialog private constructor(
    context: Context,
    private var dialogType: DialogType?,
    private var dialogShape: DialogShape?,
    private var iconOutlineColors: IntArray?,
    private var backgroundColor: Int?,
    private var textColor: Int?,
    private var buttonColor: Int?,
    private var title: String,
    private var message: String,
    private var iconRes: Int?,
    private var iconColor: Int?,
    private var positiveText: String?,
    private var positiveListener: OnClickListener?,
    private var negativeText: String?,
    private var negativeListener: OnClickListener?,
    private var inputHint: String?,
    private var inputListener: OnInputListener?
) : AlertDialog(context) {

    private lateinit var titleTextView: TextView
    private lateinit var messageTextView: TextView
    private lateinit var iconOutlineView: View
    private lateinit var iconView: ImageView
    private lateinit var buttonView: LinearLayout
    private lateinit var positiveButton: TextView
    private lateinit var negativeButton: TextView
    private lateinit var inputView: EditText

    interface OnInputListener {
        fun onInput(text: String?)
    }

    interface OnClickListener {
        fun onClick(dialog: NiceDialog)
    }

    data class Builder(
        private val context: Context
    ) {

        private var dialogType: DialogType? = null
        private var dialogShape: DialogShape? = null
        private var iconOutlineColors: IntArray? = null
        private var backgroundColor: Int? = null
        private var textColor: Int? = null
        private var buttonColor: Int? = null
        private var title: String = ""
        private var message: String = ""
        private var iconRes: Int? = null
        private var iconColor: Int? = null
        private var positiveText: String? = null
        private var positiveListener: OnClickListener? = null
        private var negativeText: String? = null
        private var negativeListener: OnClickListener? = null
        private var inputHint: String? = null
        private var inputListener: OnInputListener? = null


        fun setDialogType(dialogType: DialogType): Builder {
            this.dialogType = dialogType
            return this
        }

        fun setDialogShape(dialogShape: DialogShape): Builder {
            this.dialogShape = dialogShape
            return this
        }

        fun setIconOutlineColor(color: Int): Builder {
            this.iconOutlineColors = intArrayOf(color, color)
            return this
        }

        fun setIconOutlineColors(colors: IntArray): Builder {
            this.iconOutlineColors = colors
            return this
        }

        fun setBackgroundColor(color: Int): Builder {
            this.backgroundColor = color
            return this
        }

        fun setTextColor(color: Int): Builder {
            this.textColor = color
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

        fun setPositiveButton(text: String? = null, listener: OnClickListener): Builder {
            this.positiveText = text
            this.positiveListener = listener
            return this
        }

        fun setNegativeButton(text: String? = null, listener: OnClickListener): Builder {
            this.negativeText = text
            this.negativeListener = listener
            return this
        }

        fun setButtonColor(color: Int): Builder {
            this.buttonColor = color
            return this
        }

        fun addInputField(hint: String? = null, listener: OnInputListener): Builder {
            this.inputHint = hint
            this.inputListener = listener
            return this
        }

        fun create() = NiceDialog(
            context, dialogType, dialogShape, iconOutlineColors, backgroundColor,
            textColor, buttonColor, title, message, iconRes, iconColor, positiveText,
            positiveListener, negativeText, negativeListener, inputHint, inputListener
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        if (dialogShape == DialogShape.ARC) {
            setContentView(R.layout.dialog_icon_arc)
        } else {
            setContentView(R.layout.dialog_icon_mid)
        }
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
        prepareInput()
        buttonView = findViewById(R.id.button_view)
        positiveButton = findViewById(R.id.ok)
        negativeButton = findViewById(R.id.cancel)
        prepareButtons()

        prepareBackgroundColor()
        prepareTextColor()

    }

    private fun prepareInput() {
        inputListener?.let {
            inputView.hint = inputHint
            inputView.visibility = View.VISIBLE
        }
    }

    private fun prepareButtons() {
        positiveListener?.let {
            positiveText?.let { text -> positiveButton.text = text }
            buttonColor?.let { positiveButton.setTextColor(it) }
            buttonView.visibility = View.VISIBLE
            positiveButton.visibility = View.VISIBLE
            positiveButton.setOnClickListener {
                val text = inputView.text.toString()
                inputListener?.onInput(text)
                positiveListener?.onClick(this)

            }
        }

        negativeListener?.let {
            negativeText?.let { text -> negativeButton.text = text }
            buttonColor?.let { negativeButton.setTextColor(it) }
            buttonView.visibility = View.VISIBLE
            negativeButton.visibility = View.VISIBLE
            negativeButton.setOnClickListener {
                negativeListener?.onClick(this)
            }
        }
    }

    private fun prepareBackgroundColor() {
        backgroundColor?.let {
            val leftCorner = findViewById<ImageView>(R.id.left_corner)
            leftCorner.setColorFilter(it)
            val rightCorner = findViewById<ImageView>(R.id.right_corner)
            rightCorner.setColorFilter(it)
            val iconOutline = findViewById<ImageView>(R.id.icon_holder)
            iconOutline.setColorFilter(it)
            val bottom = findViewById<ImageView>(R.id.bottom)
            bottom.setColorFilter(it)
        }
    }

    private fun prepareTextColor() {
        textColor?.let {
            titleTextView.setTextColor(it)
            messageTextView.setTextColor(it)
        }
    }

    private fun getIconOutlineDrawable(): Drawable {
        val drawable: GradientDrawable
        when {
            iconOutlineColors != null -> {
                drawable = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, iconOutlineColors!!)
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
        return if (iconColor != null) {
            iconColor!!
        } else {
            getDialogTypeIconColor()
        }
    }

    private fun getDialogTypeIconColor(): Int {
        return when (dialogType) {
            WARNING -> {
                ContextCompat.getColor(context, R.color.colorDarkGrey)
            }
            else -> {
                Color.WHITE
            }
        }
    }

}