package com.example.gangapackagesolution.screens

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gangapackagesolution.models.DataOrException
import com.example.gangapackagesolution.models.DetailsToSend
import com.example.gangapackagesolution.models.Outcome
import com.example.gangapackagesolution.models.Quotation.Quotation
import com.example.gangapackagesolution.models.UserDetails
import com.example.gangapackagesolution.models.bill.BillState
import com.example.gangapackagesolution.models.bill.bill
import com.example.gangapackagesolution.models.itemsParticaular.itemParticulars
import com.example.gangapackagesolution.models.lr_bilty.LrBilty
import com.example.gangapackagesolution.models.lr_bilty.LrBiltyState
import com.example.gangapackagesolution.models.moneyreceipt.MoneyReceipt
import com.example.gangapackagesolution.models.moneyreceipt.MoneyReceiptState
import com.example.gangapackagesolution.models.notifications.Notifications
import com.example.gangapackagesolution.models.packageList.PackageList
import com.example.gangapackagesolution.models.packageList.state.CustomerDetails
import com.example.gangapackagesolution.models.packageList.state.packingListItems
import com.example.gangapackagesolution.repository.Repository
import com.example.gangapackagesolution.repository.TokenManagement
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(context: Context) : ViewModel() {
    val token = TokenManagement(context)

    // add lrbilty
    private val _lrbilty = MutableStateFlow<DataOrException<String, Exception>>(DataOrException())
    val lrbill: StateFlow<DataOrException<String, Exception>> = _lrbilty
    var lrBiltyno = -12


    // getting the Quotation pdf from the server
    private val _pdf = MutableStateFlow<DataOrException<String, Exception>>(DataOrException())
    val pdf: StateFlow<DataOrException<String, Exception>> = _pdf


    private val _quotation =
        MutableStateFlow<DataOrException<Quotation, Exception>>(DataOrException())
    val quotation: StateFlow<DataOrException<Quotation, Exception>> = _quotation


    private var _quotationForEdit: Quotation? = null
    val quotationForEdit: Quotation?
        get() = _quotationForEdit


    private val _allQuotation =
        MutableStateFlow<DataOrException<List<Quotation>, Exception>>(DataOrException())
    val allQuotation: StateFlow<DataOrException<List<Quotation>, Exception>> = _allQuotation


    // saveNewQuotation
    private val _quotaionSaveState =
        MutableStateFlow<DataOrException<String, Exception>>(DataOrException())
    val quotaionSaveState: StateFlow<DataOrException<String, Exception>> = _quotaionSaveState


    fun saveNewQuotation(
        quotation: Quotation,
        onComplete: () -> Unit
                        ) {
        viewModelScope.launch {
            Repository.saveQuotation(
                quotation, _quotaionSaveState, token.getToken()
                                    ) {

                onComplete()
            }

        }
    }


    fun getQuotation() {
        viewModelScope.launch {
            Repository.getTheQuotationForm(
                _quotation, token.getToken()
                                          )
        }
    }

    fun refreshTheQuotationPage() {
        _quotation.value = DataOrException()
        getAllQuotationList()
    }


    fun getAllQuotationList() {
        viewModelScope.launch {
            Repository.gettingListOfQuotation(
                _allQuotation, token.getToken()
                                             )
        }
    }

    fun saveEditedQuotation(quotation: Quotation) {
        viewModelScope.launch {
            Repository.saveEdited(
                quotation, _allQuotation, token.getToken()
                                 ) {
                refreshTheQuotationPage()
            }
        }
    }


    fun setQuotationForEdit(quotation: Quotation) {
        _quotationForEdit = quotation
    }


    fun getPdf(
        id: String,
        context: Context,
        share: Boolean
              ) {
        viewModelScope.launch {
            Repository.downloadPdf(
                id = id, context = context, pdfState = _pdf, share = share, token = token.getToken()
                                  )
        }
    }

    // delete the quotation
    fun deleteQuotation(id: String) {
        viewModelScope.launch {
            Repository.deleteQuotation(
                id, token.getToken()
                                      ) {
                refreshTheQuotationPage()
            }
        }
    }


    private val _getPackagingList =
        MutableStateFlow<DataOrException<List<PackageList>, Exception>>(DataOrException())
    val getPackagingList: StateFlow<DataOrException<List<PackageList>, Exception>> =
        _getPackagingList

    // gettingPackagingList
    fun getPackagingList() {
        viewModelScope.launch {
            Repository.getPackagingList(
                _getPackagingList, token.getToken()
                                       )
        }
    }

    // creating custom viewmodel

    val change = mutableStateOf(
        CustomerDetails(
            name = mutableStateOf(""), phone = mutableStateOf(""), packagingNo = mutableStateOf(""),
            date = mutableStateOf(""), moveTo = mutableStateOf(""), moveFrom = mutableStateOf(""),
            vehicleNo = mutableStateOf(""), id = 0
                       )
                               )

    val item = mutableStateOf(
        packingListItems(
            itemname = mutableStateOf(""), quantity = mutableStateOf(""),
            value = mutableStateOf(""),
            itemParticulars = mutableStateOf<List<itemParticulars>>(emptyList()),
            itemremark = mutableStateOf("")
                        )
                             )


    fun deletePackageList(id: Int) {
        viewModelScope.launch {
            Repository.deletePackageList(
                id.toString(), _getPackagingList, token.getToken()
                                        ) {
                getPackagingList()
            }
        }
    }


    fun addlrbilty(
        lrBiltyState: LrBiltyState,
        onComplete: () -> Unit
                  ) {
        viewModelScope.launch {

            val lrBilty1 = LrBilty(
                id = lrBiltyno, lrNumber = lrBiltyState.lrNumber.value,
                lrDate = lrBiltyState.lrDate.value, riskType = lrBiltyState.riskType.value,
                truckNumber = lrBiltyState.truckNumber.value,
                materialInsurance = lrBiltyState.materialInsurance.value,
                moveFrom = lrBiltyState.moveFrom.value, moveTo = lrBiltyState.moveTo.value,
                driverName = lrBiltyState.driverName.value,
                driverNumber = lrBiltyState.driverNumber.value,
                driverLicense = lrBiltyState.driverLicense.value,
                consignorName = lrBiltyState.consignorName.value,
                consignorNumber = lrBiltyState.consignorNumber.value,
                country = lrBiltyState.country.value, state = lrBiltyState.state.value,
                city = lrBiltyState.city.value, address = lrBiltyState.address.value,
                pincode = lrBiltyState.pincode.value,
                consigneeName = lrBiltyState.consigneeName.value,
                consigneeNumber = lrBiltyState.consigneeNumber.value,
                gstNumber = lrBiltyState.gstNumber.value, country1 = lrBiltyState.country1.value,
                state1 = lrBiltyState.state1.value, city1 = lrBiltyState.city1.value,
                address1 = lrBiltyState.address1.value, pincode1 = lrBiltyState.pincode1.value,
                numberOfPackages = lrBiltyState.numberOfPackages.value,
                demarrageCharge = lrBiltyState.demarrageCharge.value,
                chargedWeightType = lrBiltyState.Unit.value,
                actualWeight = lrBiltyState.actualWeight.value,
                remarks = lrBiltyState.remarks.value,
                actualweightType = lrBiltyState.actualWeight.value,
                description = lrBiltyState.description.value,
                policyNumber = lrBiltyState.policyNumber.value,
                freightBalance = lrBiltyState.freightBalance.value,
                otherCharges = lrBiltyState.otherCharges.value,
                stCharges = lrBiltyState.stCharges.value,
                chargedWeight = lrBiltyState.chargedWeight.value,
                demurageChargeApplicableAfter = lrBiltyState.demurageChargeApplicableAfter.value,
                freightPaid = lrBiltyState.freightPaid.value,
                freightToBeBilled = lrBiltyState.freightToBeBilled.value,
                gstPaidBy = lrBiltyState.gstPaidBy.value, gstperc = lrBiltyState.gstperc.value,
                gstNumber1 = lrBiltyState.gstNumber1.value,
                unloadingCharges = lrBiltyState.unloadingCharges.value,
                receivePackageCondition = lrBiltyState.receivePackageCondition.value,
                insuranceCompany = lrBiltyState.insuranceCompany.value,
                insuranceDate = lrBiltyState.insuranceDate.value,
                insuranceRisk = lrBiltyState.insuranceRisk.value,
                insuredAmount = lrBiltyState.insuredAmount.value,
                loadingCharges = lrBiltyState.loadingCharges.value,
                lr_cnCharges = lrBiltyState.lr_cnCharges.value,
                perDayorhour = lrBiltyState.perDayorhour.value,
                totalBasicFreight = lrBiltyState.totalBasicFreight.value, gstsubt = "", total = "",
                subtotal = ""
                                  )

            Repository.addFunction(
                State = _lrbilty, List = lrBilty1, onCompleted = {
                    onComplete()
                    lrBiltyno = -12
                }, url = "addLrBill", token.getToken()
                                  )

        }
    }

    var billID = -12

    // sending bill
    val _sendBill = MutableStateFlow<DataOrException<String, Exception>>(DataOrException())
    val sendBill: StateFlow<DataOrException<String, Exception>> = _sendBill

    fun sendBill(
        billState: BillState,
        onComplete: () -> Unit,

        ) {
        viewModelScope.launch {
            val bill = bill(
                id = billID.toString(), billNumber = billState.billNumber.value,
                companyName = billState.companyName.value, lrNumber = billState.lrNumber.value,
                invoiceDate = billState.invoiceDate.value,
                deliveryDate = billState.deliveryDate.value,
                movingPath = billState.movingPath.value,
                typeOfShipment = billState.typeOfShipment.value,
                movingPathRemark = billState.movingPathRemark.value,
                moveFrom = billState.moveFrom.value, moveTo = billState.moveTo.value,
                vehicleNumber = billState.vehicleNumber.value,
                billToName = billState.billToName.value, billToPhone = billState.billToPhone.value,
                gstin = billState.gstin.value, country = billState.country.value,
                state = billState.state.value, city = billState.city.value,
                pinCode = billState.pinCode.value, address = billState.address.value,
                citionsignorName = billState.citionsignorName.value,
                citionsignorPhone = billState.citionsignorPhone.value,
                citionsignorGstin = billState.citionsignorGstin.value,
                citionsignorCountry = billState.citionsignorCountry.value,
                citionsignorState = billState.citionsignorState.value,
                citionsignorCity = billState.citionsignorCity.value,
                citionsignorPinCode = billState.citionsignorPinCode.value,
                citionsignorAddress = billState.citionsignorAddress.value,
                consigneeName = billState.consigneeName.value,
                consigneePhone = billState.consigneePhone.value,
                consigneeGstin = billState.consigneeGstin.value,
                consigneeCountry = billState.consigneeCountry.value,
                consigneeState = billState.consigneeState.value,
                consigneeCity = billState.consigneeCity.value,
                consigneePinCode = billState.consigneePinCode.value,
                consigneeAddress = billState.consigneeAddress.value,
                packageName = billState.packageName.value,
                description = billState.description.value, weight = billState.weight.value,
                selectedWeight = billState.weightType.value, remarks = billState.remarks.value,
                freightCharge = billState.freightCharge.value,
                advancePaid = billState.advancePaid.value,
                packingCharge = billState.packingCharge.value,
                unpackingCharge = billState.unpackingCharge.value,
                loadingCharge = billState.loadingCharge.value,
                unloadingCharge = billState.unloadingCharge.value,
                packingMaterialCharge = billState.packingMaterialCharge.value,
                StorageCharge = billState.StorageCharge.value,
                carbikeTpt = billState.carbikeTpt.value,
                miscellaneousCharge = billState.miscellaneousCharge.value,
                otherCharge = billState.otherCharge.value, stCharge = billState.stCharge.value,
                greentax = billState.greentax.value, subcharge = billState.subcharge.value,
                gstinorex = billState.gstinorex.value, gst = billState.gst.value,
                gstType = billState.gstType.value, reverseCharge = billState.reverseCharge.value,
                gstpaidby = billState.gstpaidby.value,
                paymentRemark = billState.paymentRemark.value, discount = billState.discount.value,
                insuranceType = billState.insuranceType.value,
                insuranceCharge = billState.insuranceCharge.value,
                insuranceGst = billState.insuranceGst.value,
                vehicleInsuranceType = billState.vehicleInsuranceType.value,
                vehicleInsuranceCharge = billState.vehicleInsuranceCharge.value,
                vehicleInsuranceGst = billState.vehicleInsuranceGst.value,
                vehicleInsuranceRemark = billState.vehicleInsuranceRemark.value, total = null
                           )

            Repository.addFunction(
                State = _sendBill, List = bill, onCompleted = {
                    onComplete()
                    billID = -12
                }, url = "addBill", token.getToken()
                                  )

        }
    }


    // money Receipt

    val _moneyReceipt = MutableStateFlow<DataOrException<String, Exception>>(DataOrException())
    val moneyReceipt: StateFlow<DataOrException<String, Exception>> = _moneyReceipt
    var moneyreceiptno = -12
    var moneyReceiptEditState = MoneyReceiptState()

    fun moneyReceipt(
        moneyReceiptState: MoneyReceiptState,
        onComplete: () -> Unit
                    ) {
        viewModelScope.launch {

            val moneyReceipt1 = MoneyReceipt(
                id = moneyreceiptno,
                receiptNumber = moneyReceiptState.receiptNumber.value,
                date = moneyReceiptState.date.value,
                name = moneyReceiptState.name.value,
                phone = moneyReceiptState.phone.value,
                receiptAgainst = moneyReceiptState.receiptAgainst.value,
                number = moneyReceiptState.number.value,
                billQuotationDate = moneyReceiptState.billQuotationDate.value,
                moveFrom = moneyReceiptState.moveFrom.value,
                moveTo = moneyReceiptState.moveTo.value,
                paymentType = moneyReceiptState.paymentType.value,
                receiptAmount = moneyReceiptState.receiptAmount.value,
                paymentMode = moneyReceiptState.paymentMode.value,
                transactionsNumber = moneyReceiptState.transactionsNumber.value,
                branch = moneyReceiptState.branch.value,
                remarks = moneyReceiptState.remarks.value,
                                            )
            Repository.addFunction(
                State = _moneyReceipt, List = moneyReceipt1, url = "addMoneyReceipt",
                onCompleted = {
                    onComplete()
                    moneyreceiptno = -12
                }, token = token.getToken()
                                  )

        }
    }


// getting the lrbill

    private val _lrBill =
        MutableStateFlow<DataOrException<List<LrBilty>, Exception>>(DataOrException())
    val lrBill: StateFlow<DataOrException<List<LrBilty>, Exception>> = _lrBill

    fun getLrBill() {
        viewModelScope.launch {
            Repository.getLrBill(
                _lrBill, token.getToken()
                                )
        }
    }

    // editing lr
    var lrBiltyState = LrBiltyState(id = -12)

    // getting the bill

    var billState = BillState()

    // delete Lr bills
    fun deleteLr(id: String) {
        viewModelScope.launch {
            Repository.deleteLrBilty(
                id, _lrBill, token.getToken()
                                    ) {
                getLrBill()
            }
        }
    }


    val _bill = MutableStateFlow<DataOrException<List<bill>, Exception>>(DataOrException())
    val bill: StateFlow<DataOrException<List<bill>, Exception>> = _bill

    // get bills
    fun getBill() {
        viewModelScope.launch {
            Repository.getBill(
                _bill, token.getToken()
                              )
        }
    }


    // delete bill
    fun deleteBill(id: String) {
        viewModelScope.launch {
            Repository.deleteBill(
                id, _bill, token.getToken()
                                 ) {
                getBill()
            }
        }
    }

    // getting money receipt
    private val _getMoneyReceiptState =
        MutableStateFlow<DataOrException<List<MoneyReceipt>, Exception>>(DataOrException())
    val getMoneyReceipt: StateFlow<DataOrException<List<MoneyReceipt>, Exception>> =
        _getMoneyReceiptState

    fun getMoneyReceipt() {
        viewModelScope.launch {
            Repository.getMoneyReceipt(
                _getMoneyReceiptState, token.getToken()
                                      )
        }
    }


    // delete money reciept
    fun deleteMoneyReceipt(id: String) {
        viewModelScope.launch {
            Repository.deleteMoneyReceipt(
                id, _getMoneyReceiptState, token.getToken()
                                         ) {
                getMoneyReceipt()
            }
        }
    }


    // getting user details
    private val _userDetails =
        MutableStateFlow<DataOrException<UserDetails, Exception>>(DataOrException())
    val userDetails: StateFlow<DataOrException<UserDetails, Exception>> = _userDetails

    fun getUserDetails(

                      ) {

        viewModelScope.launch {
            Repository.getUserDetails(
                _userDetails, token.getToken(), token.getToken()
                                     )
        }
    }


    // save the new user
    private val _newUserState =
        MutableStateFlow<DataOrException<Outcome, Exception>>(DataOrException())
    val newUserState: StateFlow<DataOrException<Outcome, Exception>> = _newUserState

    fun saveNewUser(
        detailsToSend: DetailsToSend,
        onComplete: () -> Unit
                   ) {
        viewModelScope.launch {
            token.getToken()?.let {
                Repository.saveUserDetails(
                    detailsToSend, _newUserState, it, token.getToken()!!
                                          ) {
                    onComplete()
                }
            }
        }
    }

    //


    fun resetError() {
        _quotation.value = DataOrException(null, false, null)
        _allQuotation.value = DataOrException(null, false, null)
        _quotaionSaveState.value = DataOrException(null, false, null)
        _quotationForEdit = null
        _newUserState.value = DataOrException()
    }

    fun sendImage(
        type: String,
        link: String,
        onComplete: () -> Unit,
        onError: (String) -> Unit

                 ) {

        viewModelScope.launch {
            Repository.sendImage(jwt = token.getToken().toString(), type = type, link = link,
                                 onCompleted = { onComplete() }, error = { onError(it) }

                                )

        }
    }

    private val _lrBillPdfState =
        MutableStateFlow<DataOrException<String, Exception>>(DataOrException())
    val lrBillState: StateFlow<DataOrException<String, Exception>> = _lrBillPdfState
    fun downloadPdf(
        context: Context,
        id: String,
        share: Boolean,
        url: String
                   ) {
        Log.d("helllo ", "main viewmodel is working till now")
        viewModelScope.launch {


            Repository.downloadPdfGeneral(
                context = context, id = id, pdfState = _lrBillPdfState, share = share,
                token = token.getToken(), url = url
                                         )
        }
    }

    private val _notificationState =
        MutableStateFlow<DataOrException<List<Notifications>, Exception>>(DataOrException())
    val notifications: StateFlow<DataOrException<List<Notifications>, Exception>> = _notificationState

    fun getNotifications() {
        viewModelScope.launch {
            Repository.getNotifications(
                _notificationState,
                token.getToken()
                                       )
        }
    }

    fun sendNotificationsUpdate() {
        viewModelScope.launch {
Repository.SendNotificationsUpdate(token.getToken())
        }
    }


}