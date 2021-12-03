package com.sebastianamhj.schoolfinalproject.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId
import com.google.type.DateTime
import java.util.*

@Entity(tableName = "user_table")
data class UserModel(
    @DocumentId val id: String = "",
    val name: String = "",
    @PrimaryKey val email: String = "",
    val age: String = "0",
    val status: Boolean = false,
    val lastOnline: String = "0"
)