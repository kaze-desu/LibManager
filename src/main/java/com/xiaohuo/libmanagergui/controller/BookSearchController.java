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
        var searchField = new TextField("输入搜索内容")
        {{
            setAlignment(Pos.CENTER_LEFT);
            setPrefWidth(600);
            setPrefHeight(30);
            enableAutoContentWidthHeight();
            managedProperty().bind(visibleProperty());
        }};

        var choiceBox = new ChoiceBox<>(FXCollections.observableArrayList("标题", "作者", "发布者", "标签", "ISBN", "ISSN", "书本分类"))
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
        choiceBox.setValue("标题");
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
        var typeColumn = new VTableColumn<Data,String>("书本分类", data -> data.type);
        var titleColumn = new VTableColumn<Data,String>("书本标题", data -> data.title);
        var authorColumn = new VTableColumn<Data,String>("作者", data -> data.author);
        var publisherColumn = new VTableColumn<Data,String>("发布者", data -> data.publisher);
        var isbnColumn = new VTableColumn<Data,String>("ISBN", data -> data.isbn);
        var issnColumn = new VTableColumn<Data,String>("ISSN", data -> data.issn);
        form.getColumns().addAll(typeColumn, titleColumn, authorColumn, publisherColumn, isbnColumn, issnColumn);


        var searchButton = new FusionButton("搜索")
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
            if(!"书本分类".equals(choiceBox.getSelectionModel().getSelectedItem()))
            {
                switch (item)
                {
                    case "标题" ->
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
                    case "作者" ->
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
                    case "发布者" ->
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
                    case "标签" ->
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
                        else
                        {
                            data.issn = information.getValue().get(5);
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
        var borrowButton = new FusionButton("借阅") {{
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
                                SimpleAlert.showAndWait(Alert.AlertType.INFORMATION,"该书籍已全部被借阅");
                            }
                            else
                            {
                                var statusScene = new BookStatusBorrowController(sceneGroupSup,statusList);
                                /*VSceneGroup status = new VSceneGroup(statusScene);*/
                                sceneGroupSup.get().addScene(statusScene, VSceneHideMethod.FADE_OUT);
                                FXUtils.observeWidthHeightCenter(getContentPane(),sceneGroupSup.get().getNode());
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
                                SimpleAlert.showAndWait(Alert.AlertType.INFORMATION,"该书籍已全部被借阅");
                            }
                            else
                            {
                                var statusScene = new BookStatusBorrowController(sceneGroupSup,statusList);
                                sceneGroupSup.get().addScene(statusScene, VSceneHideMethod.TO_RIGHT);
                                FXUtils.observeWidthHeightCenter(getContentPane(),sceneGroupSup.get().getNode());
                                FXUtils.runDelay(50,()->sceneGroupSup.get().show(statusScene, VSceneShowMethod.FROM_RIGHT));
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
        var revertButton = new FusionButton("归还") {{
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
                            SimpleAlert.showAndWait(Alert.AlertType.INFORMATION,"图书馆中暂无该书籍");
                        }
                        else
                        {
                         //TODO 完成归还功能
                        }

                    }
                    catch (CollectionException ex)
                    {
                        StackTraceAlert.showAndWait("在获取书本状态时出现错误：",ex);
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
        searchField.setPromptText("书本标题");
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

        public void setData(ArrayList<String> data)
        {
            this.type = data.get(0);
            this.title = data.get(1);
            this.author = data.get(2);
            this.publisher = data.get(3);
            this.tag = data.get(4);
        }
    }
/*
    public int search() throws CollectionException
    {
        while (true)
        {
            System.out.println("Please choose a way to search your book:");
            System.out.println("1. Search by book type (Not recommended)");
            System.out.println("2. Search by book name");
            System.out.println("3. Search by book author");
            System.out.println("4. Search by book publisher");
            System.out.println("5. Search by book category");
            System.out.println("6. Search by book ISBN");
            System.out.println("7. Search by book ISSN");
            System.out.println("=================Please input your choice===================");
            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine();
            int bookId;
            switch (choice)
            {
                case "1" ->
                {
                    while (true)
                    {
                        System.out.println("Please choice a type:");
                        Map<Integer, String> typeList = new HashMap<>();
                        int count = 0;
                        for (TypeList type:TypeList.values())
                        {
                            ++count;
                            System.out.println(count+". "+type.getType());
                            typeList.put(count,type.getType());

                        }
                        int type = scanner.nextInt();
                        if(!typeList.get(type).isEmpty())
                        {
                            bookId = searchByType(typeList.get(type));
                            return bookId;
                        }
                        else
                        {
                            System.out.println("Please input a valid number!");
                        }
                    }

                }
                case "2" ->
                {
                    bookId = searchByName();
                    return bookId;
                }
                case "3" ->
                {
                    bookId = searchByAuthor();
                    return bookId;
                }
                case "4" ->
                {
                    bookId = searchByPublisher();
                    return bookId;
                }
                case "5" ->
                {
                    bookId = searchByCategory();
                    return bookId;
                }
                case "6" ->
                {
                    bookId = searchByIsbn();
                    return bookId;
                }
                case "7" ->
                {
                    bookId = searchByIssn();
                    return bookId;
                }
                default ->
                {
                }
            }
        }

    }
    public int searchByType(String type) throws CollectionException
    {
        BooksManageServiceImpl service = new BooksManageServiceImpl();
        Map<Integer, ArrayList<String>> result;
        result = service.searchByType(type);
        int count = 0;
        if (result.size() == 0)
        {
            System.out.println("No result found.");
            return -1;
        }
        else
        {
            for (Map.Entry<Integer,ArrayList<String>> information:result.entrySet())
            {
                int bookId = information.getKey();
                ArrayList<String> bookInfo = information.getValue();
                if(count % 20 == 0 && count != 0)
                {
                    System.out.println("Continue?(Y/N)");
                    Scanner scanner = new Scanner(System.in);
                    String confirm = scanner.nextLine();
                    if ("N".equals(confirm) || "n".equals(confirm))
                    {
                        System.out.println("Choice your book by inter id:");
                        bookId = scanner.nextInt();
                        return bookId;
                    }
                    else if("Y".equals(confirm) || "y".equals(confirm))
                    {
                        System.out.println("Book id: \n"+bookId);
                        for (String list:bookInfo)
                        {
                            System.out.print(list + " ");
                        }
                    }
                }
                else if(count == result.size()-1)
                {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Book id: "+bookId);
                    for (String list:bookInfo)
                    {
                        System.out.print(list + " ");
                    }
                    System.out.println("Choice your book by inter id:");
                    bookId = scanner.nextInt();
                    return bookId;
                }
                else
                {
                    System.out.println("Book id: "+bookId);
                    for (String list:bookInfo)
                    {
                        System.out.print(list + " ");
                    }
                }
                System.out.println();
                count++;
            }
        }
        return -1;
    }
    public int searchByName() throws CollectionException
    {
        return -1;
    }
    public int searchByAuthor() throws CollectionException
    {
        return -1;
    }
    public int searchByPublisher() throws CollectionException
    {
        return -1;
    }
    public int searchByCategory() throws CollectionException
    {
        return -1;
    }
    public int searchByIsbn() throws CollectionException
    {
        return -1;
    }
    public int searchByIssn() throws CollectionException
    {
        return -1;
    }*/
}
