import java.util.ArrayList;
import java.util.List;
import java.util.Deque;
import java.util.ArrayDeque;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;


class NodeLista {
    String dado;
    NodeLista proximo;

    NodeLista(String dado){
        this.dado =dado;
    }
}
class ListaDinamica {
    NodeLista cabeca;

    void inserirFim(String palavra){
        NodeLista novo = new NodeLista(palavra);
        if (cabeca == null){
            cabeca = novo;
        } else {
            NodeLista atual = cabeca;
            while (atual.proximo != null) atual = atual.proximo;
            atual.proximo = novo;
        }

    }
    List<String> inverter(){
        List<String> lista = new ArrayList<>();
        NodeLista atual = cabeca;
        while (atual != null){
            lista.add(0, atual.dado);
            atual = atual.proximo;
        }
        return lista;
    }

}
class NodeAVL {
    String dado;
    String hash;
    NodeAVL esq, dir;
    int altura;

    NodeAVL(String dado){
        this.dado = dado;
        this.altura = 1;
    }
}
class ArvoreAVL {
    NodeAVL raiz;

    private int altura(NodeAVL n) {
        return n == null ? 0 : n.altura;
    }

    private int fator(NodeAVL n){
        return n == null ? 0 : altura(n.esq) - altura(n.dir);
    }

    private void atualizarAltura(NodeAVL n) {
        n.altura = 1 + Math.max(altura(n.esq), altura(n.dir));
    }

    private NodeAVL rotacaoDireita(NodeAVL y) {
        NodeAVL x = y.esq;
        NodeAVL T2 = x.dir;
        x.dir = y;
        y.esq = T2;
        atualizarAltura(y);
        atualizarAltura(x);
        return x;
    }

    private NodeAVL rotacaoEsquerda(NodeAVL x) {
        NodeAVL y = x.dir;
        NodeAVL T2 = y.esq;
        y.esq = x;
        x.dir = T2;
        atualizarAltura(x);
        atualizarAltura(y);
        return y;
    }

    private NodeAVL inserir(NodeAVL no, String palavra) {
        if (no == null) return new NodeAVL(palavra);

        int cmp = palavra.compareToIgnoreCase(no.dado);
        if (cmp < 0) no.esq = inserir(no.esq, palavra);
        else if (cmp > 0) no.dir = inserir(no.dir, palavra);
        else return no;

        atualizarAltura(no);
        int fb = fator(no);

        if (fb > 1 && palavra.compareToIgnoreCase(no.esq.dado) < 0)
            return rotacaoDireita(no);
        if (fb < -1 && palavra.compareToIgnoreCase(no.dir.dado) > 0)
            return rotacaoEsquerda(no);
        if (fb > 1 && palavra.compareToIgnoreCase(no.esq.dado) > 0){
            no.esq = rotacaoEsquerda(no.esq);
            return rotacaoDireita(no);
        }
        if (fb < -1 && palavra.compareToIgnoreCase(no.dir.dado) < 0){
            no.dir = rotacaoDireita(no.dir);
            return rotacaoEsquerda(no);
        }
        return no;
    }
    void inserir (String palavra) {
        raiz = inserir(raiz, palavra);
    }

    private String computarHash(NodeAVL no) throws Exception {
        if (no == null) return "";

        String hashEsq = computarHash(no.esq);
        String hashDir = computarHash(no.dir);
        String hashNo = sha1(no.dado);

        String combinado;
        if (hashEsq.isEmpty() && hashDir.isEmpty()){
            combinado = hashNo;
        } else if (hashEsq.isEmpty()){
            combinado = sha1(hashDir + hashNo);
        } else if (hashDir.isEmpty()){
            combinado = sha1(hashEsq + hashNo);
        } else {
            combinado = sha1(hashEsq + hashDir + hashNo);
        }
        no.hash = combinado;
        return combinado;
    }

    String gerarHashRaiz() throws Exception {
        return computarHash(raiz);
    }

    static String sha1(String input) throws Exception {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
class PilhaAVL {
    private java.util.Deque<ArvoreAVL> pilha = new java.util.ArrayDeque<>();

    void empilhar(ArvoreAVL arvore){
        pilha.push(arvore);
    }
    ArvoreAVL desempilhar(){
        return pilha.pop();
    }

    boolean vazia(){
        return pilha.isEmpty();
    }
}
public class Main {
    public static void main(String[] args) throws Exception{

        String caminhoEntrada = "src/texto.txt";
        List<String> linhas = Files.readAllLines(Paths.get(caminhoEntrada));

        PilhaAVL pilha = new PilhaAVL();

        for (String linha : linhas) {
            if (linha.trim().isEmpty()) continue;
            ListaDinamica lista = new ListaDinamica();
            for (String token : linha.split("\\s+")){
                if (!token.isEmpty()) lista.inserirFim(token);
            }

            ArvoreAVL arvore = new ArvoreAVL();
            for (String palavra : lista.inverter()){
                arvore.inserir(palavra);
            }

            pilha.empilhar(arvore);
        }

        StringBuilder resultado = new StringBuilder();
        while (!pilha.vazia()) {
            ArvoreAVL arvore = pilha.desempilhar();
            resultado.append(arvore.gerarHashRaiz()).append("\n");
        }

        String saida = resultado.toString().trim();
        Files.writeString(Paths.get("codigo_autenticacao.txt"), saida);
        System.out.println("=== Código de Autenticação Gerado ===");
        System.out.println(saida);

    }
}