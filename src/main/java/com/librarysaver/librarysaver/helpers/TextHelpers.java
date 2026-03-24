package com.librarysaver.librarysaver.helpers;

import javafx.scene.control.TextInputControl;

public class TextHelpers {

    public static boolean fieldsNotEmpty(TextInputControl ... fields){
        boolean result = true;

        for (TextInputControl field : fields){
            if(field.getText().isEmpty()){
                result = false;
            }
        }

        return result;

    }

}
