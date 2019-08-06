package com.nicolaihansen.nicedialog

import android.graphics.Bitmap

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.*


class ImageDialog private constructor(
    context: Context,
    private var bitmap: Bitmap,
    private var imageHeight: Int?,
    private var imageScaleType: ImageView.ScaleType?,
    private var backgroundColor: Int?,
    private var textColor: Int?,
    private var buttonColor: Int?,
    private var title: String,
    private var message: String,
    private var positiveText: String?,
    private var positiveListener: OnClickListener?,
    private var negativeText: String?,
    private var negativeListener: OnClickListener?,
    private var inputHint: String?,
    private var inputListener: OnInputListener?
) : AlertDialog(context) {

    private lateinit var bannerView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var messageTextView: TextView
    private lateinit var buttonView: LinearLayout
    private lateinit var positiveButton: TextView
    private lateinit var negativeButton: TextView
    private var inputView: EditText? = null

    interface OnInputListener {
        fun onInput(text: String?)
    }

    interface OnClickListener {
        fun onClick(dialog: ImageDialog)
    }

    data class Builder(
        private val context: Context, val bitmap: Bitmap
    ) {

        private var imageHeight: Int? = null
        private var imageScaleType: ImageView.ScaleType? = null
        private var backgroundColor: Int? = null
        private var textColor: Int? = null
        private var buttonColor: Int? = null
        private var title: String = ""
        private var message: String = ""
        private var positiveText: String? = null
        private var positiveListener: OnClickListener? = null
        private var negativeText: String? = null
        private var negativeListener: OnClickListener? = null
        private var inputHint: String? = null
        private var inputListener: OnInputListener? = null

        fun setImageHeight(height: Int): Builder {
            this.imageHeight = height.px
            return this
        }

        fun setImageScaleType(scaleType: ImageView.ScaleType): Builder {
            this.imageScaleType = scaleType
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

        fun create() = ImageDialog(
            context, bitmap, imageHeight, imageScaleType, backgroundColor,
            textColor, buttonColor, title, message, positiveText,
            positiveListener, negativeText, negativeListener, inputHint, inputListener
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_banner)

        bannerView = findViewById(R.id.banner)
        prepareImage()
        titleTextView = findViewById(R.id.title)
        titleTextView.text = title
        messageTextView = findViewById(R.id.message)
        messageTextView.text = message
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
            inputView?.hint = inputHint
            inputView?.visibility = View.VISIBLE
        }
    }

    private fun prepareButtons() {
        positiveListener?.let {
            positiveText?.let { text -> positiveButton.text = text }
            buttonColor?.let { positiveButton.setTextColor(it) }
            buttonView.visibility = View.VISIBLE
            positiveButton.visibility = View.VISIBLE
            positiveButton.setOnClickListener {
                val text = inputView?.text.toString()
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

    private fun prepareImage() {
        imageHeight?.let {
            val params = bannerView.layoutParams
            params.height = it
            bannerView.layoutParams = params
        }
        bannerView.setImageBitmap(bitmap)
        bannerView.scaleType = imageScaleType
    }
}