package br.com.dio.persistence.config;

import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class ConnectionConfig {

    public static Connection getConnection() throws SQLException {
        var url = "jdbc:postgresql://postgres-dio:5432/dio";
        var user = "board";
        var password = "1board23";

        System.out.println("[DEBUG] Tentando conectar ao banco de dados PostgreSQL...");
        var connection = DriverManager.getConnection(url, user, password);
        connection.setAutoCommit(false);
        System.out.println("[DEBUG] Conexão estabelecida com sucesso: " + connection);

        return connection;
    }
}
