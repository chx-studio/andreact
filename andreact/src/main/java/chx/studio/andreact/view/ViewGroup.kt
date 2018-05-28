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
        children.clear()
        children.addAll(widgets)
    }

    fun children(widgets: Collection<Widget>) {
        children.clear()
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

    private fun Widget.keyOrIndex(index: Int): Int {
        return if (key > 0) key else index
    }

    private fun removeNonExistingElements() {
        if (childElements.isEmpty()) return
        val nextChildMap = mutableMapOf<Int, Widget>().also { map ->
            children.forEachIndexed { index, widget ->
                map[widget.keyOrIndex(index)] = widget
            }
        }
        childElements.forEachIndexed { prevIndex, prevChildElement ->
            val specNextChild = nextChildMap[prevChildElement.widget.keyOrIndex(prevIndex)]
            val isOldChildWidgetStillExists = specNextChild != null
            if (!isOldChildWidgetStillExists) {
                removeChild(prevIndex)
            }
        }
    }

    private fun addOrMoveExistingElements() {
        if (children.isEmpty()) return
        val prevChildElementMap = mutableMapOf<Int, Element>().also { map ->
            childElements.forEachIndexed { index, element ->
                map[element.widget.keyOrIndex(index)] = element
            }
        }
        children.forEachIndexed { nextIndex, nextChildWidget ->
            val specPrevElement = prevChildElementMap[nextChildWidget.keyOrIndex(nextIndex)]
            val isNewChildWidgetAlreadyExists = specPrevElement != null
            if (isNewChildWidgetAlreadyExists) {
                val prevIndex = childElements.indexOf(specPrevElement)
                val needsMoving = prevIndex != nextIndex
                if (needsMoving) {
                    moveChild(prevIndex, nextIndex)
                }
                specPrevElement!!.widget = nextChildWidget
                specPrevElement.performRebuild()
            } else {
                addChild(nextIndex, nextChildWidget)
            }
        }
    }

    private fun addChild(index: Int, childWidget: Widget) {
        val childElement = childWidget.createElement()
        childElement.depth = this.element.depth + 1
        childElements.add(index, childElement)

        val childView = childElement.createView(view.context)
        view.addView(childView, index)
    }

    private fun moveChild(prevIndex: Int, nextIndex: Int) {
        val childView = view.getChildAt(prevIndex)
        view.removeViewAt(prevIndex)
        view.addView(childView, nextIndex)

        val childElement = childElements[prevIndex]
        childElements.removeAt(prevIndex)
        childElements.add(nextIndex, childElement)
    }

    private fun removeChild(index: Int) {
        view.removeViewAt(index)
        childElements.removeAt(index)
    }
}