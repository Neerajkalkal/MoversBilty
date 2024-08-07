package com.example.gangapackagesolution.screens.moneyreciept.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.gangapackagesolution.models.moneyreceipt.MoneyReceiptState
import com.example.gangapackagesolution.screens.BillScreen.Components.ExtendedField
import com.example.gangapackagesolution.screens.BillScreen.Components.OptionsField
import com.example.gangapackagesolution.screens.BillScreen.Components.RegularField

@Composable
fun PaymentAndOtherDetails(moneyReceiptState: MoneyReceiptState) {
    RegularField(stateHolder = moneyReceiptState.moveFrom, title = "Move From")
    RegularField(stateHolder = moneyReceiptState.moveTo, title = "Move To")

    OptionsField(
        optionList = listOf("Advance", "Part", "Final Payment"), selectedValue =
        moneyReceiptState.paymentType
                )
    RegularField(stateHolder = moneyReceiptState.receiptAmount, title = "Receipt Amount")
    OptionsField(
        optionList = listOf(
            "Cash", "Cheque", "Draft", "Net Banking", "UPI", "Digital Wallet", "Credit Card",
            "Debit Card"
                           ), selectedValue = moneyReceiptState.paymentMode,
        title = "Payment Mode"
                )
    if (moneyReceiptState.paymentMode.value!="Cash"){
        RegularField(stateHolder = moneyReceiptState.transactionsNumber, title = "Transactions Number")

    }

    RegularField(stateHolder = moneyReceiptState.branch, title = "Branch")
    ExtendedField(title = "Remarks", stateHolder =moneyReceiptState.remarks,
                  80.dp)


}