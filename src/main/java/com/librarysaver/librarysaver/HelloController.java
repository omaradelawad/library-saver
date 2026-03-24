package com.librarysaver.librarysaver;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import atlantafx.base.controls.ModalPane;
import atlantafx.base.util.Animations;
import com.librarysaver.librarysaver.helpers.IconHelper;
import eu.iamgio.animated.transition.AnimatedSwitcher;
import eu.iamgio.animated.transition.Animation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


import java.io.IOException;

public class HelloController {

    private static HelloController instance;

    @FXML
    VBox homeButton ;


    @FXML
    VBox sectionsButton ;

    @FXML
    VBox downloadButton ;

    @FXML

    VBox storageButton ;

    @FXML
    VBox logout ;

    @FXML
    GridPane gridPane;

    @FXML
    StackPane contentVbox ;






    public HelloController() {
        instance = this; // تعيين النسخة عند إنشاء الكنترولر
    }

    public static HelloController getInstance() {
        return instance;
    }

    public  void setView(String FXMLpath){
        try {



            // get
            Button btn = new Button("loading...");



            Parent file = FXMLLoader.load(getClass().getResource(FXMLpath));



            contentVbox.getChildren().setAll(file);




            var animation = Animations.zoomIn(file , Duration.seconds(0.3)) ;
            animation.playFromStart();

            animation.setOnFinished( (event)->{}      ); ;


        }
        catch (IOException e) {
            System.out.println("======================== error ========================");
            e.printStackTrace();
        }

    }

    public  void setView(Parent view){
        var animation = Animations.zoomIn(view , Duration.seconds(0.3)) ;
        animation.playFromStart();
        contentVbox.getChildren().setAll(view);
    }







    public  void  initialize() {

        IconHelper.setIcon(homeButton,"HOME_16");
        IconHelper.setIcon(sectionsButton,"APPS_ADD_IN_20");
        IconHelper.setIcon(downloadButton,"ARROW_DOWNLOAD_16");
        IconHelper.setIcon(storageButton,"ARROW_SYNC_CIRCLE_24");
        IconHelper.setIcon(logout,"ARROW_PREVIOUS_20");
        onHomeClicked();
    }

    @FXML
    public void onHomeClicked(){
        System.out.println("click");
        setView("home-view.fxml") ;
    }

    @FXML
    public void onSectionsClicked(){
        System.out.println("click");
        setView("sections-view.fxml") ;
    }

    @FXML
    public void backButtonClicked(){

        setView("backup-view.fxml") ;

    }


}


