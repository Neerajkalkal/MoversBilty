package com.example.gangapackagesolution.screens.lrbilty.component

import androidx.compose.runtime.Composable
import com.example.gangapackagesolution.data.Data
import com.example.gangapackagesolution.models.lr_bilty.LrBiltyState
import com.example.gangapackagesolution.screens.BillScreen.Components.OptionsField
import com.example.gangapackagesolution.screens.BillScreen.Components.RegularField

@Composable
fun DemurrageCharge(lrBiltyState: LrBiltyState) {

    SpecialField1(
        s = "Demurrage Charge", packagingCharge = lrBiltyState.demarrageCharge,
        included = lrBiltyState.perDayorhour, list = Data.perhour
                 )

    RegularField(stateHolder = lrBiltyState.demurageChargeApplicableAfter, title = "Demurrage Charge  Applicable After(Days)",
                 wordType = false)


}
