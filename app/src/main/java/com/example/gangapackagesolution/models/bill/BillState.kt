package com.example.gangapackagesolution.models.bill

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class BillState   (
    val billNumber: MutableState<String> =mutableStateOf(""),
    val companyName:  MutableState<String> =mutableStateOf(""),
    val lrNumber:  MutableState<String> =mutableStateOf(""),
    val invoiceDate:  MutableState<String> =mutableStateOf(""),
    val deliveryDate:  MutableState<String> =mutableStateOf(""),
    val movingPath:  MutableState<String> =mutableStateOf(""),
    val typeOfShipment:  MutableState<String> =mutableStateOf(""),
    val movingPathRemark:  MutableState<String> =mutableStateOf(""),
    val moveFrom:  MutableState<String> =mutableStateOf(""),
    val moveTo:  MutableState<String> =mutableStateOf(""),
    val vehicleNumber:  MutableState<String> =mutableStateOf(""),
    // billing details
    val billToName:  MutableState<String> =mutableStateOf(""),
    val billToPhone:  MutableState<String> =mutableStateOf(""),
    val gstin: MutableState<String> =mutableStateOf(""),
    val country:  MutableState<String> =mutableStateOf(""),
    val state:  MutableState<String> =mutableStateOf(""),
    val city:  MutableState<String> =mutableStateOf(""),
    val pinCode:  MutableState<String> =mutableStateOf(""),
    val address:  MutableState<String> =mutableStateOf(""),
    //cionsignor details
    val citionsignorName:  MutableState<String> =mutableStateOf(""),
    val citionsignorPhone:  MutableState<String> =mutableStateOf(""),
    val citionsignorGstin:  MutableState<String> =mutableStateOf(""),
    val citionsignorCountry:  MutableState<String> =mutableStateOf(""),
    val citionsignorState:  MutableState<String> =mutableStateOf(""),
    val citionsignorCity:  MutableState<String> =mutableStateOf(""),
    val citionsignorPinCode:  MutableState<String> =mutableStateOf(""),
    val citionsignorAddress:  MutableState<String> =mutableStateOf(""),

    // consignee details
    val consigneeName:  MutableState<String> =mutableStateOf(""),
    val consigneePhone:  MutableState<String> =mutableStateOf(""),
    val consigneeGstin:  MutableState<String> =mutableStateOf(""),
    val consigneeCountry:  MutableState<String> =mutableStateOf(""),
    val consigneeState:  MutableState<String> =mutableStateOf(""),
    val consigneeCity:  MutableState<String> =mutableStateOf(""),
    val consigneePinCode:  MutableState<String> =mutableStateOf(""),
    val consigneeAddress:  MutableState<String> =mutableStateOf(""),
    // package details
    val packageName:  MutableState<String> =mutableStateOf(""),
    val description:  MutableState<String> =mutableStateOf(""),
    val weight:  MutableState<String> =mutableStateOf(""),
    val weightType:  MutableState<String> =mutableStateOf(""),
    val remarks:  MutableState<String> =mutableStateOf(""),
    // payment details
    val freightCharge:  MutableState<String> =mutableStateOf(""),
    val advancePaid:  MutableState<String> =mutableStateOf(""),
    val packagingChargeType:MutableState<String> =mutableStateOf(""),
    val packingCharge:  MutableState<String> =mutableStateOf(""),
    val unpackingChargeType:MutableState<String> =mutableStateOf(""),
    val unpackingCharge: MutableState<String> =mutableStateOf(""),
    val unloadingChargeType: MutableState<String> =mutableStateOf(""),
val loadingChargeType: MutableState<String> =mutableStateOf(""),
    val loadingCharge: MutableState<String> =mutableStateOf(""),
    val unloadingCharge: MutableState<String> =mutableStateOf(""),
    val packingMaterialCharge: MutableState<String> =mutableStateOf(
        ""),
    val packingMaterialChargeType: MutableState<String> =mutableStateOf(""),
    val StorageCharge: MutableState<String> =mutableStateOf(""),
    val carbikeTpt: MutableState<String> =mutableStateOf(""),
    val miscellaneousCharge: MutableState<String> =mutableStateOf(""),
    val otherCharge: MutableState<String> =mutableStateOf(""),
    val stCharge: MutableState<String> =mutableStateOf(""),
    val greentax: MutableState<String> =mutableStateOf(""),
    val subcharge: MutableState<String> =mutableStateOf(""),
    val gstinorex: MutableState<String> =mutableStateOf(""),
    val gst: MutableState<String> =mutableStateOf(""),
    val gstType: MutableState<String> =mutableStateOf(""),
    val reverseCharge: MutableState<String> =mutableStateOf(""),
    val gstpaidby: MutableState<String> =mutableStateOf(""),
    val paymentRemark: MutableState<String> =mutableStateOf(""),
    val discount: MutableState<String> =mutableStateOf(""),

    // insurance Details
    val insuranceType: MutableState<String> =mutableStateOf(""),
    val insuranceCharge: MutableState<String> =mutableStateOf(""),
    val insuranceGst: MutableState<String> =mutableStateOf(""),

    //vehicle insurance
    val vehicleInsuranceType: MutableState<String> =mutableStateOf(""),
    val vehicleInsuranceCharge: MutableState<String> =mutableStateOf(""),
    val vehicleInsuranceGst: MutableState<String> =mutableStateOf(""),
    val vehicleInsuranceRemark: MutableState<String> =mutableStateOf(""),


    )
