package com.sebastianamhj.schoolfinalproject.di

import com.google.firebase.firestore.FirebaseFirestore
import com.sebastianamhj.schoolfinalproject.data.repository.FirebaseRepository
import com.sebastianamhj.schoolfinalproject.data.repository.IconRepository
import com.sebastianamhj.schoolfinalproject.ui.viewmodels.MainViewModel
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val firebaseModule = module {

    single { FirebaseRepository(get()) }
    single { FirebaseFirestore.getInstance() }
    single { HttpClient(CIO) }
    viewModel { MainViewModel(get(), get()) }
}

val iconModule = module {
    single { IconRepository(get(), get()) }
    viewModel { MainViewModel(get(), get()) }
}