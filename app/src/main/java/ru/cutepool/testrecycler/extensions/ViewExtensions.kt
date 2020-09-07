package ru.cutepool.testrecycler.extensions

import android.content.res.Resources
import android.view.View


fun View.dpToPx(dp: Int): Int {
    return (dp * Resources.getSystem().displayMetrics.density).toInt()
}