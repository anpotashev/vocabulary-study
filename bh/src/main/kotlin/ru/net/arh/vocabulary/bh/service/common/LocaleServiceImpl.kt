package ru.net.arh.vocabulary.bh.service.common

import org.springframework.stereotype.Service
import java.util.*

@Service
class LocaleServiceImpl : LocaleService {

    override fun getAvailableLocales(): List<Locale> {
        return listOf(Locale("ru"), Locale("en"))
    }
}