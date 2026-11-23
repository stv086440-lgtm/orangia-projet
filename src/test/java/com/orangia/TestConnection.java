package com.orangia;

import java.sql.Connection;
import com.orangia.database.DatabaseConnection;


public class TestConnection {

    public static void main(String[] args) {

        try (Connection connection =
                    DatabaseConnection.getConnection()) {

            System.out.println("✅ Connexion PostgreSQL réussie");

            System.out.println(
                    connection.getMetaData().getDatabaseProductName()
            );

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}