package com.xiaohuo.libmanagergui.controller;

import io.vproxy.vfx.manager.font.FontManager;
import io.vproxy.vfx.ui.scene.VScene;
import io.vproxy.vfx.ui.scene.VSceneRole;
import io.vproxy.vfx.ui.wrapper.ThemeLabel;
import io.vproxy.vfx.util.FXUtils;

/**
 * @author Xiaohuo(Wang Boyun)
 */
public class WelcomeScene extends VScene
{

    public WelcomeScene()
    {
        super(VSceneRole.MAIN);
        enableAutoContentWidthHeight();
        var label = new ThemeLabel("Welcome to VFX") {{
            FontManager.get().setFont(this, settings -> settings.setSize(40));
        }};

        getContentPane().getChildren().add(label);
        FXUtils.observeWidthHeightCenter(getContentPane(), label);
    }
}
