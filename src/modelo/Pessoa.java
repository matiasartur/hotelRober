package modelo;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe base para qualquer pessoa do sistema.
 * Serializable permite salvar o objeto em arquivo.
 */
public class Pessoa implements Serializable {

    private String nome;
    private String cpf;

    // Construtor
    public Pessoa(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    // Gets e sets
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    // Duas pessoas são iguais se tiverem o mesmo CPF
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Pessoa outra = (Pessoa) obj;
        return Objects.equals(cpf, outra.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    // Texto que representa o objeto
    @Override
    public String toString() {
        return "Nome: " + nome + " | CPF: " + cpf;
    }
}
