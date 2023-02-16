package ru.net.arh.vocabulary.bh.service.common

import java.util.*

/**
 * Localized messages service
 */
interface MessageTemplateProvider {

    /**
     * retrieves localized message by its code.
     * @param chatId - user telegram chat identifier. Is used to get user locale
     * @param messageCode - message code (enum).
     * @return localized message
     */
    fun <E : Enum<E>> getMessage(chatId: Long, messageCode: E): String

    /**
     * get localized message by its code
     * @param locale - locale
     * @param messageCode - message code (enum).
     * @return localized message
     */
    fun <E : Enum<E>> getMessage(locale: Locale, messageCode: E): String
}