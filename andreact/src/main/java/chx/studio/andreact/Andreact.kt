package chx.studio.andreact

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.widget.FrameLayout
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

object Andreact {
    private var refreshRate = 16L
    internal var displayMetrics = DisplayMetrics()
    var designWidth = 750
    private var rootViewRef = WeakReference<FrameLayout>(null)

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
        val view = widget.createElement().createView(context)
        val rootView = FrameLayout(context)
        rootView.addView(view)
        rootViewRef = WeakReference(rootView)
        return rootView
    }

    fun update(component: Component) {
        component.updateState(buildContext)
    }
}