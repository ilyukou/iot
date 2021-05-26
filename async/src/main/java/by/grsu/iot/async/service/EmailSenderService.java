package by.grsu.iot.async.service;

public interface EmailSenderService {
    void sendMessage(String to, String subject, String text);
}
