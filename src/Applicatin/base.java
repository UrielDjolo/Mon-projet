package Applicatin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class base {

    static Connection connexion;

    public static void dd() {
        String url = "jdbc:mysql://ls-0f19f4268096a452a869b6f8467bc299c51da519.cz6cgwgke8xd.eu-west-3.rds.amazonaws.com/db0072769";
        String utilisateur = "user0072769";
        String motDePasse = "Yf3IgyBsOPa34WR";

        try {
            connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
            if (connexion != null) {
                System.out.println("Connexion à la base de données réussie !");
            }
        } catch (SQLException e) {
            System.err.println("Échec de la connexion : " + e.getMessage());
        }
    }

    public static String getResultat(String matricule) {
        String resultat = "";
        String query = "SELECT resultat FROM etudiants WHERE matricule = ?";

        try (PreparedStatement stmt = connexion.prepareStatement(query)) {
            stmt.setString(1, matricule);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                resultat = rs.getString("resultat");
            } else {
                resultat = "Matricule non trouvé.";
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la requête : " + e.getMessage());
            resultat = "Erreur lors de la requête.";
        }
        return resultat;
    }

    public static String getDetails(String matricule) {
        String details = "";
        String query = "SELECT nom,prenom, matricule,moyenne,ecole,resultat,date_naissance FROM etudiants WHERE matricule = ?";

        try (PreparedStatement stmt = connexion.prepareStatement(query)) {
            stmt.setString(1, matricule);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                details =  "Matricule: " + rs.getString("matricule") +
                		   "\nNom: " + rs.getString("nom") +
                          "\nPrénom: " + rs.getString("prenom") +
                          "\nDate de naissance: " + rs.getString("date_naissance") +
                          "\nEcole: " + rs.getString("ecole") +
                          "\nMoyenne: " + rs.getString("moyenne") +
                          "\nDécision: " + rs.getString("resultat");
            } else {
                details = "Matricule non trouvé.";
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la requête : " + e.getMessage());
            details = "Erreur lors de la requête.";
        }
        return details;
    }
}
