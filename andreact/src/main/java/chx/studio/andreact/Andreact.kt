package chx.studio.andreact

import android.content.Context
import android.view.View
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

open class Andreact {
    private var refreshRate = 16L

    private val buildContext by lazy {
        BuildContext().apply {
            Observable.interval(refreshRate, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .doOnNext { buildScope() }
                .subscribe()
        }
    }

    fun render(context: Context, widget: Widget): View {
        return widget.createElement().createView(context)
    }

    fun update(component: Component) {
        component.updateState(buildContext)
    }

    companion object : Andreact()
}