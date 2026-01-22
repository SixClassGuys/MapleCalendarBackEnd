package com.sixclassguys.maplecalendar.domain.characterInformation.dto

import com.sixclassguys.maplecalendar.domain.characterInformation.entity.CharacterInformation

data class CharacterInformationResponse(
    val popularity: Int?,
    val overallRanking: Int?,
    val serverRanking: Int?,
    val unionLevel: Int?,
    val dojangBestFloor: Int?,
) {
    companion object {
        fun fromEntity(entity: CharacterInformation) = CharacterInformationResponse(
            popularity = entity.popularity,
            overallRanking = entity.overallRanking,
            serverRanking = entity.serverRanking,
            unionLevel = entity.unionLevel,
            dojangBestFloor = entity.dojangBestFloor
        )
    }
}
