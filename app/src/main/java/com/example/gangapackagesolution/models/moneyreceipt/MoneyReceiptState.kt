package com.example.gangapackagesolution.models.moneyreceipt

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class MoneyReceiptState(
    val receiptNumber: MutableState<String> = mutableStateOf(""),
    val date: MutableState<String> = mutableStateOf(""),
    val name: MutableState<String> = mutableStateOf(""),
    val phone: MutableState<String> = mutableStateOf(""),
    val receiptAgainst: MutableState<String> = mutableStateOf(""),
    val number: MutableState<String> = mutableStateOf(""),
    val billQuotationDate: MutableState<String> = mutableStateOf(""),
    val moveFrom: MutableState<String> = mutableStateOf(""),
    val moveTo: MutableState<String> = mutableStateOf(""),
    val paymentType: MutableState<String> = mutableStateOf(""),
    val receiptAmount: MutableState<String> = mutableStateOf(""),
    val paymentMode: MutableState<String> = mutableStateOf(""),
    val transactionsNumber: MutableState<String> = mutableStateOf(""),
    val branch: MutableState<String> = mutableStateOf(""),
    val remarks: MutableState<String> = mutableStateOf("")
                            )
