package org.example;

import org.example.API;
import org.example.Database;
import org.example.Pokemon;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Database db = null;
        try{
            db = Database.getInstance();
        } catch (SQLException e) {
            System.err.println("Errore nella connessione al database");
            System.exit(-1);
        }
        API api = new API();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci pokemon: ");
        Pokemon pokemon = api.getPokemon(scanner.nextLine());
        if (pokemon == null) System.exit(-1);
        db.insert(pokemon);

    }
}
