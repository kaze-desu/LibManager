package com.xiaohuo.libmanagergui.controller;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;


/**
 * @author Xiaohuo(Wang Boyun)
 */
public class TextLimit
{
    UnaryOperator<TextFormatter.Change> modifyChange = c -> {
        int len = 40;
        if (c.isContentChange()) {
            int newLength = c.getControlNewText().length();
            if (newLength > len) {
                // replace the input text with the last len chars
                String tail = c.getControlNewText().substring(newLength - len, newLength);
                c.setText(tail);
                // replace the range to complete text
                // valid coordinates for range is in terms of old text
                int oldLength = c.getControlText().length();
                c.setRange(0, oldLength);
            }
        }
        return c;
    };
}
