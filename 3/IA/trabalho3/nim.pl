% estrutura de dados
estado_inicial(e([1,2,3,4])).
estado_final(e([0,0,0,0])).

% terminal
terminal(L,R):-
    sum(L,R) is 0.

sum([X],X).
sum([H|T], S) :- sum(T,X), S is H + X.



% utilidade
valor(E,-1,P):- terminal(E),  R is P mod 2, R=1.
valor(E,1,P):- terminal(E),  R is P mod 2, R=0.
