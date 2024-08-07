package com.example.gangapackagesolution.screens.lrbilty.component

import androidx.compose.runtime.Composable
import com.example.gangapackagesolution.models.lr_bilty.LrBiltyState
import com.example.gangapackagesolution.screens.BillScreen.Components.RegularField

@Composable
fun MoveTo(lrBiltyState: LrBiltyState){
    RegularField(stateHolder = lrBiltyState.consigneeName, title = "Consignee Name")

    RegularField(stateHolder = lrBiltyState.consigneeNumber, title = "Consignee Phone Number")
    RegularField(stateHolder = lrBiltyState.gstNumber1, title = "Gst Number")
    RegularField(stateHolder = lrBiltyState.country1, title = "Country")
    RegularField(stateHolder = lrBiltyState.state1, title = "State")
    RegularField(stateHolder = lrBiltyState.city1, title = "City")
    RegularField(stateHolder = lrBiltyState.pincode1, title = "Pincode")
    RegularField(stateHolder = lrBiltyState.address1, title = "Address")

}