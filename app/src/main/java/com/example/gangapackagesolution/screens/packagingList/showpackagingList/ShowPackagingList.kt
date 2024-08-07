package com.example.gangapackagesolution.screens.packagingList.showpackagingList

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.gangapackagesolution.R
import com.example.gangapackagesolution.screens.MainViewModel
import com.example.gangapackagesolution.screens.loadingAndErrorScreen.ErrorScreen
import com.example.gangapackagesolution.screens.loadingAndErrorScreen.LoadingScreen
import com.example.gangapackagesolution.screens.quotationScreen.quotationShow.ShoSearch
import com.example.gangapackagesolution.screens.quotationScreen.quotationShow.ShowOptions
import com.example.gangapackagesolution.screens.screenName.Screens
import com.example.gangapackagesolution.models.packageList.PackageList
import com.example.gangapackagesolution.ui.theme.latosemibold

@Composable
fun ShowPackagingList(
    packagingScreenViewmodel: MainViewModel,
    navController: NavHostController
                     ) {

    LaunchedEffect(key1 = Unit) {
        packagingScreenViewmodel.getPackagingList()
    }


    val packagingList = packagingScreenViewmodel.getPackagingList.collectAsState()

    if (packagingList.value.loading) {
        LoadingScreen(color = Color.White, indicatorColor = Color(0xFF673AB7))

    } else if (packagingList.value.e != null) {
        ErrorScreen(error = packagingList.value.e.toString()) {

        }
    } else {


        Column {
            ShowingHeader("Packaging List")

            ShoSearch(text = "Search For Packaging List", onClick = {})
            if (packagingList.value.data?.isEmpty() != true && packagingList.value.data != null) {

                val packageList1 = packagingList.value.data!!.sortedBy {
                    it.id
                }
                LazyColumn() {
                    items(packageList1) {
                        PackagingList(it, packagingScreenViewmodel, navController)
                    }
                }


            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                      ) {
                    Image(painter = painterResource(id = R.drawable.error), contentDescription = "")
                }
            }


        }
    }
}


@Composable
fun PackagingList(
    packageList: PackageList,
    packagingScreenViewmodel: MainViewModel,
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
                text = "Id : ${packageList.packagingNumber}",
                color = Color(0xFFFFC107),
                style = MaterialTheme.typography.titleLarge
                )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
               ) {
                Text(
                    text = "Date:${packageList.date}",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                    )


                Text(
                    text = "Total Items: ${packageList.particularList.size}",
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
                    text = packageList.name,
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
                    text = packageList.phone,
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
                        text = packageList.moveFrom,
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
                        text = packageList.moveTo,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                        )


                }

            }

            HorizontalDivider(modifier = Modifier.padding(15.dp), color = Color.White)
            Row(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.clickable {
                    packagingScreenViewmodel.change.value.name.value = packageList.name
                    packagingScreenViewmodel.change.value.phone.value = packageList.phone
                    packagingScreenViewmodel.change.value.moveFrom.value = packageList.moveFrom
                    packagingScreenViewmodel.change.value.moveTo.value = packageList.moveTo
                    packagingScreenViewmodel.change.value.packagingNo.value =
                        packageList.packagingNumber
                    packagingScreenViewmodel.change.value.date.value = packageList.date
                    packagingScreenViewmodel.change.value.id = packageList.id

                    packagingScreenViewmodel.item.value.itemParticulars.value =
                        packageList.particularList


                    navController.navigate(Screens.PackageForm.name)


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
        ShowMorePackage(packageList, showDialog, packagingScreenViewmodel)
    }


}

@Composable
fun ShowMorePackage(
    packageList: PackageList,
    showDialog: MutableState<Boolean>,
    packagingScreenViewmodel: MainViewModel
                   ) {
    Dialog(onDismissRequest = { showDialog.value = false }) {
        Surface {
            Column {
                Text(
                    text = "Quotation No. ${packageList.id}",
                    modifier = Modifier.padding(10.dp),
                    fontFamily = latosemibold,
                    style = MaterialTheme.typography.titleLarge
                    )
                HorizontalDivider()
                ShowOptions(s = "Delete", delete = R.drawable.delete) {
                    packagingScreenViewmodel.deletePackageList(id = packageList.id)
                }
                HorizontalDivider()
                ShowOptions(s = "Share Pdf", delete = R.drawable.next) {
                }
                HorizontalDivider()
                ShowOptions(s = "View Pdf", delete = R.drawable.pdf) {

                }
                HorizontalDivider()
            }
        }
    }
}


@Composable
fun ShowingHeader(s: String,onClick:()->Unit={}) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFF673AB7)
           ) {
        Row(
            modifier = Modifier.padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
           ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "",
                tint = Color.White,
                modifier = Modifier.clickable {
                    onClick()
                } )


            Text(
                text = s,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
                )


            Icon(
                imageVector = Icons.Default.Refresh, contentDescription = "",
                tint = Color.White
                )
        }


    }
}
