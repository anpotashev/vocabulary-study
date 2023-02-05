package ru.net.arh.vocabulary.bh.service

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import java.io.Serializable
@Service
class UpdateHandlerImpl: UpdateHandler {

    @Suppress("UNCHECKED_CAST")
    override fun onUpdate(update: Update): BotApiMethod<Serializable>? {
        val chatId = getChatId(update) ?: return null
        val messageId = getMessageId(update)
        return SendMessage.builder()
                .chatId(chatId.toString())
                .text("ping")
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