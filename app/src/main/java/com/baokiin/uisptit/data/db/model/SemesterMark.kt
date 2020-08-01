package com.baokiin.uisptit.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SemesterMark(
    @PrimaryKey val semester: String,
    val mediumScore10: Float,
    val mediumScore4: Float,
    val gpa10: Float,
    val gpa4: Float,
    val credits: Int,
    val creditsAccumulated: Int
)