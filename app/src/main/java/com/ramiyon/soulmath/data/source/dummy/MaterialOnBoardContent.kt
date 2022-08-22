package com.ramiyon.soulmath.data.source.dummy

import com.ramiyon.soulmath.domain.model.material.MaterialLearningPurpose
import com.ramiyon.soulmath.domain.model.material.MaterialOnBoard

object MaterialOnBoardContent {
    private fun getMaterialOnBoardContent() = listOf(
        MaterialOnBoard(materialId = "", page = 1, gif = 0, description = "Tahukah kamu materi apa yang akan kita pelajari kali ini?"),
        MaterialOnBoard(materialId = "", page = 2, gif = 0, description = "Hari ini, kita akan belajar mengenal angka mulai dari 1-10. Yuk, simak lebih lanjut materinya"),
        MaterialOnBoard(materialId = "", page = 3, upperImage = 0, lowerImage = 0, description = "Di atas ini adalah contoh dari angka yang akan kita pelajari hari ini!"),
    )

    fun getMaterialOnBoardContentById(materialId: String, page: Int) =
        getMaterialOnBoardContent().first {
            it.materialId == materialId && it.page == page
        }

    private fun getMaterialLearningPurpose() = listOf(
        MaterialLearningPurpose("materialId", "BAB", listOf(
            "Melalui media video siswa mampu mengenali angka dengan tepat.",
            "Melalui media video siswa mampu mempraktikan jumlah angka dengan tepat.",
            "Setelah berlatih latihan soal, siswa dapat menyebutkan angka dengan lancar.",
            "Dengan mengerjakan latihan soal, siswa mampu menuliskan angka dengan tepat."
        )),
    )

    fun getMaterialLearningPurposeById(materialId: String) = getMaterialLearningPurpose().first {
        it.materialId == materialId
    }
}