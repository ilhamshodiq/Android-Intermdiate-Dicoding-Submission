package com.the12smb.submissionstoryapp.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val name: String,
    val email: String,
    val token: String,
    val isLogin: Boolean
) : Parcelable