package ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import ru.net.arh.vocabulary.bh.data.MessageCodes
import ru.net.arh.vocabulary.bh.service.common.MessageTemplateProvider
import ru.net.arh.vocabulary.bh.service.telegram.escape
import java.text.MessageFormat

@Service
class AddedNWordsSendMessageFactory(
        private val messageTemplateProvider: MessageTemplateProvider,
) {
    fun getInstance(chatId: Long, dictionaryName: String, size: Int): SendMessage {
        val msg = MessageFormat(messageTemplateProvider.getMessage(chatId, MessageCodes.ADDED_N_WORDS))
                .format(arrayOf(size, dictionaryName.escape()))
        return SendMessage.builder()
                .chatId(chatId.toString())
                .text(msg)
                .parseMode(ParseMode.MARKDOWNV2)
                .replyMarkup(ReplyKeyboardMarkup.builder()
                        .oneTimeKeyboard(true)
                        .resizeKeyboard(true)
                        .keyboardRow(KeyboardRow(listOf(KeyboardButton("FINISH"))))
                        .build())
                .build()
    }
}