package com.ramiyon.soulmath.data.source.dummy

private fun getOnBoardContent() = listOf<Pair<String, String>>(
    Pair("Materi Lengkap", "Belajar interaktif dan menyenangkan dengan materi berbentuk video"),
    Pair("Latihan Soal", "Latihan soal lengkap dengan berbagai tingkat kesulitan"),
    Pair("Laporan Bulanan", "Dapatkan laporan progres belajarmu setiap bulan"),
)

fun getOnBoardContentByPage(page: Int) = getOnBoardContent()[page]