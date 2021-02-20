package by.grsu.iot.model.sql;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Stack;

@Entity
@Table(name = "telegramUser")
public class TelegramUser extends BaseEntity{

    private Integer userId;

    private String firstName;

    private String lastName;

    private String username;

    private Boolean isBot;

    private String languageCode;

    @Lob
    private Stack<String> state = new Stack<>();

    @OneToOne(mappedBy = "telegramUser")
    private User user;

    public TelegramUser(org.telegram.telegrambots.meta.api.objects.User rawTelegramUser) {
        this.userId = rawTelegramUser.getId();
        this.firstName = rawTelegramUser.getFirstName();
        this.lastName = rawTelegramUser.getLastName();
        this.username = rawTelegramUser.getUserName();
        this.isBot = rawTelegramUser.getIsBot();
        this.languageCode = rawTelegramUser.getLanguageCode();
    }

    public TelegramUser() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Stack<String> getState() {
        return state;
    }

    public void setState(Stack<String> state) {
        this.state = state;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getBot() {
        return isBot;
    }

    public void setBot(Boolean bot) {
        isBot = bot;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public void addState(String state){
        this.state.add(state);
    }

    public String peekState(){
        return this.state.peek();
    }

    public void popState(){
        this.state.pop();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TelegramUser)) return false;
        if (!super.equals(o)) return false;
        TelegramUser that = (TelegramUser) o;
        return Objects.equals(userId, that.userId)
//                && Objects.equals(user, that.user)
                ;
    }
}
