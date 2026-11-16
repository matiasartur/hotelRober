package principal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;

import dados.Arquivo;
import modelo.Hospede;
import modelo.Hotel;
import modelo.Quarto;
import modelo.QuartoLuxo;
import modelo.Reserva;

/**
 * Classe que mostra o menu e conversa com o usuário.
 */
public class Main {

    private static Scanner teclado = new Scanner(System.in);
    // Formato de data dia/mês/ano (STRICT não aceita datas inexistentes, como 31/02)
    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    public static void main(String[] args) {
        Hotel hotel = Arquivo.carregar(); // recupera o estado anterior
        int opcao;

        do {
            mostrarMenu(hotel);
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    cadastrarQuarto(hotel);
                    break;
                case 2:
                    cadastrarHospede(hotel);
                    break;
                case 3:
                    fazerReserva(hotel);
                    break;
                case 4:
                    cancelarReserva(hotel);
                    break;
                case 5:
                    listarQuartos(hotel);
                    break;
                case 6:
                    listarHospedes(hotel);
                    break;
                case 7:
                    listarReservas(hotel);
                    break;
                case 8:
                    consultarDisponibilidade(hotel);
                    break;
                case 0:
                    System.out.println("Salvando e saindo... Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida! Digite um número do menu.");
            }

            Arquivo.salvar(hotel); // salva depois de cada ação
        } while (opcao != 0);
    }

    // ---------- Menu ----------

    private static void mostrarMenu(Hotel hotel) {
        System.out.println();
        System.out.println("===== " + hotel.getNome().toUpperCase() + " =====");
        System.out.println("1 - Cadastrar quarto");
        System.out.println("2 - Cadastrar hóspede");
        System.out.println("3 - Fazer reserva");
        System.out.println("4 - Cancelar reserva");
        System.out.println("5 - Listar quartos");
        System.out.println("6 - Listar hóspedes");
        System.out.println("7 - Listar reservas");
        System.out.println("8 - Consultar quartos disponíveis");
        System.out.println("0 - Sair");
    }

    // ---------- Ações do menu ----------

    private static void cadastrarQuarto(Hotel hotel) {
        int numero = lerInteiro("Número do quarto: ");
        int capacidade = lerInteiro("Capacidade (pessoas): ");
        double diaria = lerDouble("Valor da diária: ");
        String tipo = lerTexto("O quarto é de luxo? (s/n): ").toLowerCase();

        try {
            if (tipo.equals("s")) {
                double adicional = lerDouble("Valor adicional do luxo: ");
                hotel.cadastrarQuarto(new QuartoLuxo(numero, capacidade, diaria, adicional));
            } else {
                hotel.cadastrarQuarto(new Quarto(numero, capacidade, diaria));
            }
            System.out.println("Quarto cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void cadastrarHospede(Hotel hotel) {
        String nome = lerTexto("Nome: ");
        String cpf = lerCpf("CPF (11 números): ");
        String telefone = lerTexto("Telefone: ");

        try {
            hotel.cadastrarHospede(new Hospede(nome, cpf, telefone));
            System.out.println("Hóspede cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void fazerReserva(Hotel hotel) {
        String cpf = lerCpf("CPF do hóspede: ");
        Hospede hospede = hotel.buscarHospede(cpf);
        if (hospede == null) {
            System.out.println("Hóspede não encontrado. Cadastre-o primeiro.");
            return;
        }

        int numero = lerInteiro("Número do quarto: ");
        Quarto quarto = hotel.buscarQuarto(numero);
        if (quarto == null) {
            System.out.println("Quarto não encontrado.");
            return;
        }

        LocalDate entrada = lerData("Data de entrada (dd/mm/aaaa): ");
        LocalDate saida = lerData("Data de saída (dd/mm/aaaa): ");

        if (entrada.isBefore(LocalDate.now())) {
            System.out.println("Erro: a entrada não pode ser no passado.");
            return;
        }

        try {
            Reserva reserva = hotel.fazerReserva(hospede, quarto, entrada, saida);
            System.out.println("Reserva realizada com sucesso!");
            System.out.println(reserva);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void cancelarReserva(Hotel hotel) {
        int codigo = lerInteiro("Código da reserva: ");
        try {
            hotel.cancelarReserva(codigo);
            System.out.println("Reserva cancelada!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarQuartos(Hotel hotel) {
        if (hotel.getQuartos().isEmpty()) {
            System.out.println("Nenhum quarto cadastrado.");
            return;
        }
        for (Quarto q : hotel.getQuartos()) {
            System.out.println(q);
        }
    }

    private static void listarHospedes(Hotel hotel) {
        if (hotel.getHospedes().isEmpty()) {
            System.out.println("Nenhum hóspede cadastrado.");
            return;
        }
        for (Hospede h : hotel.getHospedes()) {
            System.out.println(h);
        }
    }

    private static void listarReservas(Hotel hotel) {
        if (hotel.getReservas().isEmpty()) {
            System.out.println("Nenhuma reserva feita.");
            return;
        }
        for (Reserva r : hotel.getReservas()) {
            System.out.println(r);
        }
    }

    private static void consultarDisponibilidade(Hotel hotel) {
        LocalDate entrada = lerData("Data de entrada (dd/mm/aaaa): ");
        LocalDate saida = lerData("Data de saída (dd/mm/aaaa): ");

        if (!saida.isAfter(entrada)) {
            System.out.println("Erro: a saída deve ser depois da entrada.");
            return;
        }

        boolean achou = false;
        for (Quarto q : hotel.getQuartos()) {
            if (hotel.quartoDisponivel(q, entrada, saida)) {
                System.out.println(q);
                achou = true;
            }
        }
        if (!achou) {
            System.out.println("Nenhum quarto disponível nesse período.");
        }
    }

    // ---------- Leitura de dados com validação ----------
    // Cada método repete a pergunta até o usuário digitar algo válido.

    private static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = teclado.nextLine().trim();
            try {
                int valor = Integer.parseInt(texto);
                if (valor < 0) {
                    System.out.println("Digite um número positivo.");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um número inteiro.");
            }
        }
    }

    private static double lerDouble(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = teclado.nextLine().trim().replace(",", ".");
            try {
                double valor = Double.parseDouble(texto);
                if (valor <= 0) {
                    System.out.println("O valor deve ser maior que zero.");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um número (ex: 150.50).");
            }
        }
    }

    private static String lerTexto(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = teclado.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("Esse campo não pode ficar vazio.");
            } else {
                return texto;
            }
        }
    }

    private static String lerCpf(String mensagem) {
        while (true) {
            String cpf = lerTexto(mensagem);
            if (cpf.matches("\\d{11}")) { // exatamente 11 dígitos
                return cpf;
            }
            System.out.println("CPF inválido! Digite 11 números, sem pontos ou traços.");
        }
    }

    private static LocalDate lerData(String mensagem) {
        while (true) {
            String texto = lerTexto(mensagem);
            try {
                return LocalDate.parse(texto, FORMATO);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida! Use o formato dd/mm/aaaa.");
            }
        }
    }
}
