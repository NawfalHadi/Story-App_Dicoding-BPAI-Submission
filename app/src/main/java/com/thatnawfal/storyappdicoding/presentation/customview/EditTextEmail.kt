package com.thatnawfal.storyappdicoding.presentation.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.thatnawfal.storyappdicoding.R
import com.thatnawfal.storyappdicoding.utils.EMAIL_FORMAT

class EditTextEmail : AppCompatEditText {

    private lateinit var wrongFormat : Drawable
    private lateinit var matchFormat : Drawable

    private var isWrong = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        wrongFormat = ContextCompat.getDrawable(context, R.drawable.bg_form_error) as Drawable
        matchFormat = ContextCompat.getDrawable(context, R.drawable.bg_form) as Drawable

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do Nothing
            }

            override fun onTextChanged(c: CharSequence, start: Int, before: Int, count: Int) {
                if (c.isNotEmpty()) {
                    if (!c.toString().matches(EMAIL_FORMAT)){
                        error = context.getString(R.string.email_invalid)
                        isWrong = true
                    } else {
                        error = null
                        isWrong = false
                    }
                } else {
                    error = null
                    isWrong = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                // Do Nothing
            }
        })
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        background = if (isWrong) wrongFormat else matchFormat
    }
}