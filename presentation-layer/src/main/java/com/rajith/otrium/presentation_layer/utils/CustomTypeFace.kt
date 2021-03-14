package com.rajith.otrium.presentation_layer.utils

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.TypefaceSpan

class CustomTypeFace(val typefaceFont: Typeface) : TypefaceSpan("") {

    override fun updateDrawState(textPaint: TextPaint) {
        applyCustomTypeFace(textPaint)
    }

    override fun updateMeasureState(textPaint: TextPaint) {
        applyCustomTypeFace(textPaint)
    }

    private fun applyCustomTypeFace(paint: Paint) {
        val oldTypeface = paint.typeface
        val oldStyle = oldTypeface?.style ?: 0

        val fake = oldStyle and typefaceFont.style.inv()
        if (fake and Typeface.BOLD != 0) {
            paint.isFakeBoldText = true
        }

        if (fake and Typeface.ITALIC != 0) {
            paint.textSkewX = -0.25f
        }

        paint.typeface = typefaceFont
    }

}