package com.xiaohuo.libmanagergui.controller;

import com.xiaohuo.libmanagergui.services.template.TypeList;
import io.vproxy.vfx.ui.button.FusionButton;
import io.vproxy.vfx.ui.scene.VScene;
import io.vproxy.vfx.ui.scene.VSceneRole;
import io.vproxy.vfx.ui.table.VTableView;
import io.vproxy.vfx.util.FXUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;



/**
 * BookSearchController is the controller of searching books.
 * @author Xiaohuo(Wang Boyun)
 */
public class BookSearchController extends VScene
{
    BookSearchController()
    {
        super(VSceneRole.MAIN);
        enableAutoContentWidthHeight();
        /*TODO
           1.做一个搜索框，默认搜索标题，右边做一个下拉框，选择搜索方式
         * 2.在搜索框下直接做一个列表，用于显示结果予以选择
         */
        var hBox = new HBox(20);
        var searchBox = new HBox();
        var panel = new VBox();
        searchBox.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER);
        panel.setAlignment(Pos.CENTER);
        FXUtils.observeWidthHeightCenter(getContentPane(), panel);
        var searchField = new TextField("输入搜索内容")
        {{
            setPrefWidth(600);
            setPrefHeight(30);
        }};

        var choiceBox = new ChoiceBox<>(FXCollections.observableArrayList("标题", "作者", "发布者", "标签", "ISBN", "ISSN", "书本分类"));
        var typeListBox = new ChoiceBox<>(FXCollections.observableArrayList(TypeList.values()))
        {{
            setVisible(false);
            enableAutoContentWidthHeight();

        }};

        choiceBox.setValue("标题");
        choiceBox.getSelectionModel().selectedIndexProperty()
                .addListener((observable, oldValue, newValue) ->
                {
                    if (newValue.intValue() == 6)
                    {
                        searchField.setVisible(false);
                        typeListBox.setVisible(true);
                        searchField.setPrefWidth(0);
                        searchField.setPrefHeight(0);
                        typeListBox.setPrefWidth(600);
                        typeListBox.setPrefHeight(30);
                    }
                    else
                    {
                        searchField.setVisible(true);
                        typeListBox.setVisible(false);
                        typeListBox.setPrefWidth(0);
                        typeListBox.setPrefHeight(0);
                        searchField.setPrefWidth(600);
                        searchField.setPrefHeight(30);
                    }
                });
        var searchButton = new FusionButton("搜索")
        {{
            setPrefWidth(100);
            setPrefHeight(30);
        }};

        searchButton.setOnAction(event ->
        {
            var item = choiceBox.getSelectionModel().getSelectedItem();
            switch (item)
            {
                case "标题":

                    break;
                case "作者":
                    break;
                case "发布者":
                    break;
                case "标签":
                    break;
                case "ISBN":
                    break;
                case "ISSN":
                    break;
            }
        });

        var form = new VTableView<>();
        form.getNode().setPrefWidth(1000);
        form.getNode().setPrefHeight(580);

        searchField.setPromptText("书本标题");
        searchBox.getChildren().addAll(searchField,typeListBox);
        hBox.getChildren().addAll(searchBox,searchButton,choiceBox);
        panel.getChildren().addAll(hBox,form.getNode());
        getContentPane().getChildren().addAll(panel);

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
