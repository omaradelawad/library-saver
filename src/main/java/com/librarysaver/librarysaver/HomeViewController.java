package com.librarysaver.librarysaver;

import com.librarysaver.librarysaver.helpers.IconHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeViewController {

    @FXML
    Button BTN1;
    @FXML
    Button BTN2;
    @FXML
    Button BTN3;

    public void initialize(){
        IconHelper.setIcon(BTN1 , "ARROW_HOOK_UP_RIGHT_20");
        IconHelper.setIcon(BTN2 , "ARROW_HOOK_UP_RIGHT_20");
    }

}
