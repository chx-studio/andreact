package chx.studio.andreact.view

import android.content.Context
import android.view.View
import chx.studio.andreact.Widget
import com.google.android.flexbox.*

class Flexbox : ViewGroup<FlexboxLayout>() {
    var flexDirection = FlexDirection.COLUMN
    var flexWrap = FlexWrap.NOWRAP
    var justifyContent = JustifyContent.FLEX_START
    var alignItems = AlignItems.STRETCH
    var alignContent = AlignContent.STRETCH

    override fun createView(context: Context): FlexboxLayout {
        return FlexboxLayout(context)
    }

    override fun bindView() {
        super.bindView()
        view.flexDirection = flexDirection
        view.flexWrap = flexWrap
        view.justifyContent = justifyContent
        view.alignItems = alignItems
        view.alignContent = alignContent
    }

}
