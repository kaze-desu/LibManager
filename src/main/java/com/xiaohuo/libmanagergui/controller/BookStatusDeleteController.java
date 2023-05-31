package com.xiaohuo.libmanagergui.controller;

import com.xiaohuo.libmanagergui.exception.CollectionException;
import com.xiaohuo.libmanagergui.services.BooksStatusServiceImpl;
import com.xiaohuo.libmanagergui.services.template.TypeList;
import io.vproxy.vfx.ui.alert.SimpleAlert;
import io.vproxy.vfx.ui.alert.StackTraceAlert;
import io.vproxy.vfx.ui.button.FusionButton;
import io.vproxy.vfx.ui.layout.VPadding;
import io.vproxy.vfx.ui.scene.VScene;
import io.vproxy.vfx.ui.scene.VSceneRole;
import io.vproxy.vfx.ui.table.VTableColumn;
import io.vproxy.vfx.ui.table.VTableView;
import io.vproxy.vfx.ui.wrapper.ThemeLabel;
import io.vproxy.vfx.util.FXUtils;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Map;

public class BookStatusDeleteController extends VScene
{
    private Map<Integer,String> statusList;
    public BookStatusDeleteController()
    {
        super(VSceneRole.MAIN);
        enableAutoContentWidthHeight();
        var service = new BooksStatusServiceImpl();
        var textLimit = new TextLimit();

        var searchLabel = new ThemeLabel("ISBN/ISSN")
        {{
            enableAutoContentWidthHeight();
        }};
        var searchField = new TextField()
        {{
            setTextFormatter(new TextFormatter<>(textLimit.modifyChange));

        }};
        var searchVBox = new VBox()
        {{
            enableAutoContentWidthHeight();
            setPrefWidth(500);
            setPrefHeight(30);
        }};
        searchVBox.getChildren().addAll(searchLabel,searchField);
        FXUtils.observeWidthHeightCenter(searchVBox,searchLabel);
        FXUtils.observeWidthHeightCenter(searchVBox,searchField);
        var typeListLabel = new ThemeLabel("Book Type");
        var typeList = new ChoiceBox<>(FXCollections.observableArrayList(TypeList.BOOK.getType(), TypeList.JOURNAL.getType(), TypeList.NEWSPAPER.getType()))
        {{
            setValue(TypeList.BOOK.getType());
        }};
        var typeBox = new VBox()
        {{
            setPrefWidth(200);
            setPrefHeight(30);
        }};
        typeBox.getChildren().addAll(typeListLabel, typeList);
        FXUtils.observeWidthHeightCenter(typeBox,typeListLabel);
        FXUtils.observeWidthHeightCenter(typeBox,typeList);
        var searchButton = new FusionButton("Submit")
        {{
            setPrefWidth(200);
            setPrefHeight(30);
            managedProperty().bind(visibleProperty());
        }};
        var searchHBox = new HBox(10)
        {{
            managedProperty().bind(visibleProperty());
        }};
        searchHBox.getChildren().addAll(typeBox,searchVBox,searchButton);
        var confirmButton = new FusionButton("Delete")
        {{
            setPrefWidth(200);
            setPrefHeight(50);
        }};
        var cancelButton = new FusionButton("Cancel")
        {{
            setPrefWidth(200);
            setPrefHeight(50);
        }};
        var confirmBox = new HBox(50)
        {{
            setVisible(false);
            managedProperty().bind(visibleProperty());
        }};
        confirmBox.getChildren().addAll(confirmButton, cancelButton);
        FXUtils.observeWidthHeightCenter(confirmBox,confirmButton);
        FXUtils.observeWidthHeightCenter(confirmBox,cancelButton);
        //table
        var form = new VTableView<BookStatusBorrowController.Data>()
        {{
            getNode().setVisible(false);
            getNode().managedProperty().bind(getNode().visibleProperty());
            enableAutoContentWidthHeight();
            getNode().setPrefHeight(400);
            getNode().setPrefWidth(300);
        }};
        var idColumn = new VTableColumn<BookStatusBorrowController.Data,Integer>("ID", data -> data.statusId);
        var locationColumn = new VTableColumn<BookStatusBorrowController.Data,String>("Location", data -> data.location);
        form.getColumns().addAll(idColumn,locationColumn);

        searchButton.setOnAction(event ->
        {
            try
            {
                var temp = service.searchBookStatus(typeList.getSelectionModel().getSelectedItem(), searchField.getText());
                if (temp.isEmpty())
                {
                    SimpleAlert.showAndWait("Done", "All status solved.");
                }
                else
                {
                    statusList = temp;
                    //load data
                    for(var entry:statusList.entrySet())
                    {
                        form.getItems().add(new BookStatusBorrowController.Data(entry.getKey(),entry.getValue()));
                    }
                    form.getNode().setVisible(true);
                    searchHBox.setVisible(false);
                    confirmBox.setVisible(true);
                    searchHBox.setVisible(false);
                }
            }
            catch (CollectionException e)
            {
                StackTraceAlert.showAndWait("There is an error occurred in searching book status",e);
            }
        });



        confirmButton.setOnAction(event ->
        {
            try
            {
                service.deleteBookStatus(form.getSelectedItem().statusId);
                SimpleAlert.show("Success", "Book location has been deleted");
                form.getItems().clear();
                var temp = service.searchBookStatus(typeList.getSelectionModel().getSelectedItem(), searchField.getText());
                if (temp.isEmpty())
                {
                    SimpleAlert.showAndWait("Done", "All status solved.");
                    form.getNode().setVisible(false);
                    searchVBox.setVisible(true);
                    confirmBox.setVisible(false);
                    searchHBox.setVisible(true);
                }
                else
                {
                    //load data
                    statusList = temp;
                    for(var entry:statusList.entrySet())
                    {
                        form.getItems().add(new BookStatusBorrowController.Data(entry.getKey(),entry.getValue()));
                    }
                }
            }
            catch (CollectionException e)
            {
                StackTraceAlert.showAndWait("There is an error occurred in editing book location",e);
            }
        });

        cancelButton.setOnAction(event ->
        {
            form.getItems().clear();
            form.getNode().setVisible(false);
            searchVBox.setVisible(true);
            confirmBox.setVisible(false);
            searchHBox.setVisible(true);
        });
        var vBox = new VBox();
        vBox.getChildren().addAll(searchHBox,new VPadding(10),form.getNode(),new VPadding(10),confirmBox);
        FXUtils.observeWidthHeightCenter(getContentPane(),vBox);
        getContentPane().getChildren().add(vBox);
    }
}
