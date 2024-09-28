package Applicatin;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ecran extends Application {

    @Override
    public void start(Stage stageline) {
        // Connexion à la base de données
        base.dd();

        // Création d'un en-tête
        Label headerLabel = new Label("Consultation des résultats");
        headerLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10;");

        // Création des éléments de l'interface utilisateur
        TextField matriculeField = new TextField();
        matriculeField.setPromptText("Entrez votre matricule");

        Button consultButton = new Button("Consulter le résultat");
        Label resultLabel = new Label();

     // Action à exécuter lorsque le bouton est cliqué
        consultButton.setOnAction(e -> {
            String matricule = matriculeField.getText();
            if (matricule.isEmpty()) {
                resultLabel.setText("Veuillez entrer un matricule.");
                resultLabel.getStyleClass().clear();
                resultLabel.getStyleClass().add("error"); // Ajoute la classe d'erreur
                return;
            }
            String resultat = base.getResultat(matricule); // Appel de la méthode getResultat
            resultLabel.setText(resultat);
            
            if (resultat.contains("succès")) { // Exemple de condition de succès
                resultLabel.getStyleClass().clear();
                resultLabel.getStyleClass().add("success"); // Ajoute la classe de succès
            } else {
                resultLabel.getStyleClass().clear();
                resultLabel.getStyleClass().add("error"); // Ajoute la classe d'erreur
            }
        });


        // Bouton pour afficher les détails
        Button detailsButton = new Button("Détails");
        detailsButton.setOnAction(e -> {
            String matricule = matriculeField.getText();
            if (matricule.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Attention");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez entrer un matricule avant de demander les détails.");
                alert.showAndWait();
                return;
            }
            String details = base.getDetails(matricule); // Appel de la méthode getDetails

            // Affichage des détails dans une alerte
            Alert detailsAlert = new Alert(Alert.AlertType.INFORMATION);
            detailsAlert.setTitle("Détails de l'étudiant");
            detailsAlert.setHeaderText("Informations sur l'étudiant");
            detailsAlert.setContentText(details);
            detailsAlert.showAndWait();
        });

        // Bouton pour fermer l'application
        Button closeButton = new Button("Fermer");
        closeButton.setOnAction(e -> stageline.close());

        // Création d'un layout vertical pour organiser les éléments
        VBox layout = new VBox(10, headerLabel, matriculeField, consultButton, resultLabel, detailsButton, closeButton);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Création de la scène et ajout au stage
        Scene scene = new Scene(layout, 300, 250);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stageline.setScene(scene);
        stageline.setTitle("Consultation des résultats d'examen");
        stageline.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
