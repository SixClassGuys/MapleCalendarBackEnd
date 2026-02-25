package com.sixclassguys.maplecalendar.global.exception

class DuplicateMapleBgmException(
    override val message: String = "중복된 BGM입니다."
) : RuntimeException(message)