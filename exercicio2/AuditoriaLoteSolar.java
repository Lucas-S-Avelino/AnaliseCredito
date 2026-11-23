package exercicio2;

import java.util.Scanner;

public class AuditoriaLoteSolar {

    public static void main(String[] args) {

        String nomeCompleto;
        double rendaMensal, valorParcela;
        int scoreC;
        boolean restricao;
        boolean continuar = true;
        int propostasAnalisadas = 0;
        String cenario = "";
        int aprovados = 0;
        int reprovados = 0;
        Scanner leia = new Scanner(System.in);
        double maiorParcela = 0.0;
        String clienteMaiorParcela = "";
        double rendaSoma = 0;
        double rendaMediaAnalisados = 0.0;
        int reprovadosRestricao = 0;
        int reprovadosMargem = 0;
        int reprovadosScore = 0;
        String principalMotivoReprovacao = "Nenhum";
        double percentualAprovacao = 0.0;

        System.out.println("Registrar cliente? (Sim/Não)");
        char registrar = leia.nextLine().toLowerCase().charAt(0);

        while (registrar != 's' && registrar != 'n') {

            System.err.println("Erro!!\nRespostas válidas: (Sim/Não): \n)");
            registrar = leia.nextLine().toLowerCase().charAt(0);
        }
        if (registrar == 's') {
            System.out.println();

        } else {
            return;
        }

        do {

            System.out.println("Digite o seu nome completo: ");
            nomeCompleto = leia.nextLine();

            System.out.println("Digite a sua Renda Mensal Bruta: ");
            rendaMensal = leia.nextDouble();
            if (rendaMensal <= 0) {
                System.out.println("Erro: Dados de entrada inválidos. Operação cancelada.");
                return;

            }

            System.out.println("Digite o valor da parcela pretendida: ");
            valorParcela = leia.nextDouble();

            if (valorParcela <= 0) {
                System.out.println("Erro: Dados de entrada inválidos. Operação cancelada.");
                return;

            }

            System.out.println("Digite o seu Score de Crédito: {0 a 1000} ");
            scoreC = leia.nextInt();
            if (scoreC < 0 || scoreC > 1000) {
                System.out.println("Erro: Dados de entrada inválidos. Operação cancelada.");
                return;

            }

            System.out.println("Restrição no CPF: (Sim/Não)");
            leia.nextLine();
            char confirmacao = leia.nextLine().toLowerCase().charAt(0);

            while (confirmacao != 's' && confirmacao != 'n') {

                System.err.println("Erro!! digite um valor válido: <sim> ou <não> \n");

                System.out.println("Restrição no CPF: (Sim/Não)");
                confirmacao = leia.nextLine().toLowerCase().charAt(0);
            }
            if (confirmacao == 's') {
                restricao = true;
            } else {
                restricao = false;
            }

            if (valorParcela > maiorParcela) {
                maiorParcela = valorParcela;
                clienteMaiorParcela = nomeCompleto;
            }
            double percentualC = (valorParcela / rendaMensal) * 100;

            // AVALIAÇÃO
            if (restricao) {
                cenario = "REPROVADO: Restrição ativa no CPF.";
                reprovadosRestricao++;
                reprovados++;

            } else if (percentualC > 30) {
                cenario = "REPROVADO: Parcela excede 30% da renda mensal.";
                reprovadosMargem++;
                reprovados++;

            } else if (scoreC < 400) {
                cenario = "REPROVADO: Score insuficiente para a linha solar.";
                reprovadosScore++;
                reprovados++;

            } else if (scoreC >= 400 && scoreC <= 699) {
                cenario = "APROVADO COM CONDICIONAL: Financiamento liberado com exigência de 20% de entrada.";
                aprovados++;
            } else if (scoreC >= 700) {
                cenario = "APROVADO: Financiamento liberado com Taxa Premium e zero de entrada.";
                aprovados++;

            } else {
                cenario = "Não foi possível determinar o resultado.";
            }

            rendaSoma += rendaMensal;

            System.out.println("\n\n========== RESULTADO DA ANÁLISE ==========\n");

            System.out.println("Nome: " + nomeCompleto);

            System.out.printf("Renda Bruta: R$ %.2f%n", rendaMensal);

            System.out.printf("Parcela Pretendida: R$ %.2f%n", valorParcela);

            System.out.printf("Comprometimento da Renda: %.1f%%%n", percentualC);

            System.out.println("\nResultado: " + cenario);

            System.out.println("\n========================================================\n");

            propostasAnalisadas++;

            System.out.println("Deseja registrar outro cliente? (S/N): ");
            char novoCliente = leia.nextLine().toLowerCase().charAt(0);

            while (novoCliente != 's' && novoCliente != 'n') {
                System.err.println("Valor inválido! \nDeseja cadastrar um novo cliente? (S/N): ");
                novoCliente = leia.nextLine().toLowerCase().charAt(0);
            }

            if (novoCliente == 's') {
                continuar = true;
            } else {
                continuar = false;
            }

        } while (continuar);

        if (reprovados == 0) {
            principalMotivoReprovacao = "Nenhuma proposta foi reprovada!";
        } else if (reprovadosRestricao >= reprovadosMargem && reprovadosRestricao >= reprovadosScore) {
            principalMotivoReprovacao = "Restrição no CPF (" + reprovadosRestricao + " ocorrências)";
        } else if (reprovadosMargem >= reprovadosScore) {
            principalMotivoReprovacao = "Margem de Renda Ultrapassada (" + reprovadosMargem + " ocorrências)";
        } else {
            principalMotivoReprovacao = "Score Insuficiente (" + reprovadosScore + " ocorrências)";
        }

        if (propostasAnalisadas > 0) {
            percentualAprovacao = ((double) aprovados / propostasAnalisadas) * 100;
            rendaMediaAnalisados = rendaSoma / propostasAnalisadas;

        }

        System.out.println("\n========== RELATÓRIO GERENCIAL ==========\n");

        System.out.println("Total de propostas analisadas: " + propostasAnalisadas);

        System.out.println("Total de propostas aprovadas: " + aprovados);

        System.out.println("Total de propostas reprovadas: " + reprovados);

        System.out.printf("Taxa de aprovação: %.2f%%%n", percentualAprovacao);

        System.out.println("Principal motivo de reprovação: " + principalMotivoReprovacao);

        System.out.printf("Média de renda mensal: R$ %.2f%n", rendaMediaAnalisados);

        System.out.println("\n========== MAIOR PARCELA ANALISADA ==========\n");

        System.out.println("Cliente: " + clienteMaiorParcela);

        System.out.printf("Parcela: R$ %.2f%n", maiorParcela);
        System.out.println("\n========================================================\n");
        leia.close();
    }
}
