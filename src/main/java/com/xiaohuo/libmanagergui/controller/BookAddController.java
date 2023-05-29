package com.xiaohuo.libmanagergui.controller;

import com.xiaohuo.libmanagergui.exception.CollectionException;
import com.xiaohuo.libmanagergui.services.BaseBooks;
import com.xiaohuo.libmanagergui.services.BooksManageServiceImpl;
import com.xiaohuo.libmanagergui.services.template.Book;
import com.xiaohuo.libmanagergui.services.template.Journal;
import com.xiaohuo.libmanagergui.services.template.Newspaper;
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
        var label = new ThemeLabel("添加书本至书籍信息库")
        {{
            enableAutoContentWidthHeight();
            setAlignment(Pos.CENTER);
            FontManager.get().setFont(this, settings -> settings.setSize(40));
        }};
        var choiceBox = new ChoiceBox<>(FXCollections.observableArrayList("书本","期刊","报刊"))
        {{
            enableAutoContentWidthHeight();
            setPrefHeight(30);
            setPrefWidth(300);
        }};
        choiceBox.setValue("书本");
        var submitButton = new FusionButton("添加")
        {{
            enableAutoContentWidthHeight();
            setPrefHeight(30);
            setPrefWidth(100);
        }};
        var hBox = new HBox(30);
        hBox.getChildren().addAll(choiceBox, submitButton);
        var titleLabel = new ThemeLabel("标题");
        var titleField = new TextField()
        {{
            setTextFormatter(new TextFormatter<>(textLimit.modifyChange));
        }};
        var titleBox = new VBox(titleLabel, titleField);
        var authorLabel = new ThemeLabel("作者");
        var authorField = new TextField()
        {{
            setTextFormatter(new TextFormatter<>(textLimit.modifyChange));
        }};
        var authorBox = new VBox(authorLabel, authorField);
        var publisherLabel = new ThemeLabel("出版社");
        var publisherField = new TextField()
        {{
            setTextFormatter(new TextFormatter<>(textLimit.modifyChange));
        }};
        var publisherBox = new VBox(publisherLabel, publisherField);
        var categoryLabel = new ThemeLabel("类别");
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
        var copyRightLabel = new ThemeLabel("版权信息");
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
            }
            else if(newValue.intValue() == 1)
            {
                isbnBox.setVisible(false);
                issnBox.setVisible(true);
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
                if ("书本".equals(choiceBox.getValue()))
                {
                    list.add(new Book(titleField.getText(),authorField.getText(),publisherField.getText(),categoryField.getText(),isbnField.getText()));
                    try
                    {
                        service.addBook(list);
                        SimpleAlert.show("添加成功","添加书本成功");
                    }
                    catch (CollectionException e)
                    {
                        StackTraceAlert.show("添加书本时出现问题",e);
                    }
                }
                else if ("期刊".equals(choiceBox.getValue()))
                {
                    list.add(new Journal(titleField.getText(),authorField.getText(),publisherField.getText(),categoryField.getText(),issnField.getText()));
                    try
                    {
                        service.addJournal(list);
                        SimpleAlert.show("添加成功","添加期刊成功");
                    }
                    catch (CollectionException e)
                    {
                        StackTraceAlert.show("添加期刊时出现问题",e);
                    }
                }
                else
                {
                    list.add(new Newspaper(titleField.getText(),authorField.getText(),publisherField.getText(),categoryField.getText(),issnField.getText(),copyRightField.getText()));
                    try
                    {
                        service.addNewspaper(list);
                        SimpleAlert.show("添加成功","添加报刊成功");
                    }
                    catch (CollectionException e)
                    {
                        StackTraceAlert.show("添加报刊时出现问题",e);
                    }
                }
            }
            else
            {
                SimpleAlert.showAndWait("信息不完整","请填写完整信息");
            }
            });
        var vBox = new VBox();
        vBox.getChildren().addAll(label, new VPadding(20),hBox,new VPadding(20) ,titleBox, new VPadding(20),authorBox, new VPadding(20),publisherBox, new VPadding(20),categoryBox,new VPadding(20), isbnBox, issnBox,new VPadding(20),copyRightBox);
        FXUtils.observeWidthHeightCenter(getContentPane(), vBox);
        getContentPane().getChildren().add(vBox);
    }
}
