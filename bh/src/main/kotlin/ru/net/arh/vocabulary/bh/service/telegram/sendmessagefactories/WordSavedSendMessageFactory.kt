package ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import ru.net.arh.vocabulary.bh.data.MessageCodes
import ru.net.arh.vocabulary.bh.service.common.MessageTemplateProvider

@Service
class WordSavedSendMessageFactory(
        private val messageTemplateProvider: MessageTemplateProvider
) {
    fun getInstance(chatId: Long): SendMessage {
        val msg = messageTemplateProvider.getMessage(chatId, MessageCodes.MESSAGE_WORD_SAVED)
        return SendMessage.builder()
                .chatId(chatId.toString())
                .text(msg)
                .parseMode(ParseMode.MARKDOWNV2)
                .replyMarkup(ReplyKeyboardRemove(false))
                .build()
    }
}