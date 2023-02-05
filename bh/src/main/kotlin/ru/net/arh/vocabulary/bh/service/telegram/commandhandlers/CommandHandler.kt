package ru.net.arh.vocabulary.bh.service.telegram.commandhandlers

import org.telegram.telegrambots.meta.api.methods.send.SendMessage

/**
 * process "command" updates.
 * Beans should have names like command.
 * For example, to process telegram command "/my_command 123"
 * we should have an implementation annotated by @Service("my_command")
 */
interface CommandHandler {

    fun onUpdate(params: String, chatId: Long, messageId: Int, updateId: Int): SendMessage?

}
