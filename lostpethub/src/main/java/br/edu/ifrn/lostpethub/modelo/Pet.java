package br.edu.ifrn.lostpethub.modelo;

/**
 * Entidade que representa um Animal de Estimação (Pet).
 * Mapeia os dados da tabela 'pet' (RF.002 e RF.003).
 */
public class Pet {

    private Long id;
    private String nome;
    private String especie; // Ex: Cachorro, Gato, Pássaro
    private String raca;
    private String cor;
    private StatusPet status; // Padrão: COM_TUTOR
    private Long tutorId;    // Chave estrangeira para Tutor

    public Pet() {
        this.status = StatusPet.COM_TUTOR; // Critério RF.002: Inicia como COM_TUTOR
    }

    public Pet(String nome, String especie, String raca, String cor, Long tutorId) {
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.cor = cor;
        this.tutorId = tutorId;
        this.status = StatusPet.COM_TUTOR;
    }

    public Pet(Long id, String nome, String especie, String raca, String cor, StatusPet status, Long tutorId) {
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.cor = cor;
        this.status = (status != null) ? status : StatusPet.COM_TUTOR;
        this.tutorId = tutorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public StatusPet getStatus() {
        return status;
    }

    public void setStatus(StatusPet status) {
        this.status = status;
    }

    public Long getTutorId() {
        return tutorId;
    }

    public void setTutorId(Long tutorId) {
        this.tutorId = tutorId;
    }

    public boolean isPerdido() {
        return this.status == StatusPet.PERDIDO;
    }

    @Override
    public String toString() {
        return "Pet{id=" + id + 
               ", nome='" + nome + '\'' + 
               ", especie='" + especie + '\'' + 
               ", raca='" + raca + '\'' + 
               ", cor='" + cor + '\'' + 
               ", status=" + status + 
               ", tutorId=" + tutorId + '}';
    }
}
