package dados;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import modelo.Hotel;

/**
 * Cuida de salvar e carregar o Hotel em arquivo (serialização).
 * Como o Hotel guarda tudo (quartos, hóspedes e reservas), salvar o Hotel salva o sistema todo.
 */
public class Arquivo {

    private static final String NOME_ARQUIVO = "hotel.dat";

    // Grava o hotel no arquivo
    public static void salvar(Hotel hotel) {
        try (ObjectOutputStream saida = new ObjectOutputStream(new FileOutputStream(NOME_ARQUIVO))) {
            saida.writeObject(hotel);
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    // Lê o hotel do arquivo. Se não existir (primeira execução), cria um hotel novo.
    public static Hotel carregar() {
        File arquivo = new File(NOME_ARQUIVO);
        if (!arquivo.exists()) {
            return new Hotel("Pousada Sol e Mar");
        }
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (Hotel) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar os dados: " + e.getMessage());
            System.out.println("Iniciando com um hotel vazio.");
            return new Hotel("Pousada Sol e Mar");
        }
    }
}
