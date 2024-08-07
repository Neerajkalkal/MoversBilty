package com.example.gangapackagesolution.screens.BillScreen.ExpandedCards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gangapackagesolution.screens.BillScreen.Components.DateSelector
import com.example.gangapackagesolution.screens.BillScreen.Components.OptionsField
import com.example.gangapackagesolution.screens.BillScreen.Components.RegularField
import com.example.gangapackagesolution.screens.BillScreen.utils.getCurrentDate
import com.example.gangapackagesolution.data.Data
import com.example.gangapackagesolution.models.bill.BillState

@Composable
fun DetailsExpanded(modifier: Modifier = Modifier,billState: BillState) {
    val data = Data

    // implement states
    val billNumber = billState.billNumber
    val companyName = billState.companyName
    val lrNumber = billState.lrNumber
    val movingPath = billState.movingPath
    val moveFrom = billState.moveFrom
    val moveTo = billState.moveTo
    val vehicleNumber = billState.vehicleNumber
    val invoiceDate =  billState.invoiceDate
    val deliveryDate = billState.deliveryDate
    val selectedPathOption =billState.movingPath
    val shipmentType = billState.typeOfShipment
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            .padding(10.dp)
            .fillMaxSize()
    ) {

        RegularField(
            stateHolder = billNumber,
            title = "INVOICE/BILL NUMBER",
            wordType = false
        )

        RegularField(
            stateHolder = companyName,
            title = "COMPANY NAME OF PARTY"
        )

        RegularField(
            stateHolder = lrNumber,
            title = "LR NUMBER",
            wordType = false
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
        ) {
            DateSelector(
                title = "INVOICE DATE",
                selectedDate = invoiceDate,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Spacer(modifier = Modifier.weight(0.1f))

            DateSelector(
                title = "DELIVERY DATE",
                selectedDate = deliveryDate,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

        }

        OptionsField(
            title = "MOVING PATH",
            optionList = data.movingPathList,
            selectedValue = selectedPathOption
        )

        RegularField(
            stateHolder = shipmentType,
            title = "TYPE OF SHIPMENT"
        )

        RegularField(
            stateHolder = billState.movingPathRemark,
            title = "MOVING PATH REMARK"
        )

        RegularField(
            stateHolder = moveFrom,
            title = "MOVE FROM"
        )

        RegularField(
            stateHolder = moveTo,
            title = "MOVE TO"
        )

        RegularField(
            stateHolder = vehicleNumber,
            title = "VEHICLE NUMBER",
            wordType = false
        )

    }
}