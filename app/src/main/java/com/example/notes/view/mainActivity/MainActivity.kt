package com.example.notes.view.mainActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.R
import com.example.notes.di.Injector
import com.example.notes.presenter.coordinator.OnBackListener
import com.example.notes.presenter.mainActivity.MainViewModel
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import javax.inject.Inject

class MainActivity
    : AppCompatActivity()
    , OnBackListener.BackListener
{
    @Inject lateinit var viewModel: MainViewModel
    @Inject lateinit var navigatorHolder: NavigatorHolder
    @Inject lateinit var onBackListener: OnBackListener

    private val navigator = AppNavigator(this, R.id.fragmentContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.component.inject(this)
        onBackListener.registerForCalls(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.onViewReady()
    }

    override fun onBackPressed() {
        onBackListener.emit()
    }

    override fun onDestroy() {
        super.onDestroy()
        onBackListener.dispose()
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBack() {
        super.onBackPressed()
    }
}