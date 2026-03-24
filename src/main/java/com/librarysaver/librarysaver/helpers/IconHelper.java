package com.librarysaver.librarysaver.helpers;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.fluentui.FluentUiRegularAL;
import org.kordamp.ikonli.javafx.FontIcon;

public class IconHelper {

    public static void setIcon(VBox vBox , String icon){
        FontIcon fontIcon = new FontIcon(FluentUiRegularAL.valueOf(icon));
        fontIcon.setIconColor(Color.WHITE);
        fontIcon.getStyleClass().add("icon");
        fontIcon.setIconSize(30);
        vBox.getChildren().add(0,fontIcon);
    }

    public static void setIcon(Button vBox , String icon){
        FontIcon fontIcon = new FontIcon(FluentUiRegularAL.valueOf(icon));
        fontIcon.setIconColor(Color.WHITE);
        fontIcon.getStyleClass().add("icon");
        fontIcon.setIconSize(25);
        vBox.setGraphic(fontIcon);
    }

}
