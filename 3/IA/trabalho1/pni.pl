:- dynamic(estados_visitados/1).
:-dynamic(fechado/1).

pesquisa(Problema,Alg):-
  consult(Problema),
  estado_inicial(S0),
  reset_estados_visitados, % reset e inicializa
  pesquisa(Alg,[no(S0,[],[],0,0)],Solucao),
  escreve_seq_solucao(Solucao),
  retractall(fechado(_)).

guarda(L):-  
  retract(estados_em_memoria(T)),
   maior(T,L,M),
    assertz(estados_em_memoria(M)).

pesquisa(largura,Ln,Sol):- 
  pesquisa_largura(Ln,Sol).

pesquisa(profundidade,Ln,Sol):- 
  pesquisa_profundidade(Ln,Sol).

pesquisa_largura([no(E,Pai,Op,C,P)|_],no(E,Pai,Op,C,P)):- estado_final(E).

pesquisa_largura([E|R],Sol):- 
  expande(E,Lseg), esc(E),
  E=no(Ei,_Pai,_Op,_C,_P),
  assertz(fechado(Ei)),
  insere_fim(Lseg,R,Resto),
  inc_estados_visitados,
  length(Resto, L), guarda(L),
  pesquisa_largura(Resto,Sol).

pesquisa_profundidade([no(E,Pai,Op,C,P)|_],no(E,Pai,Op,C,P)):- estado_final(E).
  
pesquisa_profundidade([E|R],Sol):-  
  expande(E,Lseg), esc(E),
  E=no(Ei,_Pai,_Op,_C,_P),
  assertz(fechado(Ei)),
  insere_fim(R,Lseg,Resto),
  inc_estados_visitados,
  length(Resto, L), guarda(L),
  pesquisa_profundidade(Resto,Sol).
  
expande(no(E,Pai,Op,C,P),L):- 
  findall(no(En,no(E,Pai,Op,C,P),Opn,Cnn,P1),
    (op(E,Opn,En,Cn),
    P1 is P+1,
    Cnn is Cn+C,
    \+fechado(En)),
    L).

insere_fim([],L,L).
insere_fim(L,[],L).
insere_fim(R,[A|S],[A|L]):- 
  insere_fim(R,S,L).

escreve_seq_solucao(no(E,Pai,Op,Custo,Prof)):- nl,nl,                                         
  escreve_seq_accoes(no(E,Pai,Op,_,_)),
  write(custo(Custo)),nl,
  write(profundidade(Prof)),nl,
  estados_visitados(A),
  estados_em_memoria(B),
  write(estados_visitados(A)),nl,
  write(estados_em_memoria(B)),nl.

escreve_seq_accoes([]).
escreve_seq_accoes(no(E,Pai,Op,_,_)):- 
  escreve_seq_accoes(Pai),
  write(e(Op,E)),nl.

esc(A):- write(A), nl.

inc_estados_visitados:-
  retract(estados_visitados(Y)),
  Z is Y + 1,
  assertz(estados_visitados(Z)).

reset_estados_visitados:-
  retractall(estados_visitados(_)),
  retractall(estados_em_memoria(_)),  
  asserta(estados_visitados(0)),
  asserta(estados_em_memoria(0)).

maior(X,Y,Z):-
  X > Y -> Z = X;
  X < Y -> Z = Y;
  Z = X.