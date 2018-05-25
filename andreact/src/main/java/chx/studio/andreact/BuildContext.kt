package chx.studio.andreact

class BuildContext {
    private val dirtyElements = arrayListOf<Element>()
    private var dirtyElementsNeedsResorting = false
    private var scheduledFlushDirtyElements = false

    fun scheduleBuildFor(element: Element) {
        if (element in dirtyElements) {
            dirtyElementsNeedsResorting = true
            return
        }
        if (!scheduledFlushDirtyElements) {
            scheduledFlushDirtyElements = true
        }
        dirtyElements.add(element)
    }

    fun buildScope() {
        if (dirtyElements.isEmpty()) return
        scheduledFlushDirtyElements = true
        dirtyElementsNeedsResorting = false
        dirtyElements.sortWith(Element.comparator)
        scheduledFlushDirtyElements = false
        var dirtyCount = dirtyElements.size
        var index = 0
        while (index < dirtyCount) {
            dirtyElements[index].rebuild()
            index++
            if (dirtyCount < dirtyElements.size && dirtyElementsNeedsResorting) {
                dirtyElements.sortWith(Element.comparator)
                dirtyCount = dirtyElements.size
                while (index > 0 && dirtyElements[index - 1].dirty) {
                    index--
                }
            }
        }
        dirtyElements.clear()
        scheduledFlushDirtyElements = false
        dirtyElementsNeedsResorting = false
    }
}