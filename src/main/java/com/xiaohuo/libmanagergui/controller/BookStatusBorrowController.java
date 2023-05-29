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
 * @author Xiaohuo(Wang Boyun)
 */
public class BookStatusBorrowController extends VScene
{

    public BookStatusBorrowController(Supplier<VSceneGroup> sceneGroupSup, Map<Integer,String> statusList)
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
        var form = new VTableView<Data>()
        {{
            enableAutoContentWidthHeight();
            getNode().setPrefHeight(600);
            getNode().setPrefWidth(300);
        }};

        //table
        var idColumn = new VTableColumn<Data,Integer>("ID",data -> data.statusId);
        var locationColumn = new VTableColumn<Data,String>("位置",data -> data.location);
        form.getColumns().addAll(idColumn,locationColumn);
        //load data
        for(var entry:statusList.entrySet())
        {
            form.getItems().add(new Data(entry.getKey(),entry.getValue()));
        }
        var hBox = new HBox(50)
        {{
            enableAutoContentWidthHeight();
        }};
        var confirmButton = new FusionButton("确定借阅")
        {{
            enableAutoContentWidthHeight();
            setPrefWidth(100);
            setPrefHeight(50);
            setOnAction(e->
                    {
                        var item = form.getSelectedItem();
                        if(item==null)
                        {
                            SimpleAlert.showAndWait(Alert.AlertType.WARNING,"请选择需要借走的书所在位置");
                        }
                        else
                        {
                            try
                            {
                                service.borrowBook(item.statusId);
                            }
                            catch (CollectionException ex)
                            {
                                StackTraceAlert.showAndWait("借阅失败",ex);
                            }
                            SimpleAlert.show(Alert.AlertType.INFORMATION,"借阅成功");
                            sceneGroupSup.get().hide(BookStatusBorrowController.this, VSceneHideMethod.FADE_OUT);
                            FXUtils.runDelay(VScene.ANIMATION_DURATION_MILLIS, () ->sceneGroupSup.get().removeScene(BookStatusBorrowController.this));

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
                        sceneGroupSup.get().hide(BookStatusBorrowController.this, VSceneHideMethod.FADE_OUT);
                        FXUtils.runDelay(VScene.ANIMATION_DURATION_MILLIS, () ->sceneGroupSup.get().removeScene(BookStatusBorrowController.this));
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
    public static class Data
    {
        public Integer statusId;
        public String location;
        public Data(Integer statusId,String location)
        {
            this.statusId = statusId;
            this.location = location;
        }
    }
}

