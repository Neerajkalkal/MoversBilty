package com.example.gangapackagesolution.screens.packagingList.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gangapackagesolution.screens.quotationScreen.CustomButton
import com.example.gangapackagesolution.screens.quotationScreen.RegularField
import com.example.gangapackagesolution.screens.quotationScreen.ShowAndRemoveItems
import com.example.gangapackagesolution.models.itemsParticaular.itemParticulars

@Composable
fun ItemParticulars(
    itemname: MutableState<String>,
    quantity: MutableState<String>,
    value: MutableState<String>,
    itemremark: MutableState<String>,
    itemParticulars: MutableState<List<itemParticulars>>
                   ) {
    RegularField(quotationId = itemname, s = "Item Name")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
       ) {
        RegularField(
            quantity, false, "Quantity",
            modifier = Modifier,
            type = false
                    )
        Spacer(modifier = Modifier.width(15.dp))
        RegularField(
            value, false, "value",
            modifier = Modifier,
            type = false
                    )

    }
    RegularField(itemremark, false, "Remark")
    Spacer(modifier = Modifier.height(10.dp))
    CustomButton("Save Item") {
        if (
            !itemParticulars.value.contains(
                itemParticulars(
                    itemname.value,
                    quantity.value,
                    value.value,
                    itemremark.value
                               )
                                           )
        ) {
            itemParticulars.value += itemParticulars(
                itemname.value,
                if (quantity.value.isEmpty()) "0" else quantity.value,
                if (value.value.isEmpty()) "0" else value.value,
                itemremark.value
                                                    )
        }

        Log.d("list to saveeeee", itemParticulars.value.toString())
    }
    Spacer(modifier = Modifier.height(10.dp))

    ShowAndRemoveItems(itemParticulars = itemParticulars)
}