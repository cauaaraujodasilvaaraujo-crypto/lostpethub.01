-- ==========================================================
-- SCRIPT DE INFRAESTRUTURA DO BANCO DE DADOS - LOSTPETHUB
-- Compatível com MySQL Workbench / MySQL Server
-- ==========================================================

-- 1. Criação do Banco de Dados (Schema) caso não exista
CREATE DATABASE IF NOT EXISTS lostpethub_db
  CHARACTER SET utf8mb4 
  COLLATE utf8mb4_unicode_ci;

-- 2. Seleciona o Banco para uso
USE lostpethub_db;

-- 3. Criação da Tabela 'tutor' (RF.001)
CREATE TABLE IF NOT EXISTS tutor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    telefone VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL,
    CONSTRAINT uk_tutor_email UNIQUE (email)
);

-- 4. Criação da Tabela 'pet' (RF.002 e RF.003)
CREATE TABLE IF NOT EXISTS pet (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    especie VARCHAR(100) NOT NULL,
    raca VARCHAR(100),
    cor VARCHAR(100),
    status VARCHAR(50) NOT NULL DEFAULT 'COM_TUTOR',
    tutor_id BIGINT NOT NULL,
    CONSTRAINT fk_pet_tutor FOREIGN KEY (tutor_id) REFERENCES tutor(id) ON DELETE CASCADE
);

-- 5. Criação da Tabela 'avistamento' (RF.004)
CREATE TABLE IF NOT EXISTS avistamento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pet_id BIGINT NOT NULL,
    localizacao VARCHAR(255) NOT NULL,
    ponto_referencia VARCHAR(255),
    data_hora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    observacoes TEXT,
    CONSTRAINT fk_avistamento_pet FOREIGN KEY (pet_id) REFERENCES pet(id) ON DELETE CASCADE
);
