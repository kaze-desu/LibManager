package com.xiaohuo.libmanagergui.controller;

import com.xiaohuo.libmanagergui.exception.CollectionException;
import com.xiaohuo.libmanagergui.services.BooksManageServiceImpl;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Xiaohuo(Wangboyun)
 */
public class BookEditController extends VScene
{
    public BookEditController()
    {

        super(VSceneRole.MAIN);
        enableAutoContentWidthHeight();
        var service = new BooksManageServiceImpl();
        var textLimit = new TextLimit();

        var searchLabel = new ThemeLabel("ISBN/ISSN")
        {{
            enableAutoContentWidthHeight();
        }};
        var searchField = new TextField()
        {{
            enableAutoContentWidthHeight();
            setPrefWidth(500);
            setPrefHeight(30);
            setTextFormatter(new TextFormatter<>(textLimit.modifyChange));
        }};
        var searchVBox = new VBox()
        {{
            setLayoutY(50);
        }};
        var typeList = new ChoiceBox<>(FXCollections.observableArrayList(TypeList.BOOK.getType(), TypeList.JOURNAL.getType(), TypeList.NEWSPAPER.getType()))
        {{
            setValue(TypeList.BOOK.getType());
            setPrefHeight(50);
        }};
        var searchButton = new FusionButton("Submit")
        {{
            setPrefWidth(200);
            setPrefHeight(30);
        }};
        var titleColumnLabel = new ThemeLabel("Title");
        var titleColumnField = new TextField()
        {{
            setTextFormatter(new TextFormatter<>(textLimit.modifyChange));
        }};
        var titleColumnBox = new VBox()
        {{
            setVisible(false);
            setPrefWidth(300);
            setPrefHeight(50);
            managedProperty().bind(visibleProperty());
        }};
        titleColumnBox.getChildren().addAll(titleColumnLabel, titleColumnField);
        var authorColumnLabel = new ThemeLabel("Author");
        var authorColumnField = new TextField()
        {{
            setTextFormatter(new TextFormatter<>(textLimit.modifyChange));
        }};
        var authorColumnBox = new VBox()
        {{
            setVisible(false);
            setPrefWidth(300);
            setPrefHeight(50);
            managedProperty().bind(visibleProperty());
        }};
        authorColumnBox.getChildren().addAll(authorColumnLabel, authorColumnField);
        var publisherColumnLabel = new ThemeLabel("Publisher");
        var publisherColumnField = new TextField()
        {{
            setTextFormatter(new TextFormatter<>(textLimit.modifyChange));
        }};
        var publisherColumnBox = new VBox()
        {{
            setVisible(false);
            setPrefWidth(300);
            setPrefHeight(50);
            managedProperty().bind(visibleProperty());
        }};
        publisherColumnBox.getChildren().addAll(publisherColumnLabel, publisherColumnField);
        var categoryColumnLabel = new ThemeLabel("Category");
        var categoryColumnField = new TextField()
        {{
            setTextFormatter(new TextFormatter<>(textLimit.modifyChange));
        }};
        var categoryColumnBox = new VBox()
        {{
            setVisible(false);
            setPrefWidth(300);
            setPrefHeight(50);
            managedProperty().bind(visibleProperty());
        }};
        categoryColumnBox.getChildren().addAll(categoryColumnLabel, categoryColumnField);
        var isbnColumnLabel = new ThemeLabel("ISBN");
        var isbnColumnField = new TextField()
        {{
            setTextFormatter(new TextFormatter<>(textLimit.modifyChange));
        }};
        var isbnColumnBox = new VBox()
        {{
            setVisible(false);
            setPrefWidth(300);
            setPrefHeight(50);
            managedProperty().bind(visibleProperty());
        }};
        isbnColumnBox.getChildren().addAll(isbnColumnLabel, isbnColumnField);
        var issnColumnLabel = new ThemeLabel("ISSN");
        var issnColumnField = new TextField()
        {{
            setTextFormatter(new TextFormatter<>(textLimit.modifyChange));
        }};
        var issnColumnBox = new VBox()
        {{
            setVisible(false);
            setPrefWidth(300);
            setPrefHeight(50);
            managedProperty().bind(visibleProperty());
        }};
        issnColumnBox.getChildren().addAll(issnColumnLabel, issnColumnField);
        var copyrightColumnLabel = new ThemeLabel("CopyRight");
        var copyrightColumnField = new TextField()
        {{
            setTextFormatter(new TextFormatter<>(textLimit.modifyChange));
        }};
        var copyRightColumnBox = new VBox()
        {{
            setVisible(false);
            setPrefWidth(300);
            setPrefHeight(50);
            managedProperty().bind(visibleProperty());
        }};
        copyRightColumnBox.getChildren().addAll(copyrightColumnLabel, copyrightColumnField);
        var confirmButton = new FusionButton("Confirm")
        {{
            setPrefWidth(200);
            setPrefHeight(50);
        }};
        var cancelButton = new FusionButton("Cancel")
        {{
            setPrefWidth(200);
            setPrefHeight(50);
        }};
        var confirmBox = new HBox()
        {{
            setVisible(false);
            managedProperty().bind(visibleProperty());
        }};
        confirmButton.setOnAction(event ->
        {
            if (titleColumnField.getText().length()>0)
            {
                try
                {
                    service.editBookInformation("Title",titleColumnField.getText(),typeList.getSelectionModel().getSelectedItem(),searchField.getText());
                    SimpleAlert.showAndWait(Alert.AlertType.INFORMATION,"Book information edited successfully");
                }
                catch (CollectionException e)
                {
                    StackTraceAlert.showAndWait("There is an error occurred in editing book's title",e);
                }
            }
            if (authorColumnField.getText().length()>0)
            {
                try
                {
                    service.editBookInformation("Author",authorColumnField.getText(),typeList.getSelectionModel().getSelectedItem(),searchField.getText());
                    SimpleAlert.showAndWait(Alert.AlertType.INFORMATION,"Book information edited successfully");
                }
                catch (CollectionException e)
                {
                    StackTraceAlert.showAndWait("There is an error occurred in editing book's author",e);
                }
            }
            if (publisherColumnField.getText().length()>0)
            {
                try
                {
                    service.editBookInformation("Publisher",publisherColumnField.getText(),typeList.getSelectionModel().getSelectedItem(),searchField.getText());
                    SimpleAlert.showAndWait(Alert.AlertType.INFORMATION,"Book information edited successfully");
                }
                catch (CollectionException e)
                {
                    StackTraceAlert.showAndWait("There is an error occurred in editing book's publisher",e);
                }
            }
            if (categoryColumnField.getText().length()>0)
            {
                try
                {
                    service.editBookInformation("Category",categoryColumnField.getText(),typeList.getSelectionModel().getSelectedItem(),searchField.getText());
                    SimpleAlert.showAndWait(Alert.AlertType.INFORMATION,"Book information edited successfully");
                }
                catch (CollectionException e)
                {
                    StackTraceAlert.showAndWait("There is an error occurred in editing book's category",e);
                }
            }
            if (isbnColumnField.getText().length()>0)
            {
                try
                {
                    service.editBookInformation("Isbn",isbnColumnField.getText(),typeList.getSelectionModel().getSelectedItem(),searchField.getText());
                    SimpleAlert.showAndWait(Alert.AlertType.INFORMATION,"Book information edited successfully");
                }
                catch (CollectionException e)
                {
                    StackTraceAlert.showAndWait("There is an error occurred in editing book's ISBN",e);
                }
            }
            if (issnColumnField.getText().length()>0)
            {
                try
                {
                    service.editBookInformation("Issn",issnColumnField.getText(),typeList.getSelectionModel().getSelectedItem(),searchField.getText());
                    SimpleAlert.showAndWait(Alert.AlertType.INFORMATION,"Book information edited successfully");
                }
                catch (CollectionException e)
                {
                    StackTraceAlert.showAndWait("There is an error occurred in editing book's ISSN",e);
                }
            }
            if(copyrightColumnField.getText().length()>0)
            {
                try
                {
                    service.editBookInformation("CopyRight",isbnColumnField.getText(),typeList.getSelectionModel().getSelectedItem(),searchField.getText());
                    SimpleAlert.showAndWait(Alert.AlertType.INFORMATION,"Book information edited successfully");
                }
                catch (CollectionException e)
                {
                    StackTraceAlert.showAndWait("There is an error occurred in editing book's CopyRight",e);
                }
            }
            if (titleColumnField.getText().length()==0 && authorColumnField.getText().length()==0 && publisherColumnField.getText().length()==0 && categoryColumnField.getText().length()==0 && (isbnColumnField.getText().length()!=0 || issnColumnField.getText().length() !=0 || copyrightColumnField.getText().length()!=0))
            {
                SimpleAlert.showAndWait(Alert.AlertType.WARNING,"Please enter a value to edit");
            }
        });

        cancelButton.setOnAction(event ->
        {
            titleColumnBox.setVisible(false);
            authorColumnBox.setVisible(false);
            publisherColumnBox.setVisible(false);
            categoryColumnBox.setVisible(false);
            isbnColumnBox.setVisible(false);
            issnColumnBox.setVisible(false);
            copyRightColumnBox.setVisible(false);
            confirmBox.setVisible(false);
            searchButton.setDisable(false);
        });
        searchButton.setOnAction(event ->
        {
            try
            {
                var id = service.getBookId(typeList.getSelectionModel().getSelectedItem(),searchField.getText());
                if (id != -1)
                {
                    searchButton.setDisable(true);
                    titleColumnBox.setVisible(true);
                    authorColumnBox.setVisible(true);
                    publisherColumnBox.setVisible(true);
                    categoryColumnBox.setVisible(true);
                    confirmBox.setVisible(true);
                    if(typeList.getSelectionModel().getSelectedItem().equals(TypeList.BOOK.getType()))
                    {
                        isbnColumnBox.setVisible(true);
                    }
                    else if (typeList.getSelectionModel().getSelectedItem().equals(TypeList.JOURNAL.getType()))
                    {
                        issnColumnBox.setVisible(true);
                    }
                    else
                    {
                        issnColumnBox.setVisible(true);
                        copyRightColumnBox.setVisible(true);
                    }
                }
                else
                {
                    SimpleAlert.showAndWait("Not Found","The book is not found");
                }
            }
            catch (CollectionException e)
            {
                StackTraceAlert.showAndWait("There is an error occurred in searching book",e);
            }
        });
        var searchHBox = new HBox(30);
        var vBox = new VBox();
        searchVBox.getChildren().addAll(searchLabel,searchField);
        searchHBox.getChildren().addAll(typeList,searchVBox,searchButton);
        FXUtils.observeWidthCenter(searchHBox,searchVBox);
        vBox.getChildren().addAll(searchHBox,new VPadding(10),titleColumnBox,new VPadding(10),authorColumnBox,new VPadding(10),publisherColumnBox,new VPadding(10),categoryColumnBox,new VPadding(10),isbnColumnBox,new VPadding(10),issnColumnBox,new VPadding(10),copyRightColumnBox);
        confirmBox.getChildren().addAll(confirmButton,cancelButton);
        vBox.getChildren().add(confirmBox);
        FXUtils.observeWidthHeightCenter(getContentPane(),vBox);
        getContentPane().getChildren().add(vBox);
    }
}

