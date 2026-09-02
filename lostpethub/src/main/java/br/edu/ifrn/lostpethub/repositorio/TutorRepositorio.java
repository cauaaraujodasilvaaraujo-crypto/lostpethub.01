package br.edu.ifrn.lostpethub.repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifrn.lostpethub.modelo.Tutor;

public class TutorRepositorio {

    private Connection getConnection() throws SQLException {
        return GerenciadorDeConexao.getConnection();
    }

    // [C] - INSERIR TUTOR (RF.001)
    public void inserir(Tutor tutor) {
        String sql = "INSERT INTO tutor (nome, telefone, email) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, tutor.getNome());
            stmt.setString(2, tutor.getTelefone());
            stmt.setString(3, tutor.getEmail());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    tutor.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir tutor no MySQL", e);
        }
    }

    // [R] - SELECIONAR POR ID
    public Tutor selecionarPorId(Long id) {
        String sql = "SELECT * FROM tutor WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearTutor(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar tutor por ID", e);
        }
        return null;
    }

    // [R] - BUSCAR POR E-MAIL (Validação de unicidade - RF.001)
    public Tutor buscarPorEmail(String email) {
        String sql = "SELECT * FROM tutor WHERE LOWER(email) = LOWER(?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearTutor(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar tutor por e-mail", e);
        }
        return null;
    }

    // [R] - SELECIONAR TODOS
    public List<Tutor> selecionarTodos() {
        List<Tutor> tutores = new ArrayList<>();
        String sql = "SELECT * FROM tutor";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tutores.add(mapearTutor(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos os tutores", e);
        }
        return tutores;
    }

    // [U] - ATUALIZAR TUTOR
    public void atualizar(Tutor tutor) {
        String sql = "UPDATE tutor SET nome = ?, telefone = ?, email = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tutor.getNome());
            stmt.setString(2, tutor.getTelefone());
            stmt.setString(3, tutor.getEmail());
            stmt.setLong(4, tutor.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar tutor no MySQL", e);
        }
    }

    // [D] - EXCLUIR TUTOR
    public void excluir(Long id) {
        String sql = "DELETE FROM tutor WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir tutor do MySQL", e);
        }
    }

    private Tutor mapearTutor(ResultSet rs) throws SQLException {
        Tutor tutor = new Tutor();
        tutor.setId(rs.getLong("id"));
        tutor.setNome(rs.getString("nome"));
        tutor.setTelefone(rs.getString("telefone"));
        tutor.setEmail(rs.getString("email"));
        return tutor;
    }
}
