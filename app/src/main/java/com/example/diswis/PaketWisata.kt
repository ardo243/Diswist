package com.example.diswis

data class PaketWisata(
    val title: String,
    val description: String,
    val duration: String,
    val price: String,
    val imageResId: Int,
    val tags: List<String>
)
