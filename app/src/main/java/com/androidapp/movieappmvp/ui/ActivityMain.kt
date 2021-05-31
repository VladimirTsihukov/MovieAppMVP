package com.androidapp.movieappmvp.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import com.androidapp.movieappmvp.App
import com.androidapp.movieappmvp.R
import com.androidapp.movieappmvp.presenter.PresenterMain
import com.androidapp.movieappmvp.presenter.view.ViewMain
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

private const val REG_CODE_SETTING = 789

class ActivityMain : MvpAppCompatActivity(), ViewMain {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private lateinit var snackBar: Snackbar

    private val navigator =
        SupportAppNavigator(
            this,
            supportFragmentManager,
            R.id.nav_host_fragment_container
        )

    private val moxyPresenter by moxyPresenter {
        PresenterMain().apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.instance.appComponent.inject(this)

        initSnackBar()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }
        moxyPresenter.backClicked()
    }

    private fun initSnackBar() {
        snackBar = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            Snackbar.make(container_connect_internet, R.string.snackBar_title_device_is_offline_version_q,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.dialog_button_yes) {
                    startActivityForResult(
                        Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY),
                        REG_CODE_SETTING
                    )
                }
        } else {
            Snackbar.make(container_connect_internet, R.string.snackBar_title_device_is_offline,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.dialog_button_hide) {
                    snackBar.dismiss()
                }
        }.setTextColor(Color.RED)
            .setActionTextColor(getColor(R.color.app_assent))
    }

    override fun setVisibilityProgressBar(checkInternet: Boolean) {
        if (checkInternet) {
            snackBar.dismiss()
        } else {
            snackBar.show()
        }
    }
}