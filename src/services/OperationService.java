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
            String query = "select o.numero, o.montant, o.dateOperation, r.destination from operations o join retrait r on r.numero = o.numero where o.code = '" + code + "'";
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                Retrait retrait = new Retrait(
                        res.getInt("numero"),
                        res.getDouble("montant"),
                        res.getDate("dateOperation"),
                        res.getString("destination")
                );
                System.out.print(retrait.toString());
                list.add(retrait);
            }
            String sqlVersement = "select o.numero, o.montant, o.dateOperation, r.source  from operations o join versement r ON r.numero = o.numero where o.code = '" + code + "'";
            res = stmt.executeQuery(sqlVersement);
            while (res.next()) {
                Versement versement = new Versement(
                        res.getInt("numero"),
                        res.getDouble("montant"),
                        res.getDate("dateOperation"),
                        res.getString("source")
                );
                System.out.print(versement.toString());
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
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
//        Pattern pattern = Pattern.compile("\\d+");
//        Matcher matcher = pattern.matcher(code);
//        while (matcher.find()) {
//            numero = Integer.parseInt(matcher.group());
//        }

        try {
            Connection connection = ConnectionBase.getInstance().getConnection();
            Statement stmt = connection.createStatement();
            String sql = "INSERT INTO operations (code, montant, dateOperation) " +
                    "VALUES ('" + code + "', " + montant + ", '" + formattedDate + "')";
            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            int numero2 = 0;
            if (rs.next()) {
                numero2 = rs.getInt(1);
            }
            String query = "INSERT INTO retrait (numero, destination) VALUES (" + numero2 + ", '" + distination + "')";
            stmt.executeUpdate(query);
            stmt.close();
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }

    }

    public void createVersement(String source, String code, double montant) {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        try {
            Connection connection = ConnectionBase.getInstance().getConnection();
            String sqlOp = "INSERT INTO operations (code, montant, dateOperation) VALUES (?, ?, ?)";
            PreparedStatement psOp = connection.prepareStatement(sqlOp, Statement.RETURN_GENERATED_KEYS);
            psOp.setString(1, code);
            psOp.setDouble(2, montant);
            psOp.setString(3, formattedDate);
            psOp.executeUpdate();
            ResultSet rs = psOp.getGeneratedKeys();
            int numero = 0;
            if (rs.next()) {
                numero = rs.getInt(1);
            }
            String sqlVers = "INSERT INTO versement (numero, source) VALUES (?, ?)";
            PreparedStatement psVers = connection.prepareStatement(sqlVers);
            psVers.setInt(1, numero);
            psVers.setString(2, source);
            psVers.executeUpdate();
            psOp.close();
            psVers.close();
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
    }
}
