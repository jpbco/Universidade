estado_inicial([[v,v,v,v,v],[v,v,v,v,v],[x,o,x,v,v],[o,x,x,o,o]]).

% função terminal
terminal(L):- completo(L).
terminal(L):- linhas(L).
terminal(L):- coluna(L).
terminal(L):- diagonal(L).
%
%
completo(L):- true se o tabuleiro estA totalmente preenchido.
linhas(L):- true se existe uma linha com 3 pecas da mesma cor.
coluna(L):- true se existe uma coluna com 3 pecas da mesma cor.
diagonal(L):- true se existe uma diagonal com 3 pecas da mesma cor.


% função utilidade
valor(T,-1):-colunas(T,'C1'),!.
valor(T,-1):-
	tabuleiro(T),
	linhas(Tabuleiro,'C1'),!.
valor(T,-1):-diagonal(T,'C1'),!.
valor(_,0).

valor(T,1):-colunas(T,'C2'),!.
valor(T,1):-
	tabuleiro(T),
	linhas(Tabuleiro,'C2'),!.
valor(T,1):-diagonal(T,'C2'),!.
valor(_,)0.