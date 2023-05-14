package com.the12smb.submissionstoryapp.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val name: String,
    val userId: String,
    val token: String,
) : Parcelable