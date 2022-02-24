package com.example.anew.util

import android.os.Build
import android.util.TypedValue
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

@BindingAdapter("isColored")
fun MaterialCardView.isColored(isColored: Boolean) {
    val typedValueColor = TypedValue()
    context.theme
        .resolveAttribute(android.R.attr.colorAccent, typedValueColor, true)
    strokeColor = if (isColored) {
        typedValueColor.data
    } else {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.resources
                .getColor(android.R.color.transparent, context.theme)
        } else {
            context.resources.getColor(android.R.color.transparent)
        }
    }
}


fun View.showKeyboard() = (this.context as? Activity)?.showKeyboard()
fun View.hideKeyboard() = (this.context as? Activity)?.hideKeyboard()

fun Fragment.showKeyboard() = activity?.let(FragmentActivity::showKeyboard)
fun Fragment.hideKeyboard() = activity?.hideKeyboard()

fun Context.showKeyboard() = (this as? Activity)?.showKeyboard()
fun Context.hideKeyboard() = (this as? Activity)?.hideKeyboard()

fun Activity.showKeyboard() =
    ViewCompat.getWindowInsetsController(window.decorView)?.show(WindowInsetsCompat.Type.ime())

fun Activity.hideKeyboard() =
    ViewCompat.getWindowInsetsController(window.decorView)?.hide(WindowInsetsCompat.Type.ime())

fun hideKeyboard(view: View) =
    (view.context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(view.windowToken, 0)
