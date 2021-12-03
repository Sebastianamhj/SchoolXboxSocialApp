package com.sebastianamhj.schoolfinalproject.data.repository

import android.content.Context
import android.graphics.drawable.Drawable
import coil.imageLoader
import coil.request.ImageRequest
import io.ktor.client.*

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow

class IconRepository(
     private val client: HttpClient,
     private val context: Context
) {

    fun getIcon(seed: String): Flow<Resource<Drawable>> = flow {
        emit(Resource.Loading())
        val icon = getDiceBearIcon(seed)
        if (icon == null) {
            emit(Resource.Error(null))
        } else {
            emit(Resource.Success(icon))
        }
    }

    private suspend fun getDiceBearIcon(seed: String): Drawable? {
        val request = ImageRequest.Builder(context)
            .data("https://avatars.dicebear.com/api/avataaars/:$seed.svg")
            .build()
        return context.imageLoader.execute(request = request).drawable
    }
}