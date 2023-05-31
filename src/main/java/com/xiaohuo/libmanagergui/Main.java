package com.xiaohuo.libmanagergui;

import io.vproxy.vfx.theme.Theme;
import io.vproxy.vfx.theme.impl.DarkTheme;
import javafx.application.Application;

/**
 * Main class
 * @author xiaohuo(Wang Boyun)
 */
public class Main
{
    public static void main(String[] args)
    {
        Theme.setTheme(new DarkTheme());
        Application.launch(ApplicationMain.class);
    }
}
