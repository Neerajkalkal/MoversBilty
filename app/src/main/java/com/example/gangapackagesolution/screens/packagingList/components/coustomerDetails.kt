package com.example.gangapackagesolution.screens.packagingList.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gangapackagesolution.screens.BillScreen.Components.RegularField
import com.example.gangapackagesolution.screens.quotationScreen.DateSelector

@Composable
fun CustomerDetails(
    name: MutableState<String>,
    phone: MutableState<String>,
    packagingNumber: MutableState<String>,
    date: MutableState<String>,
    moveFrom: MutableState<String>,
    moveTo: MutableState<String>,
    vehicleNumber: MutableState<String>,
                   ) {

    Column(modifier = Modifier) {


        RegularField(
            stateHolder = name,
            title = "Name"
                    )


        RegularField(
            stateHolder = phone,
            title = "Phone"
                    )

        RegularField(
            stateHolder = packagingNumber,
            title = "Packaging Number"
                    )

        DateSelector(selectedDate = date, modifier = Modifier.fillMaxWidth(), s = "Date")
        Spacer(modifier = Modifier.height(20.dp))
        RegularField(stateHolder = moveFrom, title = "Move From")
        RegularField(stateHolder = moveTo, title = "Move To")
        RegularField(stateHolder = vehicleNumber, title = "Vehicle Number")


    }
}
