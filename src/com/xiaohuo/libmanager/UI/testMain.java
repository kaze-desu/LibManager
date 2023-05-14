package com.xiaohuo.libmanager.UI;


import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.text.Style;
import java.awt.*;

public class testMain extends Application {

    private String iconPath = "";

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Button button1 = new Button("lll");


        stage.setTitle("Library management system");

        //设置图标
        //stage.getIcons().add(new Image(iconPath));

        stage.setResizable(false);
        stage.initStyle(StageStyle.DECORATED);
        stage.show();
    }
}
