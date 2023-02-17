package ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import ru.net.arh.vocabulary.bh.data.MessageCodes
import ru.net.arh.vocabulary.bh.data.SimpleMessageData
import ru.net.arh.vocabulary.bh.data.Word
import ru.net.arh.vocabulary.bh.service.common.MessageTemplateProvider
import ru.net.arh.vocabulary.bh.service.common.UserProfileService
import ru.net.arh.vocabulary.bh.service.telegram.escape
import ru.net.arh.vocabulary.bh.service.telegram.repository.SimpleMessageDataRepository
import ru.net.arh.vocabulary.bh.service.telegram.simplemessagehandlers.CreateDictionarySimpleMessageHandlerImpl
import ru.net.arh.vocabulary.bh.service.telegram.simplemessagehandlers.SaveWordSimpleMessageHandler
import java.text.MessageFormat

@Service
class EditWordSendMessageFactory(
        private val messageTemplateProvider: MessageTemplateProvider,
        private val userProfileService: UserProfileService,
        private val simpleMessageDataRepository: SimpleMessageDataRepository
) {
    fun getInstance(chatId: Long, word: Word): SendMessage {
        val simpleMessageData = simpleMessageDataRepository.save(SimpleMessageData(
                handlerName = SaveWordSimpleMessageHandler.NAME,
                payload = hashMapOf(
                        "word_id" to word.id
                )
        ))
        val userProfile  = userProfileService.setSimpleMessageData(chatId, simpleMessageData)
        val msg = MessageFormat(messageTemplateProvider.getMessage(userProfile.locale, MessageCodes.MESSAGE_EDIT_WORD))
                .format(arrayOf(word.original.escape(), word.translated.escape()))
        return SendMessage.builder()
                .chatId(chatId.toString())
                .text(msg)
                .parseMode(ParseMode.MARKDOWNV2)
                .replyMarkup(ReplyKeyboardMarkup.builder()
                        .oneTimeKeyboard(true)
                        .resizeKeyboard(true)
                        .keyboardRow(KeyboardRow(listOf(KeyboardButton("CANCEL"))))
                        .build())
                .build()
    }
}