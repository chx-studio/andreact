package chx.studio.andreact.view

import android.content.Context
import chx.studio.andreact.Element
import chx.studio.andreact.Widget
import chx.studio.andreact.runOnUiThread

abstract class ViewGroup<V : android.view.ViewGroup> : View<V>() {
    private lateinit var element: Element
    var children = mutableListOf<Widget>()
    private var childElements = mutableListOf<Element>()

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

    private fun Widget.keyOrIndex(index: Int): String {
        return if (key.isNotEmpty()) key else index.toString()
    }

    private fun removeNonExistingElements() {
        if (childElements.isEmpty()) return
        val nextChildMap = mutableMapOf<String, Widget>().also { map ->
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
        val prevChildElementMap = mutableMapOf<String, Element>().also { map ->
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

        onAddChildView(childView, index)
    }

    private fun moveChild(fromIndex: Int, toIndex: Int) {
        val childView = view.getChildAt(fromIndex)
        view.removeViewAt(fromIndex)
        view.addView(childView, toIndex)

        val childElement = childElements[fromIndex]
        childElements.removeAt(fromIndex)
        childElements.add(toIndex, childElement)

        onMoveChildView(childView, fromIndex, toIndex)
    }

    private fun removeChild(index: Int) {
        val childView = view.getChildAt(index)
        view.removeViewAt(index)
        childElements.removeAt(index)
        onRemoveChildView(childView, index)
    }

    open fun onAddChildView(view: android.view.View, index: Int) {}

    open fun onRemoveChildView(view: android.view.View, index: Int) {}

    open fun onMoveChildView(view: android.view.View, fromIndex: Int, toIndex: Int) {}
}