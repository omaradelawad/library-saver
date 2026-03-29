package com.librarysaver.librarysaver;
import atlantafx.base.util.Animations;
import com.librarysaver.librarysaver.entities.HibernateUtil;
import com.librarysaver.librarysaver.entities.Link;
import com.librarysaver.librarysaver.entities.Section;
import com.librarysaver.librarysaver.helpers.AlertsHelpers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import org.hibernate.Session;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ViewSectionViewController   {

    public static ViewSectionViewController controller;

    public ViewSectionViewController(){
        controller = this;
    }

    @FXML
    private HBox subSectionsContainer;


    @FXML
    private ScrollPane mainPane;

    @FXML
    Label sectionId;

    @FXML
    ScrollPane cardsPane ;

    @FXML
    Label sectionName;

    @FXML
    private Button refresh;

    @FXML
    private Button addNewSection;

    @FXML
    private TextField sectionTitle;

    @FXML
    TextField  description;

    @FXML
    TextField src;


    // link fields
    @FXML
    TextField linkTitle;

    @FXML
    TextField linkDescription;

    @FXML
    TextField hyperlink;

    @FXML
    Button saveLink ;

// ====================== table ======================
    @FXML
    TableView<Link> contentTable ;
    @FXML
    TableColumn<Link,Integer> numberColumn;
    @FXML
    TableColumn<Link,String> titleColumn;
    @FXML
    TableColumn<Link,String> descriptionColumn;
    @FXML
    TableColumn<Link,String> linkColumn;
// ====================== table ======================


    private ObservableList<Link> dataList = FXCollections.observableArrayList();


    // binding the columns cells with DTO fields , id column cell will be filled with this value -> link.getId() and same for other columns
    public void dataBinding(){
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        linkColumn.setCellValueFactory(new PropertyValueFactory<>("link"));

        contentTable.setItems(dataList);
    }



    public  void initialize()  {
        dataBinding();
        cardsPane.setPannable(true);
        cardsPane.setCursor(Cursor.DEFAULT);
    }

    // Setting this section brief data
    public  void data(String id , String sectionName ){
        sectionId.setText( id.toString() );
        this.sectionName.setText(sectionName);
    }



    public void fetchSections(Long id) {
        subSectionsContainer.getChildren().clear();
        dataList.clear();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Section currentSection = session.get(Section.class, id);

            if (currentSection != null) {
                // this method will fill the subsections cards
                fetchSubSections(currentSection.children);
                // this method will fill the table rows
                fetchTableData(currentSection.links);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    // same method but with string id
    public void fetchSections(String idString) {
        Long id = Long.parseLong(idString);
        fetchSections(id);
    }


    // i have created the below methods to update ui safely without recursion => fetchTable calls fetchUi  that calls fetchTable and so

    // this method takes a list of childes section of this current section and put them in the container
    public  void fetchSubSections(List<Section> children){
        for (Section s : children) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("card.fxml"));
            try {
                Parent cardView = loader.load();
                CardsController cardController = loader.getController();
                cardController.data(s);

                //* إضافة الكارت للواجهة
                subSectionsContainer.getChildren().add(cardView);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // this method takes a list of links section of this current section and  put them into the data list to display them into the table
    public void fetchTableData(List<Link> links){

        try {
            dataList.addAll(links);
        }catch (Exception e){
            e.printStackTrace();
        }


    }


    //* this method will save the link and call the fetchSection that calls fetchSubSections to put the cards into the container and  to put the data into the table
    @FXML
    public void addNewSection(){

        if (sectionTitle.getText().isEmpty() || description.getText().isEmpty() || src.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("تحذير");
            alert.setHeaderText("حقول فارغه");
            alert.setContentText("املا الحقول");
            alert.showAndWait();
            return;
        }

        Long currentSectionId = Long.valueOf(sectionId.getText());
        Section currentSection;

        String query = "FROM Section s WHERE s.id = " + currentSectionId;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            currentSection =  session.createQuery(query , Section.class).uniqueResult() ;
            Section section = new Section(sectionTitle.getText(), description.getText(), src.getText());
            section.parent = currentSection;
            session.persist(section);
            session.getTransaction().commit();

            alert.setTitle("عمليه ناجحة");
            alert.setHeaderText("تم الحفظ");
            alert.setContentText("تم الحفظ بنجاخ");
            alert.showAndWait();
            fetchSections(sectionId.getText());
        }catch (Exception e){
            e.printStackTrace();
        }



    }


    //* this method will save the link and call the fetchSection that calls fetchTableData to put the data into the table and cards into container
    @FXML
    public void saveLink(){

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Section currentSection = session.get(Section.class, sectionId.getText());
            var link = new Link(linkTitle.getText(), linkDescription.getText() , hyperlink.getText());
            link.section =  currentSection;
            session.persist(link);
            session.getTransaction().commit();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("عمليه ناجحة");
            alert.setHeaderText("تم الحفظ");
            alert.setContentText("تم الحفظ بنجاخ");

            alert.showAndWait();

            fetchSections( Long.parseLong(sectionId.getText()));
        }

    }


    public  void editLinkView() throws IOException{


        Link selctedlink = contentTable.getSelectionModel().getSelectedItem();

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Link link = session.get(Link.class, selctedlink.getId() );

            // load the fxml file
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("LinkinputPopUp.fxml"));
            Parent parent = fxmlLoader.load();

            EditLinkPopUpController controller = fxmlLoader.getController();
            controller.data(link.id ,  link.title , link.description , link.link );

            HelloController.getInstance().contentVbox.getChildren().add(parent);
            var animation = Animations.slideInUp(parent , Duration.seconds(0.3 )) ;
            animation.playFromStart();


        }catch (NullPointerException e  ){
            AlertsHelpers.showError("حدد صف اولا");
//            e.printStackTrace();

        }




    }

    public void deleteLink(){

        Link selctedlink = contentTable.getSelectionModel().getSelectedItem();

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Link link = session.get(Link.class, selctedlink.getId() );
            session.delete(link);
            session.getTransaction().commit();

            fetchSections( Long.parseLong(sectionId.getText()) );

        }catch (NullPointerException e  ){
            AlertsHelpers.showError("حدد صف اولا");
        }

    }


    // copy the link from selected row from the table
    @FXML
    public void copyLink(){


        try {
            var clipboard = Clipboard.getSystemClipboard();

            var clipboardContent = new ClipboardContent() ;

            Link selectedLink = contentTable.getSelectionModel().getSelectedItem();

            clipboardContent.putString(selectedLink.link) ;

            clipboard.setContent(clipboardContent);
        }catch (NullPointerException e){
            AlertsHelpers.showError("حدد صف اولا");
        }




    }

}
