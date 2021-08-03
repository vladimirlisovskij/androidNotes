package com.example.notes.cleanArchitecture.view.widgetConfigActivity

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.notes.R
import com.example.notes.classes.backCoordinator.OnBackEmitter
import com.example.notes.classes.base.baseActivity.BaseActivity
import com.example.notes.cleanArchitecture.presenter.WidgetConfigActivity.WidgetConfigViewModel
import com.example.notes.cleanArchitecture.presenter.widget.NoteWidget
import com.example.notes.di.Injector
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class WidgetConfigActivity: BaseActivity<WidgetConfigViewModel>(R.layout.activity_main) {

    @Inject override lateinit var viewModel: WidgetConfigViewModel
    @Inject lateinit var navigatorHolder: NavigatorHolder
    @Inject lateinit var onBackEmitter: OnBackEmitter

    private var appWidgetId: Int = AppWidgetManager.INVALID_APPWIDGET_ID
    private val navigator = AppNavigator(this, R.id.fragmentContainer)

    private var disposable = Disposable.empty()

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.component.inject(this)
        setResult(RESULT_CANCELED)
        super.onCreate(savedInstanceState)
        appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                finish()
        }
        disposable = viewModel.resultEmitter
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { note ->
                NoteWidget.setViewContent(appWidgetId, this, note)

                val intent = Intent().apply {
                    putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                }

                setResult(RESULT_OK, intent)
                finish()
            }
        viewModel.onReady(appWidgetId)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    override fun onBackPressed() {
        onBackEmitter.emit()
    }

    override fun onResume() {
        Injector.component.inject(this)
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}