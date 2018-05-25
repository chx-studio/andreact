package chx.studio.andreact

import android.content.Context
import android.util.Log
import android.view.View

abstract class Element(open var widget: Widget) {
    var depth = 1
    var dirty = true

    abstract fun createView(context: Context): View

    fun rebuild() {
        if (!dirty) return
        Log.e("@", "${this::class.java.name}.rebuild")
        performRebuild()
        dirty = false
    }

    abstract fun performRebuild()

    fun markNeedsBuild(buildContext: BuildContext) {
        dirty = true
        buildContext.scheduleBuildFor(this)
    }

    companion object {
        val comparator = Comparator<Element> { a, b ->
            when (true) {
                a.depth > b.depth -> 1
                a.depth < b.depth -> -1
                a.dirty && !b.dirty -> 1
                !a.dirty && b.dirty -> -1
                else -> 0
            }
        }
    }
}