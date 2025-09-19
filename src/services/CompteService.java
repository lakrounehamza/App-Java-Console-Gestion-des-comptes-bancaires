package services;

import models.Compte;
import models.CompteCourant;
import models.CompteEpargne;
import models.ConnectionBase;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class CompteService {
    public HashMap<String, Compte> getAllCompt() {
        HashMap<String, Compte> listeCompt = new HashMap<>();
        try {
            Connection connection = ConnectionBase.getInstance().getConnection();
            String request1 = "select c.code, c.solde, e.decouvert from comptes c join comptecourants e on c.code = e.code;";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(request1);
            while (rs.next()) {
                String code = rs.getString("code");
                double solde = rs.getDouble("solde");
                double decouvert = rs.getDouble("decouvert");

                CompteCourant compte = new CompteCourant(code);
                compte.setSolde(solde);
                compte.setDecouvert(decouvert);

                listeCompt.put(code, compte);
            }
            String request2 = "select c.code, c.solde, e.tauxInteret from comptes c join compteepargne e on c.code = e.code;";
            ResultSet rs2 = stmt.executeQuery(request2);

            while (rs2.next()) {
                String code = rs2.getString("code");
                double solde = rs2.getDouble("solde");
                double tauxInteret = rs2.getDouble("tauxInteret");

                CompteEpargne compte = new CompteEpargne(code, tauxInteret);
                compte.setSolde(solde);

                listeCompt.put(code, compte);
            }

//            System.out.println("Database connected avec succès !");
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        } finally {
            System.out.println("Fin du traitement.");
        }
        return listeCompt;
    }

    public String createCompte(String typeCompt, double value) {
        ArrayList<String> listInterdite = new ArrayList<>();
        Random rand = new Random();
        String newCode ="";

        try (BufferedReader reader = new BufferedReader(new FileReader("dataFichier.txt"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                listInterdite.add(ligne.trim());
            }
//            reader.close();
            while (true) {
                newCode = "CPT-" + String.format("%05d", rand.nextInt(100000));
                if (!listInterdite.contains(newCode)) {
                    break;
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("dataFichier.txt", true))) {
                writer.write(newCode);
                writer.newLine();
//                writer.close();
            }

            String query;
            if ("compte Epargne".equals(typeCompt)) {
                CompteEpargne compt = new CompteEpargne(newCode, value);
                query = "CALL insertCompteEpargne('" + newCode + "',"+ 0.00+"," + value + " );";
                try (Connection connection = ConnectionBase.getInstance().getConnection();
                     Statement stmt = connection.createStatement()) {
                    stmt.executeUpdate(query);

                } catch (SQLException e) {
                    System.out.print("SQL Error: !!!! " + e.getMessage());
                }
             } else {
                CompteCourant compt = new CompteCourant(newCode, value);
                query = "CALL insertCompteCourant('" + newCode + "',"+ 0.00+"," + value + ");";
                try (Connection connection = ConnectionBase.getInstance().getConnection();
                     Statement stmt = connection.createStatement()) {
                    stmt.executeUpdate(query);
                    stmt.close();
                } catch (SQLException e) {
                    System.out.print("SQL Error: !!!! " + e.getMessage());
                }
             }

        } catch (IOException e) {
            System.out.print("IO Error:  " + e.getMessage());
        }
        return  newCode;
    }

    public void upadateSolde(String codeCompte, double newSolde) {
        Connection connection = ConnectionBase.getInstance().getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("update  comptes   set  solde = ? where code  =?");
            stmt.setDouble(1, newSolde);
            stmt.setString(2, codeCompte);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.print(e.getMessage());

        }

    }
}

