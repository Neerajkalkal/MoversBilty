package com.example.gangapackagesolution.screens.quotationScreen.quotationShow

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.gangapackagesolution.R
import com.example.gangapackagesolution.models.Quotation.Quotation
import com.example.gangapackagesolution.models.bill.BillState
import com.example.gangapackagesolution.models.lr_bilty.LrBiltyState
import com.example.gangapackagesolution.models.moneyreceipt.MoneyReceiptState
import com.example.gangapackagesolution.screens.MainViewModel
import com.example.gangapackagesolution.screens.loadingAndErrorScreen.ErrorScreen
import com.example.gangapackagesolution.screens.loadingAndErrorScreen.LoadingScreen
import com.example.gangapackagesolution.screens.screenName.Screens
import com.example.gangapackagesolution.ui.theme.latolight
import com.example.gangapackagesolution.ui.theme.latosemibold
import java.time.LocalDate

@SuppressLint("MutableCollectionMutableState")
@Composable
fun QuotationListShow(
    viewModel: MainViewModel,
    navController: NavHostController
                     ) {
    val listState = viewModel.allQuotation.collectAsState()

    val list = listState.value.data?.sortedBy {
        it.id
    }
    LaunchedEffect(Unit) {
        viewModel.getAllQuotationList()
    }

    val searchState = remember {
        mutableStateOf("")
    }

    Column {
        Surface(
            modifier = Modifier.fillMaxWidth(), color = Color(0xFF673AB7)
               ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 15.dp),
                verticalAlignment = Alignment.CenterVertically
               ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back",
                     tint = Color.White, modifier = Modifier
                        .padding(17.dp)
                        .clickable {
                            navController.navigate(Screens.Home.name)
                        })

                Text(
                    text = "Quotations List", color = Color.White, fontFamily = latosemibold,
                    style = MaterialTheme.typography.titleLarge
                    )
                Spacer(modifier = Modifier.weight(1f))
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "",
                     tint = Color.White, modifier = Modifier.clickable {
                        viewModel.refreshTheQuotationPage()
                    })

            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        ShoSearch(onClick = {})

        if (listState.value.e != null) {
            listState.value.e!!.message?.let {
                ErrorScreen(error = it) {
                    viewModel.resetError()
                }
            }
        }


        if (listState.value.loading) {
            LoadingScreen(color = Color.White, indicatorColor = Color(0xFF673AB7))
        } else {
            if (listState.value.data.isNullOrEmpty()) {
                EmptyScreen()
            } else {
                LazyColumn {
                    items(list.orEmpty()) { quotation ->
                        ShowQuotation(quotation, viewModel, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyScreen() {
    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
          ) {
        Image(painter = painterResource(id = R.drawable.error), contentDescription = "")


    }
}

@Composable
fun ShoSearch(
    onClick: () -> Unit,
    text: String = "Quotation"
             ) {


    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), color = Color.White,
        shape = RoundedCornerShape(10.dp), shadowElevation = 5.dp
           ) {
        Row(
            modifier = Modifier.padding(15.dp), verticalAlignment = Alignment.CenterVertically
           ) {
            Icon(
                imageVector = Icons.Default.Search, contentDescription = "",
                tint = Color(0xFF673AB7)
                )

            Text(
                text = "Search For $text", fontFamily = latolight,
                modifier = Modifier.padding(start = 5.dp)
                )
        }
    }
}

@Composable
fun ShowQuotation(
    quotationList: Quotation,
    viewModel: MainViewModel,
    navController: NavHostController
                 ) {
    val color = if (quotationList.status == "Pending") Color.Red else Color(0xFF4CAF50)


    val text = if (quotationList.status != "Pending") "Completed" else "Pending"


    val showMoreDialog = remember {
        mutableStateOf(false)
    }


    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), color = Color(0xFF673AB7),
        shape = RoundedCornerShape(10.dp)
           ) {
        Column(
            modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(15.dp)
              ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Quotation Number : ${quotationList.id}", color = Color(0xFFFFC107),
                    fontFamily = latosemibold, style = MaterialTheme.typography.bodyMedium
                    )

                Spacer(modifier = Modifier.weight(1f))
                Surface(
                    color = Color.White, shape = RoundedCornerShape(10.dp),
                       ) {
                    Text(
                        text = "Status : $text", color = color, fontFamily = latosemibold,
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.bodySmall
                        )
                }
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    imageVector = Icons.Default.AccountCircle, contentDescription = "",
                    tint = Color.White,
                    )
                Text(
                    text = quotationList.partyName,
                    color = Color.White,
                    fontFamily = latosemibold,
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Date : ${quotationList.date}", color = Color.White,
                    fontFamily = latosemibold, style = MaterialTheme.typography.bodyLarge
                    )

            }
            HorizontalDivider()

            Text(
                text = "From - ${quotationList.city}", color = Color.White,
                fontFamily = latosemibold
                )

            Text(
                text = "To - ${quotationList.city2}", color = Color.White, fontFamily = latosemibold
                )

            Text(
                text = "Amount: ${quotationList.total}", color = Color.White,
                fontFamily = latosemibold
                )

            HorizontalDivider()
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
               ) {

                Text(
                    text = "Picking Date : \n ${quotationList.packingDate}", color = Color.White,
                    fontFamily = latosemibold
                    )
                Text(
                    text = "Delivery Date : \n${quotationList.shiftingDate}", color = Color.White,
                    fontFamily = latosemibold
                    )


            }

            HorizontalDivider()
            Row(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.clickable {

                    viewModel.setQuotationForEdit(quotationList)
                    navController.navigate("${Screens.QuoteForm}")


                }) {


                    Icon(
                        imageVector = Icons.Default.Edit, contentDescription = "",
                        tint = Color(0xFFFFC107)
                        )

                    Text(
                        text = "Edit", color = Color(0xFFFFC107), fontFamily = latosemibold,
                        modifier = Modifier.padding(start = 10.dp)
                        )
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(modifier = Modifier.clickable {
                    showMoreDialog.value = true
                }) {
                    Text(
                        text = "More", color = Color(0xFFFFC107), fontFamily = latosemibold,
                        modifier = Modifier.padding(start = 10.dp)
                        )

                    Icon(
                        imageVector = Icons.Default.MoreVert, contentDescription = "",
                        tint = Color(0xFFFFC107)
                        )


                }

            }

        }

    }
    if (showMoreDialog.value) {
        ShowMoreDialog(
            showMoreDialog, quotationList.status, viewModel, quotationList, color, text,
            navController
                      )
    }

}

@Composable
fun ShowMoreDialog(
    showMoreDialog: MutableState<Boolean>,
    statu: String,
    viewModel: MainViewModel,
    quotationList: Quotation,
    color: Color,
    text: String,
    navController: NavHostController,
                  ) {
    val status = rememberSaveable {
        mutableStateOf(statu)
    }

    Dialog(onDismissRequest = { showMoreDialog.value = false }) {

        Surface(
            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp)
               ) {
            val context = LocalContext.current
            Column {

                Text(
                    text = "Quotation No. ${quotationList.id}", modifier = Modifier.padding(10.dp),
                    fontFamily = latosemibold, style = MaterialTheme.typography.titleLarge
                    )
                HorizontalDivider()

                ShowOptions("Delete", R.drawable.delete) {
                    viewModel.deleteQuotation(quotationList.id.toString())
                }

                ShowOptions("View Pdf", R.drawable.pdf) {
                    Log.d("TAG", "ShowMoreDialog: ")
                    viewModel.getPdf(quotationList.id.toString(), context, false)
                }
                ShowOptions("Share Pdf", R.drawable.next) {

                    viewModel.getPdf(quotationList.id.toString(), context, true)

                }
                ShowOptions("Generate LR", R.drawable.box_truck) {
                    val lrBiltyState = LrBiltyState(
                        id = -12,
                                                   )
                    lrBiltyState.lrDate.value = LocalDate.now().toString()
                    lrBiltyState.moveFrom.value = quotationList.city
                    lrBiltyState.moveTo.value = quotationList.city2
                    lrBiltyState.consignorName.value = quotationList.partyName
                    lrBiltyState.freightBalance.value = quotationList.freightCharge
                    lrBiltyState.freightPaid.value = quotationList.advancePaid
                    lrBiltyState.loadingCharges.value = quotationList.loadingCharge
                    lrBiltyState.unloadingCharges.value = quotationList.unloadingCharge
                    lrBiltyState.stCharges.value = quotationList.stCharge
                    lrBiltyState.otherCharges.value = quotationList.otherCharge
                    lrBiltyState.gstperc.value = quotationList.gstperc

                    viewModel.lrBiltyState = lrBiltyState

                    navController.navigate(Screens.LrBillForm.name)


                }
                ShowOptions("Generate Bill", R.drawable.bill) {

                    val billState = BillState()
                    billState.billNumber.value = quotationList.id.toString()
                    billState.invoiceDate.value = LocalDate.now().toString()
                    billState.companyName.value = quotationList.companyName
                    billState.billToName.value = quotationList.partyName
                    billState.moveTo.value = quotationList.city2
                    billState.moveFrom.value = quotationList.city
                    billState.city.value = quotationList.city
                    billState.state.value = quotationList.state
                    billState.country.value = quotationList.country
                    billState.pinCode.value = quotationList.pinCode
                    billState.address.value = quotationList.address
                    billState.citionsignorName.value = quotationList.partyName
                    billState.citionsignorState.value = quotationList.state
                    billState.citionsignorCity.value = quotationList.city
                    billState.citionsignorPinCode.value = quotationList.pinCode
                    billState.citionsignorAddress.value = quotationList.address
                    billState.freightCharge.value = quotationList.freightCharge
                    billState.advancePaid.value = quotationList.advancePaid
                    billState.loadingCharge.value = quotationList.loadingCharge
                    billState.unloadingCharge.value = quotationList.unloadingCharge
                    billState.stCharge.value = quotationList.stCharge
                    billState.otherCharge.value = quotationList.otherCharge
                    billState.gst.value = quotationList.gstperc
                    billState.greentax.value = quotationList.octroiGreenTAx
                    billState.subcharge.value = quotationList.subcharges
                    billState.discount.value = quotationList.discount
                    billState.insuranceType.value = quotationList.insuranceType
                    billState.vehicleInsuranceType.value = quotationList.insuranceType1
                    billState.vehicleInsuranceGst.value = quotationList.gstperc1
                    billState.insuranceGst.value = quotationList.gstperc
                    billState.insuranceCharge.value = quotationList.insuranceCharge
                    billState.vehicleInsuranceCharge.value = quotationList.insuranceCharge1


                    viewModel.billState = billState
                    viewModel.billID = -12
                    navController.navigate(Screens.BillForm.name)

                }
                ShowOptions("Generate Money Receipt", R.drawable.receipt) {
                    val moneyReceipt = MoneyReceiptState()
                    viewModel.moneyreceiptno = -12
                    moneyReceipt.date.value = LocalDate.now().toString()
                    moneyReceipt.name.value = quotationList.partyName
                    moneyReceipt.moveTo.value = quotationList.city2
                    moneyReceipt.moveFrom.value = quotationList.city
                    moneyReceipt.receiptAgainst.value = "Quotation"
                    moneyReceipt.number.value = quotationList.id.toString()
                    moneyReceipt.billQuotationDate.value = quotationList.date
                    moneyReceipt.receiptAmount.value = quotationList.total
                    viewModel.moneyReceiptEditState = moneyReceipt
                    navController.navigate(Screens.MoneyReceiptForm.name)

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                   ) {

                    Text(
                        text = "Status: $text", fontFamily = latosemibold,
                        style = MaterialTheme.typography.titleMedium, color = color
                        )
                    Switch(
                        checked = status.value == "Success", onCheckedChange = {
                            if (it) {
                                val edited = quotationList.copy(status = "Success")
                                viewModel.saveEditedQuotation(edited)

                            } else {
                                val edited = quotationList.copy(status = "Pending")
                                viewModel.saveEditedQuotation(edited)

                            }

                        }, colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White, uncheckedThumbColor = Color.White,
                            uncheckedBorderColor = Color.Red,
                            checkedBorderColor = Color(0xFF4CAF50),
                            checkedTrackColor = Color(0xFF4CAF50), uncheckedTrackColor = Color.Red
                                                         )
                          )
                }
            }
        }

    }


}

@Composable
fun ShowOptions(
    s: String,
    delete: Int,

    onClick: () -> Unit
               ) {
    Row(modifier = Modifier
        .clickable {
            onClick()
        }
        .fillMaxWidth()
        .padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = delete), contentDescription = "",
            modifier = Modifier.size(40.dp)
             )

        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = s, fontFamily = latosemibold, style = MaterialTheme.typography.titleMedium
            )
        Spacer(modifier = Modifier.weight(1f))
    }
    HorizontalDivider()

}
