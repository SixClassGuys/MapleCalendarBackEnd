package com.sixclassguys.maplecalendar.domain.maplebgm.repository

import com.sixclassguys.maplecalendar.domain.maplebgm.entity.MapleBgm
import com.sixclassguys.maplecalendar.domain.maplebgm.entity.MapleBgmPlaylist
import com.sixclassguys.maplecalendar.domain.maplebgm.entity.MapleBgmPlaylistItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MapleBgmPlaylistItemRepository : JpaRepository<MapleBgmPlaylistItem, Long> {

    // 플레이리스트 내 중복 곡 체크
    fun existsByPlaylistAndBgm(playlist: MapleBgmPlaylist, bgm: MapleBgm): Boolean

    // 특정 플레이리스트의 아이템들을 순서대로 가져오기
    fun findAllByPlaylistOrderBySortOrderAsc(playlist: MapleBgmPlaylist): List<MapleBgmPlaylistItem>

    // 삭제되지 않은 아이템만 순서대로 조회
    fun findAllByPlaylistAndIsDeletedFalseOrderBySortOrderAsc(playlist: MapleBgmPlaylist): List<MapleBgmPlaylistItem>

    // 특정 곡을 찾을 때도 삭제되지 않은 것만 확인
    fun findByPlaylistAndBgmAndIsDeletedFalse(playlist: MapleBgmPlaylist, bgm: MapleBgm): MapleBgmPlaylistItem?

    // 곡 제거 시 사용
    fun deleteByPlaylistAndBgm(playlist: MapleBgmPlaylist, bgm: MapleBgm)
}