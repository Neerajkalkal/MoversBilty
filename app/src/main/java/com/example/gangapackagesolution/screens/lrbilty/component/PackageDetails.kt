package com.example.gangapackagesolution.screens.lrbilty.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gangapackagesolution.data.Data
import com.example.gangapackagesolution.models.lr_bilty.LrBiltyState
import com.example.gangapackagesolution.screens.BillScreen.Components.ExtendedField
import com.example.gangapackagesolution.screens.BillScreen.Components.RegularField
import com.example.gangapackagesolution.screens.quotationScreen.SpecialField

@Composable
fun PackageDetails(lrBiltyState: LrBiltyState) {

    RegularField(
        stateHolder = lrBiltyState.numberOfPackages, title = "No. Of Package",
        wordType = false
                )

    ExtendedField(
        stateHolder = lrBiltyState.description, title = "Description", height = 100.dp
                 )

    SpecialField1(
        s = "Actual Weight", packagingCharge = lrBiltyState.actualWeight,
        included = lrBiltyState.Unit,
        list = Data.unitList
                )

    SpecialField1(
        s = "Charged Weight", packagingCharge = lrBiltyState.chargedWeight,
        included = lrBiltyState.Unit,
        list = Data.unitList
                )


    RegularField(
        stateHolder = lrBiltyState.receivePackageCondition, title = "Receive Package Condition",
                )
    
    ExtendedField(title = "Remark", stateHolder =lrBiltyState.remarks, height = 100.dp )

}
@Composable
fun SpecialField1(s: String, packagingCharge: MutableState<String>, data: Data = Data,included: MutableState<String>,list: List<String> = data.includingType) {

    val showDialogue = remember {
        mutableStateOf(false)
    }





    Column {

        Text(
            text = s,
            color = Color.White
            )

        Row {
            // option Selection
            Surface(
                modifier = Modifier.clickable {
                    showDialogue.value = !showDialogue.value
                },
                shape = RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)
                   ) {
                Text(
                    text = included.value + " :",
                    modifier = Modifier.padding(10.dp)
                    )
                DropdownMenu(expanded = showDialogue.value, onDismissRequest = {
                    showDialogue.value = !showDialogue.value
                }) {
                    list.forEach {
                        Surface(modifier = Modifier
                            .padding(10.dp)
                            .height(44.dp)
                            .fillMaxWidth()
                            .clickable {
                                included.value = it
                                showDialogue.value = !showDialogue.value
                            }) {
                            Text(text = it, modifier = Modifier)
                        }
                    }
                }


            }

            VerticalDivider()

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                color = Color.White,
                shape = RoundedCornerShape(
                    bottomEnd = 10.dp,
                    topEnd = 10.dp
                                          )
                   ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 10.dp)
                   ) {
                    BasicTextField(
                        value = packagingCharge.value, onValueChange = {
                            if (!it.contains(",") && it.count { char ->
                                    char == '.'
                                } <= 1 && it != ".") {
                                packagingCharge.value = it
                            }
                        }, textStyle = TextStyle(fontSize = 20.sp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        enabled = true
                                  )
                }
            }
        }
    }
}