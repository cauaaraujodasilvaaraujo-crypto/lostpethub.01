package br.edu.ifrn.lostpethub.modelo;

/**
 * Entidade que representa o Tutor / Responsável pelo animal.
 * Mapeia os dados da tabela 'tutor' (RF.001).
 */
public class Tutor {

    private Long id;
    private String nome;
    private String telefone;
    private String email;

    public Tutor() {}

    public Tutor(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public Tutor(Long id, String nome, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Tutor{id=" + id + 
               ", nome='" + nome + '\'' + 
               ", telefone='" + telefone + '\'' + 
               ", email='" + email + '\'' + '}';
    }
}
