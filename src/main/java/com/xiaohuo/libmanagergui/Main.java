package com.xiaohuo.libmanagergui;


import com.xiaohuo.libmanagergui.exception.CollectionException;
import io.vproxy.vfx.theme.Theme;
import io.vproxy.vfx.theme.impl.DarkTheme;

/**
 * Main class
 * @author xiaohuo(Wang Boyun)
 */
public class Main
{
    public static void main(String[] args) throws CollectionException
    {
        Theme.setTheme(new DarkTheme());
    }
}
