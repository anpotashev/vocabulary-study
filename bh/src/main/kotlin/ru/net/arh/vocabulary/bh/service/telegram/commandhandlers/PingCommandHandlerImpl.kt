package ru.net.arh.vocabulary.bh.service.telegram.commandhandlers

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.net.arh.vocabulary.bh.aop.ClearSimpleMessageBeforeRunThisMethod
import ru.net.arh.vocabulary.bh.data.MessageCodes
import ru.net.arh.vocabulary.bh.service.common.MessageTemplateProvider

/**
 * Handler for /ping command
 */
@Service("ping")
class PingCommandHandlerImpl(
    private val messageTemplateProvider: MessageTemplateProvider
) : CommandHandler {

    companion object {
        val log = LoggerFactory.getLogger(PingCommandHandlerImpl::class.java)
    }

    @ClearSimpleMessageBeforeRunThisMethod
    override fun onUpdate(params: String, chatId: Long, messageId: Int, updateId: Int): SendMessage? {
        log.debug("Starting processing 'ping' command. ChatId={}", chatId)
        return SendMessage.builder()
            .chatId(chatId.toString())
            .text(messageTemplateProvider.getMessage(chatId, MessageCodes.MESSAGE_PING))
            .parseMode(ParseMode.MARKDOWNV2)
            .replyToMessageId(messageId)
            .build()
    }
}