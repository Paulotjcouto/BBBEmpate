import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ApuracaoVotos {
    public static void main(String[] args) {
        ArrayList<Jogador> jogadores = criarListaJogadores();

        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        do {
            try {
                exibirMenu();
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer
            } catch (InputMismatchException exception) {
                System.err.println("Entrada inválida! " + exception.getMessage());
                scanner.nextLine(); // Limpar o buffer
            }

            switch (opcao) {
                case 1:
                    votarEmParticipante(jogadores, scanner);
                    break;
                case 2:
                    apurarResultado(jogadores);
                    break;
                case 3:
                    System.out.println("Saindo do sistema de votação...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 3);
    }

    private static ArrayList<Jogador> criarListaJogadores() {
        ArrayList<Jogador> jogadores = new ArrayList<>();
        // Adicionar participantes à lista
        jogadores.add(new Jogador("Alane Dias"));
        jogadores.add(new Jogador("Beatriz Reis"));
        jogadores.add(new Jogador("Davi Brito"));
        jogadores.add(new Jogador("Deniziane Ferreira"));
        // Adicione todos os outros jogadores aqui...

        return jogadores;
    }

    private static void exibirMenu() {
        System.out.println("\nVotação BBB - Modo Eliminação");
        System.out.println("---------------------------------");
        System.out.println("1- Em quem você vota");
        System.out.println("2- Apurar resultado");
        System.out.println("3- Sair");
        System.out.println("Escolha uma opção:");
    }

    private static void votarEmParticipante(ArrayList<Jogador> jogadores, Scanner scanner) {
        System.out.println("\nLista de participantes:");
        for (int i = 0; i < jogadores.size(); i++) {
            System.out.println((i + 1) + ". " + jogadores.get(i).getNome());
        }

        try {
            System.out.println("Digite o número do participante em que deseja votar:");
            int numeroParticipante = scanner.nextInt() - 1;

            if (numeroParticipante >= 0 && numeroParticipante < jogadores.size()) {
                jogadores.get(numeroParticipante).adicionarVotos();
                System.out.println("Voto registrado com sucesso para " + jogadores.get(numeroParticipante).getNome() + "!");
            } else {
                System.out.println("Número de participante inválido! Tente novamente.");
            }
        } catch (InputMismatchException e) {
            System.err.println("ENTRADA INVÁLIDA! " + e.getMessage());
            scanner.nextLine(); // Limpar o buffer
        }
    }

    private static void apurarResultado(ArrayList<Jogador> jogadores) {
        Jogador maisVotado = null;
        int maiorVotos = 0;
        boolean empate = false;

        for (Jogador jogador : jogadores) {
            int votos = jogador.getVotos();
            if (votos > maiorVotos) {
                maiorVotos = votos;
                maisVotado = jogador;
                empate = false; // Resetar o sinal de empate se encontrarmos um novo líder
            } else if (votos == maiorVotos) {
                empate = true; // Sinalizar empate se encontrarmos um número igual de votos
            }
        }

        if (!empate && maisVotado != null) {
            System.out.println("Participante com mais votos para eliminação:");
            System.out.println("Nome: " + maisVotado.getNome());
            System.out.println("Votos: " + maisVotado.getVotos());
            System.out.println("Mensagem de eliminação:\n" +
                    "                    Se eu conseguir mover montanhas, se eu conseguir surfar um tsunami, se eu conseguir\n" +
                    "                    domar o sol, se eu conseguir fazer o mar virar sertão, e o sertão virar mar, se eu\n" +
                    "                    conseguir dizer o que eu nunca vou conseguir dizer, aí terá chegado o dia em que eu\n" +
                    "                    vou conseguir te eliminar com alegria. Com " + maisVotado.getVotos() + " voto(s) é você quem sai, " + maisVotado.getNome() + "!"
            );
        } else if (empate) {
            System.out.println("Empate! Mais de um participante está empatado com a maior quantidade de votos.");
        } else {
            System.out.println("Ainda não há votos registrados.");
        }
    }
}