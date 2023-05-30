package com.xiaohuo.libmanagergui.controller;

import com.xiaohuo.libmanagergui.exception.CollectionException;
import com.xiaohuo.libmanagergui.services.BooksManageServiceImpl;
import com.xiaohuo.libmanagergui.services.BooksStatusServiceImpl;
import com.xiaohuo.libmanagergui.services.template.TypeList;
import io.vproxy.vfx.ui.alert.SimpleAlert;
import io.vproxy.vfx.ui.alert.StackTraceAlert;
import io.vproxy.vfx.ui.button.FusionButton;
import io.vproxy.vfx.ui.layout.VPadding;
import io.vproxy.vfx.ui.pane.FusionPane;
import io.vproxy.vfx.ui.scene.*;
import io.vproxy.vfx.ui.table.VTableColumn;
import io.vproxy.vfx.ui.table.VTableView;
import io.vproxy.vfx.util.FXUtils;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;


/**
 * BookSearchController is the controller of searching books.
 * @author Xiaohuo(Wang Boyun)
 */
public class BookSearchController extends VScene
{
    public BookSearchController(Supplier<VSceneGroup> sceneGroupSup) throws CollectionException
    {
        super(VSceneRole.MAIN);
        List<Throwable> exceptions = new ArrayList<>();
        var textLimit = new TextLimit();
        enableAutoContentWidthHeight();
        var service = new BooksManageServiceImpl();
        var hBox = new HBox(20)
        {{
            setAlignment(Pos.CENTER);
            enableAutoContentWidthHeight();
        }};
        var panel = new VBox()
        {{
            setAlignment(Pos.CENTER);
            enableAutoContentWidthHeight();
        }};
        var searchField = new TextField("Please input the keyword")
        {{
            setAlignment(Pos.CENTER_LEFT);
            setPrefWidth(600);
            setPrefHeight(30);
            enableAutoContentWidthHeight();
            managedProperty().bind(visibleProperty());
            setTextFormatter(new TextFormatter<>(textLimit.modifyChange));
        }};

        var choiceBox = new ChoiceBox<>(FXCollections.observableArrayList("Title", "Author", "Publisher", "Category", "ISBN", "ISSN", "Type"))
        {{
            enableAutoContentWidthHeight();
        }};
        var typeListBox = new ChoiceBox<>(FXCollections.observableArrayList(TypeList.values()))
        {{
            setValue(TypeList.BOOK);
            enableAutoContentWidthHeight();
            setPrefWidth(600);
            setPrefHeight(30);
            managedProperty().bind(visibleProperty());
            setVisible(false);
        }};
        var searchBox = new HBox()
        {{
            setAlignment(Pos.CENTER);
            enableAutoContentWidthHeight();
            getChildren().addAll(searchField,typeListBox,choiceBox);
        }};
        //choice box event
        choiceBox.setValue("Title");
        choiceBox.getSelectionModel().selectedIndexProperty()
                .addListener((observable, oldValue, newValue) ->
                {
                    if (newValue.intValue() == 6)
                    {
                        searchField.setVisible(false);
                        typeListBox.setVisible(true);

                    }
                    else
                    {
                        searchField.setVisible(true);
                        typeListBox.setVisible(false);

                    }
                });

        //table
        var form = new VTableView<Data>()
        {{
            enableAutoContentWidthHeight();
            getNode().setPrefWidth(1000);
            getNode().setPrefHeight(580);
        }};
        var typeColumn = new VTableColumn<Data,String>("Type", data -> data.type);
        var titleColumn = new VTableColumn<Data,String>("Title", data -> data.title);
        var authorColumn = new VTableColumn<Data,String>("Author", data -> data.author);
        var publisherColumn = new VTableColumn<Data,String>("Publisher", data -> data.publisher);
        var isbnColumn = new VTableColumn<Data,String>("ISBN", data -> data.isbn);
        var issnColumn = new VTableColumn<Data,String>("ISSN", data -> data.issn);
        var copyRightColumn = new VTableColumn<Data,String>("CopyRight", data -> data.copyRight);
        form.getColumns().addAll(typeColumn, titleColumn, authorColumn, publisherColumn, isbnColumn, issnColumn,copyRightColumn);


        var searchButton = new FusionButton("Search")
        {{
            setPrefWidth(100);
            setPrefHeight(30);
            enableAutoContentWidthHeight();
        }};
        //search button event
        searchButton.setOnAction(event ->
        {
            form.getItems().clear();
            var item = choiceBox.getSelectionModel().getSelectedItem();
            if(!"Type".equals(choiceBox.getSelectionModel().getSelectedItem()))
            {
                switch (item)
                {
                    case "Title" ->
                    {
                        try
                        {
                            var list = service.search(searchField.getText());
                            for (Map.Entry<Integer, ArrayList<String>> information : list.entrySet())
                            {
                                var data = new Data();
                                data.setData(information.getValue());
                                if (information.getValue().get(0).equals(TypeList.BOOK.toString()))
                                {
                                    data.isbn = information.getValue().get(5);
                                } else
                                {
                                    data.issn = information.getValue().get(5);
                                }
                                form.getItems().add(data);

                            }
                        } catch (CollectionException e)
                        {
                            e.printStackTrace();
                            exceptions.add(e);
                        }
                    }
                    case "Author" ->
                    {
                        try
                        {
                            var list = service.searchByAuthor(searchField.getText());
                            for (Map.Entry<Integer, ArrayList<String>> information : list.entrySet())
                            {
                                var data = new Data();
                                data.setData(information.getValue());
                                if (information.getValue().get(0).equals(TypeList.BOOK.toString()))
                                {
                                    data.isbn = information.getValue().get(5);
                                } else
                                {
                                    data.issn = information.getValue().get(5);
                                }
                                form.getItems().add(data);

                            }
                        } catch (CollectionException e)
                        {
                            e.printStackTrace();
                            exceptions.add(e);
                        }
                    }
                    case "Publisher" ->
                    {
                        try
                        {
                            var list = service.searchByPublisher(searchField.getText());
                            for (Map.Entry<Integer, ArrayList<String>> information : list.entrySet())
                            {
                                var data = new Data();
                                data.setData(information.getValue());
                                if (information.getValue().get(0).equals(TypeList.BOOK.toString()))
                                {
                                    data.isbn = information.getValue().get(5);
                                } else
                                {
                                    data.issn = information.getValue().get(5);
                                }
                                form.getItems().add(data);

                            }
                        } catch (CollectionException e)
                        {
                            e.printStackTrace();
                            exceptions.add(e);
                        }
                    }
                    case "Category" ->
                    {
                        try
                        {
                            var list = service.searchByCategory(searchField.getText());
                            for (Map.Entry<Integer, ArrayList<String>> information : list.entrySet())
                            {
                                var data = new Data();
                                data.setData(information.getValue());
                                if (information.getValue().get(0).equals(TypeList.BOOK.toString()))
                                {
                                    data.isbn = information.getValue().get(5);
                                } else
                                {
                                    data.issn = information.getValue().get(5);
                                }
                                form.getItems().add(data);

                            }
                        } catch (CollectionException e)
                        {
                            e.printStackTrace();
                            exceptions.add(e);
                        }
                    }
                    case "ISBN" ->
                    {
                        try
                        {
                            var list = service.searchByIdentityCode(TypeList.BOOK.getType(), searchField.getText());
                            var data = new Data();
                            data.setData(list);
                            data.isbn = list.get(5);
                        } catch (CollectionException e)
                        {
                            e.printStackTrace();
                            exceptions.add(e);
                        }
                    }
                    case "ISSN" ->
                    {
                        try
                        {
                            var list = service.searchByIdentityCode(TypeList.JOURNAL.getType(), searchField.getText());
                            var data = new Data();
                            data.setData(list);
                            data.isbn = list.get(5);
                        } catch (CollectionException e)
                        {
                            e.printStackTrace();
                            exceptions.add(e);
                        }
                    }
                }
            }
            else
            {
                try
                {
                    var list = service.searchByType(typeListBox.getSelectionModel().getSelectedItem().toString());
                    for(Map.Entry<Integer,ArrayList<String>>information:list.entrySet())
                    {
                        var data = new Data();
                        data.setData(information.getValue());
                        if (information.getValue().get(0).equals(TypeList.BOOK.toString()))
                        {
                            data.isbn = information.getValue().get(5);
                        }
                        else if (information.getValue().get(0).equals(TypeList.JOURNAL.toString()))
                        {
                            data.issn = information.getValue().get(5);
                        }
                        else if (information.getValue().get(0).equals(TypeList.NEWSPAPER.toString()))
                        {
                            data.issn = information.getValue().get(5);
                            data.copyRight = information.getValue().get(6);
                        }
                        form.getItems().add(data);

                    }
                }
                catch (CollectionException e)
                {
                    e.printStackTrace();
                    exceptions.add(e);
                }
            }

        });

        var statusService = new BooksStatusServiceImpl();
        var controlPane = new FusionPane(false);
        var vBox = new VBox(10);
        var borrowButton = new FusionButton("Borrow Book") {{
            setPrefWidth(120);
            setPrefHeight(40);
            enableAutoContentWidthHeight();
            setOnAction(e ->
            {
                var selected = form.getSelectedItem();
                if(selected !=null)
                {
                    if(selected.type.equals(TypeList.BOOK.getType()))
                    {
                        try
                        {
                            var statusList = statusService.searchBookStatus(selected.type, selected.isbn);
                            if (statusList.isEmpty())
                            {
                                SimpleAlert.showAndWait(Alert.AlertType.INFORMATION,"This book already all borrowed");
                            }
                            else
                            {
                                var statusScene = new BookStatusBorrowController(sceneGroupSup,statusList);
                                sceneGroupSup.get().addScene(statusScene, VSceneHideMethod.FADE_OUT);
                                FXUtils.runDelay(50,()->sceneGroupSup.get().show(statusScene, VSceneShowMethod.FADE_IN));
                            }
                        }
                        catch (CollectionException ex)
                        {
                            ex.printStackTrace();
                            exceptions.add(ex);
                        }
                    }
                    else
                    {
                        try
                        {
                            var statusList = statusService.searchBookStatus(selected.type, selected.issn);
                            if (statusList.isEmpty())
                            {
                                SimpleAlert.showAndWait(Alert.AlertType.INFORMATION,"This book already all borrowed");
                            }
                            else
                            {
                                var statusScene = new BookStatusBorrowController(sceneGroupSup,statusList);
                                sceneGroupSup.get().addScene(statusScene, VSceneHideMethod.FADE_OUT);
                                FXUtils.runDelay(50,()->sceneGroupSup.get().show(statusScene, VSceneShowMethod.FADE_IN));
                            }

                        }
                        catch (CollectionException ex)
                        {
                            ex.printStackTrace();
                            exceptions.add(ex);
                        }
                    }
                }
            });
        }};
        var revertButton = new FusionButton("Revert Book") {{
            enableAutoContentWidthHeight();
            setOnAction(e ->
            {
                var selected = form.getSelectedItem();
                if(selected.type.equals(TypeList.BOOK.getType()))
                {
                    try
                    {
                        var allStatusList = statusService.searchAllBookStatus(TypeList.BOOK.getType(), selected.isbn);
                        if(allStatusList.isEmpty())
                        {
                            SimpleAlert.showAndWait(Alert.AlertType.INFORMATION,"Library has not collect this book");
                        }
                        else
                        {
                            var statusScene = new BookStatusRevertController(sceneGroupSup,allStatusList);
                            sceneGroupSup.get().addScene(statusScene, VSceneHideMethod.FADE_OUT);
                            FXUtils.runDelay(50,()->sceneGroupSup.get().show(statusScene, VSceneShowMethod.FADE_IN));
                        }

                    }
                    catch (CollectionException ex)
                    {
                        StackTraceAlert.showAndWait("There is an error occurred in reverting book：",ex);
                    }
                }
            });
            setPrefWidth(120);
            setPrefHeight(40);
        }};
        vBox.getChildren().addAll(borrowButton,revertButton);
        FXUtils.observeWidthHeightCenter(vBox,borrowButton);
        FXUtils.observeWidthHeightCenter(vBox,revertButton);
        controlPane.getContentPane().getChildren().add(vBox);
        FXUtils.observeWidthHeightCenter(controlPane.getContentPane(),vBox);
        searchField.setPromptText("Title");
        hBox.getChildren().addAll(searchBox,searchButton,choiceBox);
        FXUtils.observeWidthHeightCenter(hBox,searchBox);
        FXUtils.observeWidthHeightCenter(hBox,searchButton);
        FXUtils.observeWidthHeightCenter(hBox,choiceBox);
        var formBox = new HBox(20);
        formBox.getChildren().addAll(form.getNode(),controlPane.getNode());
        FXUtils.observeWidthHeightCenter(formBox,form.getNode());
        FXUtils.observeWidthHeightCenter(formBox,controlPane.getNode());
        panel.getChildren().addAll(hBox,new VPadding(20),formBox);
        FXUtils.observeWidthHeightCenter(panel,hBox);
        FXUtils.observeWidthHeightCenter(panel,formBox);
        getContentPane().getChildren().add(panel);
        FXUtils.observeWidthHeightCenter(getContentPane(),panel);
        getContentPane().setMinHeight(650);
        getContentPane().setMinWidth(400);
        if(exceptions.size()>0)
        {
            throw new CollectionException(exceptions);
        }

    }
    public static class Data
    {
        public String type;
        public String title;
        public String author;
        public String publisher;
        public String tag;
        public String isbn;
        public String issn;
        public String copyRight;

        public void setData(ArrayList<String> data)
        {
            this.type = data.get(0);
            this.title = data.get(1);
            this.author = data.get(2);
            this.publisher = data.get(3);
            this.tag = data.get(4);
        }
    }
}
