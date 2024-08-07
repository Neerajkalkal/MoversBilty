package com.example.gangapackagesolution.models.packageList

import com.example.gangapackagesolution.models.itemsParticaular.itemParticulars

data class PackageList(
    var id: Int,
    var name: String = "",
    var phone: String = "",
    var packagingNumber: String = "",
    var date: String = "",
    var moveFrom: String = "",
    var moveTo: String = "",
    var vehicleNumber: String = "",
    var particularList: List<itemParticulars> ,
    val total: Int = 0
                      )
