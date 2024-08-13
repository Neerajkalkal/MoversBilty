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
    val numberOfPackages: MutableState<String> = mutableStateOf("0"),
    val description: MutableState<String> = mutableStateOf(""),
    val Unit: MutableState<String> = mutableStateOf("KiloGram"),
    val actualWeight: MutableState<String> = mutableStateOf("0"),
    val chargedWeight: MutableState<String> = mutableStateOf("0"),
    val receivePackageCondition: MutableState<String> = mutableStateOf("ALL ITEMS IN GOOD CONDITION"),
    val remarks:  MutableState<String> = mutableStateOf(""),

    // payment details
    val freightToBeBilled: MutableState<String> = mutableStateOf("0"),
    val freightPaid: MutableState<String> = mutableStateOf("0"),
    val freightBalance: MutableState<String> = mutableStateOf("0"),
    val totalBasicFreight: MutableState<String> = mutableStateOf("0"),
    val loadingCharges: MutableState<String> = mutableStateOf("0"),
    val unloadingCharges: MutableState<String> = mutableStateOf("0"),
    val stCharges: MutableState<String> = mutableStateOf("0"),
    val otherCharges: MutableState<String> = mutableStateOf("0"),
    val lr_cnCharges: MutableState<String> = mutableStateOf("0"),
    val gstperc: MutableState<String> = mutableStateOf("18"),
    val gstPaidBy: MutableState<String> = mutableStateOf(""),
    // material insurance

    val materialInsurance: MutableState<String> = mutableStateOf("0"),
    val insuranceCompany: MutableState<String> = mutableStateOf(""),
    val policyNumber: MutableState<String> = mutableStateOf(""),
    val insuranceDate: MutableState<String> = mutableStateOf(""),
    val insuredAmount: MutableState<String> = mutableStateOf(""),
    val insuranceRisk: MutableState<String> = mutableStateOf(""),

    // demurrage charge
    val demarrageCharge: MutableState<String> = mutableStateOf("500"),
    val perDayorhour: MutableState<String> = mutableStateOf("per Hour"),
    val demurageChargeApplicableAfter: MutableState<String> = mutableStateOf(""),

    )
