package com.xiaohuo.libmanagergui;

import com.xiaohuo.libmanagergui.controller.BookSearchController;

import com.xiaohuo.libmanagergui.exception.CollectionException;
import io.vproxy.vfx.ui.scene.*;
import io.vproxy.vfx.ui.stage.VStage;

import io.vproxy.vfx.ui.stage.VStageInitParams;
import io.vproxy.vfx.util.FXUtils;
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
    public void start(Stage primaryStage) throws CollectionException
    {
        scenes.add(new BookSearchController(()->sceneGroup));
        var vstageInit = new VStageInitParams();
        VStage stage = new VStage(primaryStage, vstageInit);
        sceneGroup = new VSceneGroup(scenes.get(0));
        sceneGroup.show(scenes.get(0),VSceneShowMethod.FADE_IN);
        stage.getInitialScene().enableAutoContentWidthHeight();
        stage.getInitialScene().getContentPane().getChildren().add(sceneGroup.getNode());
        FXUtils.observeHeight(stage.getInitialScene().getContentPane(), sceneGroup.getNode(), -10 - 60 - 5 - 10);
        FXUtils.observeWidth(stage.getInitialScene().getContentPane(), sceneGroup.getNode(), -20);
        stage.setTitle("图书管理系统");
        stage.getStage().setWidth(1280);
        stage.getStage().setHeight(800);
        stage.getStage().centerOnScreen();
        stage.show();

    }
}
