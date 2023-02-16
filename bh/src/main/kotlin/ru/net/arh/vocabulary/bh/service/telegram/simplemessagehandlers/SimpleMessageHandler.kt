package ru.net.arh.vocabulary.bh.service.telegram.simplemessagehandlers

import org.telegram.telegrambots.meta.api.methods.send.SendMessage

/**
 * process "simple (text)" updates.
 * Implementation chooses by bean name:
 * when userProfile.simpleMessageData.handlerName is equal to bean name
 */
interface SimpleMessageHandler {

    /**
     * handler method.
     * @param simpleMessageParams - data used at handle
     * @param chatId - telegram chat identifier
     * @param messageId - telegram message identifier
     * @param updateId - telegram update identifier
     * @param messageText - text received in telegram update
     * @return a SendMessage object to send answer or null
     * @see SendMessage
     */
    fun onUpdate(simpleMessageParams: Map<String, Any?>, chatId: Long, messageId: Int, updateId: Int, messageText: String): SendMessage?
}