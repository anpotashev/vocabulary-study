package ru.net.arh.vocabulary.bh.data

/**
 * Data used by callback handlers.
 * @see ru.net.arh.vocabulary.bh.service.telegram.TelegramCallbackQueryHandler
 * @see ru.net.arh.vocabulary.bh.service.telegram.callbackhandlers.CallbackHandler
 *
 * When creating InlineKeyboardButton, all information, needed to process stores in CallbackData object.
 * This object saved in DB. In method InlineKeyboardButton.InlineKeyboardButtonBuilder.callbackData pass an identifier of saved data.
 * @see org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton.InlineKeyboardButtonBuilder.callbackData()
 * On processing callback data retrieves from DB by this identifier.
 */
data class CallbackData(
    /**
     * callback handler's name
     */
    val name: String,
    /**
     * data used by callback handler
     */
    val params: Map<String, Any?>
)