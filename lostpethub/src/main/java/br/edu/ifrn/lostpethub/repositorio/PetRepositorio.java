package br.edu.ifrn.lostpethub.repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifrn.lostpethub.modelo.Pet;
import br.edu.ifrn.lostpethub.modelo.StatusPet;

public class PetRepositorio {

    private Connection getConnection() throws SQLException {
        return GerenciadorDeConexao.getConnection();
    }

    // [C] - INSERIR PET (RF.002)
    public void inserir(Pet pet) {
        String sql = "INSERT INTO pet (nome, especie, raca, cor, status, tutor_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, pet.getNome());
            stmt.setString(2, pet.getEspecie());
            stmt.setString(3, pet.getRaca());
            stmt.setString(4, pet.getCor());
            stmt.setString(5, pet.getStatus() != null ? pet.getStatus().name() : StatusPet.COM_TUTOR.name());
            stmt.setLong(6, pet.getTutorId());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pet.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir pet no MySQL", e);
        }
    }

    // [R] - SELECIONAR POR ID
    public Pet selecionarPorId(Long id) {
        String sql = "SELECT * FROM pet WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearPet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pet por ID", e);
        }
        return null;
    }

    // [R] - SELECIONAR TODOS OS PETS
    public List<Pet> selecionarTodos() {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM pet";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                pets.add(mapearPet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos os pets", e);
        }
        return pets;
    }

    // [R] - SELECIONAR PETS POR TUTOR (RF.002)
    public List<Pet> selecionarPorTutor(Long tutorId) {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM pet WHERE tutor_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, tutorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pets.add(mapearPet(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pets por tutor", e);
        }
        return pets;
    }

    // [R] - FILTRAR PETS POR STATUS (RF.003 - ex: listar todos os animais PERDIDOS)
    public List<Pet> selecionarPorStatus(StatusPet status) {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM pet WHERE status = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.name());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pets.add(mapearPet(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao filtrar pets por status", e);
        }
        return pets;
    }

    // [R] - FILTRAR PETS POR ESPÉCIE
    public List<Pet> selecionarPorEspecie(String especie) {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM pet WHERE LOWER(especie) = LOWER(?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, especie);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pets.add(mapearPet(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pets por espécie", e);
        }
        return pets;
    }

    // [U] - ATUALIZAR DADOS DO PET
    public void atualizar(Pet pet) {
        String sql = "UPDATE pet SET nome = ?, especie = ?, raca = ?, cor = ?, status = ?, tutor_id = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pet.getNome());
            stmt.setString(2, pet.getEspecie());
            stmt.setString(3, pet.getRaca());
            stmt.setString(4, pet.getCor());
            stmt.setString(5, pet.getStatus().name());
            stmt.setLong(6, pet.getTutorId());
            stmt.setLong(7, pet.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar pet no MySQL", e);
        }
    }

    // [U] - ATUALIZAR APENAS STATUS DO PET (RF.003)
    public void atualizarStatus(Long petId, StatusPet status) {
        String sql = "UPDATE pet SET status = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.name());
            stmt.setLong(2, petId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar status do pet no MySQL", e);
        }
    }

    // [D] - EXCLUIR PET
    public void excluir(Long id) {
        String sql = "DELETE FROM pet WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir pet do MySQL", e);
        }
    }

    private Pet mapearPet(ResultSet rs) throws SQLException {
        Pet pet = new Pet();
        pet.setId(rs.getLong("id"));
        pet.setNome(rs.getString("nome"));
        pet.setEspecie(rs.getString("especie"));
        pet.setRaca(rs.getString("raca"));
        pet.setCor(rs.getString("cor"));
        String statusStr = rs.getString("status");
        if (statusStr != null) {
            try {
                pet.setStatus(StatusPet.valueOf(statusStr.toUpperCase()));
            } catch (IllegalArgumentException e) {
                pet.setStatus(StatusPet.COM_TUTOR);
            }
        }
        pet.setTutorId(rs.getLong("tutor_id"));
        return pet;
    }
}
