package com.androidapp.movieappmvp.di

import com.androidapp.movieappmvp.di.modules.ApiModule
import com.androidapp.movieappmvp.di.modules.AppModule
import com.androidapp.movieappmvp.di.modules.CiceroneModule
import com.androidapp.movieappmvp.di.modules.DatabaseModule
import com.androidapp.movieappmvp.di.movie.MovieSubComponent
import com.androidapp.movieappmvp.presenter.PresenterMain
import com.androidapp.movieappmvp.ui.ActivityMain
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        CiceroneModule::class,
        AppModule::class,
        ApiModule::class,
        DatabaseModule::class]
)
interface AppComponent {

    fun movieSubComponent(): MovieSubComponent

    fun inject(mainActivity: ActivityMain)
    fun inject(presenterMain: PresenterMain)
}