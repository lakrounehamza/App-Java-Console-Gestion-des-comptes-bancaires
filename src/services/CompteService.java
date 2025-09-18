package services;

import models.Compte;
import models.CompteCourant;
import models.CompteEpargne;
import models.ConnectionBase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

            stmt.close();
//            System.out.println("Database connected avec succès !");
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        } finally {
            System.out.println("Fin du traitement.");
        }
        return listeCompt;
    }

    public void createCompte(String typeCompt, double value) {
        ArrayList<String> listInterdte = new ArrayList<>();
        Random rand = new Random();
        String newCode;
        try (BufferedReader reader = new BufferedReader(new FileReader("dataFichier"))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                listInterdte.add(ligne);
            }
            while (true) {
                newCode = "CPT-" + String.format("%05d", rand.nextInt(100000));
                if (!listInterdte.contains(newCode))
                    break;
            }
            String query = "";
            if (typeCompt == "compte Epargne") {
                CompteEpargne compt = new CompteEpargne(newCode, value);
                query = "insert  into   CompteEpargne  values  (" + compt.getCode() + "," + compt.getTauxInteret() + ")";
            } else {
                CompteCourant compt = new CompteCourant(newCode, value);
                query ="insert into  CompteCourant values (" +compt.getCode()+","+compt.getDecouvert()+");";
            }
            try {
                Connection connection = ConnectionBase.getInstance().getConnection();
                Statement stmt = connection.createStatement();

                stmt.executeQuery("inset  into  comptes  values(" + newCode + "," + "0");
                stmt.executeQuery(query);
                stmt.close();

            } catch (SQLException e) {
                System.out.print(e.getMessage());
            }

        } catch (IOException e) {
            System.out.print("error" + e.getMessage());
        }
    }

    public void upadateSolde(String codeCompte, double newSolde) {
        Connection connection = ConnectionBase.getInstance().getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("update  comptes   set  sode = ? where code  =?");
            stmt.setDouble(1, newSolde);
            stmt.setString(2, codeCompte);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.print(e.getMessage());

        }

    }
}

