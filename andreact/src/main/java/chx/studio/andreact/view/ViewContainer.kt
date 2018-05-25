package chx.studio.andreact.view

import android.content.Context
import android.widget.RelativeLayout
import chx.studio.andreact.Element
import chx.studio.andreact.Widget
import chx.studio.andreact.replace
import chx.studio.andreact.runOnUiThread

open class ViewContainer : View<RelativeLayout>() {
    lateinit var child: Widget

    private lateinit var element: Element
    private lateinit var childElement: Element

    override fun createElement(): Element {
        return object : Element(this@ViewContainer) {
            override fun createView(context: Context): android.view.View {
                element = this
                view = this@ViewContainer.createView(context)
                bindView()
                return view
            }

            override fun performRebuild() {
                runOnUiThread {
                    @Suppress("UNCHECKED_CAST")
                    (widget as? ViewContainer)?.let { newWidget ->
                        newWidget.childElement = childElement
                        newWidget.view = view
                        newWidget.bindView()
                    }
                }
            }
        }
    }

    override fun createView(context: Context) = RelativeLayout(context)

    override fun bindView() {
        super.bindView()
        val nextWidget = child
        if (!this::childElement.isInitialized) {
            val newChildElement = nextWidget.createElement()
            newChildElement.depth = this.element.depth + 1
            val childView = newChildElement.createView(view.context)
            view.addView(childView)
            childElement = newChildElement
        } else {
            val prevWidget = childElement.widget
            if (prevWidget.key == nextWidget.key) {
                childElement.widget = nextWidget
                childElement.performRebuild()
            } else {
                val newChildElement = nextWidget.createElement()
                newChildElement.depth = this.element.depth + 1
                val childView = newChildElement.createView(view.context)
                childView.replace(view.getChildAt(0))
                childElement = newChildElement
            }
        }
    }
}