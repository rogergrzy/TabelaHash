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
    3.  **Dobramento (versão corrigida):** A chave é dividida em 3 partes, que são combinadas usando multiplicação por números primos para garantir um bom espalhamento.
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
| 10.000 | 10.000 | 1.0 | Divisão | *[Preencha]* | *[Preencha]* | *[Preencha]* | *[Preencha]* |
| 10.000 | 10.000 | 1.0 | Multiplicação | *[...]* | *[...]* | *[...]* | *[...]* |
| 10.000 | 10.000 | 1.0 | Dobramento | *[...]* | *[...]* | *[...]* | *[...]* |
| 100.000 | 10.000 | 10.0 | Divisão | *[...]* | *[...]* | *[...]* | *[...]* |
| 100.000 | 10.000 | 10.0 | Multiplicação | *[...]* | *[...]* | *[...]* | *[...]* |
| 100.000 | 10.000 | 10.0 | Dobramento | *[...]* | *[...]* | *[...]* | *[...]* |
| 1.000.000| 100.000 | 10.0 | Divisão | *[...]* | *[...]* | *[...]* | *[...]* |
| ... | ... | ... | ... | *[...]* | *[...]* | *[...]* | *[...]* |

### Tabela 2: Desempenho - Sondagem Linear

| N (Dados) | M (Tabela) | `α` (Carga) | Função Hash | Tempo Inserção (ms) | Colisões | Tempo Busca (ms) | Comp. Média |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| 10.000 | 10.000 | 1.0 | Divisão | *[Preencha]* | *[Preencha]* | *[Preencha]* | *[Preencha]* |
| 10.000 | 10.000 | 1.0 | Multiplicação | *[...]* | *[...]* | *[...]* | *[...]* |
| 10.000 | 10.000 | 1.0 | Dobramento | *[...]* | *[...]* | *[...]* | *[...]* |
| 100.000 | 100.000| 1.0 | Divisão | *[...]* | *[...]* | *[...]* | *[...]* |
| 100.000 | 100.000| 1.0 | Multiplicação | *[...]* | *[...]* | *[...]* | *[...]* |
| 100.000 | 100.000| 1.0 | Dobramento | *[...]* | *[...]* | *[...]* | *[...]* |
| *Outros testes com α > 1 foram ignorados por serem impossíveis nesta arquitetura.* | | | | | | | |

## Análise dos Resultados

A análise dos dados coletados revela insights importantes sobre o comportamento das estruturas de dados testadas.

### 1. O Impacto Crítico do Fator de Carga (`α`)

O fator de carga provou ser o indicador mais significativo de desempenho.
* **Para ambas as arquiteturas:** Conforme `α` se aproxima de 1, o número de colisões e o tempo de busca aumentam drasticamente.
* **Encadeamento Separado:** Consegue lidar com `α > 1`, mas ao custo de um desempenho que degrada linearmente, já que as listas encadeadas se tornam muito longas (como visto no teste com N=1.000.000 e M=100.000, onde `α=10`).
* **Sondagem Linear:** É extremamente sensível a altos fatores de carga. Testes com `α` próximo de 1 (e.g., N=100.000, M=100.000) mostraram um número altíssimo de colisões devido ao **agrupamento primário**, onde longos blocos de células ocupadas se formam, exigindo muitas sondagens para encontrar um espaço livre. Cenários com `α > 1` são impossíveis de executar.

### 2. A Importância de uma Boa Função de Hash: O Caso da Função de Dobramento

Um dos resultados mais educativos do projeto foi a falha inicial da função de Dobramento.
* **Problema:** A implementação original gerava uma péssima distribuição de chaves, mapeando todos os registros para uma faixa muito pequena de índices (cerca de 3.000 posições), independentemente do tamanho da tabela.
* **Impacto:** Isso causou uma catástrofe de desempenho na Sondagem Linear, com bilhões de colisões e tempos de execução ordens de magnitude maiores que as outras funções.
* **Solução:** A função foi corrigida para "misturar" melhor as partes da chave, resultando em um espalhamento uniforme e desempenho competitivo.
* **Lição:** Este caso demonstra de forma prática que a qualidade e a correta implementação da função de hash são tão cruciais para o desempenho quanto a própria arquitetura de tratamento de colisões.

### 3. Comparativo: Encadeamento vs. Sondagem Linear

* **Robustez:** O Encadeamento Separado é mais robusto e previsível. Seu desempenho degrada de forma mais "suave" com o aumento da carga e ele não possui uma limitação física no número de elementos.
* **Performance em Cenário Ideal:** Para fatores de carga baixos (`α << 0.5`), a Sondagem Linear pode ser teoricamente mais rápida devido à melhor localidade de cache (todos os dados estão no mesmo vetor). No entanto, sua sensibilidade ao agrupamento a torna uma escolha de risco se o fator de carga não for rigorosamente controlado.
* **Uso de Memória:** A Sondagem Linear é mais econômica em memória, pois não possui a sobrecarga dos nós e ponteiros das listas encadeadas.

## Conclusão

Este trabalho permitiu validar empiricamente os conceitos teóricos de Tabelas Hash. Conclui-se que não existe uma "melhor" solução universal, mas sim uma escolha baseada em trade-offs:

1.  **Encadeamento Separado** é a escolha mais segura e flexível, ideal para cenários onde o número de elementos pode variar muito ou exceder a capacidade inicial da tabela.
2.  **Endereçamento Aberto (Sondagem Linear)** pode ser uma alternativa de alto desempenho se o fator de carga for mantido baixo e controlado, e se o uso eficiente de memória for um requisito primordial. Sua simplicidade é atraente, mas esconde uma complexidade de desempenho relacionada ao agrupamento.

Finalmente, o projeto reforça que o sucesso de uma Tabela Hash depende igualmente de três pilares: uma **estrutura de tratamento de colisões** bem escolhida, uma **função de hash** de alta qualidade que garanta o espalhamento uniforme das chaves e um **fator de carga** mantido em níveis adequados para a aplicação.

---
**Autor:** *[Seu Nome Aqui]*
