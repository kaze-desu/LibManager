package com.xiaohuo.libmanagergui.controller;

import com.xiaohuo.libmanagergui.ApplicationMain;
import io.vproxy.vfx.ui.alert.SimpleAlert;
import io.vproxy.vfx.ui.button.FusionButton;
import io.vproxy.vfx.ui.layout.VPadding;
import io.vproxy.vfx.ui.scene.*;
import io.vproxy.vfx.util.FXUtils;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.function.Supplier;

/**
 * @author Xiaohuo(Wang Boyun)
 */
public class AdminLoginController extends VScene
{
    private final String username = "admin";
    private final String password = "admin";
    public AdminLoginController(Supplier<VSceneGroup> sceneGroupSup,VScene nextScene)
    {
        super(VSceneRole.DRAWER_HORIZONTAL);
        enableAutoContentWidthHeight();
        var textLimit = new TextLimit();
        getNode().setPrefHeight(100);
        getNode().setBackground(new Background(new BackgroundFill(
                Color.rgb(36, 41, 46),
                CornerRadii.EMPTY,
                new Insets(0,400,0,400)
        )));
        var usernameField = new TextField("Username")
        {{
            enableAutoContentWidthHeight();
            getNode().setPrefWidth(200);
            setTextFormatter(new TextFormatter<>(textLimit.modifyChange));
        }};
        var passwordField = new TextField("Password")
        {{
            enableAutoContentWidthHeight();
            getNode().setPrefWidth(200);
            setTextFormatter(new TextFormatter<>(textLimit.modifyChange));
        }};
        var inputBox = new VBox();
        inputBox.getChildren().addAll(usernameField,new VPadding(10),passwordField);
        FXUtils.observeWidthHeightCenter(inputBox,usernameField);
        FXUtils.observeWidthHeightCenter(inputBox,passwordField);
        var loginButton = new FusionButton("Login")
        {{
            enableAutoContentWidthHeight();
            setPrefHeight(50);
            setPrefWidth(100);

        }};
        loginButton.setOnAction(event -> {
            if(usernameField.getText().equals(username) && passwordField.getText().equals(password))
            {
                SimpleAlert.show("Login Successful", "Welcome, Administrator");
                ApplicationMain.loginStatus = true;
                sceneGroupSup.get().hide(this, VSceneHideMethod.FADE_OUT);
                sceneGroupSup.get().show(nextScene, VSceneShowMethod.FROM_LEFT);
            }
            else
            {
                SimpleAlert.showAndWait("Login Failed", "username or password is incorrect");
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
