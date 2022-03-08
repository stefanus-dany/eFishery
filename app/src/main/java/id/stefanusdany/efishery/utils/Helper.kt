package id.stefanusdany.efishery.utils

import android.view.View

object Helper {
    fun View.visibility(value: Boolean) {
        this.visibility = if (value) View.VISIBLE else View.GONE
    }
}