package com.ramiyon.soulmath.data.source.dummy

import com.ramiyon.soulmath.domain.model.material.MaterialLearningPurpose
import com.ramiyon.soulmath.domain.model.material.MaterialOnBoard

object MaterialOnBoardContent {

    private fun getMaterialLearningPurpose() = listOf(
        MaterialLearningPurpose("MATERIAL001", "BAB", listOf(
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