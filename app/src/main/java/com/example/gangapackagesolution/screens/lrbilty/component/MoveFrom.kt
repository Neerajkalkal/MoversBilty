package com.example.gangapackagesolution.screens.lrbilty.component

import androidx.compose.runtime.Composable
import com.example.gangapackagesolution.models.lr_bilty.LrBiltyState
import com.example.gangapackagesolution.screens.BillScreen.Components.RegularField

@Composable
fun MoveFrom(lrBiltyState: LrBiltyState) {
    RegularField(stateHolder = lrBiltyState.consignorName, title = "Consignor Name")

    RegularField(stateHolder = lrBiltyState.consignorNumber, title = "Consignor Phone Number")
    RegularField(stateHolder = lrBiltyState.gstNumber, title = "Gst Number")
    RegularField(stateHolder = lrBiltyState.country, title = "Country")
    RegularField(stateHolder = lrBiltyState.state, title = "State")
    RegularField(stateHolder = lrBiltyState.city, title = "City")
    RegularField(stateHolder = lrBiltyState.pincode, title = "Pincode")
    RegularField(stateHolder = lrBiltyState.address, title = "Address")

}