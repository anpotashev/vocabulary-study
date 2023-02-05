package ru.net.arh.vocabulary.bh.service

import java.text.MessageFormat

/**
 * Localized messages service
 */
interface MessageTemplateProvider {

    /**
     * get message by it's code in user's locale
     * @param chatId - user's chat-id identifier
     * @param messageCode - message code (enum). Looks for message by messageCode.toString()
     * @return localized message
     */
    fun <E: Enum<E>> getMessage(chatId: Long, messageCode: E): String

}