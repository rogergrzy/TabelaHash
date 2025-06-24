# Análise de Desempenho de Tabelas Hash em Java

## Visão Geral

Este projeto acadêmico tem como objetivo a implementação e análise de desempenho de duas arquiteturas clássicas de Tabela Hash em Java: **Encadeamento Separado** e **Endereçamento Aberto com Sondagem Linear**.

O estudo compara o comportamento dessas estruturas sob diferentes cargas de dados e tamanhos de tabela, utilizando três funções de hash distintas. As métricas de desempenho analisadas foram o tempo de inserção, o número de colisões, o tempo de busca e o número médio de comparações por busca.

## Estruturas Implementadas

Foram desenvolvidas do zero duas implementações de Tabela Hash para tratar colisões:

1.  **Tabela Hash com Encadeamento Separado (Separate Chaining):** Em caso de colisão, os registros com o mesmo índice de hash são armazenados em uma lista encadeada, que é anexada àquela posição do vetor principal. Esta abordagem permite que o fator de carga da tabela ultrapasse 1.

2.  **Tabela Hash com Endereçamento Aberto (Open Addressing) e Sondagem Linear:** Em caso de colisão, a estrutura procura a próxima posição livre no próprio vetor de forma sequencial (`indice + 1`, `indice + 2`, ...). Esta abordagem é limitada a um fator de carga máximo de 1 e é suscetível ao fenômeno de agrupamento primário.

## Metodologia

O experimento foi conduzido em um ambiente controlado para garantir a comparabilidade dos resultados.

* **Linguagem:** Java
* **Geração de Dados:** Conjuntos de dados com 10.000, 100.000 e 1.000.000 de registros foram gerados aleatoriamente. Cada registro contém um código único de 9 dígitos. Uma `seed` estática (`12345L`) foi utilizada para garantir que os mesmos conjuntos de dados fossem usados em todos os testes.
* **Tamanhos da Tabela:** 1.000, 10.000 e 100.000 posições.
* **Funções de Hash Testadas:**
    1.  **Resto da Divisão:** `chave % tamanhoTabela`
    2.  **Multiplicação:** `(int) (tamanho * ((chave * A) % 1))` com `A ≈ 0.618`
    3.  **Dobramento:** A chave é dividida em 3 partes e combinada. A análise dos resultados revela diferentes níveis de eficácia desta função nas duas arquiteturas testadas.
* **Métricas Coletadas:**
    * **Tempo de Inserção/Busca:** Medido em milissegundos (ms).
    * **Colisões:** Contadas durante a fase de inserção.
    * **Comparações:** Contadas durante a fase de busca e apresentadas como uma média por operação.

## Como Executar o Projeto

1.  Clone o repositório.
2.  Certifique-se de ter o JDK (Java Development Kit) instalado.
3.  Compile todos os arquivos `.java` através de um terminal na pasta do projeto:
    ```bash
    javac *.java
    ```
4.  Execute a classe principal que rege o experimento:
    ```bash
    java Main
    ```

## Resultados

Os dados a seguir foram coletados durante a execução dos testes.

**Nota:** O Fator de Carga (`α`) é a razão entre o Número de Registros (N) e o Tamanho da Tabela (M).

### Tabela 1: Desempenho - Encadeamento Separado

| N (Dados) | M (Tabela) | `α` (Carga) | Função Hash | Tempo Inserção (ms) | Colisões | Tempo Busca (ms) | Comp. Média |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| 10.000 | 1.000 | 10.0 | Divisão | 1 | 9000 | 1 | 5 |
| 10.000 | 1.000 | 10.0 | Multiplicação | 0 | 9000 | 0 | 6 |
| 10.000 | 1.000 | 10.0 | Dobramento | 0 | 9000 | 0 | 6 |
| 10.000 | 10.000 | 1.0 | Divisão | 0 | 3648 | 0 | 1 |
| 10.000 | 10.000 | 1.0 | Multiplicação | 0 | 3660 | 0 | 1 |
| 10.000 | 10.000 | 1.0 | Dobramento | 0 | 7856 | 0 | 3 |
| 10.000 | 100.000 | 0.1 | Divisão | 0 | 474 | 0 | 1 |
| 10.000 | 100.000 | 0.1 | Multiplicação | 0 | 461 | 0 | 1 |
| 10.000 | 100.000 | 0.1 | Dobramento | 0 | 7856 | 0 | 3 |
| 100.000 | 1.000 | 100.0 | Divisão | 6 | 99000 | 20 | 50 |
| 100.000 | 1.000 | 100.0 | Multiplicação | 1 | 99000 | 63 | 51 |
| 100.000 | 1.000 | 100.0 | Dobramento | 1 | 99000 | 59 | 50 |
| 100.000 | 10.000 | 10.0 | Divisão | 0 | 90000 | 6 | 5 |
| 100.000 | 10.000 | 10.0 | Multiplicação | 1 | 90002 | 7 | 6 |
| 100.000 | 10.000 | 10.0 | Dobramento | 0 | 97317 | 36 | 29 |
| 100.000 | 100.000 | 1.0 | Divisão | 2 | 36735 | 2 | 1 |
| 100.000 | 100.000 | 1.0 | Multiplicação | 3 | 36902 | 4 | 1 |
| 100.000 | 100.000 | 1.0 | Dobramento | 1 | 97317 | 36 | 29 |
| 1.000.000| 1.000 | 1000.0| Divisão | 9 | 999000 | 17253 | 500 |
| 1.000.000| 1.000 | 1000.0| Multiplicação | 11 | 999000 | 13601 | 500 |
| 1.000.000| 1.000 | 1000.0| Dobramento | 11 | 999000 | 22278 | 500 |
| 1.000.000| 10.000 | 100.0 | Divisão | 12 | 990000 | 1620 | 50 |
| 1.000.000| 10.000 | 100.0 | Multiplicação | 14 | 990000 | 2218 | 50 |
| 1.000.000| 10.000 | 100.0 | Dobramento | 11 | 997179 | 22207 | 284 |
| 1.000.000| 100.000 | 10.0 | Divisão | 42 | 900005 | 367 | 5 |
| 1.000.000| 100.000 | 10.0 | Multiplicação | 20 | 900002 | 454 | 5 |
| 1.000.000| 100.000 | 10.0 | Dobramento | 9 | 997179 | 14968 | 284 |


### Tabela 2: Desempenho - Sondagem Linear

| N (Dados) | M (Tabela) | `α` (Carga) | Função Hash | Tempo Inserção (ms) | Colisões | Tempo Busca (ms) | Comp. Média |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| 10.000 | 10.000 | 1.0 | Divisão | 2 | 494758 | 1 | 50 |
| 10.000 | 10.000 | 1.0 | Multiplicação | 2 | 619360 | 1 | 62 |
| 10.000 | 10.000 | 1.0 | Dobramento | 96 | 38261984 | 95 | 3827 |
| 10.000 | 100.000 | 0.1 | Divisão | 0 | 542 | 0 | 1 |
| 10.000 | 100.000 | 0.1 | Multiplicação | 0 | 527 | 0 | 1 |
| 10.000 | 100.000 | 0.1 | Dobramento | 95 | 38256561 | 96 | 3826 |
| 100.000 | 100.000| 1.0 | Divisão | 57 | 22182102 | 57 | 222 |
| 100.000 | 100.000| 1.0 | Multiplicação | 56 | 22098453 | 58 | 221 |
| 100.000 | 100.000| 1.0 | Dobramento | 12285 | 4863083761 | 12336 | 48631 |
| *Outros testes com α > 1 foram ignorados por serem impossíveis nesta arquitetura.* | | | | | | | |

## Análise dos Resultados

A análise dos dados coletados revela insights importantes sobre o comportamento das estruturas de dados testadas.

### 1. Análise do Encadeamento Separado: Robustez e Previsibilidade
A Tabela 1 demonstra a principal vantagem do Encadeamento Separado: sua robustez. A estrutura lida com qualquer fator de carga, e seu desempenho degrada de forma previsível. Observe que a `Comp. Média` para as funções de Divisão e Multiplicação é aproximadamente `α/2`, o que é consistente com a teoria. Por exemplo, para `α=100`, a média de comparações foi ~50; para `α=1000`, foi ~500. A função de `Dobramento`, no entanto, consistentemente apresentou um desempenho inferior, com mais colisões e tempos de busca maiores, indicando uma distribuição de chaves menos eficiente mesmo nesta arquitetura.

### 2. Análise da Sondagem Linear e o Agrupamento Primário
A Tabela 2 expõe a grande fraqueza da Sondagem Linear. O caso com N=100.000 e M=100.000 (`α=1.0`) é emblemático: as funções de Divisão e Multiplicação geraram mais de **22 milhões de colisões** para inserir 100.000 itens. Isso demonstra o efeito do **agrupamento primário (primary clustering)**, onde chaves diferentes que colidem na mesma região criam longos blocos de células ocupadas, exigindo um número massivo de sondagens para resolver colisões subsequentes.

### 3. O Caso Crítico da Função de Dobramento na Sondagem Linear
Os resultados da função de `Dobramento` na Tabela 2 são a mais clara demonstração da importância de uma função de hash. Os valores de colisão (na casa dos **bilhões**) e comparações (dezenas de milhares) não são um erro de medição, mas a consequência de uma falha de design na implementação inicial da função. Ela gerava uma péssima distribuição, mapeando todas as chaves para uma faixa muito pequena de índices. Para a Sondagem Linear, isso cria um "engarrafamento" quase infinito, tornando a estrutura inutilizável. Mesmo no cenário com `α=0.1` (tabela 90% vazia), a função falhou catastroficamente, provando que o problema era a má distribuição, e não o preenchimento da tabela.

### 4. Comparativo Direto: Robustez vs. Sensibilidade
Comparando o caso `N=100.000, M=100.000` (`α=1.0`) entre as duas tabelas:
* **Encadeamento:** `Comp. Média` ≈ 1 (para as boas funções).
* **Sondagem Linear:** `Comp. Média` ≈ 222.

Isso mostra que, para uma tabela cheia, o Encadeamento é ordens de magnitude mais eficiente, pois as colisões são isoladas em pequenas listas, enquanto na Sondagem Linear elas afetam grandes regiões da tabela.

## Conclusão

Este trabalho permitiu validar empiricamente os conceitos teóricos de Tabelas Hash. Conclui-se que não existe uma "melhor" solução universal, mas sim uma escolha baseada em trade-offs:

1.  **Encadeamento Separado** é a escolha mais segura, robusta e flexível, ideal para cenários onde o número de elementos pode variar muito ou exceder a capacidade inicial da tabela. Seu desempenho degrada de forma suave e previsível.

2.  **Endereçamento Aberto (Sondagem Linear)** só é viável em cenários com fator de carga rigorosamente controlado e baixo (`α << 0.7`). Sua sensibilidade extrema ao agrupamento e a falhas na função de hash o tornam uma opção de alto risco, apesar de sua potencial vantagem em uso de memória.

3.  **A Qualidade da Função de Hash é Soberana:** O projeto demonstrou que uma função de hash mal implementada pode inutilizar completamente a estrutura de dados, independentemente da arquitetura de tratamento de colisões. A performance de uma tabela hash é, antes de tudo, um reflexo da qualidade de sua função de espalhamento.

---
**Autor:** *[Seu Nome Aqui]*
