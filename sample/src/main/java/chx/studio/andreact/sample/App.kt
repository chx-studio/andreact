package chx.studio.andreact.sample

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import chx.studio.andreact.Andreact
import chx.studio.andreact.Component
import chx.studio.andreact.Widget
import chx.studio.andreact.view.*
import com.google.android.flexbox.JustifyContent

class App : Component() {
    var count = 0

    private val updateCount = {
        count++
        Andreact.update(this)
    }

    override fun build(): Widget {
        Log.e("@", "App.build")
        return Flexbox().apply {
            justifyContent = JustifyContent.CENTER
            height = View.MATCH_PARENT
            children(
                Text(count.toString()).apply {
                    flexGrow = 1
                    height = 150.dp
                    padding(32.dp)
                    background = ColorDrawable(Color.parseColor("#00838f"))
                    textSize = 80.sp
                    textColor = Color.WHITE
                },
                Button("BUTTON").apply {
                    width = 750.rp
                    padding(64)
                    background = ColorDrawable(Color.parseColor("#2196f3"))
                    textColor = Color.WHITE
                    textSize = 24.sp
                    onClick = updateCount
                }
            )
        }
    }
}