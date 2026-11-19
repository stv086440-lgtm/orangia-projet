package com.orangia.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseTest {

    public static void main(String[] args) {

        String sql = "SELECT * FROM roles";

        try (
                Connection connection = DatabaseConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(sql)) {

            System.out.println("Connexion réussie !");
            System.out.println("Liste des rôles :");

            while (result.next()) {
                System.out.println(
                        result.getInt("id") + " - " +
                                result.getString("nom"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}