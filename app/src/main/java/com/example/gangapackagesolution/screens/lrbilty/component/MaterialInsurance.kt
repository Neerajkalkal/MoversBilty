package com.example.gangapackagesolution.screens.lrbilty.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.gangapackagesolution.models.lr_bilty.LrBiltyState
import com.example.gangapackagesolution.screens.BillScreen.Components.DateSelector
import com.example.gangapackagesolution.screens.BillScreen.Components.OptionsField
import com.example.gangapackagesolution.screens.BillScreen.Components.RegularField

@Composable
fun MaterialInsurance(lrBiltyState: LrBiltyState) {
    OptionsField(optionList = listOf("Insured","Non-Insured"), selectedValue =lrBiltyState.materialInsurance,
                 title = "Material Insurance")

    if (lrBiltyState.materialInsurance.value == "Insured") {
        RegularField(
            stateHolder = lrBiltyState.insuranceCompany, title = "Material Insurance Company"
                    )
        RegularField(stateHolder = lrBiltyState.policyNumber, title = "Policy Number")
        DateSelector(
            selectedDate = lrBiltyState.insuranceDate, modifier = Modifier.fillMaxWidth(),
            title = "Insurance Date"
                    )

        RegularField(stateHolder = lrBiltyState.insuredAmount, title = "Insured Amount")
        RegularField(stateHolder = lrBiltyState.insuranceRisk, title = "Insured Risk")

    }
}