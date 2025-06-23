package laricaco;

import java.io.*;

public class Persistencia {

    public static void salvarSistema(SistemaGerenciamento sistema, String caminhoArquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
            oos.writeObject(sistema);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SistemaGerenciamento carregarSistema(String caminhoArquivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminhoArquivo))) {
            return (SistemaGerenciamento) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Sistema não encontrado. Criando novo.");
            return null;
        }
    }
}

