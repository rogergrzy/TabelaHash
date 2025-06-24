public class ListaEncadeada {
    private No inicio;

    public ListaEncadeada() {
        this.inicio = null;
    }

    public boolean estaVazia() {
        return this.inicio == null;
    }

    public void inserir(Registro reg) {
        No novoNo = new No(reg);
        novoNo.proximo = this.inicio;
        this.inicio = novoNo;
    }

    public ResultadoBusca buscar(int codigo) {
        No atual = this.inicio;
        int comparacoes = 0;

        while (atual != null) {
            comparacoes++;
            if (atual.dado.codigo == codigo) {
                return new ResultadoBusca(atual.dado, comparacoes);
            }
            atual = atual.proximo;
        }

        return new ResultadoBusca(null, comparacoes);
    }
}

class ResultadoBusca {
    public Registro registro;
    public int comparacoes;

    public ResultadoBusca(Registro registro, int comparacoes) {
        this.registro = registro;
        this.comparacoes = comparacoes;
    }
}