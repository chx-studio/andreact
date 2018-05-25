package chx.studio.andreact

import android.content.Context
import android.view.View

abstract class Component : Widget() {
    private lateinit var element: Element

    override fun createElement(): Element {
        return object : Element(this@Component) {
            private lateinit var builtElement: Element
            private lateinit var builtView: View

            override fun createView(context: Context): View {
                element = this
                willMount()
                builtElement = build().createElement()
                builtView = builtElement.createView(context)
                didMount()
                return builtView
            }

            override fun performRebuild() {
                widget.willUpdate()
                val prevBuiltWidget = builtElement.widget
                val nextBuiltWidget = build()
                if (Widget.shouldUpdate(prevBuiltWidget, nextBuiltWidget)) {
                    builtElement.widget = nextBuiltWidget
                    builtElement.performRebuild()
                } else {
                    builtElement = nextBuiltWidget.createElement()
                    val lastView = builtElement.createView(builtView.context)
                    lastView.replace(builtView)
                    builtView = lastView
                }
                widget.didUpdate()
            }
        }
    }

    abstract fun build(): Widget

    fun updateState(buildContext: BuildContext) {
        element.markNeedsBuild(buildContext)
    }
}