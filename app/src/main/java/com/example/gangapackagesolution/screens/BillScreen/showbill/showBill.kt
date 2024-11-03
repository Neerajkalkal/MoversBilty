package com.example.gangapackagesolution.screens.BillScreen.showbill

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.gangapackagesolution.R
import com.example.gangapackagesolution.models.bill.BillState
import com.example.gangapackagesolution.models.bill.bill
import com.example.gangapackagesolution.models.moneyreceipt.MoneyReceiptState
import com.example.gangapackagesolution.screens.MainViewModel
import com.example.gangapackagesolution.screens.loadingAndErrorScreen.LoadingScreen
import com.example.gangapackagesolution.screens.lrbilty.showlrbilty.ShowBills
import com.example.gangapackagesolution.screens.packagingList.showpackagingList.ShowingHeader
import com.example.gangapackagesolution.screens.quotationScreen.quotationShow.EmptyScreen
import com.example.gangapackagesolution.screens.quotationScreen.quotationShow.ShoSearch
import com.example.gangapackagesolution.screens.quotationScreen.quotationShow.ShowOptions
import com.example.gangapackagesolution.screens.screenName.Screens
import com.example.gangapackagesolution.ui.theme.latosemibold
import java.time.LocalDate
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowBillScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    value: Color
                  ) {

    val state = mainViewModel.bill.collectAsState()
    Column {

        val search = remember {
            mutableStateOf("")
        }
        val active = remember {
            mutableStateOf(false)
        }

        ShowingHeader(s = "Bills", color = value, onClick1 = {mainViewModel.getBill()}) {
            navController.popBackStack()
        }
        if (state.value.loading) {
            LoadingScreen(color = Color.White, indicatorColor = value)
        }
        if (state.value.e != null) {
            Text(text = state.value.e.toString())
        }
        if (state.value.data != null) {
            if (state.value.data!!.isNotEmpty()) {
                state.value.data?.let {
                    val packageList1 = remember {
                        it
                    }

                    var List1 = remember {
                        mutableStateOf(packageList1)
                    }

                    SearchBar(query = search.value, onQueryChange = {
                        search.value = it

                        if (packageList1 != null) {
                            List1.value = packageList1.filter { list ->

                                list.billToName.toLowerCase(Locale.ROOT)
                                    .contains(
                                        search.value.toLowerCase()
                                             ) || list.citionsignorName.toLowerCase()
                                    .contains(search.value.toLowerCase())

                            }
                        }


                    }, onSearch = {
                        if (packageList1 != null) {
                            List1.value = packageList1.filter { list ->

                                list.billToName.toLowerCase()
                                    .contains(
                                        search.value.toLowerCase()
                                             ) || list.consigneeName.toLowerCase()
                                    .contains(search.value.toLowerCase())

                            }
                        }
                    },
                              active = active.value, onActiveChange = {
                            active.value = !active.value
                        },
                              modifier = Modifier.fillMaxWidth(),
                              placeholder = { Text(text = "Search For Name") }) {
                        LazyColumn() {
                            items(List1.value) { quotation ->
                                BillFormat(quotation, navController, mainViewModel, color = value)
                            }
                        }

                    }



                    LazyColumn {
                        items(it) {
                            BillFormat(it, navController, mainViewModel, color = value)
                        }
                    }
                }
            } else {
                EmptyScreen()
            }
        }


    }
}

@Composable
fun BillFormat(
    bill: bill,
    navController: NavHostController,
    mainViewModel: MainViewModel,
    color: Color
              ) {

    val showDialog = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.padding(15.dp),
        color =color ,
        shape = MaterialTheme.shapes.small
           ) {

        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = "Id : ${bill.billNumber}",
                color = Color(0xFFE4E2DB),
                style = MaterialTheme.typography.titleLarge
                )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
               ) {
                Text(
                    text = "Date:${bill.invoiceDate}",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                    )


                Text(
                    text = "Total : ₹${bill.total}",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                    )
            }

            HorizontalDivider(modifier = Modifier.padding(15.dp), color = Color.White)


            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    imageVector = Icons.Default.AccountCircle, contentDescription = "",
                    tint = Color(0xFFF5F1E6)
                    )

                Text(
                    text = bill.billToName,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(5.dp)
                    )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Default.Call, contentDescription = "",
                    tint = Color(0xFFCECBC5)
                    )

                Text(
                    text = bill.billToPhone,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(5.dp)
                    )
            }



            HorizontalDivider(modifier = Modifier.padding(15.dp), color = Color.White)

            Row {
                Column {
                    Text(
                        text = "From:",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                        )
                    Text(
                        text = bill.moveFrom,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                        )


                }

                Spacer(modifier = Modifier.weight(1f))

                Column {
                    Text(
                        text = "To:",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                        )
                    Text(
                        text = bill.moveTo,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                        )


                }

            }

            HorizontalDivider(modifier = Modifier.padding(15.dp), color = Color.White)
            Row(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.clickable {
                    mainViewModel.billID = bill.id.toInt()

                    val billState = BillState()

                    billState.billNumber.value = bill.billNumber
                    billState.companyName.value = bill.companyName
                    billState.lrNumber.value = bill.lrNumber
                    billState.invoiceDate.value = bill.invoiceDate
                    billState.deliveryDate.value = bill.deliveryDate
                    billState.movingPath.value = bill.movingPath
                    billState.typeOfShipment.value = bill.typeOfShipment
                    billState.movingPathRemark.value = bill.movingPathRemark
                    billState.moveFrom.value = bill.moveFrom
                    billState.moveTo.value = bill.moveTo
                    billState.vehicleNumber.value = bill.vehicleNumber

// Billing details
                    billState.billToName.value = bill.billToName
                    billState.billToPhone.value = bill.billToPhone
                    billState.gstin.value = bill.gstin
                    billState.country.value = bill.country
                    billState.state.value = bill.state
                    billState.city.value = bill.city
                    billState.pinCode.value = bill.pinCode
                    billState.address.value = bill.address

// Consignor details
                    billState.citionsignorName.value = bill.citionsignorName
                    billState.citionsignorPhone.value = bill.citionsignorPhone
                    billState.citionsignorGstin.value = bill.citionsignorGstin
                    billState.citionsignorCountry.value = bill.citionsignorCountry
                    billState.citionsignorState.value = bill.citionsignorState
                    billState.citionsignorCity.value = bill.citionsignorCity
                    billState.citionsignorPinCode.value = bill.citionsignorPinCode
                    billState.citionsignorAddress.value = bill.citionsignorAddress

// Consignee details
                    billState.consigneeName.value = bill.consigneeName
                    billState.consigneePhone.value = bill.consigneePhone
                    billState.consigneeGstin.value = bill.consigneeGstin
                    billState.consigneeCountry.value = bill.consigneeCountry
                    billState.consigneeState.value = bill.consigneeState
                    billState.consigneeCity.value = bill.consigneeCity
                    billState.consigneePinCode.value = bill.consigneePinCode
                    billState.consigneeAddress.value = bill.consigneeAddress

// Package details
                    billState.packageName.value = bill.packageName
                    billState.description.value = bill.description
                    billState.weight.value = bill.weight
                    billState.weightType.value =
                        bill.selectedWeight // Assuming selectedWeight matches weightType
                    billState.remarks.value = bill.remarks

// Payment details
                    billState.freightCharge.value = bill.freightCharge
                    billState.advancePaid.value = bill.advancePaid
                    billState.packagingChargeType.value = "Additional From Freight"
                    billState.packingCharge.value = bill.packingCharge
                    billState.unpackingChargeType.value = "Additional From Freight"
                    billState.unpackingCharge.value = bill.unpackingCharge
                    billState.loadingChargeType.value = "Additional From Freight"
                    billState.loadingCharge.value = bill.loadingCharge
                    billState.unloadingChargeType.value = "Additional From Freight"
                    billState.unloadingCharge.value = bill.unloadingCharge


                    billState.carbikeTpt.value = bill.carbikeTpt
                    billState.miscellaneousCharge.value = bill.miscellaneousCharge
                    billState.otherCharge.value = bill.otherCharge
                    billState.stCharge.value = bill.stCharge
                    billState.greentax.value = bill.greentax


                    billState.gstin.value = bill.gstinorex
                    billState.gst.value = bill.gst
                    billState.gstType.value = bill.gstType



                    billState.reverseCharge.value = bill.reverseCharge
                    billState.gstpaidby.value = bill.gstpaidby
                    billState.paymentRemark.value = bill.paymentRemark
                    billState.discount.value = bill.discount

// Insurance details
                    billState.insuranceType.value = bill.insuranceType
                    billState.insuranceCharge.value = bill.insuranceCharge
                    billState.insuranceGst.value = bill.insuranceGst

// Vehicle insurance details
                    billState.vehicleInsuranceType.value = bill.vehicleInsuranceType
                    billState.vehicleInsuranceCharge.value = bill.vehicleInsuranceCharge
                    billState.vehicleInsuranceGst.value = bill.vehicleInsuranceGst
                    billState.vehicleInsuranceRemark.value = bill.vehicleInsuranceRemark
                    mainViewModel.billState = billState
                    navController.navigate(Screens.BillForm.name)

                }) {


                    Icon(
                        imageVector = Icons.Default.Edit, contentDescription = "",
                        tint = Color(0xFFFFFFFF)
                        )

                    Text(
                        text = "Edit",
                        color = Color(0xFFFCFCFC),
                        fontFamily = latosemibold,
                        modifier = Modifier.padding(start = 10.dp)
                        )
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(modifier = Modifier.clickable {
                    showDialog.value = true
                }) {
                    Text(
                        text = "More",
                        color = Color(0xFFD5D2CC),
                        fontFamily = latosemibold,
                        modifier = Modifier.padding(start = 10.dp)
                        )

                    Icon(
                        imageVector = Icons.Default.MoreVert, contentDescription = "",
                        tint = Color(0xFFFFFDF7)
                        )


                }

            }

        }


    }
    if (showDialog.value) {
        ShowMore(
            showDialog, bill, mainViewModel,
            navController
                )
    }


}

@Composable
fun ShowMore(

    showDialog: MutableState<Boolean>,
    bill: bill,
    mainViewModel: MainViewModel,
    navController: NavHostController,

    ) {
    Dialog(onDismissRequest = { showDialog.value = false }) {
        val context = LocalContext.current
        Surface {
            Column {
                Text(
                    text = "Id.: ${bill.billNumber}",
                    modifier = Modifier.padding(10.dp),
                    fontFamily = latosemibold,
                    style = MaterialTheme.typography.titleLarge
                    )
                HorizontalDivider()
                ShowOptions(s = "Delete", delete = R.drawable.delete) {

                    mainViewModel.deleteBill(bill.id)
                }
                HorizontalDivider()
                ShowOptions(s = "Share Pdf", delete = R.drawable.next) {
                    mainViewModel.downloadPdf(
                        context = context,
                        id = bill.id,
                        share = true,
                        url = "billPdf"
                                             )
                }
                HorizontalDivider()
                ShowOptions(s = "View Pdf", delete = R.drawable.pdf) {

                    mainViewModel.downloadPdf(
                         context = context,
                         id = bill.id,
                         share = false,
                         url = "billPdf"
                                             )
                }
                HorizontalDivider()
                ShowOptions(s = "Generate Receipt", delete = R.drawable.receipt) {
                    val moneyReceiptState = MoneyReceiptState()
                    moneyReceiptState.date.value = LocalDate.now().toString()
                    moneyReceiptState.receiptAgainst.value = "Bill"
                    moneyReceiptState.billQuotationDate.value = bill.invoiceDate
                    moneyReceiptState.number.value = bill.billNumber
                    moneyReceiptState.moveFrom.value = bill.moveFrom
                    moneyReceiptState.moveTo.value = bill.moveTo
                    moneyReceiptState.receiptAmount.value = bill.total.toString()

                    mainViewModel.moneyreceiptno = -12
                    mainViewModel.moneyReceiptEditState = moneyReceiptState

                    navController.navigate(Screens.MoneyReceiptForm.name)
                }

            }
        }
    }
}



