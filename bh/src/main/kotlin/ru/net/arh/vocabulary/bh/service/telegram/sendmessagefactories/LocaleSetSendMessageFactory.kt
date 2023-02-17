package ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.net.arh.vocabulary.bh.data.MessageCodes
import ru.net.arh.vocabulary.bh.service.common.MessageTemplateProvider
import ru.net.arh.vocabulary.bh.service.telegram.escape
import java.text.MessageFormat
import java.util.*

@Service
class LocaleSetSendMessageFactory(
        private val messageTemplateProvider: MessageTemplateProvider,
) {
    fun getInstance(chatId: Long, locale: Locale): SendMessage {
        val msg = MessageFormat(messageTemplateProvider.getMessage(locale, MessageCodes.MESSAGE_LOCALE_SET))
                .format(arrayOf(locale.getDisplayName(locale).escape()))
        return SendMessage.builder()
                .chatId(chatId.toString())
                .text(msg)
                .parseMode(ParseMode.MARKDOWNV2)
                .build()
    }
}