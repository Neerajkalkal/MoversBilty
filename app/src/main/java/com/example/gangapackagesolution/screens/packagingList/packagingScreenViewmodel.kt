package com.example.gangapackagesolution.screens.packagingList

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gangapackagesolution.models.DataOrException
import com.example.gangapackagesolution.models.packageList.PackageList
import com.example.gangapackagesolution.models.packageList.state.CustomerDetails
import com.example.gangapackagesolution.models.packageList.state.packingListItems
import com.example.gangapackagesolution.repository.Repository
import com.example.gangapackagesolution.repository.TokenManagement
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class packagingScreenViewmodel(context: Context) : ViewModel() {
    private val _packagingState =
        MutableStateFlow<DataOrException<String, Exception>>(DataOrException())
    val packagingState: StateFlow<DataOrException<String, Exception>> = _packagingState

    val token = TokenManagement(context)

    //  submit the packaging list
    fun submitPackageList(
        onComplete: () -> Unit,
        id: Int,
        itemParticulars: MutableState<packingListItems>,
        customerDetails: MutableState<CustomerDetails>
                         ) {
        val packageList = PackageList(
            id = id,
            particularList = itemParticulars.value.itemParticulars.value,
            name = customerDetails.value.name.value,
            phone = customerDetails.value.phone.value,
            packagingNumber = customerDetails.value.packagingNo.value,
            date = customerDetails.value.date.value,
            moveTo = customerDetails.value.moveTo.value,
            moveFrom = customerDetails.value.moveFrom.value,
            vehicleNumber = customerDetails.value.vehicleNo.value
                                     )

        viewModelScope.launch {
            Repository.addFunction(_packagingState, packageList, onCompleted = {onComplete()}, token = token.getToken())
        }
    }




}