import hashlib
from estruturas import ArvoreAVL

def calcular_hash_sha1(texto):
    return hashlib.sha1(texto.encode('utf-8')).hexdigest()

def computar_hash_arvore(nodo):
    if nodo is None:
        return ""

    h_esq = computar_hash_arvore(nodo.esquerda)
    h_dir = computar_hash_arvore(nodo.direita)
    h_p = calcular_hash_sha1(nodo.palavra)

    combinacao = h_esq + h_dir + h_p
    return calcular_hash_sha1(combinacao)

def processar_autenticador(caminho_arquivo):
    with open(caminho_arquivo, 'r', encoding='utf-8') as f:
        for linha in f:
            palavras = linha.split() 
            arvore = ArvoreAVL()
            
            for p in reversed(palavras):
                arvore.inserir(p)
            
            hash_linha = computar_hash_arvore(arvore.raiz)
            print(hash_linha)