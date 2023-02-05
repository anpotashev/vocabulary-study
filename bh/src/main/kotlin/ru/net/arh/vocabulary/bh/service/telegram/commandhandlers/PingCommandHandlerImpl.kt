package ru.net.arh.vocabulary.bh.service.telegram.commandhandlers

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.net.arh.vocabulary.bh.data.MessageCodes
import ru.net.arh.vocabulary.bh.service.common.MessageTemplateProvider

@Service("ping")
class PingCommandHandlerImpl(
    private val messageTemplateProvider: MessageTemplateProvider
) : CommandHandler {


    override fun onUpdate(params: String, chatId: Long, messageId: Int, updateId: Int): SendMessage? {
        return SendMessage.builder()
            .chatId(chatId.toString())
            .text(messageTemplateProvider.getMessage(chatId, MessageCodes.PING))
            .parseMode(ParseMode.MARKDOWNV2)
            .replyToMessageId(messageId)
            .build()
    }
}