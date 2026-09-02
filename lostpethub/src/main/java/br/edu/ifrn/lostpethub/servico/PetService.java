package br.edu.ifrn.lostpethub.servico;

import java.util.List;

import br.edu.ifrn.lostpethub.modelo.Pet;
import br.edu.ifrn.lostpethub.modelo.StatusPet;
import br.edu.ifrn.lostpethub.modelo.Tutor;
import br.edu.ifrn.lostpethub.repositorio.PetRepositorio;
import br.edu.ifrn.lostpethub.repositorio.TutorRepositorio;

/**
 * Camada de Serviço responsável pelas regras de negócio de Pets (RF.002 e RF.003).
 */
public class PetService {

    private final PetRepositorio petRepositorio = new PetRepositorio();
    private final TutorRepositorio tutorRepositorio = new TutorRepositorio();

    /**
     * Cadastra um novo pet vinculado a um tutor (RF.002).
     */
    public void cadastrarPet(Pet pet) {
        if (pet == null) {
            throw new IllegalArgumentException("Erro de Regra: Dados do pet não podem ser nulos.");
        }
        if (pet.getNome() == null || pet.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Erro de Regra: O nome do animal é obrigatório.");
        }
        if (pet.getEspecie() == null || pet.getEspecie().trim().isEmpty()) {
            throw new IllegalArgumentException("Erro de Regra: A espécie do animal é obrigatória.");
        }
        if (pet.getTutorId() == null) {
            throw new IllegalArgumentException("Erro de Regra: O vínculo com um Tutor é obrigatório.");
        }

        // Critério RF.002: Validação de existência do tutor
        Tutor tutor = tutorRepositorio.selecionarPorId(pet.getTutorId());
        if (tutor == null) {
            throw new IllegalArgumentException("Erro de Regra: Tutor de ID " + pet.getTutorId() + " não encontrado.");
        }

        // Critério RF.002: Iniciar como COM_TUTOR por padrão
        if (pet.getStatus() == null) {
            pet.setStatus(StatusPet.COM_TUTOR);
        }

        petRepositorio.inserir(pet);
    }

    public Pet buscarPetPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Erro de Regra: ID do pet é obrigatório.");
        }
        return petRepositorio.selecionarPorId(id);
    }

    public List<Pet> listarTodosPets() {
        return petRepositorio.selecionarTodos();
    }

    public List<Pet> listarPetsPorTutor(Long tutorId) {
        if (tutorId == null) {
            throw new IllegalArgumentException("Erro de Regra: ID do tutor é obrigatório.");
        }
        return petRepositorio.selecionarPorTutor(tutorId);
    }

    /**
     * Lista apenas os animais marcados como PERDIDO (RF.003).
     */
    public List<Pet> listarPetsPerdidos() {
        return petRepositorio.selecionarPorStatus(StatusPet.PERDIDO);
    }

    public List<Pet> filtrarPetsPorStatus(StatusPet status) {
        if (status == null) {
            return listarTodosPets();
        }
        return petRepositorio.selecionarPorStatus(status);
    }

    public List<Pet> filtrarPetsPorEspecie(String especie) {
        if (especie == null || especie.trim().isEmpty()) {
            return listarTodosPets();
        }
        return petRepositorio.selecionarPorEspecie(especie.trim());
    }

    /**
     * Altera o status do animal e dispara alertas pertinentes (RF.003).
     */
    public void alterarStatusPet(Long petId, StatusPet novoStatus) {
        if (petId == null) {
            throw new IllegalArgumentException("Erro de Regra: ID do pet é obrigatório.");
        }
        if (novoStatus == null) {
            throw new IllegalArgumentException("Erro de Regra: O novo status não pode ser nulo.");
        }

        Pet pet = petRepositorio.selecionarPorId(petId);
        if (pet == null) {
            throw new IllegalArgumentException("Erro de Regra: Pet de ID " + petId + " não encontrado.");
        }

        petRepositorio.atualizarStatus(petId, novoStatus);

        // Alertas de negócio
        if (novoStatus == StatusPet.PERDIDO) {
            System.out.println("LOG ALERTA COMUNIDADE: O pet '" + pet.getNome() + "' (" + pet.getEspecie() + ") foi registrado como DESAPARECIDO!");
        } else if (novoStatus == StatusPet.ENCONTRADO || novoStatus == StatusPet.RESGATADO) {
            System.out.println("LOG NOTIFICAÇÃO: Boas notícias! O pet '" + pet.getNome() + "' foi marcado como " + novoStatus + "!");
        }
    }

    public void atualizarPet(Pet pet) {
        if (pet == null || pet.getId() == null) {
            throw new IllegalArgumentException("Erro de Regra: ID do pet obrigatório para atualização.");
        }
        petRepositorio.atualizar(pet);
    }

    public void removerPet(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Erro de Regra: ID inválido para exclusão.");
        }
        petRepositorio.excluir(id);
    }

    /**
     * Exibe o relatório detalhado do animal e contato do tutor responsável.
     */
    public void exibirRelatorioPet(Pet pet) {
        Tutor tutor = tutorRepositorio.selecionarPorId(pet.getTutorId());
        System.out.println("--------------------------------------------------");
        System.out.println("Ficha do Animal - LostPetHub");
        System.out.println("Nome: " + pet.getNome());
        System.out.println("Espécie: " + pet.getEspecie() + " | Raça: " + (pet.getRaca() != null ? pet.getRaca() : "N/D"));
        System.out.println("Cor: " + (pet.getCor() != null ? pet.getCor() : "N/D"));
        System.out.println("Status: " + pet.getStatus());
        if (tutor != null) {
            System.out.println("Tutor Responsável: " + tutor.getNome());
            System.out.println("Contato Tutor: " + tutor.getTelefone() + " (" + tutor.getEmail() + ")");
        }
        System.out.println("--------------------------------------------------");
    }
}
