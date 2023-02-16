package ru.net.arh.vocabulary.bh.service.common

import java.util.*

/**
 * Service to work with locales
 */
interface LocaleService {

    /**
     * get available locales
     * @return collection of Locale
     */
    fun getAvailableLocales(): List<Locale>
}