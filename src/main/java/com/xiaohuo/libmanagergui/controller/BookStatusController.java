package com.xiaohuo.libmanagergui.controller;

import io.vproxy.vfx.ui.scene.VScene;
import io.vproxy.vfx.ui.scene.VSceneRole;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Map;

/**
 * @author Xiaohuo(Wang Boyun)
 */
public class BookStatusController extends VScene
{

    public BookStatusController(Map<Integer,String> statusList)
    {
        super(VSceneRole.DRAWER_VERTICAL);
        enableAutoContentWidthHeight();
        var panel = new Pane();
        panel.setBackground(new Background(new BackgroundFill(
                new Color(0xaf / 255d, 0xcb / 255d, 0x9e / 255d, 1),
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));
        System.out.println("show3");


        getContentPane().getChildren().add(panel);
    }
}
