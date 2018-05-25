package chx.studio.andreact

import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup

fun runOnUiThread(fn: () -> Unit) {
    val mainLooper = Looper.getMainLooper()
    if (Thread.currentThread() == mainLooper.thread) {
        fn()
    } else {
        Handler(mainLooper).apply {
            removeCallbacksAndMessages(null)
            post { fn() }
        }
    }
}

fun View.replace(prevView: View) {
    (prevView.parent as? ViewGroup)?.let { parent ->
        val index = parent.indexOfChild(prevView)
        parent.addView(this, index)
        parent.removeView(prevView)
        id = prevView.id
    }
}