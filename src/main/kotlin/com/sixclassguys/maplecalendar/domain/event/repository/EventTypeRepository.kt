package com.sixclassguys.maplecalendar.domain.event.repository

import com.sixclassguys.maplecalendar.domain.event.entity.EventType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EventTypeRepository : JpaRepository<EventType, Long> {

    fun findByName(name: String): EventType?

    fun existsByName(name: String): Boolean
}