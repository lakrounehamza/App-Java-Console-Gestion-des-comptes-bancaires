package services;

import models.ConnectionBase;
import models.Operation;
import models.Retrait;
import models.Versement;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperationService {
    public ArrayList<Operation> getOperationByCodeCompte(String code) {
        ArrayList<Operation> list = new ArrayList<>();
        try {
            Connection connection = ConnectionBase.getInstance().getConnection();
            Statement stmt = connection.createStatement();
            String query = "select o.numero, o.montant, o.dateOperation, r.distination from operations o join retrait r on r.id = o.numero where o.code = '" + code + "'";
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                Retrait retrait = new Retrait(
                        res.getInt("numero"),
                        res.getDouble("montant"),
                        res.getDate("dateOperation"),
                        res.getString("distination")
                );
                list.add(retrait);
            }
            String sqlVersement = "select o.numero, o.montant, o.dateOperation, r.source  from operations o join versement r ON r.id = o.numero where o.code = '" + code + "'";
            res = stmt.executeQuery(sqlVersement);
            while (res.next()) {
                Versement versement = new Versement(
                        res.getInt("numero"),
                        res.getDouble("montant"),
                        res.getDate("dateOperation"),
                        res.getString("source")
                );
                list.add(versement);
            }
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return list;
    }

    public void createRetrait(String distination , String code, double montant) {
        int numero = 0;
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(code);
        while (matcher.find()) {
            numero = Integer.parseInt(matcher.group());
        }

        try {
            Connection connection = ConnectionBase.getInstance().getConnection();
            Statement stmt = connection.createStatement();
            stmt.executeQuery("insert  into   retrait  values(" + numero + "," + distination + ")");
            stmt.executeQuery("insert  into  operations values(" + numero + "," + code + "," + montant + "," + formattedDate + ")");
            stmt.close();
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
    }

    public void createVersement(String source , String code, double montant) {
        int numero = 0;
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(code);
        while (matcher.find()) {
            numero = Integer.parseInt(matcher.group());
        }
        try {
            Connection connection = ConnectionBase.getInstance().getConnection();
            Statement stmt = connection.createStatement();
            stmt.executeQuery("insert  into   versement  values(" + numero + "," + source + ")");
            stmt.executeQuery("insert  into  operations values(" + numero + "," + code + "," + montant + "," + formattedDate + ")");
            stmt.close();
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
    }
}
