package com.xiaohuo.libmanagergui.controller;

import com.xiaohuo.libmanagergui.exception.CollectionException;
import io.vproxy.vfx.manager.font.FontManager;
import io.vproxy.vfx.ui.button.FusionButton;
import io.vproxy.vfx.ui.layout.VPadding;
import io.vproxy.vfx.ui.scene.VScene;
import io.vproxy.vfx.ui.scene.VSceneRole;
import io.vproxy.vfx.ui.stage.VStage;
import io.vproxy.vfx.ui.wrapper.ThemeLabel;
import io.vproxy.vfx.util.FXUtils;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
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
                new ThemeLabel("Test Page") {{
                    FontManager.get().setFont(this, settings -> settings.setSize(40));
                }},
                new VPadding(30),
                new ThemeLabel("Use Test button to get anywhere that you want."),
                new FusionButton("Test") {{
                    setPrefHeight(50);
                    setOnAction(e -> {
                        var stage = new VStage();
                        var box = new HBox();
                        try
                        {
                            box.getChildren().add(new BookSearchController().getNode());
                        } catch (CollectionException ex)
                        {
                            throw new RuntimeException(ex);
                        }
                        stage.getInitialScene().getContentPane().getChildren().add(box);
                        FXUtils.observeWidthHeightCenter(stage.getInitialScene().getContentPane(), box);
                        stage.setTitle("搜索");
                        stage.getStage().setWidth(1280);
                        stage.getStage().setHeight(800);
                        stage.getStage().centerOnScreen();
                        stage.getInitialScene().enableAutoContentWidthHeight();
                        stage.show();

                    });
                }})
        {{
        setAlignment(Pos.CENTER);
    }};
        getContentPane().getChildren().add(pane);
    }
}
