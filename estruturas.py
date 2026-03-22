class NodoAVL:
    def __init__(self, palavra):
        self.palavra = palavra
        self.esquerda = None
        self.direita = None
        self.altura = 1

class ArvoreAVL:
    def __init__(self):
        self.raiz = None

    def obter_altura(self, nodo):
        return nodo.altura if nodo else 0

    def inserir(self, palavra):
        self.raiz = self._inserir_recursivo(self.raiz, palavra)

    def _inserir_recursivo(self, nodo, palavra):
        if not nodo:
            return NodoAVL(palavra)
        
        p_lower = palavra.lower()
        n_lower = nodo.palavra.lower()

        if p_lower < n_lower:
            nodo.esquerda = self._inserir_recursivo(nodo.esquerda, palavra)
        elif p_lower > n_lower:
            nodo.direita = self._inserir_recursivo(nodo.direita, palavra)
        else:
            return nodo

        nodo.altura = 1 + max(self.obter_altura(nodo.esquerda), self.obter_altura(nodo.direita))
        return self._balancear(nodo)

    def _balancear(self, nodo):
        return nodo