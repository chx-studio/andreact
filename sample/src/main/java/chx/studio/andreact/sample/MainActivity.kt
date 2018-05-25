package chx.studio.andreact.sample

import android.app.Activity
import android.os.Bundle
import android.widget.FrameLayout
import chx.studio.andreact.Andreact
import chx.studio.andreact.Widget

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(App())
    }

    private fun setContentView(widget: Widget) {
        val layout = FrameLayout(this)
        layout.addView(Andreact.render(this, widget))
        setContentView(layout)
    }
}