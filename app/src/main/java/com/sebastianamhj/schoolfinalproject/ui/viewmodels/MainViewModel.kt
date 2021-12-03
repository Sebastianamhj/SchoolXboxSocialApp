package com.sebastianamhj.schoolfinalproject.ui.viewmodels

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sebastianamhj.schoolfinalproject.data.entities.UserModel
import com.sebastianamhj.schoolfinalproject.data.repository.FirebaseRepository
import com.sebastianamhj.schoolfinalproject.data.repository.IconRepository
import com.sebastianamhj.schoolfinalproject.data.repository.Resource

class MainViewModel(
    private val firebaseRepository: FirebaseRepository,
    private val iconRepository: IconRepository
) : ViewModel() {

    val firebaseUsers = firebaseRepository.getUsers().asLiveData()

    val mainFirebaseUser = firebaseRepository.getMainUser().asLiveData()

    fun getDetailFirebaseUser(userId: String): LiveData<Resource<UserModel>> {
        return firebaseRepository.getDetailUser(userId = userId).asLiveData()
    }

    fun removeFirebaseUser(userId: String): LiveData<Resource<String>> {
        return firebaseRepository.removeUser(userId = userId).asLiveData()
    }

    fun updateFirebaseUserNickname(userId: String, nickName: String): LiveData<Resource<String>> {
        return firebaseRepository.updateUserNickname(userId = userId, nickname = nickName).asLiveData()
    }

    fun getDiceBearIcon(seed: String): LiveData<Resource<Drawable>> {
        return iconRepository.getIcon(seed = seed).asLiveData()
    }
}