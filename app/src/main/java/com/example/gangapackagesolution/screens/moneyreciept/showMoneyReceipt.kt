package com.example.gangapackagesolution.screens.moneyreciept

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
import com.example.gangapackagesolution.models.moneyreceipt.MoneyReceipt
import com.example.gangapackagesolution.models.moneyreceipt.MoneyReceiptState
import com.example.gangapackagesolution.screens.BillScreen.showbill.BillFormat
import com.example.gangapackagesolution.screens.MainViewModel
import com.example.gangapackagesolution.screens.loadingAndErrorScreen.ErrorScreen
import com.example.gangapackagesolution.screens.loadingAndErrorScreen.LoadingScreen
import com.example.gangapackagesolution.screens.packagingList.showpackagingList.ShowingHeader
import com.example.gangapackagesolution.screens.quotationScreen.quotationShow.ShoSearch
import com.example.gangapackagesolution.screens.quotationScreen.quotationShow.ShowOptions
import com.example.gangapackagesolution.screens.screenName.Screens
import com.example.gangapackagesolution.ui.theme.latosemibold
import com.google.firebase.Firebase
import com.google.firebase.app
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowMoneyReceipt(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    color: Color
                    ) {
    val search = remember {
        mutableStateOf("")
    }
    val active = remember {
        mutableStateOf(false)
    }
    val state = mainViewModel.getMoneyReceipt.collectAsState()


    val x = Firebase.app.name

    Column {
        ShowingHeader(s = "Money Receipts", color = color, onClick1 = {mainViewModel.getMoneyReceipt()}) {
            navController.popBackStack()
        }



        if (state.value.loading) {
            LoadingScreen(color = Color.White, indicatorColor =color)
        } else if (state.value.e != null) {
            ErrorScreen(error = state.value.e.toString()) {
                navController.popBackStack()
            }
        } else {
            if (state.value.data != null) {
                val packageList1 = remember {
                   state.value.data
                }

                var List1 = remember {
                    mutableStateOf(packageList1)
                }

                SearchBar(query = search.value, onQueryChange = {
                    search.value = it

                    if (packageList1 != null) {
                        List1.value = packageList1.filter { list ->

                            list.name.toLowerCase(Locale.ROOT)
                                .contains(
                                    search.value.toLowerCase()
                                         ) || list.phone
                                .contains(search.value)

                        }
                    }


                }, onSearch = {
                    if (packageList1 != null) {
                        List1.value = packageList1.filter { list ->

                            list.name.toLowerCase(Locale.ROOT)
                                .contains(
                                    search.value.toLowerCase()
                                         ) || list.phone
                                .contains(search.value)
}
                    }
                },
                          active = active.value, onActiveChange = {
                        active.value = !active.value
                    },
                          modifier = Modifier.fillMaxWidth(),
                          placeholder = { Text(text = "Search For Name or Mobile No.") }) {
                    LazyColumn() {
                        items(List1.value?: emptyList()) { quotation ->
                            ShowMoneyReceipts(quotation, mainViewModel, navController,color)
                        }
                    }

                }




                LazyColumn {
                    items(state.value.data ?: emptyList()) { item ->
                        ShowMoneyReceipts(item, mainViewModel, navController,color)


                    }
                }
            }
        }


    }
}

@Composable
fun ShowMoneyReceipts(
    moneyReceipt: MoneyReceipt,
    mainViewModel: MainViewModel,
    navController: NavHostController,
    color: Color
                     ) {

    val showDialog = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.padding(15.dp),
        color = color,
        shape = MaterialTheme.shapes.small
           ) {

        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = "Id : ${moneyReceipt.receiptNumber}",
                color = Color(0xFFFFFFFF),
                style = MaterialTheme.typography.titleLarge
                )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
               ) {
                Text(
                    text = "Date:${moneyReceipt.date}",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                    )


                Text(
                    text = "Total : ₹${moneyReceipt.receiptAmount}",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                    )
            }

            HorizontalDivider(modifier = Modifier.padding(15.dp), color = Color.White)


            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    imageVector = Icons.Default.AccountCircle, contentDescription = "",
                    tint = Color(0xFFE7E4DA)
                    )

                Text(
                    text = "Name:${moneyReceipt.name}",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(5.dp)
                    )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Default.Call, contentDescription = "",
                    tint = Color(0xFFF3F0E8)
                    )

                Text(
                    text = moneyReceipt.phone,
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
                        text = moneyReceipt.moveFrom,
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
                        text = moneyReceipt.moveTo,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                        )


                }

            }

            HorizontalDivider(modifier = Modifier.padding(15.dp), color = Color.White)
            Row(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.clickable {
                    val moneyReceiptState = MoneyReceiptState()
                    mainViewModel.moneyreceiptno = moneyReceipt.id

                    moneyReceiptState.receiptAgainst.value = moneyReceipt.receiptAgainst
                    moneyReceiptState.receiptNumber.value = moneyReceipt.receiptNumber
                    moneyReceiptState.date.value = moneyReceipt.date
                    moneyReceiptState.name.value = moneyReceipt.name
                    moneyReceiptState.number.value = moneyReceipt.number
                    moneyReceiptState.moveFrom.value = moneyReceipt.moveFrom
                    moneyReceiptState.moveTo.value = moneyReceipt.moveTo
                    moneyReceiptState.receiptAmount.value = moneyReceipt.receiptAmount
                    moneyReceiptState.phone.value = moneyReceipt.phone
                    moneyReceiptState.paymentType.value = moneyReceipt.paymentType
                    moneyReceiptState.paymentMode.value = moneyReceipt.paymentMode
                    moneyReceiptState.remarks.value = moneyReceipt.remarks
                    moneyReceiptState.branch.value = moneyReceipt.branch

                    mainViewModel.moneyReceiptEditState = moneyReceiptState

                    navController.navigate(Screens.MoneyReceiptForm.name)

                }) {


                    Icon(
                        imageVector = Icons.Default.Edit, contentDescription = "",
                        tint = Color(0xFFEEEBE4)
                        )

                    Text(
                        text = "Edit",
                        color = Color(0xFFE9E4D5),
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
                        color = Color(0xFFFAF5E8),
                        fontFamily = latosemibold,
                        modifier = Modifier.padding(start = 10.dp)
                        )

                    Icon(
                        imageVector = Icons.Default.MoreVert, contentDescription = "",
                        tint = Color(0xFFFDFBF6)
                        )


                }

            }

        }


    }
    if (showDialog.value) {
        ShowMorePackage(showDialog, mainViewModel, moneyReceipt)
    }


}

@Composable
fun ShowMorePackage(

    showDialog: MutableState<Boolean>,
    mainViewModel: MainViewModel,
    moneyReceipt: MoneyReceipt,

    ) {
    val context = LocalContext.current
    Dialog(onDismissRequest = { showDialog.value = false }) {
        Surface {
            Column {
                Text(
                    text = "Id.: 1",
                    modifier = Modifier.padding(10.dp),
                    fontFamily = latosemibold,
                    style = MaterialTheme.typography.titleLarge
                    )
                HorizontalDivider()
                ShowOptions(s = "Delete", delete = R.drawable.delete) {
                    mainViewModel.deleteMoneyReceipt(moneyReceipt.id.toString())

                }
                HorizontalDivider()
                ShowOptions(s = "Share Pdf", delete = R.drawable.next) {
                    mainViewModel.downloadPdf(
                        context = context,
                        id = moneyReceipt.id.toString(),
                        share = true,
                        url = "moneyReceiptPdf"

                                             )
                }
                HorizontalDivider()
                ShowOptions(s = "View Pdf", delete = R.drawable.pdf) {
                    mainViewModel.downloadPdf(
                        context = context,
                        id = moneyReceipt.id.toString(),
                        share = false,
                        url = "moneyReceiptPdf"

                                             )
                }
            }
        }
    }
}


