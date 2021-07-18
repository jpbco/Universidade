%n(n(x,y,quadrante),dominio,valor).
dominio([1,2,3,4,5,6,7,8,9]).

estado_inicial(e(
	% Não afetados
	[
	% quadrante 1
	v(n(1,1,1),D,_),v(n(1,3,1),D,_),
	v(n(2,1,1),D,_),v(n(2,2,1),D,_),
	v(n(2,3,1),D,_),v(n(3,2,1),D,_),
	v(n(3,3,1),D,_),
	% quadrante 2
	v(n(1,4,2),D,_),v(n(1,5,2),D,_),
	v(n(2,5,2),D,_),v(n(3,4,2),D,_),
	v(n(3,5,2),D,_),v(n(3,6,2),D,_),
	% quadrante 3
	v(n(1,7,3),D,_),v(n(2,7,3),D,_),
	v(n(2,8,3),D,_),v(n(2,9,3),D,_),
	v(n(3,8,3),D,_),
	% quadrante 4
	v(n(4,1,4),D,_),v(n(4,2,4),D,_),
	v(n(4,3,4),D,_),v(n(5,1,4),D,_),
	v(n(5,2,4),D,_),v(n(5,3,4),D,_),
	v(n(6,2,4),D,_),v(n(6,3,4),D,_),
	% quadrante 5
	v(n(4,4,5),D,_),v(n(4,5,5),D,_),
	v(n(5,4,5),D,_),v(n(6,5,5),D,_),
	v(n(6,6,5),D,_),
	% quadrante 6
	v(n(4,7,6),D,_),v(n(4,8,6),D,_),
	v(n(4,9,6),D,_),v(n(5,7,6),D,_),
	v(n(6,7,6),D,_),v(n(6,8,6),D,_),
	v(n(6,9,6),D,_),
	% quadrante 7
	v(n(7,1,7),D,_),v(n(7,2,7),D,_),
	v(n(7,3,7),D,_),v(n(8,3,7),D,_),
	v(n(9,1,7),D,_),v(n(9,2,7),D,_),
	% quadrante 8
	v(n(7,5,8),D,_),v(n(7,6,8),D,_),
	v(n(8,4,8),D,_),v(n(8,6,8),D,_),
	v(n(9,4,8),D,_),v(n(9,5,8),D,_),
	% quadrante 9
	v(n(7,7,9),D,_),v(n(7,8,9),D,_),
	v(n(7,9,9),D,_),v(n(8,7,9),D,_),
	v(n(8,9,9),D,_),v(n(9,7,9),D,_),
	v(n(9,8,9),D,_),v(n(9,9,9),D,_)
	],

	% Afetados
	[
	% quadrante 1
	v(n(1,2,1),D,1),v(n(3,1,1),D,7),
	% quadrante 2
	v(n(1,6,2),D,8),v(n(2,4,2),D,5),
	v(n(2,6,2),D,9),
	% quadrante 3
	v(n(1,8,3),D,7),v(n(1,9,3),D,3),
	v(n(3,7,3),D,9),v(n(3,9,3),D,4),
	% quadrante 4
	v(n(4,6,5),D,4),v(n(6,1,4),D,8),
	% quadrante 5
	v(n(5,5,5),D,3),v(n(5,6,5),D,5),
	v(n(6,4,5),D,9),
	% quadrante 6
	v(n(5,8,6),D,1),v(n(5,9,6),D,8),
	% quadrante 7
	v(n(7,4,8),D,7),v(n(8,1,7),D,2),
	v(n(8,2,7),D,6),v(n(9,3,7),D,5),
	% quadrante 8
	v(n(8,5,8),D,4),v(n(9,6,8),D,3),
	% quadrante 9
	v(n(8,8,9),D,3)
	]
	)):- dominio(D).


restricoes(e(_,[v(n(X,Y,Z), _, V)|Afect])):-
	findall(V3,member(v(n(_,_,Z),_,V3),Afect),L3),
	checkDifference([V|L3]),
	findall(V1,member(v(n(X,_,_),_,V1),Afect),L),
	checkDifference([V|L]),
	findall(V2,member(v(n(_,Y,_),_,V2),Afect),L2),
	checkDifference([V|L2]),
	L \= L2, L3\=L, L3\=L2.


checkDifference([]).
checkDifference([X|Afect]):-
	\+ member(X,Afect), checkDifference(Afect).

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