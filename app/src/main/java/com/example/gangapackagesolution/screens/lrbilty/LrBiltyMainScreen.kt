package com.example.gangapackagesolution.screens.lrbilty

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gangapackagesolution.models.lr_bilty.LrBiltyState
import com.example.gangapackagesolution.screens.BillScreen.ActionCard
import com.example.gangapackagesolution.screens.BillScreen.Components.PaymentDetails
import com.example.gangapackagesolution.screens.MainViewModel
import com.example.gangapackagesolution.screens.lrbilty.component.DemurrageCharge
import com.example.gangapackagesolution.screens.lrbilty.component.Lr_BillDetails
import com.example.gangapackagesolution.screens.lrbilty.component.MaterialInsurance
import com.example.gangapackagesolution.screens.lrbilty.component.MoveFrom
import com.example.gangapackagesolution.screens.lrbilty.component.MoveTo
import com.example.gangapackagesolution.screens.lrbilty.component.PackageDetails
import com.example.gangapackagesolution.screens.lrbilty.component.TruckDetails
import com.example.gangapackagesolution.screens.quotationScreen.CustomButton
import com.example.gangapackagesolution.screens.quotationScreen.Header
import com.example.gangapackagesolution.screens.screenName.Screens

@Composable
fun LrBillMainScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel
                    ) {
    val lrBiltyState = LrBiltyState(
        id = 1,
                                   )


    Surface(
        modifier = Modifier.fillMaxWidth()
           ) {


            LrFormScreen(lrBiltyState,mainViewModel,navController)




    }
}

@Composable
fun LrFormScreen(
    lrBiltyState: LrBiltyState,
    mainViewModel: MainViewModel,
    navController: NavHostController
                ) {

    Column {


        Header(text = "Lr Bill", Color(0xFF673AB7)) {
            navController.popBackStack()
        }


        Column(
            modifier = Modifier
                .padding(10.dp)
                .verticalScroll(rememberScrollState())
              ) {

            ActionCard(title = "Lr Details", expandCardCompose = {
                Lr_BillDetails(lrBiltyState = lrBiltyState)
            })

            Spacer(modifier = Modifier.height(10.dp))

            ActionCard(title = "Bill Details", expandCardCompose = {
                TruckDetails(lrBiltyState = lrBiltyState)
            })

            Spacer(modifier = Modifier.height(10.dp))

            ActionCard(title = "Consignor/Move From", expandCardCompose = {
                MoveFrom(lrBiltyState = lrBiltyState)
            })

            Spacer(modifier = Modifier.height(10.dp))

            ActionCard(title = "Consignee/Move To", expandCardCompose = {
                MoveTo(lrBiltyState = lrBiltyState)
            })
            Spacer(modifier = Modifier.height(10.dp))

            ActionCard(title = "Packing Details", expandCardCompose =
            {
                PackageDetails(lrBiltyState = lrBiltyState)
            })
            Spacer(modifier = Modifier.height(10.dp))

            ActionCard(title = "Payment Details", expandCardCompose = {
                PaymentDetails(lrBiltyState = lrBiltyState)
            })
            Spacer(modifier = Modifier.height(10.dp))

            ActionCard(title = "Material Insurance", expandCardCompose = {
                MaterialInsurance(lrBiltyState)
            })

            Spacer(modifier = Modifier.height(10.dp))

            ActionCard(title = "Demurrage Charge", expandCardCompose = {
                DemurrageCharge(lrBiltyState = lrBiltyState)
            })

            Spacer(modifier = Modifier.height(40.dp))

            CustomButton(onItemClick1 = "Submit") {
                mainViewModel.addlrbilty(lrBiltyState) {
                    navController.popBackStack()
                }
            }

        }

    }
}