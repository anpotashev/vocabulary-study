package ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import ru.net.arh.vocabulary.bh.data.MessageCodes
import ru.net.arh.vocabulary.bh.service.common.MessageTemplateProvider
import ru.net.arh.vocabulary.bh.service.telegram.escape
import java.text.MessageFormat

@Service
class WordDeletedSendMessageFactory(
        private val messageTemplateProvider: MessageTemplateProvider
) {
    fun getInstance(chatId: Long, original: String): SendMessage {
        val msg = MessageFormat(messageTemplateProvider.getMessage(chatId, MessageCodes.MESSAGE_WORD_DELETED))
                .format(arrayOf(original.escape()))
        return SendMessage.builder()
                .chatId(chatId.toString())
                .text(msg)
                .parseMode(ParseMode.MARKDOWNV2)
                .replyMarkup(ReplyKeyboardRemove(false))
                .build()
    }
}