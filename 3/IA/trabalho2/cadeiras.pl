%dominio
dominio(['Maria', 'Manuel', 'Madalena', 'Joaquim', 'Ana', 'Julio','Matilde', 'Gabriel']).
%variaveis
lugares(8).

estado_inicial(e([
    v(c(1),D,_), v(c(2),D,_), v(c(3),D,_), v(c(4),D,_), v(c(5),D,_), v(c(6),D,_), v(c(7),D,_), v(c(8),D,_)],[])):- dominio(D).



%Restrições
%c(i) <> c(j) e |i-j| <> c(i)-c(j)
restricoes([esq('Manuel','Maria'), frente('Joaquim','Maria'),
lado('Joaquim', 'Matilde'), cabeceira('Gabriel') ]).

%ve_restricoes(e(Nafect,[A])).
ve_restricoes(e(_Nafec,Afect)):- 
    \+ (member(v(c(I),_Di,Vi), Afect),
    member(v(c(J),_Dj,Vj),Afect),
    I \= J,
    restric(I,Vi,J,Vj)).

%SUCEDE SE ALGUMA RESTRICAO FALHA
restric(I,X,J,Y):- restricoes(L), member(esq(X,Y),L), \+ (I is J+1; (I=1,J=8)).
restric(I,X,J,Y):- restricoes(L), member(dir(X,Y),L), \+ (I is J+1; (I=8,J=1)).
restric(I,X,J,Y):- restricoes(L), member(lado(X,Y),L), \+ ((I is J+1; (I=8,J=1));(I is J+1; (I=1,J=8))).
restric(I,X,J,_):- restricoes(L), member(cabeceira(X),L), \+ (I=1,J=8).

esc(N,L):-
	sort(L,L1),
  	esc(N,N,L1).

esc(N,I,[v(_,_,V)|List]):-
  ( I >= 1 ->
    write(V),
    write(' '),
    I1 is I-1,
    esc(N,I1,List)
  ; nl,
    esc(N,N,[v(_,_,V)|List]) ).
esc(_,_,[]).



























































































































































































% Nuno se leres isto é gay xD