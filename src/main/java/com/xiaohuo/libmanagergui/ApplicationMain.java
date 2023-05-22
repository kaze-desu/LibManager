package com.xiaohuo.libmanagergui;

import com.xiaohuo.libmanagergui.controller.WelcomeScene;

import io.vproxy.vfx.ui.scene.*;
import io.vproxy.vfx.ui.stage.VStage;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xiaohuo(Wang Boyun)
 */
public class ApplicationMain extends Application
{
    private final List<VScene> scenes = new ArrayList<>();
    private VSceneGroup sceneGroup;
    @Override
    public void start(Stage primaryStage)
    {
        VStage stage = new VStage(primaryStage);
        var welcome = new WelcomeScene();
        stage.getRootSceneGroup().addScene(welcome,VSceneHideMethod.TO_LEFT);
        stage.getRootSceneGroup().show(welcome,VSceneShowMethod.FADE_IN);
        stage.setTitle("图书管理系统");
        stage.getStage().setWidth(1280);
        stage.getStage().setHeight(800);
        stage.getStage().centerOnScreen();
        stage.show();
    }
}
