%Dimensão do "mapa" onde o agente se encontra
lim(X,Y):- 
    X < 7,
    X >=0,
    Y < 7,
    Y>=0.

estado_inicial((1,0)).
estado_final((4,6)).

bloqueadas((0,5)).
bloqueadas((6,2)).
bloqueadas((5,2)).
bloqueadas((3,1)).
bloqueadas((3,2)).
bloqueadas((3,3)).
bloqueadas((6,4)).

%representacao dos operadores
%op(Estado_atual, operador, estado_seguinte, custo)
op((X, Y), dir, (Z, Y), 1):- 
    lim(X,Y),
    Z is X+1,
    \+ bloqueadas((Z,Y)).

op((X, Y),sobe, (X, Z), 1):-
    lim(X,Y),
    Z is Y+1,
    \+ bloqueadas((X, Z)).

op((X, Y), esq, (Z, Y), 1):-
    lim(X,Y),
    Z is X-1,
    \+ bloqueadas((Z,Y)).
    
op((X, Y), desce, (X, Z), 1):-
    lim(X,Y),
    Z is Y-1,
    \+ bloqueadas((X, Z)).

% Heurística de Manhattan
%h((X,Y),Val):- estado_final((Xf,Yf)), mod(Vi, Xf, X), mod(Vj, Yf, Y), Val is (Vi+Vj).

% Heurística alternativa
h((X,Y),0).

mod(Vj,X,Y) :- X<Y,!, Vj is Y-X.
mod(Vj,X,Y) :- Vj is X-Y.



