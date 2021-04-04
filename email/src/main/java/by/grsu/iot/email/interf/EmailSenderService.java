package by.grsu.iot.email.interf;

public interface EmailSenderService {
    void sendMessage(String to, String subject, String text);
}
