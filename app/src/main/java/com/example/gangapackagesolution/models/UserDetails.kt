package com.example.gangapackagesolution.models
data class UserDetails(
    val name: String,
    val totalBill: String,
    val totalQuotation: String,
    val companyName: String,
    val totalMoneyReceipt: String,
    val phoneNumber: String,
    val signature: String,
    val companyLogo:String,
    val subscribed:Boolean,
    val subscription:List<Pricing>,
    val qrCode:String,
    val newNotification:Boolean
                      )


data class Pricing(
    val id: String,
    val price: String,
                  )