# Trabalho vivencial - Estrutura de Dados (N692)

## Professor: Prof. Msc. Paulo Cirillo Souza Barbosa <br><br> Equipe (50):

- Karoline de Sousa Alves Benigno
- Luis Filipy Pereira de Sousa
- Rafael Gomes
- Eduardo Mesquita de Freitas
- Luís Miguel Frazão de Sousa
- Julia Santos

## Descrição do projeto:

1. Realizar a leitura de um arquivo textual, em arquivo .TXT. <b>Obs: A equipe está livre para produzir um parágrafo com um texto que seja em português-BR que possua no mínimo 30 linhas;</b>
2. Cada linha do texto é consumida individualmente e as palavras presentes nessa linha textual são inseridas em uma lista dinâmica. Nesse caso, as palavras são obtidas ao identificar os espaços em branco entre textos.
3. Deve-se em seguida, acessar os elementos da lista no sentido reverso (do final até o início) e inserir tais elementos um a um em uma  ́arvore AVL. Para realizar as comparações lexicográficas Strings, pode-se utilizar o método compareToIgnoreCase(). Além disso, deve-se desconsiderar quaisquer palavras duplicadas na String em questão.
4. Após o cadastro de todas as Strings para aquela linha, deve-se inserir a árvore AVL em uma pilha.
5. Os procedimentos devem ser repetidos para todas as linhas no texto.
6. Após a pilha de  ́arvores ser construída, deve-se desempilhar as árvores de uma em uma, e gerar o código hash para aquela árvore desempilhada.
7. Cada hash gerado de cada uma das árvores deve ser concatenado e separados por uma quebra de linha.
