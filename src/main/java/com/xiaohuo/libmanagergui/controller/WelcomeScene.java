package com.xiaohuo.libmanagergui.controller;

import io.vproxy.vfx.manager.font.FontManager;
import io.vproxy.vfx.ui.layout.VPadding;
import io.vproxy.vfx.ui.scene.VScene;
import io.vproxy.vfx.ui.scene.VSceneRole;
import io.vproxy.vfx.ui.wrapper.ThemeLabel;
import io.vproxy.vfx.util.FXUtils;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

/**
 * @author Xiaohuo(Wang Boyun)
 */
public class WelcomeScene extends VScene
{

    public WelcomeScene()
    {
        super(VSceneRole.MAIN);
        enableAutoContentWidthHeight();
        var pane = new VBox(
                new ThemeLabel("01. VStage") {{
                    FontManager.get().setFont(this, settings -> settings.setSize(40));
                }},
                new VPadding(30),
                new ThemeLabel("VStage is the VFX version Stage.")
        ) {{
            setAlignment(Pos.CENTER);
        }};
        getContentPane().getChildren().add(pane);
        FXUtils.observeWidthHeightCenter(getContentPane(), pane);
    }
}
