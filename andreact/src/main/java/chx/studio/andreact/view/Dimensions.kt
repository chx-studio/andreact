package chx.studio.andreact.view

import android.util.DisplayMetrics
import android.util.TypedValue
import chx.studio.andreact.Andreact

private fun DisplayMetrics.px(value: Number, unit: Int): Int {
    return TypedValue.applyDimension(unit, value.toFloat(), this).toInt()
}

private fun Number.px(unit: Int): Int {
    return Andreact.displayMetrics.px(this, unit)
}

val Number.px get() = px(TypedValue.COMPLEX_UNIT_PX)
val Number.dp get() = px(TypedValue.COMPLEX_UNIT_DIP)
val Number.sp get() = px(TypedValue.COMPLEX_UNIT_SP)
val Number.pt get() = px(TypedValue.COMPLEX_UNIT_PT)
val Number.inch get() = px(TypedValue.COMPLEX_UNIT_IN)
val Number.mm get() = px(TypedValue.COMPLEX_UNIT_MM)

val Number.rp: Int
    get() {
        val width = Andreact.displayMetrics.widthPixels.toDouble()
        val designWidth = Andreact.designWidth
        return (width / designWidth * this.toDouble()).toInt()
    }