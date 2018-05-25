package chx.studio.andreact.sample

import android.graphics.Color
import android.util.Log
import chx.studio.andreact.Andreact
import chx.studio.andreact.Component
import chx.studio.andreact.Widget
import chx.studio.andreact.view.*

class App : Component() {
    var count = 0

    private val updateCount = {
        count++
        Andreact.update(this)
    }

    override fun build(): Widget {
        Log.e("@", "App.build")
        return Column().apply {
            children(
                Text(count.toString()).apply {
                    height = 150.dp
                    marginTop = 100.rp
                    padding(32.dp)
                    backgroundColor = Color.parseColor("#00838f")
                    textSize = 80.sp
                    textColor = Color.WHITE
                },
                Button("BUTTON").apply {
                    padding(64)
                    width = 750.rp
                    backgroundColor = Color.parseColor("#2196f3")
                    textColor = Color.WHITE
                    textSize = 24.sp
                    onClick = updateCount
                }
            )
        }
    }
}