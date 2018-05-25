package chx.studio.andreact.view

import android.view.ViewGroup as AndroidViewGroup

interface Style<V : android.view.View> {
    var width: Int
    var height: Int
    var paddingLeft: Int
    var paddingRight: Int
    var paddingTop: Int
    var paddingBottom: Int
    var marginLeft: Int
    var marginRight: Int
    var marginTop: Int
    var marginBottom: Int
    var backgroundColor: Int
    var borderStyle: Int
    var borderWidth: Int
    var borderColor: Int
    var borderRadius: Int
    var flex: Int
    var flexWrap: Int
    var alignSelf: Int
    var position: Int
    var left: Int
    var right: Int
    var top: Int
    var bottom: Int
    var view: V

    fun bindView() {
        view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
        view.setBackgroundColor(backgroundColor)
        view.layoutParams = (view.layoutParams ?: AndroidViewGroup.MarginLayoutParams(0, 0)).also {
            it.width = width
            it.height = height
            (it as? AndroidViewGroup.MarginLayoutParams)?.setMargins(marginLeft, marginTop, marginRight, marginBottom)
        }
    }

    fun size(width: Int, height: Int = width) {
        this.width = width
        this.height = height
    }

    fun paddingHorizontal(left: Int, right: Int = left) {
        paddingLeft = left
        paddingRight = right
    }

    fun paddingVertical(top: Int, bottom: Int = top) {
        paddingTop = top
        paddingBottom = bottom
    }

    fun padding(left: Int, top: Int = left, right: Int = left, bottom: Int = top) {
        paddingLeft = left
        paddingTop = top
        paddingRight = right
        paddingBottom = bottom
    }

    fun marginHorizontal(left: Int, right: Int = left) {
        marginLeft = left
        marginRight = right
    }

    fun marginVertical(top: Int, bottom: Int = top) {
        marginTop = top
        marginBottom = bottom
    }

    fun margin(left: Int, top: Int = left, right: Int = left, bottom: Int = top) {
        marginLeft = left
        marginTop = top
        marginRight = right
        marginBottom = bottom
    }
}