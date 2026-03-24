package com.librarysaver.librarysaver;
import atlantafx.base.util.Animations;
import com.librarysaver.librarysaver.entities.HibernateUtil;
import com.librarysaver.librarysaver.entities.Link;
import com.librarysaver.librarysaver.helpers.AlertsHelpers;
import com.librarysaver.librarysaver.helpers.TextHelpers;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import org.hibernate.Session;

import java.io.IOException;

public class EditLinkPopUpController {

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


    public void data( Long id , String name , String description , String url ){
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


        System.out.println("save button pressed");

        try(Session session = HibernateUtil.getSessionFactory().openSession()){

            session.beginTransaction();

            var currentLink = session.get(Link.class , Long.parseLong(idLabel.getText()));

            boolean fieldsNotEmpty = TextHelpers.fieldsNotEmpty(nameField, descriptionField, urlField);

            System.out.println(  "result ============================== " +   fieldsNotEmpty);

            if (fieldsNotEmpty && currentLink != null) {
                currentLink.title = nameField.getText();
                currentLink.description = descriptionField.getText();
                currentLink.link = urlField.getText();

                session.persist(currentLink);
                session.getTransaction().commit();

                AlertsHelpers.showUpdateSuccess();
                SectionViewController.section.initialize();

                cancelButtonAction();
                ViewSectionViewController.controller.fetchSections(currentLink.section.id);
            }else{
                AlertsHelpers.showEmptyFieldsAlert();
            }



        }catch (Exception e){

            e.printStackTrace();

        }

    }




}
