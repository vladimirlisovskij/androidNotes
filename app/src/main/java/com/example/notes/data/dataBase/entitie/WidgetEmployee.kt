package com.example.notes.data.dataBase.entitie

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class WidgetEmployee(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val header: String,
    val desc: String,
    val body: String
) : Parcelable