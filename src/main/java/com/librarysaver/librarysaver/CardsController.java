package com.librarysaver.librarysaver;
import atlantafx.base.controls.ModalPane;
import atlantafx.base.theme.NordLight;
import atlantafx.base.util.Animations;
import com.librarysaver.librarysaver.entities.HibernateUtil;
import com.librarysaver.librarysaver.entities.Section;
import com.librarysaver.librarysaver.helpers.AlertsHelpers;
import com.librarysaver.librarysaver.helpers.IconHelper;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.hibernate.Session;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class CardsController {


    @FXML

    private Button viewSectionButton ;

    @FXML
    private Button editSectionButton ;

    @FXML
    private Button deleteSectionButton ;

    @FXML
    Label titleLabel;

    @FXML
    Label descriptionLabel;

    @FXML
    Label id;



    public void initialize(){
        // static class
        IconHelper.setIcon(viewSectionButton, "EYE_SHOW_20");
        IconHelper.setIcon(deleteSectionButton, "DELETE_20");

        IconHelper.setIcon(editSectionButton,
                "LINK_EDIT_20");

    }

    @FXML
    public void seeSection() throws IOException{

        try {
            // load the fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("section-view.fxml"));
            // render the file - Parent can contain any pane or node
            Parent root = loader.load() ;
            // getting the controller of view-Section-view
            ViewSectionViewController controller = loader.getController();

            // this method will fill the data in the header
            controller.data(this.id.getText() , this.titleLabel.getText());
            // this method will fetch data depending on section id
            controller.fetchSections( Long.parseLong(this.id.getText()));
            //
            HelloController.getInstance().setView(root);
            System.out.println("clicked");
        }catch (Exception e){
            System.err.println("============================= error =============================");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }



    @FXML
    //? used to fill data to the card
    public void data(Section section){
        titleLabel.setText(section.title);
        descriptionLabel.setText(section.description);
        id.setText(section.id.toString());
    }


    @FXML
    public void deleteSection() throws IOException {

        Long id =  Long.parseLong(this.id.getText()) ;
        Long parentId =  null ;


        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Section section = session.get(Section.class, id);

            if (section.parent != null) {

                parentId = section.parent.id ;
                section.parent.children.remove(section);

                section.parent = null ;

                session.delete(section);
                session.getTransaction().commit();

                AlertsHelpers.showDeleteSuccess();
                ViewSectionViewController.controller.fetchSections(parentId);
                return;

            }

            session.remove(section);
            session.getTransaction().commit();
            AlertsHelpers.showDeleteSuccess();
            SectionViewController.section.initialize();






        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("عمليه فاشله");
            alert.setHeaderText("فشل الحذف");
            alert.setContentText("فشل الحذف حاول مره اخرى" + e.getMessage());
            alert.showAndWait();

            System.err.println("================================= error =================================");
            System.out.println( "deleting error : " + e.getMessage());
        }

    }



    public  void editSection() throws IOException{


        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Section section = session.get(Section.class, Long.parseLong(this.id.getText()) );

            // load the fxml file
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("inputPopUp.fxml"));
            Parent parent = fxmlLoader.load();

            editSectionPopUpController controller = fxmlLoader.getController();
            controller.data( section.id ,  section.title , section.description , section.imagePath);

            HelloController.getInstance().contentVbox.getChildren().add(parent);
            var animation = Animations.slideInUp(parent , Duration.seconds(0.3 )) ;
            animation.playFromStart();

        }




    }


}
