package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static Database instance;
    private Connection connection;

    private Database() throws SQLException {
        // Punta direttamente a pokemon.db nella root del progetto (stesso file del pannello Database)
        String url = "jdbc:sqlite:pokemon.db";
        connection = DriverManager.getConnection(url);
        System.out.println("Connected to database");
        creaTabella();
    }

    private void creaTabella() throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS pokemon (
                    id INTEGER PRIMARY KEY,
                    nome TEXT NOT NULL,
                    tipo1 TEXT,
                    tipo2 TEXT
                );
                """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public static Database getInstance() throws SQLException {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public void insert(Pokemon pokemon) {
        try {
            if (connection == null || !connection.isValid(5)) {
                return;
            }
        } catch (SQLException e) {
            System.err.println("Errore nella connessione al database: " + e.getMessage());
            return;
        }

        String query = "INSERT OR REPLACE INTO pokemon(id, nome, tipo1, tipo2, hp, abilita) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, pokemon.id);
            stmt.setString(2, pokemon.name);
            stmt.setString(3, !pokemon.tipo1.isEmpty() ? pokemon.tipo1.get(0).getType().getName() : null);
            stmt.setString(4, pokemon.tipo2.size() > 1 ? pokemon.tipo2.get(1).getType().getName() : null);
            stmt.setInt(5, pokemon.HP);
            stmt.setString(6, pokemon.Abilta);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore nella query: " + e.getMessage());
        }
    }
}