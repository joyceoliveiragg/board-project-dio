package br.com.dio.persistence.config;

import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class ConnectionConfig {

    public static Connection getConnection() throws SQLException {
        var url = "jdbc:postgresql://board_postgres:5432/boarddb";
        var user = "postgres";
        var password = "postgres";

        System.out.println("[DEBUG] Tentando conectar ao banco de dados PostgreSQL...");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SQLException("Thread de conexão foi interrompida", e);
        }

        var connection = DriverManager.getConnection(url, user, password);
        connection.setAutoCommit(false);
        System.out.println("[DEBUG] Conexão estabelecida com sucesso: " + connection);

        return connection;
    }
}
