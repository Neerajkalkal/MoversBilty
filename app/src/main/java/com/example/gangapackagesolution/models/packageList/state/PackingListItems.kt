package com.example.gangapackagesolution.models.packageList.state

import androidx.compose.runtime.MutableState
import com.example.gangapackagesolution.models.itemsParticaular.itemParticulars

data class packingListItems(
    val itemname: MutableState<String>,
    val quantity: MutableState<String>,
    val value: MutableState<String>,
    val itemremark: MutableState<String>,
    val itemParticulars: MutableState<List<itemParticulars>>
                           )
