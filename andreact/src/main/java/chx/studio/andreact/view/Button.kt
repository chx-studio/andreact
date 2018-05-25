package chx.studio.andreact.view

import android.content.Context

open class Button(text: String = "") : Text(text) {
    var onClick: () -> Unit = { }

    override fun createView(context: Context) = android.widget.Button(context)

    override fun bindView() {
        super.bindView()
        view.setOnClickListener { onClick() }
    }
}