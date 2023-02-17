package ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import ru.net.arh.vocabulary.bh.data.MessageCodes
import ru.net.arh.vocabulary.bh.service.common.MessageTemplateProvider

@Service
class UserFinishedAddingWordsSendMessageFactory(
        private val messageTemplateProvider: MessageTemplateProvider
) {
    fun getInstance(chatId: Long): SendMessage {
        val msg = messageTemplateProvider.getMessage(chatId, MessageCodes.MESSAGE_USER_FINISHED_ADDING_WORDS)
        return SendMessage.builder()
                .chatId(chatId.toString())
                .text(msg)
                .replyMarkup(ReplyKeyboardRemove(false))
                .build()
    }
}