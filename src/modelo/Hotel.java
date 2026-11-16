package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Classe principal do negócio: guarda as listas e as regras do sistema.
 * Os erros de regra são lançados com IllegalArgumentException.
 */
public class Hotel implements Serializable {

    private String nome;
    private ArrayList<Quarto> quartos;
    private ArrayList<Hospede> hospedes;
    private ArrayList<Reserva> reservas;
    private int proximoCodigo; // controla o código das reservas

    public Hotel(String nome) {
        this.nome = nome;
        this.quartos = new ArrayList<>();
        this.hospedes = new ArrayList<>();
        this.reservas = new ArrayList<>();
        this.proximoCodigo = 1;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Quarto> getQuartos() {
        return quartos;
    }

    public ArrayList<Hospede> getHospedes() {
        return hospedes;
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    // ---------- Cadastros ----------

    public void cadastrarQuarto(Quarto quarto) {
        if (buscarQuarto(quarto.getNumero()) != null) {
            throw new IllegalArgumentException("Já existe um quarto com esse número.");
        }
        quartos.add(quarto);
    }

    public void cadastrarHospede(Hospede hospede) {
        if (buscarHospede(hospede.getCpf()) != null) {
            throw new IllegalArgumentException("Já existe um hóspede com esse CPF.");
        }
        hospedes.add(hospede);
    }

    // ---------- Buscas (retornam null se não achar) ----------

    public Quarto buscarQuarto(int numero) {
        for (Quarto q : quartos) {
            if (q.getNumero() == numero) {
                return q;
            }
        }
        return null;
    }

    public Hospede buscarHospede(String cpf) {
        for (Hospede h : hospedes) {
            if (h.getCpf().equals(cpf)) {
                return h;
            }
        }
        return null;
    }

    public Reserva buscarReserva(int codigo) {
        for (Reserva r : reservas) {
            if (r.getCodigo() == codigo) {
                return r;
            }
        }
        return null;
    }

    // ---------- Reservas ----------

    // Verifica se o quarto está livre no período (ignora reservas canceladas)
    public boolean quartoDisponivel(Quarto quarto, LocalDate entrada, LocalDate saida) {
        for (Reserva r : reservas) {
            if (r.isAtiva() && r.getQuarto().equals(quarto)) {
                // Há conflito quando os períodos se sobrepõem
                if (entrada.isBefore(r.getSaida()) && saida.isAfter(r.getEntrada())) {
                    return false;
                }
            }
        }
        return true;
    }

    public Reserva fazerReserva(Hospede hospede, Quarto quarto, LocalDate entrada, LocalDate saida) {
        if (!saida.isAfter(entrada)) {
            throw new IllegalArgumentException("A saída deve ser depois da entrada.");
        }
        if (!quartoDisponivel(quarto, entrada, saida)) {
            throw new IllegalArgumentException("Quarto indisponível nesse período.");
        }
        Reserva nova = new Reserva(proximoCodigo, hospede, quarto, entrada, saida);
        proximoCodigo++;
        reservas.add(nova);
        return nova;
    }

    public void cancelarReserva(int codigo) {
        Reserva r = buscarReserva(codigo);
        if (r == null) {
            throw new IllegalArgumentException("Reserva não encontrada.");
        }
        if (!r.isAtiva()) {
            throw new IllegalArgumentException("Essa reserva já está cancelada.");
        }
        r.setAtiva(false);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Hotel outro = (Hotel) obj;
        return Objects.equals(nome, outro.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }

    @Override
    public String toString() {
        return "Hotel: " + nome + " | Quartos: " + quartos.size()
                + " | Hóspedes: " + hospedes.size()
                + " | Reservas: " + reservas.size();
    }
}
