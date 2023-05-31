package com.xiaohuo.libmanagergui.controller;

import com.xiaohuo.libmanagergui.services.BooksManageServiceImpl;
import com.xiaohuo.libmanagergui.services.BooksStatusServiceImpl;
import com.xiaohuo.libmanagergui.services.template.TypeList;
import io.vproxy.vfx.ui.alert.SimpleAlert;
import io.vproxy.vfx.ui.alert.StackTraceAlert;
import io.vproxy.vfx.ui.button.FusionButton;
import io.vproxy.vfx.ui.layout.VPadding;
import io.vproxy.vfx.ui.scene.VScene;
import io.vproxy.vfx.ui.scene.VSceneRole;
import io.vproxy.vfx.ui.wrapper.ThemeLabel;
import io.vproxy.vfx.util.FXUtils;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * @author xiaohuo(Wang Boyun)
 */
public class BookDeleteController extends VScene
{
    private  ArrayList<Integer> statusID = new ArrayList<>();

    public BookDeleteController()
    {
        super(VSceneRole.MAIN);
        enableAutoContentWidthHeight();
        var textLimit = new TextLimit();
        var typeListLabel = new ThemeLabel("Book Type: ")
        {{
            enableAutoContentWidthHeight();
        }};
        var typeList = new ChoiceBox<>(FXCollections.observableArrayList(TypeList.BOOK.getType(),TypeList.JOURNAL.getType(),TypeList.NEWSPAPER.getType()))
        {{
            enableAutoContentWidthHeight();
            setPrefHeight(30);
            setPrefWidth(100);
            setValue(TypeList.BOOK.getType());
        }};
        var typeListBox = new VBox();
        typeListBox.getChildren().addAll(typeListLabel,typeList);
        var deleteTypeLabel = new ThemeLabel("Delete by: ")
        {{
            enableAutoContentWidthHeight();
        }};
        var deleteType = new ChoiceBox<>(FXCollections.observableArrayList("ISBN/ISSN","Title"))
        {{
            enableAutoContentWidthHeight();
            setPrefHeight(30);
            setPrefWidth(500);
        }};
        var deleteTypeBox = new VBox();
        deleteTypeBox.getChildren().addAll(deleteTypeLabel,deleteType);
        var deleteButton = new FusionButton("Delete")
        {{
            setDisable(true);
            enableAutoContentWidthHeight();
            setPrefWidth(100);
        }};
        var deleteField = new TextField()
        {{
            setVisible(false);
            managedProperty().bind(visibleProperty());
            enableAutoContentWidthHeight();
            setPrefHeight(30);
            setPrefWidth(500);
            setTextFormatter(new TextFormatter<>(textLimit.modifyChange));
        }};
        deleteType.setOnAction(event ->
        {
            deleteField.setVisible(true);
            deleteButton.setDisable(false);
        });
        deleteButton.setOnAction(event ->
        {
            var service = new BooksManageServiceImpl();
            var statusService = new BooksStatusServiceImpl();

            String type = typeList.getSelectionModel().getSelectedItem();
            String input = deleteField.getText();

            if("ISBN/ISSN".equals(deleteType.getSelectionModel().getSelectedItem()))
            {
                try
                {
                   statusID = statusService.getStatusId(statusService.searchBookStatus(type,input));
                   service.deleteBookByIdentityCode(type,input,statusID);
                    SimpleAlert.showAndWait("Successful","Delete successfully");
                }
                catch (Exception e)
                {
                    StackTraceAlert.showAndWait("There is an error occurred in deleting book by ISBN/ISSN",e);
                }
            }
            else
            {
                try
                {
                    ArrayList<String> book = service.getBookByTitleAndType(input,type);
                    statusID = statusService.getStatusId(statusService.searchBookStatus(type,book.get(4)));
                    service.deleteBookByIdentityCode(type,book.get(4),statusID);
                    SimpleAlert.showAndWait("Successful","Delete successfully");
                }
                catch (Exception e)
                {
                    StackTraceAlert.showAndWait("There is an error occurred in deleting book by title",e);
                }
            }
        });
        var deleteBox = new HBox(30);
        var vBox = new VBox();
        deleteBox.getChildren().addAll(typeListBox,deleteTypeBox,deleteButton);
        vBox.getChildren().addAll(deleteBox,new VPadding(10),deleteField);
        FXUtils.observeWidthHeightCenter(getContentPane(),vBox);
        getContentPane().getChildren().add(vBox);
    }
}
