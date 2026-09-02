package br.edu.ifrn.lostpethub.servico;

import java.util.List;

import br.edu.ifrn.lostpethub.modelo.Tutor;
import br.edu.ifrn.lostpethub.repositorio.TutorRepositorio;

/**
 * Camada de Serviço responsável pelas regras de negócio de Tutores (RF.001).
 */
public class TutorService {

    private final TutorRepositorio repositorio = new TutorRepositorio();

    /**
     * Cadastra um novo tutor aplicando as validações de RF.001.
     */
    public void cadastrarTutor(Tutor tutor) {
        if (tutor == null) {
            throw new IllegalArgumentException("Erro de Regra: Dados do tutor não podem ser nulos.");
        }
        if (tutor.getNome() == null || tutor.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Erro de Regra: O nome do tutor é obrigatório.");
        }
        if (tutor.getTelefone() == null || tutor.getTelefone().trim().isEmpty()) {
            throw new IllegalArgumentException("Erro de Regra: O telefone de contato é obrigatório.");
        }
        if (tutor.getEmail() == null || tutor.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Erro de Regra: O e-mail do tutor é obrigatório.");
        }
        if (!tutor.getEmail().contains("@") || !tutor.getEmail().contains(".")) {
            throw new IllegalArgumentException("Erro de Regra: Formato de e-mail inválido.");
        }

        // Critério RF.001: Unicidade de e-mail
        Tutor existente = repositorio.buscarPorEmail(tutor.getEmail().trim());
        if (existente != null) {
            throw new IllegalArgumentException("Erro de Regra: Já existe um tutor cadastrado com o e-mail '" 
                + tutor.getEmail() + "'.");
        }

        repositorio.inserir(tutor);
    }

    public Tutor buscarTutorPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Erro de Regra: ID do tutor é obrigatório.");
        }
        return repositorio.selecionarPorId(id);
    }

    public List<Tutor> listarTodosTutores() {
        return repositorio.selecionarTodos();
    }

    public void atualizarTutor(Tutor tutor) {
        if (tutor == null || tutor.getId() == null) {
            throw new IllegalArgumentException("Erro de Regra: Não é possível atualizar sem informar o ID do tutor.");
        }
        repositorio.atualizar(tutor);
    }

    public void removerTutor(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Erro de Regra: ID inválido para exclusão.");
        }
        repositorio.excluir(id);
    }
}
