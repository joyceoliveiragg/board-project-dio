package br.com.dio.persistence.migration;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;

@AllArgsConstructor
public class MigrationStrategy {
    private static final Logger logger = LoggerFactory.getLogger(MigrationStrategy.class);
    private final Connection connection;

    public void executeMigration() throws LiquibaseException, IOException {
        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;

        try (FileOutputStream fos = new FileOutputStream("liquibase.log");
             PrintStream logStream = new PrintStream(fos)) {

            System.setOut(logStream);
            System.setErr(logStream);

            JdbcConnection jdbcConnection = new JdbcConnection(connection);
            Liquibase liquibase = new Liquibase(
                    "db/changelog/db.changelog-master.yml",
                    new ClassLoaderResourceAccessor(),
                    jdbcConnection);

            liquibase.update();

        } catch (DatabaseException e) {
            logger.error("Database migration failed", e);
            throw e;
        } catch (IOException e) {
            logger.error("Failed to set up migration logging", e);
            throw e;
        } finally {
            System.setOut(originalOut);
            System.setErr(originalErr);
        }
    }
}