package by.grsu.iot.telegram.domain;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramResponse {

    private Update update;

    private DeleteMessage deleteMessage;
    private SendMessage sendMessage;
    private AnswerCallbackQuery answerCallbackQuery;
    private EditMessageText editMessageText;
    private EditMessageReplyMarkup editMessageReplyMarkup;
    private SendMediaGroup sendMediaGroup;

    public TelegramResponse() {
    }
    public TelegramResponse(SendMessage sendMessage) {
        this.sendMessage = sendMessage;
    }

    public TelegramResponse(SendMediaGroup sendMediaGroup) {
        this.sendMediaGroup = sendMediaGroup;
    }

    public TelegramResponse(AnswerCallbackQuery answerCallbackQuery) {
        this.answerCallbackQuery = answerCallbackQuery;
    }

    public TelegramResponse(EditMessageText EditMessageText, Update update) {
        this.editMessageText = EditMessageText;
        this.update = update;
    }

    public TelegramResponse(EditMessageReplyMarkup editMessageReplyMarkup) {
        this.editMessageReplyMarkup = editMessageReplyMarkup;
    }

    public TelegramResponse(DeleteMessage deleteMessage) {
        this.deleteMessage = deleteMessage;
    }

    public DeleteMessage getDeleteMessage() {
        return deleteMessage;
    }

    public void setDeleteMessage(DeleteMessage deleteMessage) {
        this.deleteMessage = deleteMessage;
    }

    public boolean hasSendMessage() {
        return this.sendMessage != null;
    }

    public boolean hasAnswerCallbackQuery() {
        return this.answerCallbackQuery != null;
    }

    public boolean hasEditMessageText() {
        return this.editMessageText != null;
    }

    public boolean hasEditMessageReplyMarkup() {
        return this.editMessageReplyMarkup != null;
    }

    public SendMessage getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(SendMessage sendMessage) {
        this.sendMessage = sendMessage;
    }

    public AnswerCallbackQuery getAnswerCallbackQuery() {
        return answerCallbackQuery;
    }

    public void setAnswerCallbackQuery(AnswerCallbackQuery answerCallbackQuery) {
        this.answerCallbackQuery = answerCallbackQuery;
    }

    public EditMessageText getEditMessageText() {
        return editMessageText;
    }

    public void setEditMessageText(EditMessageText editMessageText) {
        this.editMessageText = editMessageText;
    }

    public EditMessageReplyMarkup getEditMessageReplyMarkup() {
        return editMessageReplyMarkup;
    }

    public void setEditMessageReplyMarkup(EditMessageReplyMarkup editMessageReplyMarkup) {
        this.editMessageReplyMarkup = editMessageReplyMarkup;
    }

    public boolean hasSendMediaGroup() {
        return this.sendMediaGroup != null;
    }

    public SendMediaGroup getSendMediaGroup() {
        return sendMediaGroup;
    }

    public void setSendMediaGroup(SendMediaGroup sendMediaGroup) {
        this.sendMediaGroup = sendMediaGroup;
    }

    public boolean hasDeleteMessage() {
        return this.deleteMessage != null;
    }

    public Update getUpdate() {
        return update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }
}
