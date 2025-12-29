package com.sixclassguys.maplecalendar.domain.event.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "events")
class Event(
    @Id
    val id: Long, // notice_id를 PK로 사용
    var title: String,
    var url: String,
    var thumbnailUrl: String?,
    var date: String,
    var startDate: LocalDateTime,
    var endDate: LocalDateTime
) {

    fun updateIfChanged(
        title: String,
        url: String,
        thumbnailUrl: String?,
        date: String,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): Boolean {
        if (this.title == title &&
            this.url == url &&
            this.thumbnailUrl == thumbnailUrl &&
            this.date == date &&
            this.startDate == startDate &&
            this.endDate == endDate
        ) return false

        this.title = title
        this.url = url
        this.thumbnailUrl = thumbnailUrl
        this.date = date
        this.startDate = startDate
        this.endDate = endDate

        return true
    }
}