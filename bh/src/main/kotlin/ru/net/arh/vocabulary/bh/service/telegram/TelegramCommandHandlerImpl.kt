package ru.net.arh.vocabulary.bh.service.telegram

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import ru.net.arh.vocabulary.bh.service.telegram.commandhandlers.CommandHandler
import java.io.Serializable

@Service
class TelegramCommandHandlerImpl(
    private val commandHandlers: Map<String, CommandHandler>
) : TelegramCommandHandler {

    @Suppress("UNCHECKED_CAST")
    override fun onUpdate(update: Update): BotApiMethod<Serializable>? {
        val chatId = update.message.chatId
        val messageId = getMessageId(update)
        val updateId = update.updateId
        val (command, params) = splitToCommandAndParam(update.message.text)
        return commandHandlers.get(command)
            ?.onUpdate(params, chatId, messageId, updateId) as BotApiMethod<Serializable>?
    }

    private fun getMessageId(update: Update): Int {
        return update.message.messageId
    }

    private fun splitToCommandAndParam(text: String): Pair<String, String> {
        val indexOfSpace = text.indexOf(' ')
        return if (indexOfSpace == -1) text.substring(1) to "" else text.substring(1, indexOfSpace) to text.substring(
            indexOfSpace,
            text.length
        ).trim()
    }
}