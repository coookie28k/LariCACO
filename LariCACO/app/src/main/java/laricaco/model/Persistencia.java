package laricaco.model;

import java.io.*;

public class Persistencia {

    public static SistemaGerenciamento carregarSistema(String caminhoArquivo) {
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            System.out.println("Arquivo não encontrado: " + caminhoArquivo);
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminhoArquivo))) {
            SistemaGerenciamento sistema = (SistemaGerenciamento) ois.readObject();
            System.out.println("Sistema carregado com sucesso.");
            return sistema;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar sistema:");
            e.printStackTrace(); // MOSTRA A EXCEÇÃO NO CONSOLE
            return null;
        }
    }

    public static void salvarSistema(SistemaGerenciamento sistema, String caminhoArquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
            oos.writeObject(sistema);
            System.out.println("Sistema salvo com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar sistema:");
            e.printStackTrace();
        }
    }
}


