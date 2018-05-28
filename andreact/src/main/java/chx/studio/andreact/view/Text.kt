package chx.studio.andreact.view

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import android.widget.TextView

open class Text(var text: String = "") : View<TextView>() {
    var textColor: Int = Color.GRAY
    var textSize: Int = 16.sp
    var textStyle: Int = 0
    var textWeight: Int = 0
    var fontFamily: Int = 0

    override fun createView(context: Context) = TextView(context)

    override fun bindView() {
        super.bindView()
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
        view.setTextColor(textColor)
        view.text = text
    }
}