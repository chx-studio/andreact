package chx.studio.andreact

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

object Andreact {
    private var refreshRate = 16L
    internal var displayMetrics = DisplayMetrics()
    internal var designWidth = 750

    private val buildContext by lazy {
        BuildContext().apply {
            Observable.interval(refreshRate, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .doOnNext { buildScope() }
                .subscribe()
        }
    }

    fun render(context: Context, widget: Widget): View {
        displayMetrics = context.resources.displayMetrics
        return widget.createElement().createView(context)
    }

    fun update(component: Component) {
        component.updateState(buildContext)
    }
}