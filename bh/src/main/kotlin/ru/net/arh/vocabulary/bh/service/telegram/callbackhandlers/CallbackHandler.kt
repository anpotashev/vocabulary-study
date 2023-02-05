package ru.net.arh.vocabulary.bh.service.telegram.callbackhandlers

import org.telegram.telegrambots.meta.api.methods.send.SendMessage

/**
 * process "command" updates.
 * Beans should have names like command.
 * For example, to process telegram command "/my_command 123"
 * we should have an implementation annotated by @Service("my_command")
 */
interface CallbackHandler {
    fun onUpdate(callbackParams: Map<String, Any?>, chatId: Long, messageId: Int, updateId: Int): SendMessage?
}