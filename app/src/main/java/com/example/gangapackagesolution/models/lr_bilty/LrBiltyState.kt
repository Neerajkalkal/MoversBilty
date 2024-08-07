package com.example.gangapackagesolution.models.lr_bilty

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class LrBiltyState(
    val id: Int,
    val lrNumber: MutableState<String> = mutableStateOf(""),
    val lrDate: MutableState<String> = mutableStateOf(""),
    val riskType: MutableState<String> = mutableStateOf(""),

    // truck vehicles details
    val truckNumber: MutableState<String> = mutableStateOf(""),
    val moveFrom: MutableState<String> = mutableStateOf(""),
    val moveTo: MutableState<String> = mutableStateOf(""),

    // driver details
    val driverName: MutableState<String> = mutableStateOf(""),
    val driverNumber: MutableState<String> = mutableStateOf(""),
    val driverLicense: MutableState<String> = mutableStateOf(""),

    //consignor/moveFrom details
    val consignorName: MutableState<String> = mutableStateOf(""),
    val consignorNumber: MutableState<String> = mutableStateOf(""),
    val gstNumber: MutableState<String> = mutableStateOf(""),
    val country: MutableState<String> = mutableStateOf(""),
    val state: MutableState<String> = mutableStateOf(""),
    val city: MutableState<String> = mutableStateOf(""),
    val pincode: MutableState<String> = mutableStateOf(""),
    val address: MutableState<String> = mutableStateOf(""),

    // consignee/moveTo details
    val consigneeName: MutableState<String> = mutableStateOf(""),
    val consigneeNumber: MutableState<String> = mutableStateOf(""),
    val gstNumber1: MutableState<String> = mutableStateOf(""),
    val country1: MutableState<String> = mutableStateOf(""),
    val state1: MutableState<String> = mutableStateOf(""),
    val city1: MutableState<String> = mutableStateOf(""),
    val pincode1: MutableState<String> = mutableStateOf(""),
    val address1: MutableState<String> = mutableStateOf(""),

    //packages details
    val numberOfPackages: MutableState<String> = mutableStateOf(""),
    val description: MutableState<String> = mutableStateOf(""),
    val Unit: MutableState<String> = mutableStateOf("KiloGram"),
    val actualWeight: MutableState<String> = mutableStateOf(""),
    val chargedWeight: MutableState<String> = mutableStateOf(""),
    val receivePackageCondition: MutableState<String> = mutableStateOf("ALL ITEMS IN GOOD CONDITION"),
    val remarks:  MutableState<String> = mutableStateOf(""),

    // payment details
    val freightToBeBilled: MutableState<String> = mutableStateOf(""),
    val freightPaid: MutableState<String> = mutableStateOf(""),
    val freightBalance: MutableState<String> = mutableStateOf(""),
    val totalBasicFreight: MutableState<String> = mutableStateOf(""),
    val loadingCharges: MutableState<String> = mutableStateOf(""),
    val unloadingCharges: MutableState<String> = mutableStateOf(""),
    val stCharges: MutableState<String> = mutableStateOf(""),
    val otherCharges: MutableState<String> = mutableStateOf(""),
    val lr_cnCharges: MutableState<String> = mutableStateOf(""),
    val gstperc: MutableState<String> = mutableStateOf(""),
    val gstPaidBy: MutableState<String> = mutableStateOf(""),
    // material insurance

    val materialInsurance: MutableState<String> = mutableStateOf(""),
    val insuranceCompany: MutableState<String> = mutableStateOf(""),
    val policyNumber: MutableState<String> = mutableStateOf(""),
    val insuranceDate: MutableState<String> = mutableStateOf(""),
    val insuredAmount: MutableState<String> = mutableStateOf(""),
    val insuranceRisk: MutableState<String> = mutableStateOf(""),

    // demurrage charge
    val demarrageCharge: MutableState<String> = mutableStateOf(""),
    val perDayorhour: MutableState<String> = mutableStateOf(""),
    val demurageChargeApplicableAfter: MutableState<String> = mutableStateOf(""),

    )
