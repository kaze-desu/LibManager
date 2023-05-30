package com.xiaohuo.libmanagergui.controller;

import com.xiaohuo.libmanagergui.exception.CollectionException;
import com.xiaohuo.libmanagergui.services.BaseBooks;
import com.xiaohuo.libmanagergui.services.BooksManageServiceImpl;
import com.xiaohuo.libmanagergui.services.template.Book;
import com.xiaohuo.libmanagergui.services.template.Journal;
import com.xiaohuo.libmanagergui.services.template.Newspaper;
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
import java.util.ArrayList;


/**
 * @author Xiaohuo(Wang Boyun)
 */
public class BookAddController extends VScene
{
    public BookAddController()
    {
        super(VSceneRole.MAIN);
        enableAutoContentWidthHeight();
        var service = new BooksManageServiceImpl();
        var textLimit = new TextLimit();
        var label = new ThemeLabel("Add Book to information database")
        {{
            enableAutoContentWidthHeight();
            setAlignment(Pos.CENTER);
            FontManager.get().setFont(this, settings -> settings.setSize(40));
        }};
        var choiceBox = new ChoiceBox<>(FXCollections.observableArrayList("Book","Journal","Newspaper"))
        {{
            enableAutoContentWidthHeight();
            setPrefHeight(30);
            setPrefWidth(500);
        }};
        choiceBox.setValue("Book");
        var submitButton = new FusionButton("Add")
        {{
            enableAutoContentWidthHeight();
            setPrefHeight(30);
            setPrefWidth(100);
        }};
        var hBox = new HBox(30);
        hBox.getChildren().addAll(choiceBox, submitButton);
        var titleLabel = new ThemeLabel("Title");
        var titleField = new TextField()
        {{
            setTextFormatter(new TextFormatter<>(textLimit.modifyChange));
        }};
        var titleBox = new VBox(titleLabel, titleField);
        var authorLabel = new ThemeLabel("Author");
        var authorField = new TextField()
        {{
            setTextFormatter(new TextFormatter<>(textLimit.modifyChange));
        }};
        var authorBox = new VBox(authorLabel, authorField);
        var publisherLabel = new ThemeLabel("Publisher");
        var publisherField = new TextField()
        {{
            setTextFormatter(new TextFormatter<>(textLimit.modifyChange));
        }};
        var publisherBox = new VBox(publisherLabel, publisherField);
        var categoryLabel = new ThemeLabel("Category");
        var categoryField = new TextField()
        {{
            setTextFormatter(new TextFormatter<>(textLimit.modifyChange));
        }};
        var categoryBox = new VBox(categoryLabel, categoryField);
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
        var issnField = new TextField();
        var issnBox = new VBox(issnLabel, issnField){{
            setVisible(false);
            managedProperty().bind(visibleProperty());
        }};
        var copyRightLabel = new ThemeLabel("CopyRight");
        var copyRightField = new TextField()
        {{
            setTextFormatter(new TextFormatter<>(textLimit.modifyChange));
        }};
        var copyRightBox = new VBox(copyRightLabel, copyRightField)
        {{
            setVisible(false);
            managedProperty().bind(visibleProperty());
        }};
        choiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == 0)
            {
                isbnBox.setVisible(true);
                issnBox.setVisible(false);
                copyRightBox.setVisible(false);
            }
            else if(newValue.intValue() == 1)
            {
                isbnBox.setVisible(false);
                issnBox.setVisible(true);
                copyRightBox.setVisible(false);
            }
            else
            {
                isbnBox.setVisible(false);
                issnBox.setVisible(true);
                copyRightBox.setVisible(true);
            }
        });
        submitButton.setOnAction(event -> {
            if(titleField.getText().length()>0&&authorField.getText().length()>0&&publisherField.getText().length()>0&&categoryField.getText().length()>0||isbnField.getText().length()>0||(issnField.getText().length()>0&&copyRightField.getText().length()>0))
            {
                ArrayList<BaseBooks> list = new ArrayList<>();
                if (choiceBox.getSelectionModel().getSelectedItem().equals(TypeList.BOOK.getType()))
                {
                    list.add(new Book(titleField.getText(),authorField.getText(),publisherField.getText(),categoryField.getText(),isbnField.getText()));
                    try
                    {
                        service.add(list);
                        SimpleAlert.show("Successful","Add book successfully");
                    }
                    catch (CollectionException e)
                    {
                        StackTraceAlert.show("There is an error occurred when adding book",e);
                    }
                }
                else if (choiceBox.getSelectionModel().getSelectedItem().equals(TypeList.JOURNAL.getType()))
                {
                    list.add(new Journal(titleField.getText(),authorField.getText(),publisherField.getText(),categoryField.getText(),issnField.getText()));
                    try
                    {
                        service.add(list);
                        SimpleAlert.show("Successful","Add journal successfully");
                    }
                    catch (CollectionException e)
                    {
                        StackTraceAlert.show("There is an error occurred when adding Journal",e);
                    }
                }
                else
                {
                    list.add(new Newspaper(titleField.getText(),authorField.getText(),publisherField.getText(),categoryField.getText(),issnField.getText(),copyRightField.getText()));
                    try
                    {
                        service.add(list);
                        SimpleAlert.show("Successful","Add newspaper successfully");
                    }
                    catch (CollectionException e)
                    {
                        StackTraceAlert.show("There is an error occurred when adding Newspaper",e);
                    }
                }
            }
            else
            {
                SimpleAlert.showAndWait("incomplete information","Please fill in the complete information");
            }
            });
        var vBox = new VBox();
        vBox.getChildren().addAll(label, new VPadding(20),hBox,new VPadding(20) ,titleBox, new VPadding(20),authorBox, new VPadding(20),publisherBox, new VPadding(20),categoryBox,new VPadding(20), isbnBox, issnBox,new VPadding(20),copyRightBox);
        FXUtils.observeWidthHeightCenter(getContentPane(), vBox);
        getContentPane().getChildren().add(vBox);
    }
}
