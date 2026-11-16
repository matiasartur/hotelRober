package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Uma reserva liga um Hóspede a um Quarto em um período.
 */
public class Reserva implements Serializable {

    private int codigo;
    private Hospede hospede;
    private Quarto quarto;
    private LocalDate entrada;
    private LocalDate saida;
    private boolean ativa; // false = reserva cancelada

    public Reserva(int codigo, Hospede hospede, Quarto quarto, LocalDate entrada, LocalDate saida) {
        this.codigo = codigo;
        this.hospede = hospede;
        this.quarto = quarto;
        this.entrada = entrada;
        this.saida = saida;
        this.ativa = true;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Hospede getHospede() {
        return hospede;
    }

    public void setHospede(Hospede hospede) {
        this.hospede = hospede;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    public LocalDate getEntrada() {
        return entrada;
    }

    public void setEntrada(LocalDate entrada) {
        this.entrada = entrada;
    }

    public LocalDate getSaida() {
        return saida;
    }

    public void setSaida(LocalDate saida) {
        this.saida = saida;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    // Calcula o valor total: número de noites x valor da diária
    public double getValorTotal() {
        long noites = ChronoUnit.DAYS.between(entrada, saida);
        return noites * quarto.getValorDiaria();
    }

    // Duas reservas são iguais se tiverem o mesmo código
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Reserva outra = (Reserva) obj;
        return codigo == outra.codigo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        String situacao;
        if (ativa) {
            situacao = "ATIVA";
        } else {
            situacao = "CANCELADA";
        }
        return "Reserva #" + codigo + " [" + situacao + "]\n"
                + "   Hóspede: " + hospede.getNome() + "\n"
                + "   Quarto: " + quarto.getNumero() + "\n"
                + "   Entrada: " + entrada + " | Saída: " + saida + "\n"
                + "   Total: R$ " + String.format("%.2f", getValorTotal());
    }
}
