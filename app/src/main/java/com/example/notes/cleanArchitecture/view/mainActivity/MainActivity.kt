package com.example.notes.cleanArchitecture.view.mainActivity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.notes.R
import com.example.notes.classes.backCoordinator.OnBackEmitter
import com.example.notes.classes.base.baseActivity.BaseActivity
import com.example.notes.classes.services.ConnectivityStatusService
import com.example.notes.classes.services.DataBaseUpdateService
import com.example.notes.cleanArchitecture.presenter.mainActivity.MainViewModel
import com.example.notes.di.Injector
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import javax.inject.Inject

class MainActivity: BaseActivity<MainViewModel>(R.layout.activity_main)
{
    @Inject override lateinit var viewModel: MainViewModel
    @Inject lateinit var navigatorHolder: NavigatorHolder
    @Inject lateinit var onBackEmitter: OnBackEmitter

    private val updateConnection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) { }

        override fun onServiceDisconnected(name: ComponentName?) { }
    }

    private val connectivityStatusConnection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) { }

        override fun onServiceDisconnected(name: ComponentName?) { }
    }

    private val navigator = object: AppNavigator(this, R.id.fragmentContainer) {
        override fun setupFragmentTransaction(
            screen: FragmentScreen,
            fragmentTransaction: FragmentTransaction,
            currentFragment: Fragment?,
            nextFragment: Fragment
        ) {
            fragmentTransaction.setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_in,
                R.anim.fade_out,
                R.anim.slide_out
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onBackPressed() {
        onBackEmitter.emit()
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
        Intent(this, ConnectivityStatusService::class.java).also { intent ->
            bindService(intent, connectivityStatusConnection, Context.BIND_AUTO_CREATE)
        }
        Intent(this, DataBaseUpdateService::class.java).also { intent ->
            bindService(intent, updateConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
        unbindService(connectivityStatusConnection)
        unbindService(updateConnection)
    }
}