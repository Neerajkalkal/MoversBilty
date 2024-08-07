package com.example.gangapackagesolution.screens.moneyreciept.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.gangapackagesolution.models.moneyreceipt.MoneyReceiptState
import com.example.gangapackagesolution.screens.BillScreen.Components.DateSelector
import com.example.gangapackagesolution.screens.BillScreen.Components.OptionsField
import com.example.gangapackagesolution.screens.BillScreen.Components.RegularField


@Composable
fun MoneyReceiptDetails(moneyReceiptState: MoneyReceiptState) {

    RegularField(stateHolder = moneyReceiptState.receiptNumber, title = "Receipt Number")

    DateSelector(
        selectedDate = moneyReceiptState.date, modifier = Modifier.fillMaxWidth(),
        title = "Receipt Date"
                )

    RegularField(stateHolder = moneyReceiptState.name, title = "Name")

    RegularField(stateHolder = moneyReceiptState.phone, title = "Phone",wordType = true)


    OptionsField(
        optionList = listOf("Bill", "Quotation"), selectedValue = moneyReceiptState.receiptAgainst,
        title = "Receipt Against"
                )

    RegularField(stateHolder = moneyReceiptState.number, title = "Bill Number/Quotation Number", wordType = false)

    DateSelector(
        selectedDate = moneyReceiptState.billQuotationDate, modifier = Modifier.fillMaxWidth(),
        title = "Bill/Quotation Date"
                )
}