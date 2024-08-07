package com.example.gangapackagesolution.screens.BillScreen.ExpandedCards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gangapackagesolution.data.Data
import com.example.gangapackagesolution.models.bill.BillState
import com.example.gangapackagesolution.screens.BillScreen.Components.ExtendedField
import com.example.gangapackagesolution.screens.BillScreen.Components.OptionsField
import com.example.gangapackagesolution.screens.BillScreen.Components.RegularField

@Composable
fun PackageExpanded(
    modifier: Modifier = Modifier,
    billState: BillState
                   ) {
    val data = Data
    val packageState = billState.packageName
    val descriptionState = billState.description
    val weightState = billState.weight
    val selectedWeightState = billState.weightType
    val remarkState = billState.remarks

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            .padding(10.dp)
            .fillMaxSize()
          ) {
        RegularField(
            stateHolder = packageState,
            title = "PACKAGE"
                    )
        RegularField(
            stateHolder = descriptionState,
            title = "DESCRIPTION",
            wordType = false
                    )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
           )
        {
            Box(modifier = Modifier.weight(4f)) {
                RegularField(
                    stateHolder = weightState,
                    title = "TOTAL WEIGHT",
                    wordType = false,
                            )
            }
            Box(modifier = Modifier.weight(1f)) {
                OptionsField(
                    optionList = data.weightUnitList,
                    selectedValue = selectedWeightState
                            )
            }
        }

        ExtendedField(
            title = "REMARK",
            stateHolder = remarkState,
            height = 120.dp
                     )
    }
}