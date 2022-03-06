package com.example.applicationrecruitment.controller;

import com.example.applicationrecruitment.models.AHP;
import com.example.applicationrecruitment.MainApplication;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;

public class DialogEditBasicsController {
    public VBox mainBox;
    public TextField headIdField;
    public TextField alternativesField;
    public Button okButton;
    public AnchorPane dialogRoot;
    private Stage dialogStage;
    private boolean okClicked = false;

    public void handleCancel(ActionEvent actionEvent) {
        dialogStage.close();
    }

    public void handleOK(ActionEvent actionEvent) {
        handleOK();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        dialogRoot.setOnKeyPressed((event) -> {
            if(event.getCode() == KeyCode.ENTER) {
                handleOK();
            }
        });
        headIdField.setText(MainApplication.AHP.headId);
        alternativesField.setText("");
        for( String alternative : MainApplication.AHP.alternatives ){
            alternativesField.setText(alternativesField.getText() + alternative + ", ");
        }
    }

    private void handleOK() {
        if( isInputValid() ){
            okClicked = true;
            dialogStage.close();
        }
    }

    private boolean isInputValid() {
        String headId = headIdField.getText();
        String[] alternatives = alternativesField.getText().split(",");
        for( int i=0; i<alternatives.length; i++ ){
            alternatives[i] = alternatives[i].trim();
        }
        String errorMessage = "";
        if( headId == null || headId.replaceAll(" ","").equals("") ){
            errorMessage += "No valid head Id!\n";
        }else{
            MainApplication.AHP.headId = headId;
        }
        if( alternatives.length < 2 ){
            errorMessage += "Not enough alternatives!\n";
        }else{
            for( String alternative : alternatives ){
                if( AHP.idOccupied(alternative)){
                    errorMessage += "Invalid alternatives!\n";
                    break;
                }
            }
            MainApplication.AHP.alternatives = Arrays.asList(alternatives);
            while (MainApplication.AHP.alternatives.remove(""));
        }
        return RootLayoutController.showErrorAlert(errorMessage,dialogStage);
    }

    public boolean isOkClicked() {
        return okClicked;
    }
}
