package com.librarysaver.librarysaver;
import atlantafx.base.util.Animations;
import com.librarysaver.librarysaver.entities.HibernateUtil;
import com.librarysaver.librarysaver.entities.Section;
import com.librarysaver.librarysaver.helpers.AlertsHelpers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.internal.SessionFactoryImpl;
import org.kordamp.ikonli.fluentui.FluentUiRegularAL;
import org.kordamp.ikonli.javafx.FontIcon;
import org.testng.annotations.Test;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.hibernate.cfg.Configuration;


@Test
public class SectionViewController {

    public  static SectionViewController section;

    @FXML
    HBox addNewSection  ;

    @FXML
    Button viewSectionButton;

    @FXML
    ScrollPane mainPane;

    @FXML
    ScrollPane cardsPane;

    @FXML
    TextField  sectionName;
    @FXML
    TextField  description;

    @FXML
    TextField src;

    @FXML
    Button saveButton;

    @FXML
    Label sectionsCount;

    public SectionViewController() {
        section = this ;
    }

    public  void initialize() throws IOException {
        cardsPane.setPannable(true);

        List<Section> sections ;

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {

            String query = "from Section s WHERE s.parent IS null";
            sections = session.createQuery(query , Section.class).list()   ;
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("تحذير");
            alert.setHeaderText("خطا في  جلب البيانات");
            alert.setContentText("تعذر جلب البيانات , كود الخطا : " + e.getMessage());
            alert.showAndWait();
            return;
        }


        addNewSection.getChildren().clear();
        sectionsCount.setText( "عدد المجموعات :" + sections.stream().count());
        for (Section section : sections) {

            //* card file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("card.fxml"));
            Parent card = loader.load();

            //* get current card controller
            CardsController cardsController = loader.getController();

            //? fill data , when creating card we use this method from cards controller to fill the data
            cardsController.data(section) ;
            addNewSection.getChildren().add(card);
        }


    }


    @FXML
    public void saveButtonClicked() throws IOException {
        System.out.println("clicked");
        if (sectionName.getText().isEmpty() || description.getText().isEmpty() || src.getText().isEmpty()) {
            AlertsHelpers.showEmptyFieldsAlert();
            return;
        }

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {

            session.beginTransaction();

            Section section = new Section(sectionName.getText() , description.getText() ,  src.getText());
//            section.title = sectionName.getText() ;
//            section.description = description.getText() ;
//            section.imagePath = src.getText() ;

            session.persist(section);
            session.getTransaction().commit() ;
            AlertsHelpers.showSaveSuccess();

            var animation = Animations.flash(saveButton) ;
            animation.playFromStart();
            initialize();

            return;
        }catch (Exception e ){

        }


    }




}
