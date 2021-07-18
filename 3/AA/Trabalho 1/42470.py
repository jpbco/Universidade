# Imports

import numpy as np
from sklearn.model_selection import train_test_split


def Homogeneous(data):
    """
    Verifica se as instâncias no conjunto de dados são homogéneas.

    Utiliza a função np.unique(data[:, -1]) que retorna um array ordenado com os elementos únicos
    da última coluna do conjunto de dados (coluna das classes) e depois retorna True caso apenas
    exista apenas 1 elemento (classe) nesse array.


    Parameters
    ----------
    data (numpy.ndarray) : Conjunto de dados

    Returns
    -------
    True | False
    """
    classes = np.unique(data[:, -1])
    return len(classes) == 1


def Label(data):
    """
    Devolve a etiqueta mais apropriada para um conj de dados(etiqueta da classe maioritária).

    Utiliza a função np.unique(data[:, -1], return_counts=True) que retorna um array ordenado com
    os elementos únicos da última coluna do conjunto de dados (coluna das classes) e um array ordenado
    com o número de ocurrências dos elementos do primeiro array.
    Para devolver a etiqueta da classe maioritária basta devolver o valor do array com os elementos únicos
    cujo índice é igual ao índice do maior elemento do array com o número de ocurrências.

    Parameters
    ----------
    data (numpy.ndarray) : Conjunto de dados

    Returns
    -------
    Etiqueta : String
    """
    classes, classes_count = np.unique(data[:, -1], return_counts=True)
    return classes[classes_count.argmax()]


def split_data(data, column, value):
    """

    Referências: https://stackoverflow.com/a/21757819

    Divide um (numpy.ndarray) em dois com base num valor de uma coluna à escolha.
    Um dos arrays irá conter todas as linhas do array original cujo valor da coluna escolhida
    corresponde ao que foi passado como argumento, o outro array irá conter o resto das linhas.

    Parameters
    ----------
    data (numpy.ndarray) : Conjunto de dados

    column : (int)

    value : (Any)


    Returns
    -------

    data_equals = (numpy.ndarray)

    data_differs = (numpy.ndarray)
    """
    data_equals = data[data[:, column] == value]
    data_differs = data[data[:, column] != value]

    return data_equals, data_differs


def Entropy(data):
    """
    Referências:
    https://en.wikipedia.org/wiki/ID3_algorithm#Entropy
    https://www.quora.com/What-is-difference-between-Gini-Impurity-and-Entropy-in-Decision-Tree

    data – conjunto de exemplo de treino

    classes - conjunto de classes de data - informação retornada pela função
    np.unique(data[:, -1], return_counts=True) mas como não é utilizada não é guardada (uso de _)

    p - Array com as porções de elementos de uma classe em relação ao número de elementos em data
    """

    _, classes_count = np.unique(data[:, -1], return_counts=True)
    p = classes_count / sum(classes_count)
    return -sum(p * np.log2(p))


def Gini(data):
    """
    Referências:
    https://en.wikipedia.org/wiki/Decision_tree_learning#Gini_impurity
    https://www.quora.com/What-is-difference-between-Gini-Impurity-and-Entropy-in-Decision-Tree

    data – conjunto de exemplo de treino

    classes - conjunto de classes de data - informação retornada pela função
    np.unique(data[:, -1], return_counts=True) mas como não é utilizada não é guardada (uso de _)

    p - Array com as porções de elementos de uma classe em relação ao número de elementos em data


    """

    _, classes_count = np.unique(data[:, -1], return_counts=True)
    p = classes_count / sum(classes_count)

    return 1 - sum(p ** 2)


def ClassificationError(data):
    """
    Referências: https://youtu.be/a-SIt_X0_oY?t=2607

    data – conjunto de exemplo de treino

    classes - conjunto de classes de data - informação retornada pela função
    np.unique(data[:, -1], return_counts=True) mas como não é utilizada não é guardada (uso de _)

    p - Array com as porções de elementos de uma classe em relação ao número de elementos em data

    """
    _, classes_count = np.unique(data[:, -1], return_counts=True)
    p = classes_count / sum(classes_count)
    if p.any():
        return 1 - max(p)
    else:
        return 0


def Information_Gain(data_left, data_right, criterion):
    """
    Referências: https://www.quora.com/What-is-difference-between-Gini-Impurity-and-Entropy-in-Decision-Tree
    e https://datascience.stackexchange.com/a/5118

    N - número de exemplos no conjunto
    """

    N = len(data_left) + len(data_right)
    parent_impurity = left_impurity = right_impurity = 1

    if criterion == "entropy":
        parent_impurity = Entropy(np.append(data_left, data_right, axis=0))
        left_impurity = Entropy(data_left)
        right_impurity = Entropy(data_right)
    elif criterion == "gini":
        parent_impurity = Gini(np.append(data_left, data_right, axis=0))
        left_impurity = Gini(data_left)
        right_impurity = Gini(data_right)
    elif criterion == "error":
        parent_impurity = ClassificationError(np.append(data_left, data_right, axis=0))
        left_impurity = ClassificationError(data_left)
        right_impurity = ClassificationError(data_right)

    return (
        parent_impurity
        - ((len(data_left) / N) * left_impurity)
        - ((len(data_right) / N) * right_impurity)
    )


def determine_best_split(data, criterion):
    """
    Referências:
    https://towardsdatascience.com/understanding-decision-trees-for-classification-python-9663d683c952 e
    https://en.wikipedia.org/wiki/Decision_tree_learning#Information_gain

    A função começa por calcular todas as divisões possíveis e para isso calcula o dict possible_splits-

    column_count - número de atributos(menos 1 que corresponde às etiquetas) do conjunto de dados

    possible_splits (dict) - { I : La, I2 : La2, ... , In:Lan}

        I - (Atributo) - índice da coluna no conjunto de dados para a possível divisão
        La - (Lista de valores únicos para cada atributo) lista dos valores únicos da coluna I para a possível divisão



        Exemplo: Usando o conjunto de dados com o formato do ficheiro weather.nominal.csv

        data=   [['overcast', 'mild', 'high', 'TRUE', 'yes'],
                ['rainy', 'cool', 'normal', 'FALSE', 'yes'],
                ['rainy', 'cool', 'normal', 'TRUE', 'no'],
                ['sunny', 'hot', 'high', 'FALSE', 'no']]

        possible_splits =   {
                            0:['overcast', 'rainy', 'sunny'],
                            1:['cool', 'hot', 'mild'],
                            2:['high', 'normal'],
                            3:['FALSE', 'TRUE']
                            }

        Índice - Atributo
        0 - outlook
        1 - temperature
        2 - humidity
        3 - windy
        4 - play (classe)
    - e através dos valores de possible_splits({ I : La, I2 : La2, ... , In:Lan}) é chamada a função split_data()
    para divir o conjunto de dados em subconjuntos de acordo com os valores  em La,La2,..,Lan.
    Depois é calculado o Information Gain para cada subconjunto e é selecionado o subconjunto com o maior Information Gain
    e a função retorna o atributo e o seu respetivo valor que caso o conjunto de dados seja dividido com esses valores o
    valor de Information Gain é o maior.

    """
    possible_splits = {}
    column_count = np.size(data, 1) - 1
    for column_index in range(column_count):
        values = data[:, column_index]
        possible_splits[column_index] = np.unique(values)

    max_inf_gain = 0
    for column_index in possible_splits:
        for value in possible_splits[column_index]:
            data_left, data_right = split_data(data, column_index, value)
            current_inf_gain = Information_Gain(data_left, data_right, criterion)

            if max_inf_gain <= current_inf_gain:
                max_inf_gain = current_inf_gain
                best_split_column = column_index
                best_split_value = value

    return best_split_column, best_split_value


class Node(object):
    """
    Classe que representa um nó de uma árvore de decisão

    Atributos:
    prediction - um tuplo que guarda o índice da coluna onde foi realizada a decisão de
    separaração do conjunto de dados e o valor que foi usado para essa separação.
    left - Nó filho que contém os elementos do conjunto de dados cujos valores da posição guardada no tuplo prediction era iguais.
    right - Nó filho que contém os elementos do conjunto de dados cujos valores da posição guardada no tuplo prediction era diferentes.
    leaf - Indica se o nó é uma folha, se for o valor guardado no tuplo é a etiqueta que vai ser atribuída durante a previsão.
    """

    def __init__(self, prediction):
        self.prediction = prediction
        self.left = None
        self.right = None
        self.leaf = False


class DecisionTree(object):
    """
    Classe que representa uma árvore de decisão

    Atributos:
    criterion - indica a função de pureza que vai ser utilizada
    prune - indica se o modelo utiliza o método de reduced error pruning (não implementado)
    root - raiz da árvore de decisão
    """

    def __init__(self, criterion="entropy", prune=False):
        if criterion.lower() in ["gini", "entropy", "error"]:
            self.criterion = criterion.lower()
            self.prune = prune
            self.root = None
        else:
            raise SyntaxError("Criterion inválido")

    def fit(self, X, y):
        data = np.append(X, y.reshape(-1, 1), axis=1)
        self.root = self.growTree(data)

    def growTree(self, data):
        # Algoritmo ID3

        # Se os exemplos forem de uma única classe então é lhe atribuida a etiqueta dessa classe
        if Homogeneous(data):
            node = Node((None, Label(data)))
            node.leaf = True
            return node

        # Retorna o índice da coluna e o valor com os quais resulta a melhor partição do conjunto de dados.
        column, value = determine_best_split(data, self.criterion)
        data_left, data_right = split_data(data, column, value)

        # Se o subconjunto tiver vazio a folha é etiquetada
        if len(data_left) == 0 or len(data_right) == 0:
            t = Node(Label(data))
            t.leaf = True
            return t
        # Parte recursiva do algoritmo
        # Vai criando novos filhos até que todos os exemplos estejam etiquetados
        sub_tree = Node((column, value))
        sub_tree.left = self.growTree(data_left)
        sub_tree.right = self.growTree(data_right)

        return sub_tree

    def predict(self, data, node):
        # Função recursiva que percorre a árvore que atribui uma etiqueta a um exemplo
        # Se o nó atual for uma folha então é atribuído o valor guardado no tuplo da folha como etiqueta para o exemplo
        if node.leaf:
            return node.prediction[1]
        # Se o exemplo tiver o valor node.prediction[1] na coluna com o id node.prediction[0] então é o percurso da árvore
        # segue pelo ramo da esquerda, senão segue pelo ramo da direita.
        if data[node.prediction[0]] == node.prediction[1]:
            return self.predict(data, node.left)
        return self.predict(data, node.right)

    def score(self, X, y):
        predictions = np.array([self.predict(x, self.root) for x in X])
        accuracy = predictions == y
        return accuracy.mean()


data = np.genfromtxt("vote.csv", delimiter=",", dtype=None, encoding=None)
xdata = data[1:, 0:-1]
ydata = data[1:, -1]
x_train, x_test, y_train, y_test = train_test_split(xdata, ydata, random_state=0)
classifier = DecisionTree("gini")
classifier.fit(x_train, y_train)
result = classifier.score(x_test, y_test)
print("Percentagem de casos corretamente classificados {:2.2%}".format(result))
