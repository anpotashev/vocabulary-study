package ru.net.arh.vocabulary.bh.service.common

import java.util.*

/**
 * Localized messages service
 */
interface MessageTemplateProvider {

    /**
     * get localized message by it's code.
     * @param chatId - user's chat-id identifier. Used to get user's locale
     * @param messageCode - message code (enum). Looks for message by messageCode.toString()
     * @return localized message
     */
    fun <E : Enum<E>> getMessage(chatId: Long, messageCode: E): String

    /**
     * get localized message by it's code
     * @param locale - locale
     * @param messageCode - message code (enum). Looks for message by messageCode.toString()
     * @return localized message
     */
    fun <E : Enum<E>> getMessage(locale: Locale, messageCode: E): String
}