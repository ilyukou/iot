package by.grsu.iot.telegram.template;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.LinkedList;
import java.util.List;

@Component
public class InlineKeyboardMarkupTemplate {

    public static InlineKeyboardMarkup getMarkup(final List<InlineKeyboardButton> buttons,
                                                 final byte maxButtonInRow) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(getKeyboard(buttons, maxButtonInRow));

        return markup;
    }

    public static List<List<InlineKeyboardButton>> getKeyboard(final List<InlineKeyboardButton> buttons,
                                                               final byte maxButtonInRow) {
        List<List<InlineKeyboardButton>> keyboard = new LinkedList<>();

        for (int i = 0; i < buttons.size(); i += maxButtonInRow) {
            List<InlineKeyboardButton> row = new LinkedList<>();

            // If remaining buttons size less than max size in one row -> add all in a row
            if (i + maxButtonInRow > buttons.size()) {
                for (int j = i; j < buttons.size(); j++) {
                    row.add(buttons.get(j));
                }
            } else {
                for (int j = i; j < i + maxButtonInRow; j++) {
                    row.add(buttons.get(j));
                }
            }

            keyboard.add(row);
        }

        return keyboard;
    }
}
