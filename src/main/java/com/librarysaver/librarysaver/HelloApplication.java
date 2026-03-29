package com.librarysaver.librarysaver;
import atlantafx.base.theme.*;
import com.librarysaver.librarysaver.entities.HibernateUtil;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1900, 900);
        Application.setUserAgentStylesheet(new CupertinoLight().getUserAgentStylesheet());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();


        Task<Void> hibernateTask = new Task<>() {
            @Override
            protected Void call() {
                HibernateUtil.getSessionFactory(); // استدعاء التهيئة هنا
                return null;
            }
        };

        new Thread(hibernateTask).start();

    }
}
