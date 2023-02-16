package ru.net.arh.vocabulary.bh.service.telegram

import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import java.io.Serializable

/**
 * Main class for handle telegram updates.
 * Retrieves information about the kind of updates, select a handler and delegates processing to it
 */
interface UpdateHandler {

    fun onUpdate(update: Update): BotApiMethod<Serializable>?
}
