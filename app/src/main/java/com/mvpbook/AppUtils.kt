package com.mvpbook

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast

fun inflate(context: Context, viewId: Int, parent: ViewGroup? = null, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(viewId, parent, attachToRoot)
}

fun showToast(context: Context, strError: String) {
    Toast.makeText(context, strError, Toast.LENGTH_SHORT).show()
}
fun empty(value:EditText): Boolean {
if (value.text.isEmpty()) {
    value.error = "is empty"
    value.requestFocus()
    return false
}
    else return true
}


fun bold(context: Context): Typeface? {
    val bold = Typeface.createFromAsset(context.assets, "Roboto-Bold.ttf")
    return bold
}

fun regular(context: Context): Typeface? {
    val regular = Typeface.createFromAsset(context.assets, "Roboto-Regular.ttf")
    return regular
}

fun light(context: Context): Typeface? {
    val light = Typeface.createFromAsset(context.assets, "Roboto-Light.ttf")
    return light
}

fun sans(context: Context): Typeface? {
    val sans = Typeface.createFromAsset(context.assets, "open sans.ttf")
    return sans

}