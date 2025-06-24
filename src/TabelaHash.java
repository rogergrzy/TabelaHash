enum FuncaoHash {
    DIVISAO,
    MULTIPLICACAO,
    DOBRAMENTO
}

public class TabelaHash {
    private ListaEncadeada[] tabela;
    private int tamanho;
    private FuncaoHash funcaoHashUtilizada;
    private long colisoes;

    private final double A = 0.6180339887;

    public TabelaHash(int tamanho, FuncaoHash funcao) {
        this.tamanho = tamanho;
        this.tabela = new ListaEncadeada[tamanho];

        for (int i = 0; i < tamanho; i++) {
            tabela[i] = new ListaEncadeada();
        }

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
        int indice = hash(reg.codigo);

        if (!tabela[indice].estaVazia()) {
            this.colisoes++;
        }

        tabela[indice].inserir(reg);
    }

    public ResultadoBusca buscar(int codigo) {
        int indice = hash(codigo);
        return tabela[indice].buscar(codigo);
    }

    public long getColisoes() {
        return this.colisoes;
    }
}
