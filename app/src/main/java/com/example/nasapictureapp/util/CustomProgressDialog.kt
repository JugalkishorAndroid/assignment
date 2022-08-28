package com.example.nasapictureapp.util

import android.app.Dialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.example.nasapictureapp.R
import com.example.nasapictureapp.databinding.LayoutCustomProgressBarBinding

class CustomProgressDialog {

    var dialog: CustomDialog? = null
    private lateinit var binding: LayoutCustomProgressBarBinding

    fun show(context: Context): Dialog {
        return show(context, null)
    }

    fun show(context: Context, title: CharSequence?): Dialog {
        binding = LayoutCustomProgressBarBinding.inflate(LayoutInflater.from(context))
        if (title != null) {
            binding.cpTitle.text = title
        }

//To set colors programmatically
        // Card Color
//        binding.cpCardView.setCardBackgroundColor(ResourcesCompat.getColor(context.resources, R.color.customProgressBarBackground, null))

        // Progress Bar Color
//        setColorFilter(binding.cpProgressBar.indeterminateDrawable, ResourcesCompat.getColor(context.resources, R.color.color_accent, null))

        // Text Color
//        binding.cpTitle.setTextColor(Color.WHITE)

        (if (Build.VERSION.SDK_INT < 24) binding.animatedLoader.drawable as AnimatedVectorDrawableCompat else binding.animatedLoader.drawable as AnimatedVectorDrawable).start()

        dialog = CustomDialog(context)
        dialog?.setContentView(binding.root)
        dialog?.setCancelable(true)
        dialog?.show()
        return dialog!!
    }

    fun dismiss(){
        if (dialog != null) {
            if (dialog!!.isShowing) {
                dialog?.dismiss()
            }
        }
    }

    private fun setColorFilter(drawable: Drawable, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            @Suppress("DEPRECATION")
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    class CustomDialog(context: Context) : Dialog(context, R.style.CustomDialogTheme) {
        /*init {
            // Set Semi-Transparent Color for Dialog Background
            window?.decorView?.rootView?.setBackgroundResource(R.color.customDialogBackground)
            window?.decorView?.setOnApplyWindowInsetsListener { _, insets ->
                insets.consumeSystemWindowInsets()
            }
        }*/
    }
}