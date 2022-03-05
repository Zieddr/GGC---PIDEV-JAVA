/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.Commande.PasserCommandeController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import GUI.Forum.ForumHomeGUIController;
import GUI.Shop.AffichageProduitClientController;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Spenz
 */
public class DashboardController implements Initializable {

    int idClient;
    //int idClient = 111;
    public static AnchorPane parentClient;

    @FXML
    private Button btnForum;
    @FXML
    private Button btnShop;
    @FXML
    private AnchorPane homeClient;
    @FXML
    private Button btnCommande;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        parentClient = homeClient;
        btnForum.setOnAction(a -> {
            try {
                FXMLLoader forumPage = new FXMLLoader(getClass().getResource("Forum/ForumHomeGUI.fxml"));
                ForumHomeGUIController fhc = new ForumHomeGUIController(idClient);
                forumPage.setController(fhc);
                Parent root = forumPage.load();

                refreshParent(root);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        });
        
        btnShop.setOnAction(a -> {
            try {

                FXMLLoader shopPage = new FXMLLoader(getClass().getResource("Shop/AffichageProduitClient.fxml"));
                AffichageProduitClientController fhc = new AffichageProduitClientController(idClient);
                shopPage.setController(fhc);
                Parent root = shopPage.load();
                homeClient.getChildren().clear();
                homeClient.getChildren().add(root);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        });
        btnCommande.setOnAction(a -> {
            try {

                FXMLLoader cmdPage = new FXMLLoader(getClass().getResource("Commande/PasserCommande.fxml"));
                PasserCommandeController fhc = new PasserCommandeController(idClient);
                cmdPage.setController(fhc);
                Parent root = cmdPage.load();
                homeClient.getChildren().clear();
                homeClient.getChildren().add(root);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }

    public static void refreshParent(Parent root) {
        parentClient.getChildren().clear();
        parentClient.getChildren().add(root);
        Stage stg=((Stage) parentClient.getScene().getWindow());
        stg.sizeToScene();
    }
}
