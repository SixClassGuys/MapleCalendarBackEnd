package com.sixclassguys.maplecalendar.global.exception

class MapleBgmNotFoundException(
    override val message: String = "BGM을 찾을 수 없습니다."
) : RuntimeException(message)