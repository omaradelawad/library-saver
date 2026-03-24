package com.librarysaver.librarysaver;
import atlantafx.base.util.Animations;
import com.librarysaver.librarysaver.entities.HibernateUtil;
import com.librarysaver.librarysaver.entities.Section;
import com.librarysaver.librarysaver.helpers.AlertsHelpers;
import com.librarysaver.librarysaver.helpers.TextHelpers;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.hibernate.Session;

import java.awt.*;
import java.io.IOException;

public class editSectionPopUpController {

// ================= controls =================
    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    @FXML
    HBox mainContainer ;

// ================= data =================

    @FXML
    private TextField nameField ;

    @FXML
    private TextArea descriptionField ;

    @FXML
    private TextField urlField ;

    @FXML
    private Label idLabel;


    public void data( Long id , String name , String description , String url){
        idLabel.setText( String.valueOf(id) );
        nameField.setText(name);
        descriptionField.setText(description);
        urlField.setText(url);
    }




    @FXML
    public void cancelButtonAction() {
        Parent box =  (Parent) HelloController.getInstance().contentVbox.getChildren().get(1);

        var animation = Animations.slideOutDown(box , Duration.seconds(0.3)) ;
        animation.playFromStart();

        animation.setOnFinished(event -> {
            HelloController.getInstance().contentVbox.getChildren().remove(1);
        }) ;


    }

    @FXML
    public void saveButtonAction() throws IOException {

        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            session.beginTransaction();

            var currentSection = session.get(Section.class , Long.parseLong(idLabel.getText()));

            boolean fieldsNotEmpty = TextHelpers.fieldsNotEmpty(nameField, descriptionField, urlField);

            if (fieldsNotEmpty && currentSection != null) {
                currentSection.title = nameField.getText();
                currentSection.description = descriptionField.getText();
                currentSection.imagePath = urlField.getText();

                session.persist(currentSection);
                session.getTransaction().commit();
                AlertsHelpers.showUpdateSuccess();
                SectionViewController.section.initialize();
                cancelButtonAction();
            }else{
                AlertsHelpers.showEmptyFieldsAlert();
            }



        }

    }




}
