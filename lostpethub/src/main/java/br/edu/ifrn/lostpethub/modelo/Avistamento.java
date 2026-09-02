package br.edu.ifrn.lostpethub.modelo;

import java.time.LocalDateTime;

/**
 * Entidade que representa um Avistamento de animal perdido.
 * Mapeia os dados da tabela 'avistamento' (RF.004).
 */
public class Avistamento {

    private Long id;
    private Long petId;
    private String localizacao;
    private String pontoReferencia;
    private LocalDateTime dataHora;
    private String observacoes;

    public Avistamento() {
        this.dataHora = LocalDateTime.now();
    }

    public Avistamento(Long petId, String localizacao, String pontoReferencia, String observacoes) {
        this.petId = petId;
        this.localizacao = localizacao;
        this.pontoReferencia = pontoReferencia;
        this.observacoes = observacoes;
        this.dataHora = LocalDateTime.now();
    }

    public Avistamento(Long id, Long petId, String localizacao, String pontoReferencia, LocalDateTime dataHora, String observacoes) {
        this.id = id;
        this.petId = petId;
        this.localizacao = localizacao;
        this.pontoReferencia = pontoReferencia;
        this.dataHora = (dataHora != null) ? dataHora : LocalDateTime.now();
        this.observacoes = observacoes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getPontoReferencia() {
        return pontoReferencia;
    }

    public void setPontoReferencia(String pontoReferencia) {
        this.pontoReferencia = pontoReferencia;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    @Override
    public String toString() {
        return "Avistamento{id=" + id + 
               ", petId=" + petId + 
               ", localizacao='" + localizacao + '\'' + 
               ", pontoReferencia='" + pontoReferencia + '\'' + 
               ", dataHora=" + dataHora + 
               ", observacoes='" + observacoes + '\'' + '}';
    }
}
