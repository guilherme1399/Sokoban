package pt.iscte.poo.sokobanstarter;

import java.io.*;
import java.util.*;

public class Pontuacao implements Comparable<Pontuacao> {

    private int pontos;
    private String nome;

    public Pontuacao(int pontos, String nome) {
        this.pontos = pontos;
        this.nome = nome;
    }

    @Override
    public int compareTo(Pontuacao outraPontuacao) {
        // Comparacao com base nos pontos
        return Integer.compare(this.pontos, outraPontuacao.pontos);
    }

    public int getPontos() {
        return pontos;
    }

    public String getNome() {
        return nome;
    }

    public static void salvarPontuacao(int level, int movimentos, String nomeJogador) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Pontuacoes/Pontuacoes_" + level + ".txt", true))) {
            Pontuacao pontuacao = new Pontuacao(movimentos, nomeJogador);
            String linha = pontuacao.getPontos() + " " + pontuacao.getNome();
            writer.write(linha);
            writer.newLine();  
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Pontuacao> top3(String file) {
        List<Pontuacao> pontuacoes = new ArrayList<>();
        try (Scanner s = new Scanner(new File(file))) {
            while (s.hasNextLine()) {
                String[] partes = s.nextLine().split(" ");
                int pontos = Integer.parseInt(partes[0]);
                String nome = partes[1];
                pontuacoes.add(new Pontuacao(pontos, nome));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(pontuacoes);

        int nrlinhas = Math.min(pontuacoes.size(), 3); // tava a dar erro se nao usasse o min
        return pontuacoes.subList(0, nrlinhas);
    }

    public static void atualizarArquivoPontuacoes(String filePath, List<Pontuacao> pontuacoes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Pontuacao pontuacao : pontuacoes) {
                String linha = pontuacao.getPontos() + " " + pontuacao.getNome();
                writer.write(linha);
                writer.newLine();  
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
