package com.example.gangapackagesolution.screens.lrbilty.showlrbilty

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.gangapackagesolution.R
import com.example.gangapackagesolution.models.bill.BillState
import com.example.gangapackagesolution.models.lr_bilty.LrBilty
import com.example.gangapackagesolution.models.lr_bilty.LrBiltyState
import com.example.gangapackagesolution.screens.MainViewModel
import com.example.gangapackagesolution.screens.loadingAndErrorScreen.ErrorScreen
import com.example.gangapackagesolution.screens.loadingAndErrorScreen.LoadingScreen
import com.example.gangapackagesolution.screens.packagingList.showpackagingList.ShowingHeader
import com.example.gangapackagesolution.screens.quotationScreen.quotationShow.ShoSearch
import com.example.gangapackagesolution.screens.quotationScreen.quotationShow.ShowOptions
import com.example.gangapackagesolution.screens.screenName.Screens
import com.example.gangapackagesolution.ui.theme.latosemibold
import java.time.LocalDateTime

@Composable
fun ShowLrBill(
    navController: NavHostController,
    mainViewModel: MainViewModel
              ) {
    val state = mainViewModel.lrBill.collectAsState()


    val data = state.value.data?.sortedBy {
        it.lrNumber
    }


    Column {
        ShowingHeader(s = "Lr Bills") {
            navController.popBackStack()
        }

        ShoSearch(onClick = {}, text = "LR Bills")


        if (state.value.loading) {
            LoadingScreen(
                color = Color.White, indicatorColor =
                Color(0xFF673AB7)
                         )
        }
        if (state.value.e != null) {
            ErrorScreen(error = state.value.e.toString()) {
                navController.popBackStack()
            }
        }

        if (state.value.data != null) {
            LazyColumn {
                items(data!!) {
                    ShowBills(it, mainViewModel,navController)
                }
            }


        }

        if (data!=null) {
            if (data.isEmpty() && state.value.e == null && !state.value.loading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center

                      ) {
                    Image(painter = painterResource(id = R.drawable.error), contentDescription = "")
                    Text(text = "No Data Found")
                }
            }
        }
    }
}

@Composable
fun ShowBills(
    lrBilty: LrBilty,
    mainViewModel: MainViewModel,
    navController: NavHostController
             ) {

    val showDialog = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.padding(15.dp),
        color = Color(0xFF673AB7),
        shape = MaterialTheme.shapes.small
           ) {

        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = "Id : ${lrBilty.lrNumber}",
                color = Color(0xFFFFC107),
                style = MaterialTheme.typography.titleLarge
                )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
               ) {
                Text(
                    text = "Date:${lrBilty.lrDate}",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                    )


                Text(
                    text = "Total : ${lrBilty.total}",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                    )
            }

            HorizontalDivider(modifier = Modifier.padding(15.dp), color = Color.White)


            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    imageVector = Icons.Default.AccountCircle, contentDescription = "",
                    tint = Color(0xFFFFC107)
                    )

                Text(
                    text = "Customer Name:${lrBilty.consignorName}",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(5.dp)
                    )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Default.Call, contentDescription = "",
                    tint = Color(0xFFFFC107)
                    )

                Text(
                    text = lrBilty.consignorNumber,
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
                        text = lrBilty.moveFrom,
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
                        text = lrBilty.moveTo,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                        )


                }

            }

            HorizontalDivider(modifier = Modifier.padding(15.dp), color = Color.White)
            Row(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.clickable {

                    val lrBilledit = LrBiltyState(
                        id = lrBilty.id,
                                                 )
                    lrBilledit.lrNumber.value = lrBilty.lrNumber
                    lrBilledit.lrDate.value = lrBilty.lrDate
                    lrBilledit.riskType.value = lrBilty.riskType
                    lrBilledit.truckNumber.value = lrBilty.truckNumber
                    lrBilledit.moveFrom.value = lrBilty.moveFrom
                    lrBilledit.moveTo.value = lrBilty.moveTo
                    lrBilledit.driverName.value = lrBilty.driverName
                    lrBilledit.driverNumber.value = lrBilty.driverNumber
                    lrBilledit.driverLicense.value = lrBilty.driverLicense
                    lrBilledit.consignorName.value = lrBilty.consignorName
                    lrBilledit.consignorNumber.value = lrBilty.consignorNumber
                    lrBilledit.gstNumber.value = lrBilty.gstNumber
                    lrBilledit.country.value = lrBilty.country
                    lrBilledit.state.value = lrBilty.state
                    lrBilledit.city.value = lrBilty.city
                    lrBilledit.pincode.value = lrBilty.pincode
                    lrBilledit.address.value = lrBilty.address
                    lrBilledit.consigneeName.value = lrBilty.consigneeName
                    lrBilledit.consigneeNumber.value = lrBilty.consigneeNumber
                    lrBilledit.gstNumber1.value = lrBilty.gstNumber1
                    lrBilledit.country1.value = lrBilty.country1
                    lrBilledit.state1.value = lrBilty.state1
                    lrBilledit.city1.value = lrBilty.city1
                    lrBilledit.pincode1.value = lrBilty.pincode1
                    lrBilledit.address1.value = lrBilty.address1
                    lrBilledit.numberOfPackages.value = lrBilty.numberOfPackages
                    lrBilledit.description.value = lrBilty.description
                    lrBilledit.Unit.value = lrBilty.actualweightType
                    lrBilledit.actualWeight.value = lrBilty.actualWeight
                    lrBilledit.chargedWeight.value = lrBilty.chargedWeight
                    lrBilledit.receivePackageCondition.value = lrBilty.receivePackageCondition
                    lrBilledit.remarks.value = lrBilty.remarks
                    lrBilledit.freightToBeBilled.value = lrBilty.freightToBeBilled
                    lrBilledit.freightPaid.value = lrBilty.freightPaid
                    lrBilledit.freightBalance.value = lrBilty.freightBalance
                    lrBilledit.totalBasicFreight.value = lrBilty.totalBasicFreight
                    lrBilledit.loadingCharges.value = lrBilty.loadingCharges
                    lrBilledit.unloadingCharges.value = lrBilty.unloadingCharges
                    lrBilledit.stCharges.value = lrBilty.stCharges
                    lrBilledit.otherCharges.value = lrBilty.otherCharges
                    lrBilledit.lr_cnCharges.value = lrBilty.lr_cnCharges
                    lrBilledit.gstperc.value = lrBilty.gstperc
                    lrBilledit.gstPaidBy.value = lrBilty.gstPaidBy
                    lrBilledit.materialInsurance.value = lrBilty.materialInsurance
                    lrBilledit.insuranceCompany.value = lrBilty.insuranceCompany
                    lrBilledit.policyNumber.value = lrBilty.policyNumber
                    lrBilledit.insuranceDate.value = lrBilty.insuranceDate
                    lrBilledit.insuredAmount.value = lrBilty.insuredAmount
                    lrBilledit.insuranceRisk.value = lrBilty.insuranceRisk
                    lrBilledit.demarrageCharge.value = lrBilty.demarrageCharge
                    lrBilledit.perDayorhour.value = lrBilty.perDayorhour
                    lrBilledit.demurageChargeApplicableAfter.value = lrBilty.demurageChargeApplicableAfter



                    mainViewModel.lrBiltyno = lrBilty.id



                    mainViewModel.lrBiltyState =
                        lrBilledit

                    navController.navigate(Screens.LrBillForm.name)


                }) {


                    Icon(
                        imageVector = Icons.Default.Edit, contentDescription = "",
                        tint = Color(0xFFFFC107)
                        )

                    Text(
                        text = "Edit",
                        color = Color(0xFFFFC107),
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
                        color = Color(0xFFFFC107),
                        fontFamily = latosemibold,
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
    if (showDialog.value) {
        ShowMorePackage(showDialog,mainViewModel,lrBilty,navController)
    }


}

@Composable
fun ShowMorePackage(

    showDialog: MutableState<Boolean>,
    mainViewModel: MainViewModel,
    lrBilty: LrBilty,
    navController: NavHostController,

    ) {
    Dialog(onDismissRequest = { showDialog.value = false }) {
        Surface {
            val context = LocalContext.current
            Column {
                Text(
                    text = "Id.: ${lrBilty.lrNumber}",
                    modifier = Modifier.padding(10.dp),
                    fontFamily = latosemibold,
                    style = MaterialTheme.typography.titleLarge
                    )
                HorizontalDivider()
                ShowOptions(s = "Delete", delete = R.drawable.delete) {
mainViewModel.deleteLr(id = lrBilty.id.toString())
                }
                HorizontalDivider()
                ShowOptions(s = "Share Pdf", delete = R.drawable.next) {
                    mainViewModel.downloadPdf(
                        context = context,
                        id = lrBilty.id.toString(),
                        share = true,
                        url = "LrBill"
                                             )
                }
                HorizontalDivider()
                ShowOptions(s = "View Pdf", delete = R.drawable.pdf) {
                    Log.d("helllo ", "main viewmodel is working till now")
                    mainViewModel.downloadPdf(
                        context = context,
                        id = lrBilty.id.toString(),
                        share = false,
                        url = "LrBill"
                                             )
                }
                HorizontalDivider()
                ShowOptions(s = "Generate Bill", delete = R.drawable.bill) {

                    val date = LocalDateTime.now().toLocalDate()
                    val billState = BillState(
                                             )
                    billState.lrNumber.value = lrBilty.lrNumber
                    billState.invoiceDate.value = date.toString()
                    billState.movingPath.value = "By Road"
                    billState.moveFrom.value = lrBilty.moveFrom
                    billState.moveTo.value = lrBilty.moveTo
                    billState.vehicleNumber.value = lrBilty.truckNumber
                    billState.billToName.value = lrBilty.consigneeName
                    billState.billToPhone.value = lrBilty.consigneeNumber
                    billState.gstin.value = lrBilty.gstNumber
                    billState.country.value = lrBilty.country
                    billState.state.value = lrBilty.state
                    billState.city.value = lrBilty.city
                    billState.pinCode.value = lrBilty.pincode
                    billState.address.value = lrBilty.address

                    billState.consigneeName.value = lrBilty.consignorName
                    billState.consigneePhone.value = lrBilty.consignorNumber
                    billState.consigneeGstin.value = lrBilty.gstNumber
                    billState.consigneeCountry.value = lrBilty.country
                    billState.consigneeState.value = lrBilty.state
                    billState.consigneeCity.value = lrBilty.city
                    billState.consigneePinCode.value = lrBilty.pincode
                    billState.consigneeAddress.value = lrBilty.address
                    billState.packageName.value = lrBilty.numberOfPackages
                    billState.description.value = lrBilty.description
                    billState.weightType.value = lrBilty.actualweightType
                    billState.weight.value = lrBilty.chargedWeight
                    billState.freightCharge.value = lrBilty.totalBasicFreight
                    billState.loadingCharge.value = lrBilty.loadingCharges
                    billState.advancePaid.value = lrBilty.freightPaid
                   billState.loadingChargeType.value = "Additional From Freight"
                    billState.loadingCharge.value = lrBilty.loadingCharges
                    billState.unloadingChargeType.value = "Additional To Freight"
                    billState.unloadingCharge.value = lrBilty.unloadingCharges
                    billState.stCharge.value = lrBilty.stCharges
                    billState.otherCharge.value = lrBilty.otherCharges
                    billState.miscellaneousCharge.value = lrBilty.lr_cnCharges
                    billState.gst.value = lrBilty.gstperc
                    billState.gstin.value ="Included In Bill"
                    billState.gstpaidby.value = lrBilty.gstPaidBy



                    mainViewModel.billState = billState
                    navController.navigate(Screens.BillForm.name)


                }
            }
        }
    }
}

