package com.librarysaver.librarysaver.helpers;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertsHelpers {

    // 1 - تنبيه الحقول الفارغة (Warning)
    public static void showEmptyFieldsAlert() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("تنبيه");
        alert.setHeaderText(null);
        alert.setContentText("يرجى ملء جميع الحقول المطلوبة!");
        alert.showAndWait();
    }

    // 2 - تنبيه تم الحفظ (Information)
    public static void showSaveSuccess() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("نجاح العملية");
        alert.setHeaderText(null);
        alert.setContentText("تم حفظ البيانات بنجاح.");
        alert.showAndWait();
    }

    // 3 - تنبيه تم التعديل (Information)
    public static void showUpdateSuccess() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("نجاح العملية");
        alert.setHeaderText(null);
        alert.setContentText("تم تعديل البيانات بنجاح.");
        alert.showAndWait();
    }

    // 4 - تنبيه تم الحذف (Information)
    public static void showDeleteSuccess() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("نجاح العملية");
        alert.setHeaderText(null);
        alert.setContentText("تم حذف البيانات بنجاح.");
        alert.showAndWait();
    }
}