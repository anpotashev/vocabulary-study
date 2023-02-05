package ru.net.arh.vocabulary.bh.service.telegram.callbackhandlers

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.net.arh.vocabulary.bh.service.businesslogic.QuestService
import ru.net.arh.vocabulary.bh.service.telegram.sendmessagefactories.QuestSendMessageFactory

@Service(ProcessQuestAnswerCallbackHandlerImpl.NAME)
class ProcessQuestAnswerCallbackHandlerImpl(
    private val questService: QuestService,
    private val questSendMessageFactory: QuestSendMessageFactory
) : CallbackHandler {

    companion object {
        const val NAME = "processQuestAnswer"
    }

    override fun onUpdate(
        callbackParams: Map<String, Any?>,
        chatId: Long,
        messageId: Int,
        updateId: Int
    ): SendMessage? {
        val success = callbackParams.get("success") as Boolean?
        val historyId = callbackParams.get("historyId").toString().toLong()
        questService.saveQuestResult(historyId, success)
        val questData = questService.questNewWord(chatId)
        return questSendMessageFactory.getInstance(chatId, questData)
    }
}