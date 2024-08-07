package com.example.gangapackagesolution.screens.nav

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gangapackagesolution.models.bill.BillState
import com.example.gangapackagesolution.repository.TokenManagement
import com.example.gangapackagesolution.screens.BillScreen.BillMainScreen
import com.example.gangapackagesolution.screens.BillScreen.BillScreenForm
import com.example.gangapackagesolution.screens.BillScreen.showbill.ShowBillScreen
import com.example.gangapackagesolution.screens.Login
import com.example.gangapackagesolution.screens.MainViewModel
import com.example.gangapackagesolution.screens.homeScreen.HomeScreen
import com.example.gangapackagesolution.screens.loginViewmodel
import com.example.gangapackagesolution.screens.lrbilty.LrBillMainScreen
import com.example.gangapackagesolution.screens.lrbilty.LrFormScreen
import com.example.gangapackagesolution.screens.lrbilty.showlrbilty.ShowLrBill
import com.example.gangapackagesolution.screens.moneyreciept.MoneyReceipt
import com.example.gangapackagesolution.screens.moneyreciept.MoneyReceiptForm
import com.example.gangapackagesolution.screens.moneyreciept.ShowMoneyReceipt
import com.example.gangapackagesolution.screens.newuser.NewUser
import com.example.gangapackagesolution.screens.packagingList.PackageForm
import com.example.gangapackagesolution.screens.packagingList.PackagingScreen
import com.example.gangapackagesolution.screens.packagingList.packagingScreenViewmodel
import com.example.gangapackagesolution.screens.packagingList.showpackagingList.ShowPackagingList
import com.example.gangapackagesolution.screens.profile.ProfileScreen
import com.example.gangapackagesolution.screens.quotationScreen.QuotationMainScreen
import com.example.gangapackagesolution.screens.quotationScreen.QuotationScreen
import com.example.gangapackagesolution.screens.quotationScreen.quotationShow.QuotationListShow
import com.example.gangapackagesolution.screens.screenName.Screens
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MainNav(context: Context) {
    val TokenManagement = TokenManagement(context)
    val navController = rememberNavController()
    val mainViewModel = MainViewModel(context)
    val packagingScreenViewmodel = packagingScreenViewmodel(context)

    NavHost(
        navController = navController, startDestination =
         if (TokenManagement.getToken().isNullOrBlank()) {
             Screens.Login.name
         } else {
             if (TokenManagement.isNewUser()) {
                 Screens.NewUser.name
             }
             else{

                 Screens.Home.name
             }
         }


           ) {


        composable(route = Screens.Login.name) {
            val loginViewmodel = loginViewmodel(context)
            Login(loginViewmodel, navController)
        }


        // homeScreen
        composable(route = Screens.Home.name) {
            val systemUiController = rememberSystemUiController()
            systemUiController.setSystemBarsColor(
                color = Color(0xFF673AB7), // Change to your desired color
                darkIcons = false,

                )
            HomeScreen(navController,mainViewModel)
        }

        // ListOfQuotations
        composable(route = Screens.QuoteList.name) {
            QuotationListShow(viewModel = mainViewModel, navController)
        }

//QuoteEditForm
        composable(
            route = Screens.QuoteForm.name
                  ) {

            mainViewModel.quotationForEdit?.let { it1 ->
                QuotationScreen(
                    data =
                    it1,
                               ) {
                    mainViewModel.saveEditedQuotation(quotation = it)
                    navController.popBackStack()
                }
            }

        }

        // getQuotationScreen

        composable(
            route = Screens.GetQuote.name
                  ) {
            QuotationMainScreen(
                navController = navController,
                MainViewModel = mainViewModel
                               )
        }

        // packaging list Screen
        composable(route = Screens.PackageScreen.name) {
            PackagingScreen(
                packagingScreenViewmodel,
                navController
                           )
        }

        //getpackage
        composable(
            route = Screens.GetPackage.name
                  ) {


            ShowPackagingList(packagingScreenViewmodel = mainViewModel, navController)
        }

        // packageForm
        composable(
            route = Screens.PackageForm.name
                  ) {

            PackageForm(
                navController = navController, packagingScreenViewmodel = packagingScreenViewmodel,
                change = mainViewModel.change, item = mainViewModel.item
                       )

        }

        // lrMain
        composable(
            route = Screens.LrBill.name
                  ) {
            LrBillMainScreen(navController, mainViewModel)
        }
// get lr
        composable(Screens.GetLrBill.name) {
            LaunchedEffect(Unit) {
                mainViewModel.getLrBill()
            }
            ShowLrBill(navController, mainViewModel)
        }

        // edit lr
        composable(route = Screens.LrBillForm.name) {

            LrFormScreen(
                lrBiltyState = mainViewModel.lrBiltyState, mainViewModel = mainViewModel,
                navController = navController
                        )

        }


        composable(Screens.MoneyReceiptScreen.name) {
            MoneyReceipt(navController, mainViewModel)
        }
        composable(Screens.GetMoneyReceipt.name) {
            LaunchedEffect(Unit) {
                mainViewModel.getMoneyReceipt()
            }
            ShowMoneyReceipt(navController, mainViewModel)
        }

        composable(Screens.MoneyReceiptForm.name) {

            MoneyReceiptForm(
                moneyReceiptState = mainViewModel.moneyReceiptEditState,
                navController = navController, mainViewModel = mainViewModel
                            )
        }

        // bill screen
        composable(Screens.Bill.name) {
            val billState = BillState()
            BillMainScreen(navController = navController, billState = billState, mainViewModel)
        }


        // show bills
        composable(Screens.GetBill.name) {
            LaunchedEffect(Unit) {
                mainViewModel.getBill()
            }

            ShowBillScreen(navController, mainViewModel)
        }
        // bill Form
        composable(route = Screens.BillForm.name) {
            BillScreenForm(
                billState = mainViewModel.billState, navController = navController,
                mainViewModel = mainViewModel
                          )

        }

        composable(Screens.ProfileScreen.name){
            ProfileScreen(mainViewModel,navController)
        }

        composable(Screens.NewUser.name){
            NewUser(navController,TokenManagement.getToken(),TokenManagement.getEmail(),mainViewModel,TokenManagement){

            }

        }

    }
}