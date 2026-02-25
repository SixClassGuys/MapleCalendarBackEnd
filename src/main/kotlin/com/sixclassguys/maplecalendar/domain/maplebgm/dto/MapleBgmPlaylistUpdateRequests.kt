package com.sixclassguys.maplecalendar.domain.maplebgm.dto

data class MapleBgmPlaylistUpdateRequests(
    val name: String?,          // 수정할 이름 (null이면 수정 안 함)
    val isPublic: Boolean?,     // 공개 여부 변경
    val bgmIdOrder: List<Long>
)