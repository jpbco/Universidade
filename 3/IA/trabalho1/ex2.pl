lim(A,B) :- 
    A < 7,
    A >= 0,
    B < 7,
    B >= 0.

estado_inicial(((1,0),(1,1))).

estado_final(((4,5),(4,6))).

bloqueadas((0,5)).
bloqueadas((6,2)).
bloqueadas((5,2)).
bloqueadas((3,1)).
bloqueadas((3,2)).
bloqueadas((3,3)).
bloqueadas((6,4)).	

op(((X,Y),(Xc,Yc)), (N,M), ((X1,Y1),(X1c,Y1c)), 1):- 
    member(N,[1, -1]),
    member(M,[0]),
    X is Xc-N, Y is Yc - M,
    X1 = Xc, Y1 = Yc,
    X1c is Xc+N, Y1c is Yc + M,
    \+ bloqueadas((X1c,Y1c)),
    lim(X1c,Y1c).

op(((X,Y),(Xc,Yc)), (N,M), ((X1,Y1),(X1c,Y1c)), 1):- 
    member(N,[0]),
    member(M,[1, -1]),
    X is Xc-N, Y is Yc - M,
    X1 = Xc, Y1 = Yc,
    X1c is Xc+N, Y1c is Yc + M,
    \+ bloqueadas((X1c, Y1c)),
    lim(X1c,Y1c).

op(((X,Y),(Xc,Yc)), (N,M), ((X1,Y1),(Xc,Yc)), 1):-
    member(M,[1,-1]),
    member(N,[0]),
    X1 is X+N, Y1 is Y+M, lim(X1,Y1),
    (X1,Y1) \= (Xc,Yc),
    \+ bloqueadas((X1, Y1)).

op(((X,Y),(Xc,Yc)), (N,M), ((X1,Y1),(Xc,Yc)), 1):- 
    member(M,[0]),
    member(N,[1,-1]),
    X1 is X+N, Y1 is Y+M, lim(X1,Y1),
    (X1,Y1) \= (Xc,Yc),
    \+ bloqueadas((X1, Y1)).
    

%heuristicas
hAgente( ( (Xa,Ya),_) ,Val):-
    estado_final(((Xfa,Yfa),_)),
    mod(Vi, Xfa, Xa),
    mod(Vj, Yfa, Ya),
    Val is (Vi+Vj).

h(( _,(Xc,Yc) ),Val):-
    estado_final((_,(Xfc,Yfc))),
    mod(Vi, Xfc, Xc),
    mod(Vj, Yfc, Yc),
    Val is (Vi+Vj).
  
mod(Vj,X,Y) :- X<Y,!, Vj is Y-X.
mod(Vj,X,Y) :- Vj is X-Y.
