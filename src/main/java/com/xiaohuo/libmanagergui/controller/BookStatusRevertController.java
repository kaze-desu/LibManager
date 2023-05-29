package com.xiaohuo.libmanagergui.controller;

import com.xiaohuo.libmanagergui.exception.CollectionException;
import com.xiaohuo.libmanagergui.services.BooksStatusServiceImpl;
import io.vproxy.vfx.ui.alert.SimpleAlert;
import io.vproxy.vfx.ui.alert.StackTraceAlert;
import io.vproxy.vfx.ui.button.FusionButton;
import io.vproxy.vfx.ui.scene.VScene;
import io.vproxy.vfx.ui.scene.VSceneGroup;
import io.vproxy.vfx.ui.scene.VSceneHideMethod;
import io.vproxy.vfx.ui.scene.VSceneRole;
import io.vproxy.vfx.ui.table.VTableColumn;
import io.vproxy.vfx.ui.table.VTableView;
import io.vproxy.vfx.util.FXUtils;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.function.Supplier;

/**
 * @author xiaohuo(Wang Boyun)
 */
public class BookStatusRevertController extends VScene
{

    public BookStatusRevertController(Supplier<VSceneGroup> sceneGroupSup, Map<Integer, BooksStatusServiceImpl.StatusData> statusList)
    {
        super(VSceneRole.DRAWER_HORIZONTAL);
        enableAutoContentWidthHeight();
        getNode().setBackground(new Background(new BackgroundFill(
                Color.rgb(36, 41, 46),
                CornerRadii.EMPTY,
                new Insets(0,300,0,300)
        )));
        getNode().minWidth(400);
        getNode().minHeight(700);
        var service = new BooksStatusServiceImpl();
        var vBox = new VBox()
        {{
            enableAutoContentWidthHeight();
        }};
        var form = new VTableView<BookStatusBorrowController.Data>()
        {{
            enableAutoContentWidthHeight();
            getNode().setPrefHeight(600);
            getNode().setPrefWidth(300);
        }};

        //table
        var idColumn = new VTableColumn<BookStatusBorrowController.Data,Integer>("ID", data -> data.statusId);
        var locationColumn = new VTableColumn<BookStatusBorrowController.Data,String>("位置", data -> data.location);
        form.getColumns().addAll(idColumn,locationColumn);
        //load data
        for(var entry:statusList.entrySet())
        {
            if (!entry.getValue().status)
            {
                form.getItems().add(new BookStatusBorrowController.Data(entry.getKey(),entry.getValue().location));
            }
        }
        var hBox = new HBox(50)
        {{
            enableAutoContentWidthHeight();
        }};
        var confirmButton = new FusionButton("确定归还")
        {{
            enableAutoContentWidthHeight();
            setPrefWidth(100);
            setPrefHeight(50);
            setOnAction(e->
                    {
                        var item = form.getSelectedItem();
                        if(item==null)
                        {
                            SimpleAlert.showAndWait(Alert.AlertType.WARNING,"请选择需要归还的书原先所在位置");
                        }
                        else
                        {
                            try
                            {
                                service.revertBook(item.statusId);
                            }
                            catch (CollectionException ex)
                            {
                                StackTraceAlert.showAndWait("归还失败",ex);
                            }
                            SimpleAlert.show(Alert.AlertType.INFORMATION,"归还成功");
                            sceneGroupSup.get().hide(BookStatusRevertController.this, VSceneHideMethod.FADE_OUT);
                            FXUtils.runDelay(VScene.ANIMATION_DURATION_MILLIS, () ->sceneGroupSup.get().removeScene(BookStatusRevertController.this));
                        }
                    }
            );
        }};
        var cancelButton = new FusionButton("取消")
        {{
            enableAutoContentWidthHeight();
            setPrefWidth(100);
            setPrefHeight(50);
            setOnAction(e->
                    {
                        sceneGroupSup.get().hide(BookStatusRevertController.this, VSceneHideMethod.FADE_OUT);
                        FXUtils.runDelay(VScene.ANIMATION_DURATION_MILLIS, () ->sceneGroupSup.get().removeScene(BookStatusRevertController.this));
                    }
            );
        }};
        hBox.getChildren().addAll(confirmButton,cancelButton);
        FXUtils.observeWidthHeightCenter(hBox,confirmButton);
        FXUtils.observeWidthHeightCenter(hBox,cancelButton);
        vBox.getChildren().addAll(form.getNode(),hBox);
        FXUtils.observeWidthHeightCenter(vBox,form.getNode());
        FXUtils.observeWidthHeightCenter(vBox,hBox);
        getContentPane().getChildren().add(vBox);
        FXUtils.observeWidthHeightCenter(getContentPane(), vBox);
    }
}
