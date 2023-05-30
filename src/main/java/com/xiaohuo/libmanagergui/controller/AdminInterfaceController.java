package com.xiaohuo.libmanagergui.controller;

import io.vproxy.vfx.ui.button.FusionButton;
import io.vproxy.vfx.ui.scene.*;
import io.vproxy.vfx.util.FXUtils;
import javafx.scene.layout.*;

import java.util.function.Supplier;

/**
 * @author Xiaohuo(Wang Boyun)
 */
public class AdminInterfaceController extends VScene
{

    public AdminInterfaceController(Supplier<VSceneGroup> sceneGroupSup)
    {
        super(VSceneRole.MAIN);
        enableAutoContentWidthHeight();
        var addBook = new FusionButton("Add Book")
        {{
            enableAutoContentWidthHeight();
            setPrefHeight(100);
            setPrefWidth(200);


        }};
        addBook.setOnAction(event ->
        {
            var scene = new BookAddController();
            sceneGroupSup.get().addScene(scene);
            sceneGroupSup.get().show(scene, VSceneShowMethod.FROM_LEFT);
        });
        var editBook = new FusionButton("Edit Book")
        {{
            enableAutoContentWidthHeight();
            setPrefHeight(100);
            setPrefWidth(200);
        }};
        editBook.setOnAction(event ->
        {
            var scene = new BookEditController();
            sceneGroupSup.get().addScene(scene);
            sceneGroupSup.get().show(scene, VSceneShowMethod.FROM_LEFT);
        });
        var deleteBook = new FusionButton("Delete Book")
        {{
            enableAutoContentWidthHeight();
            setPrefHeight(100);
            setPrefWidth(200);
        }};
        deleteBook.setOnAction(event ->
        {
            var scene = new BookDeleteController();
            sceneGroupSup.get().addScene(scene);
            sceneGroupSup.get().show(scene, VSceneShowMethod.FROM_LEFT);
        });
        var addStatus = new FusionButton("Add book Status")
        {{
            enableAutoContentWidthHeight();
            setPrefHeight(100);
            setPrefWidth(200);
        }};
        addStatus.setOnAction(event ->
        {
            var scene = new BookStatusAddController();
            sceneGroupSup.get().addScene(scene);
            sceneGroupSup.get().show(scene, VSceneShowMethod.FROM_LEFT);
        });
        var editStatus = new FusionButton("Edit Book Status")
        {{
            enableAutoContentWidthHeight();
            setPrefHeight(100);
            setPrefWidth(200);
        }};
        editStatus.setOnAction(event ->
        {
            var scene = new BookStatusEditController();
            sceneGroupSup.get().addScene(scene);
            sceneGroupSup.get().show(scene, VSceneShowMethod.FROM_LEFT);
        });
        var deleteStatus = new FusionButton("Delete Book Status")
        {{
            enableAutoContentWidthHeight();
            setPrefHeight(100);
            setPrefWidth(200);
        }};
        deleteStatus.setOnAction(event ->
        {
            var scene = new BookStatusDeleteController();
            sceneGroupSup.get().addScene(scene);
            sceneGroupSup.get().show(scene, VSceneShowMethod.FROM_LEFT);
        });
        var gridPane = new GridPane();
        gridPane.setLayoutY(200);
        gridPane.setHgap(50);
        gridPane.setVgap(50);
        FXUtils.observeWidthCenter(getContentPane(), gridPane);
        gridPane.add(addBook, 0, 0);
        gridPane.add(editBook, 1, 0);
        gridPane.add(deleteBook, 2, 0);
        gridPane.add(addStatus, 0, 1);
        gridPane.add(editStatus, 1, 1);
        gridPane.add(deleteStatus, 2, 1);
        getContentPane().getChildren().add(gridPane);
    }
}
