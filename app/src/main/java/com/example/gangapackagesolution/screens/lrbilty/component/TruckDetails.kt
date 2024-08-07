package com.example.gangapackagesolution.screens.lrbilty.component

import androidx.compose.runtime.Composable
import com.example.gangapackagesolution.models.lr_bilty.LrBiltyState
import com.example.gangapackagesolution.screens.BillScreen.Components.RegularField

@Composable
fun  TruckDetails(lrBiltyState: LrBiltyState){

    RegularField(stateHolder = lrBiltyState.truckNumber, title = "Truck/Vehicle No.")
    RegularField(stateHolder = lrBiltyState.moveFrom, title = "Move From")
    RegularField(stateHolder = lrBiltyState.moveTo, title = "Move To")
    RegularField(stateHolder = lrBiltyState.driverName, title = "Driver Name")
    RegularField(stateHolder = lrBiltyState.driverNumber, title = "Driver Mobile")
    RegularField(stateHolder = lrBiltyState.driverLicense, title = "Driver's License No.")



}