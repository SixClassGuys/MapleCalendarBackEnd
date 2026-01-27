package com.sixclassguys.maplecalendar.domain.character.dto

data class MapleCharacterListResponse(
    val groupedCharacters: Map<String, List<CharacterSummaryResponse>>
)