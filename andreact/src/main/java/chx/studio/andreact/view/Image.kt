package chx.studio.andreact.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView

class Image : View<ImageView>() {
    var scaleType = ImageView.ScaleType.FIT_CENTER
    var drawable: Drawable? = null

    override fun createView(context: Context) = ImageView(context)

    override fun bindView() {
        super.bindView()
        view.scaleType = scaleType
        view.setImageDrawable(drawable)
    }
}