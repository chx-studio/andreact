package chx.studio.andreact.view

import android.content.Context
import android.widget.FrameLayout

open class Stack : ViewGroup<FrameLayout>() {
    override fun createView(context: Context) = FrameLayout(context)
}