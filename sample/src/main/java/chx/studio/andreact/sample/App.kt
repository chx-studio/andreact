package chx.studio.andreact.sample

import android.graphics.Color
import android.util.Log
import chx.studio.andreact.Andreact
import chx.studio.andreact.Component
import chx.studio.andreact.Widget
import chx.studio.andreact.view.Button
import chx.studio.andreact.view.Container
import chx.studio.andreact.view.Text

class App : Component() {
    var count = 0

    override fun build(): Widget {
        Log.e("@", "App.build")
        return Container().apply {
            padding(100, 50)
            children(
                Text().apply {
                    text = "hello, world."
                    height = 500
                    marginTop = 100
                    paddingTop = 300
                    paddingHorizontal(32)
                    backgroundColor = Color.parseColor("#00838f")
                    textColor = Color.WHITE
                },
                Button().apply {
                    text = "count: $count"
                    padding(64)
                    margin(64)
                    backgroundColor = Color.parseColor("#2196f3")
                    textColor = Color.WHITE
                    textSize = 24
                    onClick = {
                        count++
                        Andreact.update(this@App)
                    }
                }
            )
        }
    }
}