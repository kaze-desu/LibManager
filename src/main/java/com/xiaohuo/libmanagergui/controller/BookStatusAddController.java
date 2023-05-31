package com.xiaohuo.libmanagergui.controller;

import com.xiaohuo.libmanagergui.exception.CollectionException;
import com.xiaohuo.libmanagergui.services.BooksManageServiceImpl;
import com.xiaohuo.libmanagergui.services.BooksStatusServiceImpl;
import com.xiaohuo.libmanagergui.services.template.TypeList;
import io.vproxy.vfx.manager.font.FontManager;
import io.vproxy.vfx.ui.alert.SimpleAlert;
import io.vproxy.vfx.ui.alert.StackTraceAlert;
import io.vproxy.vfx.ui.button.FusionButton;
import io.vproxy.vfx.ui.layout.VPadding;
import io.vproxy.vfx.ui.scene.VScene;
import io.vproxy.vfx.ui.scene.VSceneRole;
import io.vproxy.vfx.ui.wrapper.ThemeLabel;
import io.vproxy.vfx.util.FXUtils;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Xiaohuo (Wang Boyun)
 */
public class BookStatusAddController extends VScene
{

    public BookStatusAddController()
    {
        super(VSceneRole.MAIN);
        enableAutoContentWidthHeight();
        var service = new BooksStatusServiceImpl();
        var manageService = new BooksManageServiceImpl();
        var textLimit = new TextLimit();
        var label = new ThemeLabel("Add Book Status")
        {{
            enableAutoContentWidthHeight();
            setAlignment(Pos.CENTER);
            FontManager.get().setFont(this, settings -> settings.setSize(40));
        }};
        var typeBox = new ChoiceBox<>(FXCollections.observableArrayList("Book","Journal","Newspaper"))
        {{
            enableAutoContentWidthHeight();
            setPrefHeight(30);
            setPrefWidth(500);
        }};
        typeBox.setValue("Book");
        var submitButton = new FusionButton("Add")
        {{
            enableAutoContentWidthHeight();
            setPrefHeight(30);
            setPrefWidth(100);
        }};
        var hBox = new HBox(30);
        hBox.getChildren().addAll(typeBox, submitButton);
        var locationLabel = new ThemeLabel("Location");
        var locationField = new TextField()
        {{
            setTextFormatter(new TextFormatter<>(textLimit.modifyChange));
        }};
        var locationBox = new VBox(locationLabel, locationField);
        var isbnLabel = new ThemeLabel("ISBN");
        var isbnField = new TextField()
        {{
            setTextFormatter(new TextFormatter<>(textLimit.modifyChange));
        }};
        var isbnBox = new VBox(isbnLabel, isbnField)
        {{
            managedProperty().bind(visibleProperty());
        }};
        var issnLabel = new ThemeLabel("ISSN");
        var issnField = new TextField()
        {{
            setTextFormatter(new TextFormatter<>(textLimit.modifyChange));
        }};
        var issnBox = new VBox(issnLabel, issnField)
        {{
            managedProperty().bind(visibleProperty());
            setVisible(false);
        }};
        typeBox.setOnAction(e->
        {
            if(typeBox.getSelectionModel().getSelectedItem().equals(TypeList.BOOK.getType()))
            {
                issnBox.setVisible(false);
                isbnBox.setVisible(true);
            }
            else
            {
                isbnBox.setVisible(false);
                issnBox.setVisible(true);
            }
        });
        submitButton.setOnAction(event -> {
            if(locationField.getText().length()>0&&(isbnField.getText().length()>0 || issnField.getText().length()>0))
            {
                if(typeBox.getSelectionModel().getSelectedItem().equals(TypeList.BOOK.getType()))
                {
                    try
                    {
                        if (manageService.getBookId(TypeList.BOOK.getType(), isbnField.getText())==-1)
                        {
                            SimpleAlert.showAndWait("Not Found","The book is not found");
                        }
                        else
                        {
                            try
                            {
                                service.addBookStatus(TypeList.BOOK.getType(), isbnField.getText(), locationField.getText(),true);
                                SimpleAlert.showAndWait("Success","Add book's status successfully");
                            }
                            catch (CollectionException e)
                            {
                                StackTraceAlert.showAndWait("There is an error occurred in adding book's status",e);
                            }
                        }
                    }
                    catch (CollectionException e)
                    {
                        StackTraceAlert.showAndWait("There is an error occurred in checking book exist",e);
                    }
                }
                else if(typeBox.getSelectionModel().getSelectedItem().equals(TypeList.JOURNAL.getType()))
                {
                    try
                    {
                        if (manageService.getBookId(TypeList.JOURNAL.getType(), issnField.getText())==-1)
                        {
                            SimpleAlert.showAndWait("Not Found","The journal is not found");
                        }
                        else
                        {
                            try
                            {
                                service.addBookStatus(TypeList.JOURNAL.getType(), issnField.getText(), locationField.getText(),true);
                                SimpleAlert.showAndWait("Success","Add journal's status successfully");
                            }
                            catch (CollectionException e)
                            {
                                StackTraceAlert.showAndWait("There is an error occurred in adding journal's status",e);
                            }
                        }
                    }
                    catch (CollectionException e)
                    {
                        StackTraceAlert.showAndWait("There is an error occurred in checking journal exist",e);
                    }

                }
                else
                {
                    try
                    {
                        if (manageService.getBookId(TypeList.NEWSPAPER.getType(), issnField.getText())==-1)
                        {
                            SimpleAlert.showAndWait("Not Found","The journal is not found");
                        }
                        else
                        {
                            try
                            {
                                service.addBookStatus(TypeList.NEWSPAPER.getType(), issnField.getText(), locationField.getText(),true);
                                SimpleAlert.showAndWait("Success","Add newspaper's status successfully");
                            }
                            catch (CollectionException e)
                            {
                                StackTraceAlert.showAndWait("There is an error occurred in adding newspaper's status",e);
                            }
                        }
                    }
                    catch (CollectionException e)
                    {
                        StackTraceAlert.showAndWait("There is an error occurred in checking newspaper exist",e);
                    }

                }
            }
            else
            {
                SimpleAlert.showAndWait("incomplete information","Please fill in the complete information");
            }
        });
        var vBox = new VBox();
        FXUtils.observeWidthHeightCenter(getContentPane(), vBox);
        vBox.getChildren().addAll(label, new VPadding(10),hBox, new VPadding(10),locationBox, new VPadding(10),isbnBox,issnBox);
        getContentPane().getChildren().add(vBox);
    }
}
