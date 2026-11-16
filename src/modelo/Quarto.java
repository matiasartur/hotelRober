package modelo;

import java.io.Serializable;
import java.util.Objects;

/**
 * Representa um quarto comum do hotel/pousada.
 */
public class Quarto implements Serializable {

    private int numero;
    private int capacidade;     // quantas pessoas cabem
    private double valorDiaria; // preço por noite

    public Quarto(int numero, int capacidade, double valorDiaria) {
        this.numero = numero;
        this.capacidade = capacidade;
        this.valorDiaria = valorDiaria;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    // Dois quartos são iguais se tiverem o mesmo número
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Quarto outro = (Quarto) obj;
        return numero == outro.numero;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }

    @Override
    public String toString() {
        return "Quarto " + numero + " | Capacidade: " + capacidade
                + " | Diária: R$ " + String.format("%.2f", getValorDiaria());
    }
}
