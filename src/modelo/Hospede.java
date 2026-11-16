package modelo;

import java.util.Objects;

/**
 * Hóspede é uma Pessoa (HERANÇA): ganha nome e cpf e acrescenta telefone.
 */
public class Hospede extends Pessoa {

    private String telefone;

    public Hospede(String nome, String cpf, String telefone) {
        super(nome, cpf); // chama o construtor da classe Pessoa
        this.telefone = telefone;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Hospede outro = (Hospede) obj;
        return Objects.equals(telefone, outro.telefone);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString() + " | Telefone: " + telefone;
    }
}
