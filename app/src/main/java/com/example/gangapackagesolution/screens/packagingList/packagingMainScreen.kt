package com.example.gangapackagesolution.screens.packagingList

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gangapackagesolution.screens.BillScreen.ActionCard
import com.example.gangapackagesolution.screens.loadingAndErrorScreen.ErrorScreen
import com.example.gangapackagesolution.screens.loadingAndErrorScreen.LoadingScreen
import com.example.gangapackagesolution.screens.packagingList.components.ItemParticulars
import com.example.gangapackagesolution.screens.quotationScreen.CustomButton
import com.example.gangapackagesolution.screens.quotationScreen.Header
import com.example.gangapackagesolution.models.itemsParticaular.itemParticulars
import com.example.gangapackagesolution.models.packageList.state.CustomerDetails
import com.example.gangapackagesolution.models.packageList.state.packingListItems
import com.example.gangapackagesolution.screens.packagingList.components.CustomerDetails as CustomerDetails1

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun PackagingScreen(
    Viewmodel: packagingScreenViewmodel,
    navController: NavHostController,
    color: Color
                   ) {


    val change = remember {
        mutableStateOf(
            CustomerDetails(
                name = mutableStateOf(""),
                phone = mutableStateOf(""),
                packagingNo = mutableStateOf(""),
                date = mutableStateOf(""),
                moveTo = mutableStateOf(""),
                moveFrom = mutableStateOf(""),
                vehicleNo = mutableStateOf("")
                , id = -11
                           )
                      )
    }
    val item = remember {
        mutableStateOf(
            packingListItems(
                itemname = mutableStateOf(""),
                quantity = mutableStateOf(""),
                value = mutableStateOf(""),
                itemParticulars = mutableStateOf<List<itemParticulars>>(emptyList()),
                itemremark = mutableStateOf("")
                            )
                      )
    }
    val state = Viewmodel.packagingState.collectAsState()

    if (state.value.loading) {
        LoadingScreen(color = Color.White, indicatorColor = color)
    } else if (state.value.e != null) {
        ErrorScreen(error = state.value.e.toString()) {
            navController.popBackStack()
        }
    } else {

        PackageForm(navController, Viewmodel, change, item,color)

    }
}

@Composable
fun PackageForm(
    navController: NavHostController,
    packagingScreenViewmodel: packagingScreenViewmodel,
    change: MutableState<CustomerDetails>,
    item: MutableState<packingListItems>,
    color: Color
               ) {
    Column() {
        Header(
            text = "Packaging List",
            color = color
              ) {
            navController.popBackStack()
        }

        // coustomer Details

        Column(
            modifier = Modifier
                .padding(10.dp)
                .verticalScroll(rememberScrollState())
              ) {

            ActionCard(title = "Customer Details", expandCardCompose = {
                CustomerDetails1(
                    name = change.value.name, phone = change.value.phone,
                    packagingNumber = change.value.packagingNo, date = change.value.date,
                    moveFrom = change.value.moveFrom, moveTo = change.value.moveTo,
                    vehicleNumber = change.value.vehicleNo,
                                )
            }, color =  color)

            Spacer(modifier = Modifier.height(20.dp))
            ActionCard(title = "Item Details", expandCardCompose = {
                ItemParticulars(
                    itemname = item.value.itemname, quantity = item.value.quantity,
                    value = item.value.value, itemremark = item.value.itemremark,
                    itemParticulars = item.value.itemParticulars,color)
            }, color = color)

            Spacer(modifier = Modifier.height(20.dp))

            CustomButton(onItemClick1 = "Submit",color) {
                packagingScreenViewmodel.submitPackageList(
                    id = change.value.id,
                    itemParticulars = item,
                    onComplete = {
                        navController.popBackStack()
                    },
                    customerDetails = change,

                    )
            }
        }

    }
}
