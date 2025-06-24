public class Main {
    public static void main(String[] args) {
        int[] tamanhosTabela = {1000, 10000, 100000};
        int[] tamanhosDados = {10000, 100000, 1000000};
        FuncaoHash[] funcoes = {FuncaoHash.DIVISAO, FuncaoHash.MULTIPLICACAO, FuncaoHash.DOBRAMENTO};
        String[] tiposTabela = {"ENCADEAMENTO", "SONDAGEM_LINEAR"};
        long seed = 12345L;


        System.out.println("Iniciando análise de desempenho de Tabelas Hash");
        System.out.println("Seed para geração de dados: " + seed);
        System.out.println("-------------------------------------------------");

        for (String tipo : tiposTabela) {
            System.out.println("\n########## TESTANDO TABELA DO TIPO: " + tipo + "##########");

            for (int tamanhoDados : tamanhosDados) {
                System.out.println("\n===== CONJUNTO DE DADOS COM " + tamanhoDados + " REGISTROS =====");

                Registro[] dados = GeradorDados.gerarRegistros(tamanhoDados, seed);

                for (int tamanhoTabela : tamanhosTabela) {
                    System.out.println("\n  --- Tabela Hash com Tamanho " + tamanhoTabela + " ---");

                    if (tipo.equals("SONDAGEM_LINEAR") && tamanhoDados > tamanhoTabela) {
                        System.out.println("\n  --- Tabela com Tamanho " + tamanhoTabela + " | CONJUNTO DE DADOS " + tamanhoDados + " -> Ignorando (Fator de Carga > 1)");
                        continue;
                    }

                    System.out.println("\n  --- Tabela com Tamanho " + tamanhoTabela + " | Conjunto de dados: " + tamanhoDados);

                    for (FuncaoHash funcao : funcoes) {
                        if (tipo.equals("ENCADEAMENTO")) {
                            testarTabelaEncadeamento(tamanhoTabela, tamanhoDados, funcao, dados);
                        } else {
                            testarTabelaSondagemLinear(tamanhoTabela, tamanhoDados, funcao, dados);
                        }
                    }
                }
            }
        }
    }

    public static void testarTabelaEncadeamento(int tamanhoTabela, int tamanhoDados, FuncaoHash funcao, Registro[] dados) {
        TabelaHash tabelaHash = new TabelaHash(tamanhoTabela, funcao);

        long inicioInsercao = System.nanoTime();
        for (int i = 0; i < tamanhoDados; i++) {
            tabelaHash.inserir(dados[i]);
        }
        long fimInsercao = System.nanoTime();
        long tempoInsercao = (fimInsercao - inicioInsercao) / 1000000;
        long colisoes = tabelaHash.getColisoes();

        long comparacoesBusca = 0;
        long tempoBuscaTotal = 0;
        int numBuscas = 5;

        for (int i = 0; i < numBuscas; i++) {
            comparacoesBusca = 0;
            long inicioBusca = System.nanoTime();
            for (int j = 0; j < tamanhoDados; j++) {
                ResultadoBusca res = tabelaHash.buscar(dados[j].codigo);
                comparacoesBusca += res.comparacoes;
            }
            long fimBusca = System.nanoTime();
            tempoBuscaTotal += (fimBusca - inicioBusca);
        }

        long tempoBuscaMedio = (tempoBuscaTotal / numBuscas) / 1000000;
        long mediaComparacoes = comparacoesBusca / tamanhoDados;

        System.out.printf("    Função: %-15s | Inserção: %5d ms | Colisões: %8d | Busca: %5d ms | Comp. Média: %d\n",
                funcao, tempoInsercao, colisoes, tempoBuscaMedio, mediaComparacoes);
    }

    public static void testarTabelaSondagemLinear(int tamanhoTabela, int tamanhoDados, FuncaoHash funcao, Registro[] dados) {
        TabelaHashSondagemLinear tabelaHash = new TabelaHashSondagemLinear(tamanhoTabela, funcao);

        long inicioInsercao = System.nanoTime();
        for (int i = 0; i < tamanhoDados; i++) {
            tabelaHash.inserir(dados[i]);
        }
        long fimInsercao = System.nanoTime();
        long tempoInsercao = (fimInsercao - inicioInsercao) / 1000000;
        long colisoes = tabelaHash.getColisoes();

        long comparacoesBusca = 0;
        long tempoBuscaTotal = 0;
        int numBuscas = 5;

        for (int i = 0; i < numBuscas; i++) {
            comparacoesBusca = 0;
            long inicioBusca = System.nanoTime();
            for (int j = 0; j < tamanhoDados; j++) {
                ResultadoBusca res = tabelaHash.buscar(dados[j].codigo);
                comparacoesBusca += res.comparacoes;
            }
            long fimBusca = System.nanoTime();
            tempoBuscaTotal += (fimBusca - inicioBusca);
        }

        long tempoBuscaMedio = (tempoBuscaTotal / numBuscas) / 1000000;
        long mediaComparacoes = comparacoesBusca / tamanhoDados;

        System.out.printf("    Função: %-15s | Inserção: %5d ms | Colisões: %8d | Busca: %5d ms | Comp. Média: %d\n",
                funcao, tempoInsercao, colisoes, tempoBuscaMedio, mediaComparacoes);
    }
}