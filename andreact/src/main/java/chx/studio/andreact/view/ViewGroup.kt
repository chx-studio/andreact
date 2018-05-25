package chx.studio.andreact.view

import android.content.Context
import chx.studio.andreact.Element
import chx.studio.andreact.Widget
import chx.studio.andreact.runOnUiThread

abstract class ViewGroup<V : android.view.ViewGroup> : View<V>() {
    private lateinit var element: Element
    private var children = mutableListOf<Widget>()
    private var childElements = mutableListOf<Element>()

    fun children(vararg widgets: Widget) {
        children.addAll(widgets)
    }

    fun children(widgets: Collection<Widget>) {
        children.addAll(widgets)
    }

    override fun createElement(): Element {
        return object : Element(this@ViewGroup) {
            override fun createView(context: Context): android.view.View {
                element = this
                view = this@ViewGroup.createView(context)
                bindView()
                return view
            }

            override fun performRebuild() {
                runOnUiThread {
                    @Suppress("UNCHECKED_CAST")
                    (widget as? chx.studio.andreact.view.ViewGroup<V>)?.let { newWidget ->
                        newWidget.childElements = childElements
                        newWidget.view = view
                        newWidget.bindView()
                    }
                }
            }
        }
    }

    override fun bindView() {
        super.bindView()
        removeNonExistingElements()
        addOrMoveExistingElements()
    }

    private fun removeNonExistingElements() {
        val newChildMap = mutableMapOf<Int, Widget>().also { map ->
            children.forEachIndexed { index, widget ->
                var key = widget.key
                if (key <= 0) key = index
                map[key] = widget
            }
        }
        childElements.forEachIndexed { prevIndex, prevChildElement ->
            var prevKey = prevChildElement.widget.key
            if (prevKey <= 0) prevKey = prevIndex
            val specNewChild = newChildMap[prevKey]
            val isOldChildWidgetStillExists = specNewChild != null
            if (!isOldChildWidgetStillExists) {
                view.removeViewAt(prevIndex)
                childElements.removeAt(prevIndex)
            }
        }
    }

    private fun addOrMoveExistingElements() {
        val prevChildElementMap = mutableMapOf<Int, Element>().also { map ->
            childElements.forEachIndexed { index, element ->
                var key = element.widget.key
                if (key <= 0) key = index
                map[key] = element
            }
        }
        children.forEachIndexed { newIndex, newChildWidget ->
            var newKey = newChildWidget.key
            if (newKey <= 0) newKey = newIndex
            val specPrevElement = prevChildElementMap[newKey]
            val isNewChildWidgetAlreadyExists = specPrevElement != null
            if (isNewChildWidgetAlreadyExists) {
                val prevIndex = childElements.indexOf(specPrevElement)
                val needsMoving = prevIndex != newIndex
                if (needsMoving) {
                    val childView = view.getChildAt(prevIndex)
                    view.removeViewAt(prevIndex)
                    view.addView(childView, newIndex)
                    childElements.removeAt(prevIndex)
                    childElements.add(newIndex, specPrevElement!!)
                }
                specPrevElement!!.widget = newChildWidget
                specPrevElement.performRebuild()
            } else {
                val newElement = newChildWidget.createElement()
                newElement.depth = this.element.depth + 1
                val childView = newElement.createView(view.context)
                view.addView(childView, newIndex)
                childElements.add(newIndex, newElement)
            }
        }
    }
}