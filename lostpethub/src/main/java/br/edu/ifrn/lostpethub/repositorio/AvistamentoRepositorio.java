package br.edu.ifrn.lostpethub.repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifrn.lostpethub.modelo.Avistamento;

public class AvistamentoRepositorio {

    private Connection getConnection() throws SQLException {
        return GerenciadorDeConexao.getConnection();
    }

    // [C] - INSERIR AVISTAMENTO (RF.004)
    public void inserir(Avistamento avistamento) {
        String sql = "INSERT INTO avistamento (pet_id, localizacao, ponto_referencia, data_hora, observacoes) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, avistamento.getPetId());
            stmt.setString(2, avistamento.getLocalizacao());
            stmt.setString(3, avistamento.getPontoReferencia());
            stmt.setTimestamp(4, Timestamp.valueOf(avistamento.getDataHora()));
            stmt.setString(5, avistamento.getObservacoes());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    avistamento.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir avistamento no MySQL", e);
        }
    }

    // [R] - SELECIONAR POR ID
    public Avistamento selecionarPorId(Long id) {
        String sql = "SELECT * FROM avistamento WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearAvistamento(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar avistamento por ID", e);
        }
        return null;
    }

    // [R] - SELECIONAR AVISTAMENTOS POR PET (RF.004 - Histórico cronológico)
    public List<Avistamento> selecionarPorPet(Long petId) {
        List<Avistamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM avistamento WHERE pet_id = ? ORDER BY data_hora DESC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, petId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearAvistamento(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar avistamentos por pet", e);
        }
        return lista;
    }

    // [R] - SELECIONAR TODOS
    public List<Avistamento> selecionarTodos() {
        List<Avistamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM avistamento ORDER BY data_hora DESC";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearAvistamento(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos os avistamentos", e);
        }
        return lista;
    }

    // [D] - EXCLUIR AVISTAMENTO
    public void excluir(Long id) {
        String sql = "DELETE FROM avistamento WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir avistamento do MySQL", e);
        }
    }

    private Avistamento mapearAvistamento(ResultSet rs) throws SQLException {
        Avistamento av = new Avistamento();
        av.setId(rs.getLong("id"));
        av.setPetId(rs.getLong("pet_id"));
        av.setLocalizacao(rs.getString("localizacao"));
        av.setPontoReferencia(rs.getString("ponto_referencia"));
        Timestamp ts = rs.getTimestamp("data_hora");
        if (ts != null) {
            av.setDataHora(ts.toLocalDateTime());
        }
        av.setObservacoes(rs.getString("observacoes"));
        return av;
    }
}
