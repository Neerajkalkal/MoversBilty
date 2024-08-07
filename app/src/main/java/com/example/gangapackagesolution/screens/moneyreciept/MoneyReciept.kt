package com.example.gangapackagesolution.screens.moneyreciept

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.gangapackagesolution.models.moneyreceipt.MoneyReceiptState
import com.example.gangapackagesolution.screens.BillScreen.ActionCard
import com.example.gangapackagesolution.screens.MainViewModel
import com.example.gangapackagesolution.screens.moneyreciept.components.MoneyReceiptDetails
import com.example.gangapackagesolution.screens.moneyreciept.components.PaymentAndOtherDetails
import com.example.gangapackagesolution.screens.quotationScreen.CustomButton
import com.example.gangapackagesolution.screens.quotationScreen.Header
import java.time.LocalDateTime


@Composable
fun MoneyReceipt(
    navController: NavHostController,
    mainViewModel: MainViewModel
                ) {
    Surface(modifier = Modifier.fillMaxSize()) {
        val moneyReceiptState = MoneyReceiptState()
        val date = LocalDateTime.now().toLocalDate()
        moneyReceiptState.date.value = date.toString()
        moneyReceiptState.billQuotationDate.value = date.toString()
        moneyReceiptState.receiptAgainst.value = "Bill"
        moneyReceiptState.paymentType.value = "Part"
        moneyReceiptState.paymentMode.value = "Cash"


        MoneyReceiptForm(moneyReceiptState, navController,mainViewModel)
    }

}

@Composable
fun MoneyReceiptForm(
    moneyReceiptState: MoneyReceiptState,
    navController: NavHostController,
    mainViewModel: MainViewModel
                    ) {

    Column(modifier = Modifier.padding()) {

        Header(text = "Money Receipt", color = Color(0xFF673AB7)) {
            navController.popBackStack()
        }

        Column(
            modifier = Modifier
                .padding(10.dp)
                .verticalScroll(rememberScrollState())
              ) {
            ActionCard(
                title = "Money Receipt Details",
                expandCardCompose = { MoneyReceiptDetails(moneyReceiptState) })


            Spacer(modifier = Modifier.height(20.dp))

            ActionCard(
                title = "Payment and other Details",
                expandCardCompose = { PaymentAndOtherDetails(moneyReceiptState) })

            Spacer(modifier = Modifier.height(20.dp))
            CustomButton(onItemClick1 = "Save Money Receipt") {
mainViewModel.moneyReceipt(
moneyReceiptState = moneyReceiptState
                          ){
    navController.popBackStack()
}
            }

        }


    }
}