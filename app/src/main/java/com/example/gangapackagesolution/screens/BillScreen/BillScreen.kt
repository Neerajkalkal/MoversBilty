package com.example.gangapackagesolution.screens.BillScreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.gangapackagesolution.models.bill.BillState
import com.example.gangapackagesolution.screens.MainViewModel

@Composable
fun BillMainScreen(
    navController: NavController,
    billState: BillState,
    mainViewModel: MainViewModel
                  ){


    BillScreenForm(billState = billState,navController = navController, mainViewModel = mainViewModel)
}