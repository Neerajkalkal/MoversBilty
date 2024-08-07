package com.example.gangapackagesolution.models.packageList.state

import androidx.compose.runtime.MutableState

data class CustomerDetails(
    var id:Int,
    var name: MutableState<String>,
    val phone: MutableState<String>,
    val packagingNo: MutableState<String>,
    val date: MutableState<String>,
    val moveFrom: MutableState<String>,
    val moveTo: MutableState<String>,
    val vehicleNo: MutableState<String>)
