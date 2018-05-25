package chx.studio.andreact.view

import android.content.Context
import android.widget.LinearLayout

class Row : ViewGroup<LinearLayout>() {
    override fun createView(context: Context): LinearLayout {
        return LinearLayout(context).apply { orientation = LinearLayout.HORIZONTAL }
    }
}