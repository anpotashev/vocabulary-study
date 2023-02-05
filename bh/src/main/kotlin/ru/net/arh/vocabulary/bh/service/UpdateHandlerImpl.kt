package ru.net.arh.vocabulary.bh.service

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import ru.net.arh.vocabulary.bh.data.MessageCodes
import java.io.Serializable

@Service
class UpdateHandlerImpl(
    private val messageTemplateProvider: MessageTemplateProvider
) : UpdateHandler {

    @Suppress("UNCHECKED_CAST")
    override fun onUpdate(update: Update): BotApiMethod<Serializable>? {
        val chatId = getChatId(update) ?: return null
        val messageId = getMessageId(update)
        return SendMessage.builder()
            .chatId(chatId.toString())
            .text(messageTemplateProvider.getMessage(chatId, MessageCodes.PING).bold())
            .parseMode(ParseMode.MARKDOWN)
            .let { if (messageId != null) it.replyToMessageId(messageId) else it }
            .build() as BotApiMethod<Serializable>
    }

    private fun getMessageId(update: Update): Int? {
        return update.message?.messageId ?: update.callbackQuery?.message?.messageId
    }

    private fun getChatId(update: Update): Long? {
        return update.message?.chatId ?: update.callbackQuery?.message?.chatId
    }

}