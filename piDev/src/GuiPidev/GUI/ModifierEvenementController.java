/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiPidev.GUI;

import Entities.Evenement;
import Services.EvenementCrud;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Azer Lahmer
 */
public class ModifierEvenementController implements Initializable {

    @FXML
    private TextField tfReference;

    @FXML
    private DatePicker tfDateDebut;
    @FXML
    private DatePicker tfDateFin;


    /**
     * Initializes the controller class.
     */
    
    int reference = 1;
    EvenementCrud ec = new EvenementCrud();
    @FXML
    private TextField tfLocalisation;
    @FXML
    private TextField tfNbrParticipants;
    @FXML
    private TextField tfDescription;
    @FXML
    private Button btnModifier;
    
    Evenement e;
    
    
    
//    public void setEvenement(Evenement e) {
//        this.e = e;
//    } 
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        e = ec.afficherEvenement(reference);
        tfReference.setText(e.getReference()+"");
        tfReference.setEditable(false);
        tfReference.setDisable(true);
        
        tfDateDebut.setValue(e.getDateDebut().toLocalDate());
        tfDateFin.setValue(e.getDateFin().toLocalDate());
        
        tfLocalisation.setText(e.getLocalisation());
        tfDescription.setText(e.getDescription());
        tfNbrParticipants.setText(e.getNbrParticipant()+"");
        
    }

    @FXML
    private void modifierEvenement(ActionEvent event) {
         if (tfReference.getText().isEmpty() || tfLocalisation.getText().isEmpty()|| tfDescription.getText().isEmpty()|| tfNbrParticipants.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setHeaderText(null);
            alert.setContentText(" Champ vide!");
            alert.show();
        } else {
        e.setDateDebut(java.sql.Date.valueOf(tfDateDebut.getValue()));
        e.setDateFin(java.sql.Date.valueOf(tfDateFin.getValue()));
        e.setLocalisation(tfLocalisation.getText());
        e.setDescription(tfDescription.getText());
        e.setNbrParticipant(Integer.parseInt(tfNbrParticipants.getText()));
        ec.modifierEvenement(e);
         Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Succesful");
            alert.setHeaderText(null);
            alert.setContentText(" Evenement modifié avec succéez!");
            alert.show();
        ((Stage) btnModifier.getScene().getWindow()).close();
    }}

    public void setReference(int reference) {
        this.reference = reference;
    }

    
    
}

