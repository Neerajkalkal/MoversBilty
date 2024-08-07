package com.example.gangapackagesolution.screens.lrbilty.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.gangapackagesolution.data.Data
import com.example.gangapackagesolution.models.lr_bilty.LrBiltyState
import com.example.gangapackagesolution.screens.BillScreen.Components.DateSelector
import com.example.gangapackagesolution.screens.BillScreen.Components.OptionsField
import com.example.gangapackagesolution.screens.BillScreen.Components.RegularField

@Composable
fun Lr_BillDetails(
    lrBiltyState: LrBiltyState
                  ) {

    RegularField(stateHolder = lrBiltyState.lrNumber,
                 title = "Lr Number")
    

    DateSelector(selectedDate = lrBiltyState.lrDate, modifier = Modifier.fillMaxWidth(),
                 title = "Lr Date")

   OptionsField(optionList = Data.riskType, selectedValue = lrBiltyState.riskType,
                title = "Risk Type")


}