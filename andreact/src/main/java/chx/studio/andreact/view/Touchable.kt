package chx.studio.andreact.view

import android.content.Context
import android.widget.FrameLayout

class Touchable : ViewGroup<FrameLayout>() {
    var onClick: () -> Unit = { }

    override fun createView(context: Context) = FrameLayout(context)

    override fun bindView() {
        super.bindView()
        view.setOnClickListener { onClick() }
    }
}