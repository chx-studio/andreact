package chx.studio.andreact

abstract class Widget {
    var key: String = ""

    abstract fun createElement(): Element

    protected open fun willMount() {}

    protected open fun didMount() {}

    open fun willUpdate() {}

    open fun didUpdate() {}

    companion object {
        fun shouldUpdate(prevWidget: Widget, nextWidget: Widget): Boolean {
            return prevWidget::class == nextWidget::class && prevWidget.key == nextWidget.key
        }
    }
}