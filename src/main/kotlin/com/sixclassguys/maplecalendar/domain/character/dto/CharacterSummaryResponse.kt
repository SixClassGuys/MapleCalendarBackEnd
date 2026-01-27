package com.sixclassguys.maplecalendar.domain.character.dto

data class CharacterSummaryResponse(
    val id: Long,
    val ocid: String,
    val characterName: String,
    val characterLevel: Long,
    val characterClass: String,
    val characterImage: String?,
    val isRepresentativeCharacter: Boolean
)