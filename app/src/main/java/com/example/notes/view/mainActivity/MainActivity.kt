package com.example.notes.view.mainActivity

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.R
import com.example.notes.application.MainApplication
import com.example.notes.base.BaseView
import com.example.notes.presenter.mainActivity.MainViewModel
import com.example.notes.presenter.mainActivity.MainViewModelImpl
import com.example.notes.presenter.recycler.RecyclerViewModel
import com.example.notes.view.recycler.ListNotesView
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import javax.inject.Inject
import javax.inject.Provider

class MainActivity
    : AppCompatActivity()
{
    @Inject
    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator = AppNavigator(this, R.id.fragmentContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        MainApplication.instance.presenterInjector.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.onViewReady()
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}