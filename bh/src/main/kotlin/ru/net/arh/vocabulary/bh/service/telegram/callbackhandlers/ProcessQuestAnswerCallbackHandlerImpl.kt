package ru.net.arh.vocabulary.bh.service.telegram.callbackhandlers

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.net.arh.vocabulary.bh.aop.ClearSimpleMessageBeforeRunThisMethod
import ru.net.arh.vocabulary.bh.service.businesslogic.QuestService
import ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories.QuestSendMessageFactory

/**
 * CallbackHandler implementation.
 * Used when user sends quest result.
 */
@Service(ProcessQuestAnswerCallbackHandlerImpl.NAME)
class ProcessQuestAnswerCallbackHandlerImpl(
    private val questService: QuestService,
    private val questSendMessageFactory: QuestSendMessageFactory
) : CallbackHandler {

    companion object {
        const val NAME = "processQuestAnswer"
        val log = LoggerFactory.getLogger(ProcessQuestAnswerCallbackHandlerImpl::class.java)
    }

    @ClearSimpleMessageBeforeRunThisMethod
    override fun onUpdate(
        callbackParams: Map<String, Any?>,
        chatId: Long,
        messageId: Int,
        updateId: Int
    ): SendMessage {
        log.debug("Starting processing 'processQuestAnswer' callback. ChatId={}", chatId)
        log.debug("Saving result of current quest.")
        val success = callbackParams.get("success") as Boolean?
        val historyId = callbackParams.get("historyId").toString().toLong()
        questService.saveQuestResult(historyId, success)
        log.debug("Retrieving next quest.")
        val questData = questService.questNewWord(chatId)
        return questSendMessageFactory.getInstance(chatId, questData)
    }
}