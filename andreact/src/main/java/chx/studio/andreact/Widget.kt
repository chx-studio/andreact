package chx.studio.andreact

abstract class Widget {
    var key: Int = 0

    abstract fun createElement(): Element

    protected open fun willMount() {}

    protected open fun didMount() {}

    open fun willUpdate() {}

    open fun didUpdate() {}

    companion object {
        private var nextRootIndex: Int = 0

        fun shouldUpdate(prevWidget: Widget, nextWidget: Widget): Boolean {
            return prevWidget::class == nextWidget::class && prevWidget.key == nextWidget.key
        }
    }
}