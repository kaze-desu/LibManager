package com.xiaohuo.libmanagergui;

import com.xiaohuo.libmanagergui.controller.AdminInterfaceController;
import com.xiaohuo.libmanagergui.controller.AdminLoginController;
import com.xiaohuo.libmanagergui.controller.BookSearchController;

import com.xiaohuo.libmanagergui.exception.CollectionException;
import io.vproxy.vfx.manager.image.ImageManager;
import io.vproxy.vfx.theme.Theme;
import io.vproxy.vfx.ui.button.FusionButton;
import io.vproxy.vfx.ui.button.FusionImageButton;
import io.vproxy.vfx.ui.layout.VPadding;
import io.vproxy.vfx.ui.scene.*;
import io.vproxy.vfx.ui.stage.VStage;

import io.vproxy.vfx.ui.stage.VStageInitParams;
import io.vproxy.vfx.util.FXUtils;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
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
    public static boolean loginStatus = false;
    @Override
    public void start(Stage primaryStage) throws CollectionException
    {
        scenes.add(new BookSearchController(()->sceneGroup));
        scenes.add(new AdminInterfaceController(()->sceneGroup));
        scenes.add(new AdminLoginController(()->sceneGroup,scenes.get(1)));
        var vstageInit = new VStageInitParams();
        VStage stage = new VStage(primaryStage, vstageInit);
        sceneGroup = new VSceneGroup(scenes.get(0));
        sceneGroup.addScene(scenes.get(1));
        sceneGroup.addScene(scenes.get(2), VSceneHideMethod.FADE_OUT);
        var menuScene = new VScene(VSceneRole.DRAWER_VERTICAL);
        menuScene.getNode().setPrefWidth(450);
        menuScene.enableAutoContentWidth();
        menuScene.getNode().setBackground(new Background(new BackgroundFill(
                Theme.current().subSceneBackgroundColor(),
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));
        stage.getRootSceneGroup().addScene(menuScene, VSceneHideMethod.TO_LEFT);
        var menuVBox = new VBox(50) {{
            setPadding(new Insets(0, 0, 0, 24));
            getChildren().add(new VPadding(20));
        }};
        menuScene.getContentPane().getChildren().add(menuVBox);
        var adminButton = new FusionButton("AdminLogin")
        {{
            setPrefHeight(100);
            setPrefWidth(200);
        }};
        adminButton.setDisableAnimation(true);
        adminButton.setOnAction(e ->
        {
            if(!loginStatus)
            {
                stage.getRootSceneGroup().hide(menuScene, VSceneHideMethod.TO_LEFT);
                sceneGroup.show(scenes.get(2), VSceneShowMethod.FADE_IN);
            }
            else
            {
                 stage.getRootSceneGroup().hide(menuScene, VSceneHideMethod.TO_LEFT);
                 sceneGroup.show(scenes.get(1), VSceneShowMethod.FROM_LEFT);
            }
        });
        menuVBox.getChildren().add(adminButton);
        FXUtils.observeWidthHeightCenter(menuVBox,adminButton);
        var searchButton = new FusionButton("Search Book")
        {{
            setPrefHeight(100);
            setPrefWidth(200);
        }};
        searchButton.setDisableAnimation(true);
        searchButton.setOnAction(e ->
        {
            stage.getRootSceneGroup().hide(menuScene, VSceneHideMethod.TO_LEFT);
            sceneGroup.show(scenes.get(0), VSceneShowMethod.FROM_RIGHT);
        });
        menuVBox.getChildren().add(searchButton);
        FXUtils.observeWidthHeightCenter(menuVBox,searchButton);
        var menuBtn = new FusionImageButton(ImageManager.get().load("images/menu.png")) {{
            setPrefWidth(40);
            setPrefHeight(VStage.TITLE_BAR_HEIGHT + 1);
            getImageView().setFitHeight(15);
            setLayoutX(-2);
            setLayoutY(-1);
        }};
        menuBtn.setOnAction(e ->
        {
            if(loginStatus)
            {
                adminButton.setText("AdminPanel");
            }
            stage.getRootSceneGroup().show(menuScene, VSceneShowMethod.FROM_LEFT);
        });
        stage.getRoot().getContentPane().getChildren().add(menuBtn);
        stage.getInitialScene().enableAutoContentWidthHeight();
        stage.getInitialScene().getContentPane().getChildren().add(sceneGroup.getNode());
        FXUtils.observeHeight(stage.getInitialScene().getContentPane(), sceneGroup.getNode(), -10 - 60 - 5 - 10);
        FXUtils.observeWidth(stage.getInitialScene().getContentPane(), sceneGroup.getNode(), -20);
        stage.setTitle("Library Manager");
        stage.getStage().setWidth(1280);
        stage.getStage().setHeight(800);
        stage.getStage().centerOnScreen();
        stage.show();
    }
}
