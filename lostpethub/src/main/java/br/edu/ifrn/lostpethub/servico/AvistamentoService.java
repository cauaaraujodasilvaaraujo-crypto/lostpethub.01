package br.edu.ifrn.lostpethub.servico;

import java.util.List;

import br.edu.ifrn.lostpethub.modelo.Avistamento;
import br.edu.ifrn.lostpethub.modelo.Pet;
import br.edu.ifrn.lostpethub.modelo.StatusPet;
import br.edu.ifrn.lostpethub.repositorio.AvistamentoRepositorio;
import br.edu.ifrn.lostpethub.repositorio.PetRepositorio;

/**
 * Camada de Serviço responsável pelas regras de negócio de Avistamentos (RF.004).
 */
public class AvistamentoService {

    private final AvistamentoRepositorio avistamentoRepositorio = new AvistamentoRepositorio();
    private final PetRepositorio petRepositorio = new PetRepositorio();

    /**
     * Registra um novo relato/avistamento de animal perdido (RF.004).
     */
    public void registrarAvistamento(Avistamento avistamento) {
        if (avistamento == null) {
            throw new IllegalArgumentException("Erro de Regra: Dados do avistamento não podem ser nulos.");
        }
        if (avistamento.getLocalizacao() == null || avistamento.getLocalizacao().trim().isEmpty()) {
            throw new IllegalArgumentException("Erro de Regra: A localização do avistamento é obrigatória.");
        }
        if (avistamento.getPetId() == null) {
            throw new IllegalArgumentException("Erro de Regra: O ID do pet avistado é obrigatório.");
        }

        // Critério RF.004: Validação do Pet
        Pet pet = petRepositorio.selecionarPorId(avistamento.getPetId());
        if (pet == null) {
            throw new IllegalArgumentException("Erro de Regra: Pet de ID " + avistamento.getPetId() + " não encontrado.");
        }

        // Critério RF.004: Só é permitido registrar avistamentos para animais com status PERDIDO
        if (pet.getStatus() != StatusPet.PERDIDO) {
            throw new IllegalStateException("Erro de Regra: Não é possível registrar avistamento para o pet '" 
                + pet.getNome() + "' pois ele não está com status PERDIDO (Status atual: " + pet.getStatus() + ").");
        }

        if (avistamento.getDataHora() == null) {
            avistamento.setDataHora(java.time.LocalDateTime.now());
        }

        avistamentoRepositorio.inserir(avistamento);

        System.out.println("LOG NOTIFICAÇÃO AO TUTOR: Novo avistamento registrado para '" 
            + pet.getNome() + "' em '" + avistamento.getLocalizacao() + "' (" 
            + (avistamento.getPontoReferencia() != null ? avistamento.getPontoReferencia() : "Sem ref.") + ").");
    }

    /**
     * Consulta o histórico cronológico de avistamentos de um pet (RF.004).
     */
    public List<Avistamento> listarHistoricoAvistamentos(Long petId) {
        if (petId == null) {
            throw new IllegalArgumentException("Erro de Regra: ID do pet é obrigatório.");
        }
        return avistamentoRepositorio.selecionarPorPet(petId);
    }

    public List<Avistamento> listarTodosAvistamentos() {
        return avistamentoRepositorio.selecionarTodos();
    }

    public void removerAvistamento(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Erro de Regra: ID inválido para exclusão.");
        }
        avistamentoRepositorio.excluir(id);
    }
}
