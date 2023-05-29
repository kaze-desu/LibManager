package com.xiaohuo.libmanagergui.controller;

import io.vproxy.vfx.ui.alert.SimpleAlert;
import io.vproxy.vfx.ui.button.FusionButton;
import io.vproxy.vfx.ui.layout.VPadding;
import io.vproxy.vfx.ui.scene.*;
import io.vproxy.vfx.util.FXUtils;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.function.Supplier;

/**
 * @author Xiaohuo(Wang Boyun)
 */
public class AdminLoginController extends VScene
{
    private boolean admin = false;
    private final String username = "admin";
    private final String password = "admin";
    public AdminLoginController(Supplier<VSceneGroup> sceneGroupSup,VScene nextScene)
    {
        super(VSceneRole.DRAWER_HORIZONTAL);
        enableAutoContentWidthHeight();
        getNode().setPrefHeight(100);
        getNode().setBackground(new Background(new BackgroundFill(
                Color.rgb(36, 41, 46),
                CornerRadii.EMPTY,
                new Insets(0,400,0,400)
        )));
        var usernameField = new TextField("用户名")
        {{
            enableAutoContentWidthHeight();
            getNode().setPrefWidth(200);
        }};
        var passwordField = new TextField("密码")
        {{
            enableAutoContentWidthHeight();
            getNode().setPrefWidth(200);
        }};
        var inputBox = new VBox();
        inputBox.getChildren().addAll(usernameField,new VPadding(10),passwordField);
        FXUtils.observeWidthHeightCenter(inputBox,usernameField);
        FXUtils.observeWidthHeightCenter(inputBox,passwordField);
        var loginButton = new FusionButton("登录")
        {{
            enableAutoContentWidthHeight();
            setPrefHeight(50);
            setPrefWidth(100);
        }};
        loginButton.setOnAction(event -> {
            if(usernameField.getText().equals(username) && passwordField.getText().equals(password))
            {
                admin = true;
                SimpleAlert.show("登录成功", "欢迎您，管理员");
                sceneGroupSup.get().hide(this, VSceneHideMethod.FADE_OUT);
                sceneGroupSup.get().show(nextScene, VSceneShowMethod.FROM_LEFT);
            }
            else
            {
                SimpleAlert.showAndWait("登录失败", "用户名或密码错误");
            }
        });
        var hBox = new HBox(10)
        {{
            enableAutoContentWidthHeight();
        }};
        hBox.getChildren().addAll(inputBox, loginButton);
        FXUtils.observeWidthHeightCenter(hBox,inputBox);
        FXUtils.observeWidthHeightCenter(hBox,loginButton);
        getContentPane().getChildren().add(hBox);
        FXUtils.observeWidthHeightCenter(getContentPane(),hBox);
        getNode().setLayoutY(300);

    }
}
