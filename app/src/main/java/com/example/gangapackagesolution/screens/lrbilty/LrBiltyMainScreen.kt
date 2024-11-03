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

@Composable
fun LrBillMainScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    value: Color
                    ) {
    val lrBiltyState = LrBiltyState(
        id = 1,
                                   )


    Surface(
        modifier = Modifier.fillMaxWidth()
           ) {


            LrFormScreen(lrBiltyState,mainViewModel,navController, color =value)




    }
}

@Composable
fun LrFormScreen(
    lrBiltyState: LrBiltyState,
    mainViewModel: MainViewModel,
    navController: NavHostController,color: Color
                ) {

    Column {


        Header(text = "Lr Bill", color) {
            navController.popBackStack()
        }


        Column(
            modifier = Modifier
                .padding(10.dp)
                .verticalScroll(rememberScrollState())
              ) {

            ActionCard(title = "Lr Details", expandCardCompose = {
                Lr_BillDetails(lrBiltyState = lrBiltyState)
            }, color =color )

            Spacer(modifier = Modifier.height(10.dp))

            ActionCard(title = "Bill Details", expandCardCompose = {
                TruckDetails(lrBiltyState = lrBiltyState)
            }, color =color
                       )

            Spacer(modifier = Modifier.height(10.dp))

            ActionCard(title = "Consignor/Move From", expandCardCompose = {
                MoveFrom(lrBiltyState = lrBiltyState)

            }, color =color)

            Spacer(modifier = Modifier.height(10.dp))

            ActionCard(title = "Consignee/Move To", expandCardCompose = {
                MoveTo(lrBiltyState = lrBiltyState)
            }, color =color)
            Spacer(modifier = Modifier.height(10.dp))

            ActionCard(
                title = "Packing Details", expandCardCompose =
            {
                PackageDetails(lrBiltyState = lrBiltyState)
            }, color =color)
            Spacer(modifier = Modifier.height(10.dp))

            ActionCard(title = "Payment Details", expandCardCompose = {
                PaymentDetails(lrBiltyState = lrBiltyState)
            }, color =color)
            Spacer(modifier = Modifier.height(10.dp))

            ActionCard(title = "Material Insurance", expandCardCompose = {
                MaterialInsurance(lrBiltyState)
            }, color =color)

            Spacer(modifier = Modifier.height(10.dp))

            ActionCard(title = "Demurrage Charge", expandCardCompose = {
                DemurrageCharge(lrBiltyState = lrBiltyState)
            }, color =color)

            Spacer(modifier = Modifier.height(40.dp))

            CustomButton(onItemClick1 = "Submit", color =color) {
                mainViewModel.addlrbilty(lrBiltyState) {
                    navController.popBackStack()
                }
            }

        }

    }
}