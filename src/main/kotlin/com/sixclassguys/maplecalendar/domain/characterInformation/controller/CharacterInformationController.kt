package com.sixclassguys.maplecalendar.domain.characterInformation.controller

import com.sixclassguys.maplecalendar.domain.characterInformation.dto.CharacterInformationResponse
import com.sixclassguys.maplecalendar.domain.characterInformation.service.CharacterInformationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/character")
class CharacterInformationController(
    private val characterInformationService: CharacterInformationService
) {

    @GetMapping
    fun getCharacterInfo(@RequestParam apiKey: String): CharacterInformationResponse {
        // 캐릭터 정보 최신화?
        characterInformationService.refreshCharacterInfo(apiKey)
        // 서비스에서 캐릭터 정보 가져오기
        val characterInfo = characterInformationService.getCharacterInfoByApiKey(apiKey)
        return CharacterInformationResponse.fromEntity(characterInfo)
    }
}