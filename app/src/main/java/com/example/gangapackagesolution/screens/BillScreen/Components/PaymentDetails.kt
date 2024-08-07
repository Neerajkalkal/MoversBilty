package com.example.gangapackagesolution.screens.BillScreen.Components

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import com.example.gangapackagesolution.data.Data
import com.example.gangapackagesolution.models.lr_bilty.LrBiltyState

@Composable
fun PaymentDetails(lrBiltyState: LrBiltyState) {
    RegularField(
        stateHolder = lrBiltyState.freightToBeBilled,
        title = "Freight To Be Billed",
        wordType = false
                )



    RegularField(
        stateHolder = lrBiltyState.freightPaid,
        title = "Freight Paid",
        wordType = false
                )

    RegularField(
        stateHolder = lrBiltyState.freightBalance,
        title = "Freight to Pay",
        wordType = false
                )
    HorizontalDivider()

    RegularField(
        stateHolder = lrBiltyState.totalBasicFreight, title = "Total Basic Freight",
        wordType = false
                )
    RegularField(
        stateHolder = lrBiltyState.loadingCharges, title = "Loading Charges",
        wordType = false
                )
    RegularField(
        stateHolder = lrBiltyState.unloadingCharges, title = "Unloading Charges",
        wordType = false
                )
    RegularField(
        stateHolder = lrBiltyState.stCharges, title = "S.T. Charges",
        wordType = false
                )
    RegularField(
        stateHolder = lrBiltyState.otherCharges, title = "Other Charges",
        wordType = false
                )
    HorizontalDivider()
    OptionsField(optionList = Data.gstperc, selectedValue = lrBiltyState.gstperc, title = "GST %")
    OptionsField(
        optionList = listOf("Consignee", "Consignor"), selectedValue = lrBiltyState.gstPaidBy,
        title = "GST Paid By",
                )


}