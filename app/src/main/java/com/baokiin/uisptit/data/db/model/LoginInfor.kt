package com.baokiin.uisptit.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LoginInfor(@PrimaryKey var username: String, var password: String)