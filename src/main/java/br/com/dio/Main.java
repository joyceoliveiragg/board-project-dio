package br.com.dio;

import br.com.dio.persistence.config.ConnectionConfig;
import br.com.dio.persistence.migration.MigrationStrategy;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Connection connection = ConnectionConfig.getConnection();
            new MigrationStrategy(connection).executeMigration();
        } catch (Exception e) {
            System.err.println("Migration failed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}