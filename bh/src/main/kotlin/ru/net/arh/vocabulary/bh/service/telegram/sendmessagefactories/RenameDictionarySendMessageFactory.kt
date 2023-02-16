package ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import ru.net.arh.vocabulary.bh.data.Dictionary
import ru.net.arh.vocabulary.bh.data.MessageCodes
import ru.net.arh.vocabulary.bh.data.SimpleMessageData
import ru.net.arh.vocabulary.bh.service.common.MessageTemplateProvider
import ru.net.arh.vocabulary.bh.service.common.UserProfileService
import ru.net.arh.vocabulary.bh.service.telegram.escape
import ru.net.arh.vocabulary.bh.service.telegram.repository.SimpleMessageDataRepository
import ru.net.arh.vocabulary.bh.service.telegram.simplemessagehandlers.RenameDictionarySimpleMessageHandlerImpl
import java.text.MessageFormat

@Service
class RenameDictionarySendMessageFactory(
        private val userProfileService: UserProfileService,
        private val simpleMessageDataRepository: SimpleMessageDataRepository,
        private val messageTemplateProvider: MessageTemplateProvider
) {
    fun getInstance(chatId: Long, dictionary: Dictionary): SendMessage {
        val userProfile = userProfileService.getUserProfile(chatId)
        val simpleMessageData = SimpleMessageData(
                handlerName = RenameDictionarySimpleMessageHandlerImpl.NAME,
                payload = hashMapOf(
                        "dict_id" to dictionary.id
                )
        ).let { simpleMessageDataRepository.save(it) }
        userProfile.simpleMessageData = simpleMessageData
        userProfileService.save(userProfile)
        val msg = MessageFormat(messageTemplateProvider.getMessage(userProfile.locale, MessageCodes.MESSAGE_ENTER_NEW_DICTIONARY_NAME))
                .format(arrayOf(dictionary.name.escape()))
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