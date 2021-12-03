package com.sebastianamhj.schoolfinalproject.data.repository

import androidx.compose.runtime.MutableState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import com.sebastianamhj.schoolfinalproject.data.entities.UserModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseRepository(
    private val db: FirebaseFirestore
) {

    fun getUsers(): Flow<Resource<List<UserModel>>> = flow {
        emit(Resource.Loading())
        var users = getFirebaseUsers()
        if (users == null) {
            emit(Resource.Error(null))
        } else {
            emit(Resource.Success(users))
        }
    }


    private suspend fun getFirebaseUsers(): List<UserModel>? = suspendCoroutine { task ->
        var userList: MutableList<UserModel> = ArrayList()
        db
            .collection("users")
            .orderBy("status", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) userList.add(document.toObject())
                task.resume(userList)
            }
            .addOnFailureListener {
                task.resume(null)
            }
    }

    fun getMainUser(): Flow<Resource<UserModel>> = flow {
        emit(Resource.Loading())
        val user = getMainFirebaseUser()
        if (user == null) {
            emit(Resource.Error(null))
        } else {
            emit(Resource.Success(user))
        }
    }

    private suspend fun getMainFirebaseUser(): UserModel? = suspendCoroutine { task ->
        var user: UserModel
        db
            .collection("main")
            .document("mainUser")
            .get()
            .addOnSuccessListener { result ->
                user = result.toObject()!!
                task.resume(user)
            }
            .addOnFailureListener {
                task.resume(null)
            }
    }

    fun getDetailUser(userId: String): Flow<Resource<UserModel>> = flow {
        emit(Resource.Loading())
        val user = getDetailFirebaseUser(userId)
        if (user == null) {
            emit(Resource.Error(null))
        } else {
            emit(Resource.Success(user))
        }
    }

    private suspend fun getDetailFirebaseUser(userId: String): UserModel? =
        suspendCoroutine { task ->
            var user: UserModel
            db
                .collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener { result ->
                    user = result.toObject()!!
                    task.resume(user)
                }
                .addOnFailureListener {
                    task.resume(null)
                }
        }

    fun removeUser(userId: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        val status = removeFirebaseUser(userId = userId)
        if (status == null) {
            emit(Resource.Error(null))
        } else {
            emit(Resource.Success(status))
        }
    }

    private suspend fun removeFirebaseUser(userId: String): String? = suspendCoroutine { task ->
        var status: String
        db
            .collection("users")
            .document(userId)
            .delete()
            .addOnSuccessListener { result ->
                status = "friend has been removed"
                task.resume(status)
            }
            .addOnFailureListener {
                task.resume(null)
            }
    }

    fun updateUserNickname(userId: String, nickname: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        val status = updateFirebaseUserNickname(userId = userId, nickname = nickname)
        if (status == null) {
            emit(Resource.Error(null))
        } else {
            emit(Resource.Success(status))
        }
    }

    private suspend fun updateFirebaseUserNickname(userId: String, nickname: String): String? =
        suspendCoroutine { task ->
            var status: String
            db
                .collection("users")
                .document(userId)
                .update("nickname", nickname)
                .addOnSuccessListener { result ->
                    status = "Nickname has been updated"
                    task.resume(status)
                }
                .addOnFailureListener {
                    task.resume(null)
                }
        }

    fun createUser(name: String) {

    }
}