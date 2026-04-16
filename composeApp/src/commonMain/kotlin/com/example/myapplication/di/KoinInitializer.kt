package com.example.myapplication.di

import com.example.myapplication.data.InMemoryMovieRepository
import com.example.myapplication.domain.MovieRepository
import com.example.myapplication.list.MoviesListViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

val moviesModule = module {
    single { InMemoryMovieRepository() } bind MovieRepository::class
    viewModelOf(::MoviesListViewModel)
}

fun initKoin(config: KoinAppDeclaration? = null): KoinApplication {
    return startKoin {
        config?.invoke(this)
        modules(
            moviesModule,
        )
    }
}
