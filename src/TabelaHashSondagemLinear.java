public class TabelaHashSondagemLinear {
    private Registro[] tabela;
    private int tamanho;
    private int quantidadeAtual;
    private FuncaoHash funcaoHashUtilizada;
    private long colisoes;

    private final double A = 0.6180339887;

    public TabelaHashSondagemLinear(int tamanho, FuncaoHash funcao) {
        this.tamanho = tamanho;
        this.tabela = new Registro[tamanho];
        this.quantidadeAtual = 0;
        this.funcaoHashUtilizada = funcao;
        this.colisoes = 0;
    }

    private int hash(int chave) {
        switch (funcaoHashUtilizada) {
            case DIVISAO:
                return hashDivisao(chave);
            case MULTIPLICACAO:
                return hashMultiplicacao(chave);
            case DOBRAMENTO:
                return hashDobramento(chave);
            default:
                return 0;
        }
    }

    private int hashDivisao(int chave) {
        int resultado = chave % tamanho;
        return resultado >= 0 ? resultado : resultado + tamanho;
    }

    private int hashMultiplicacao(int chave) {
        double produto = chave * A;
        double parteFracionaria = produto - (long) produto;
        return (int) (tamanho * parteFracionaria);
    }

    private int hashDobramento(int chave) {
        int p1 = chave / 1000000;
        int p2 = (chave / 1000) % 1000;
        int p3 = chave % 1000;

        int soma = p1 + p2 + p3;
        return soma % tamanho;
    }

    public void inserir(Registro reg) {
        if (quantidadeAtual >= tamanho) {
            System.err.println("ERRO: Tabela Hash cheia!");
            return;
        }

        int indice = hash(reg.codigo);

        while (tabela[indice] != null) {
            this.colisoes++;
            indice = (indice + 1) % tamanho;
        }

        tabela[indice] = reg;
        quantidadeAtual++;
    }

    public ResultadoBusca buscar(int codigo) {
        int comparacoes = 0;
        int indice = hash(codigo);

        while (tabela[indice] != null) {
            comparacoes++;
            if (tabela[indice].codigo == codigo) {
                return new ResultadoBusca(tabela[indice], comparacoes);
            }

            indice = (indice + 1) % tamanho;

            if (comparacoes >= tamanho) {
                break;
            }
        }

        return new ResultadoBusca(null, comparacoes);
    }

    public long getColisoes() {
        return this.colisoes;
    }
}
