package ru.net.arh.vocabulary.bh.service.telegram.commandhandlers

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.net.arh.vocabulary.bh.aop.ClearSimpleMessageBeforeRunThisMethod
import ru.net.arh.vocabulary.bh.service.businesslogic.QuestService
import ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories.QuestSendMessageFactory

/**
 * Handler for /ping command
 */
@Service("quest")
class QuestCommandHandlerImpl(
    private val questService: QuestService,
    private val questSendMessageFactory: QuestSendMessageFactory
) : CommandHandler {

    companion object {
        val log = LoggerFactory.getLogger(QuestCommandHandlerImpl::class.java)
    }

    @ClearSimpleMessageBeforeRunThisMethod
    override fun onUpdate(params: String, chatId: Long, messageId: Int, updateId: Int): SendMessage? {
        log.debug("Starting processing 'quest' command. ChatId={}", chatId)
        val questData = questService.questNewWord(chatId)
        return questSendMessageFactory.getInstance(chatId, questData)
    }
}