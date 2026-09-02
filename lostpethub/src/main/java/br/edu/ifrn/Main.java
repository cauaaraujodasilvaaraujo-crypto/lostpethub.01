package br.edu.ifrn;

import java.util.List;

import br.edu.ifrn.lostpethub.modelo.Avistamento;
import br.edu.ifrn.lostpethub.modelo.Pet;
import br.edu.ifrn.lostpethub.modelo.StatusPet;
import br.edu.ifrn.lostpethub.modelo.Tutor;
import br.edu.ifrn.lostpethub.servico.AvistamentoService;
import br.edu.ifrn.lostpethub.servico.PetService;
import br.edu.ifrn.lostpethub.servico.TutorService;

public class Main {
    public static void main(String[] args) {
        TutorService tutorService = new TutorService();
        PetService petService = new PetService();
        AvistamentoService avistamentoService = new AvistamentoService();

        System.out.println("=================================================================");
        System.out.println("         LOSTPETHUB - SISTEMA DE LOCALIZAÇÃO DE PETS");
        System.out.println("=================================================================");

        try {
            // -------------------------------------------------------------
            // 1. RF.001 - CADASTRO DE TUTORES / RESPONSÁVEIS
            // -------------------------------------------------------------
            System.out.println("\n--- [1] RF.001: Cadastro de Tutores ---");
            Tutor tutor1 = new Tutor("Cauã Araújo", "(84) 99888-7766", "caua@email.com");
            Tutor tutor2 = new Tutor("Mariana Souza", "(84) 98777-6655", "mariana@email.com");

            tutorService.cadastrarTutor(tutor1);
            tutorService.cadastrarTutor(tutor2);

            System.out.println("Tutores cadastrados com sucesso:");
            System.out.println(" -> " + tutor1);
            System.out.println(" -> " + tutor2);

            // Teste de validação: E-mail duplicado
            System.out.println("\n[Teste de Validação] Tentando cadastrar tutor com e-mail duplicado...");
            try {
                Tutor tutorDuplicado = new Tutor("Outro Cauã", "(84) 91111-2222", "caua@email.com");
                tutorService.cadastrarTutor(tutorDuplicado);
            } catch (Exception e) {
                System.out.println(">> Bloqueio de Regra com Sucesso: " + e.getMessage());
            }

            // -------------------------------------------------------------
            // 2. RF.002 - CADASTRO DE PETS
            // -------------------------------------------------------------
            System.out.println("\n--- [2] RF.002: Cadastro de Animais (Pets) ---");
            Pet pet1 = new Pet("Thor", "Cachorro", "Golden Retriever", "Dourado", tutor1.getId());
            Pet pet2 = new Pet("Mia", "Gato", "Siamês", "Branco com Marrom", tutor2.getId());

            petService.cadastrarPet(pet1);
            petService.cadastrarPet(pet2);

            System.out.println("Pets cadastrados com status padrão 'COM_TUTOR':");
            System.out.println(" -> " + pet1);
            System.out.println(" -> " + pet2);

            // -------------------------------------------------------------
            // 3. RF.003 - ALTERAÇÃO DE STATUS E ALERTA DE DESAPARECIMENTO
            // -------------------------------------------------------------
            System.out.println("\n--- [3] RF.003: Alerta de Desaparecimento de Pet ---");
            System.out.println("Registrando o desaparecimento do pet Thor...");
            petService.alterarStatusPet(pet1.getId(), StatusPet.PERDIDO);

            System.out.println("\nLista de Animais Perdidos na Comunidade:");
            List<Pet> perdidos = petService.listarPetsPerdidos();
            perdidos.forEach(petService::exibirRelatorioPet);

            // -------------------------------------------------------------
            // 4. RF.004 - REGISTRO E HISTÓRICO DE AVISTAMENTOS
            // -------------------------------------------------------------
            System.out.println("\n--- [4] RF.004: Registro de Avistamentos ---");

            // Teste de validação: Registrar avistamento de pet que NÃO está perdido
            System.out.println("[Teste de Validação] Tentando avistar pet que não está perdido...");
            try {
                Avistamento avistamentoInvalido = new Avistamento(pet2.getId(), "Av. Central, 100", "Praça", "Gata dormindo");
                avistamentoService.registrarAvistamento(avistamentoInvalido);
            } catch (Exception e) {
                System.out.println(">> Bloqueio de Regra com Sucesso: " + e.getMessage());
            }

            // Registrando avistamentos válidos para o Thor (PERDIDO)
            System.out.println("\nRegistrando avistamentos reais para o Thor...");
            Avistamento av1 = new Avistamento(pet1.getId(), "Rua das Flores, próx. ao Posto", "Próximo à conveniência", "Visto correndo assustado");
            Avistamento av2 = new Avistamento(pet1.getId(), "Parque das Dunas, Entrada Leste", "Perto do coreto", "Foi visto bebendo água");

            avistamentoService.registrarAvistamento(av1);
            avistamentoService.registrarAvistamento(av2);

            System.out.println("\nHistórico de Avistamentos do pet Thor:");
            List<Avistamento> historico = avistamentoService.listarHistoricoAvistamentos(pet1.getId());
            historico.forEach(System.out::println);

            // -------------------------------------------------------------
            // 5. RESGATE E ATUALIZAÇÃO FINAL
            // -------------------------------------------------------------
            System.out.println("\n--- [5] Resgate do Animal ---");
            petService.alterarStatusPet(pet1.getId(), StatusPet.RESGATADO);

            Pet petThorAtualizado = petService.buscarPetPorId(pet1.getId());
            petService.exibirRelatorioPet(petThorAtualizado);

            // -------------------------------------------------------------
            // 6. LIMPEZA / REMOÇÃO DOS DADOS DE TESTE
            // -------------------------------------------------------------
            System.out.println("\n--- [6] Limpeza de Dados de Teste ---");
            tutorService.removerTutor(tutor1.getId());
            tutorService.removerTutor(tutor2.getId());
            System.out.println("Tutores e dados relacionados limpos com sucesso!");

            System.out.println("\n=================================================================");
            System.out.println("        TODOS OS REQUISITOS FORAM TESTADOS COM SUCESSO!");
            System.out.println("=================================================================");

        } catch (Exception e) {
            System.err.println("Erro durante a execução: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
