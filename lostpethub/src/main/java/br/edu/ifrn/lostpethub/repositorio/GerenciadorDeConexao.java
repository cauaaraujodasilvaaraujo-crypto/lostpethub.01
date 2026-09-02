package br.edu.ifrn.lostpethub.repositorio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe de Infraestrutura responsável pelo gerenciamento da conexão com o banco MySQL.
 * Centraliza as configurações de URL, Usuário e Senha (Princípio SoC).
 */
public class GerenciadorDeConexao {

    private static final String URL = "jdbc:mysql://localhost:3306/lostpethub_db?useTimezone=true&serverTimezone=UTC";
    private static final String USER = "root";     // Substitua pelo seu usuário do MySQL
    private static final String PASSWORD = "";     // Substitua pela sua senha do MySQL

    /**
     * Abre e retorna uma conexão ativa com o banco lostpethub_db.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
