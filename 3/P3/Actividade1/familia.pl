homem(joao).
homem(rui).
homem(manuel).
homem(ricardo).
homem(barnabe).
homem(jeremias).
mulher(maria).
mulher(ana).
mulher(rita).
mulher(silvia).
mulher(sarah).

progenitor(joao, maria).
progenitor(joao, rui).
progenitor(manuel, joao).
progenitor(ricardo, manuel).
progenitor(ana, rui).
progenitor(rita, joao).
progenitor(rita, silvia).
progenitor(sarah,jeremias).
progenitor(barnabe,jeremias).

mae(X, Y) :- mulher(X), progenitor(X, Y).
pai(X, Y) :- homem(X), progenitor(X, Y).
antepassado(X,Y) :- progenitor(X,Y).
antepassado(X,Y) :- progenitor(X,Z), antepassado(Z,Y).
antepassado(X,Y,Z) :- antepassado(Z,X), antepassado(Z,Y), X \= Y.
irmao(X,Y) :- progenitor(Z,X), progenitor(Z,Y), X \= Y.
tio(Tio, X) :- irmao(Tio,Pai), progenitor(Pai, X).
parente(A,B) :- antepassado(A,B); antepassado(B,A); antepassado(A,B,_).

