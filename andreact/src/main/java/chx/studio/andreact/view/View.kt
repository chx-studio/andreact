package chx.studio.andreact.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import chx.studio.andreact.Element
import chx.studio.andreact.Widget
import chx.studio.andreact.runOnUiThread

abstract class View<V : android.view.View> : Widget(), Style<V> {
    override var width: Int = WRAP_CONTENT
    override var height: Int = WRAP_CONTENT
    override var paddingLeft: Int = 0
    override var paddingRight: Int = 0
    override var paddingTop: Int = 0
    override var paddingBottom: Int = 0
    override var marginLeft: Int = 0
    override var marginRight: Int = 0
    override var marginTop: Int = 0
    override var marginBottom: Int = 0
    override var background: Drawable? = null
    override var order: Int = 0
    override var flexGrow: Number = 0
    override var flexShrink: Number = 1
    override var flexBasisPercent: Number = -1
    override var absolute: Boolean = false
    override lateinit var view: V

    override fun createElement(): Element {
        return object : Element(this@View) {
            override fun createView(context: Context): android.view.View {
                view = this@View.createView(context)
                bindView()
                return view
            }

            override fun performRebuild() {
                runOnUiThread {
                    @Suppress("UNCHECKED_CAST")
                    (widget as? View<V>)?.let { newWidget ->
                        newWidget.view = view
                        newWidget.bindView()
                    }
                }
            }
        }
    }

    abstract fun createView(context: Context): V

    companion object {
        const val MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT
        const val WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT
    }
}