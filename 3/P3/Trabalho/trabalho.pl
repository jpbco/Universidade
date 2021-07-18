% Testar
%  set_prolog_flag(answer_write_options,[max_depth(0)]).
% ambiguo([(a, [0,1,0]), (c, [0,1]), (j, [0,0,1]), (l, [1,0]), (p, [0]), (s, [1]), (v, [1,0,1])],M,T1,T2).
% ambiguo([(a, [0,1,0]), (p, [0]), (s, [1])],M,T1,T2).
% ambiguo([(a, [0,1,1,0]),(b, [0,1,1,1,1,1]),(c, [1,1,0,0,1,1,1,1]),(f, [1,0,1,1,1,0]),(j, [0,1,0]),(l, [0,1,0,0]),(r, [0,1,1,1,0])], M, T1, T2).
:-dynamic code/2.

store_code([]).
store_code([H|T]):- arg(1,H,X), arg(2,H,Y), assertz(code(X,Y)), store_code(T).

get_lista_comb_final(Lista,N):- 
    findall(Codigos,code(_,Codigos),X), 
    nPIr(X,N,Lista),!.

find_messages_final(T1,N):- get_lista_comb_final(Lista,N), 
  member(T1,Lista),
  flatten(T1,M), 
  findall(Bin,code(_,Bin),Todos), 
  select(M,Todos,_).

store_todos(Lista,Number):-between(1,Number,N),
               nPr(Lista,N,X),
                 member(A,X),(maplist(arg(1),A,LL),
                  maplist(arg(2),A,LL1)),flatten(LL1,FLL1),
                   Termo =.. [code,LL,FLL1],
                   assertz(Termo).

store_todos_final(Lista,Number):- findall(_,store_todos(Lista,Number),_).

decode(A,R):- code(T1,A), R=[T1].

decode_final(M1,T2,M):- 
              between(2,7,N),
              findall(T,find_messages_final(T,N),R),
              length(R,L), L > 0,!, member(Meb,R),
              maplist(decode,Meb,AA),
              flatten(AA,T2),
              flatten(Meb,M),
              code(T1,M),
              M1 = [T1],!.
  

ambiguo_final2(Lista,R):- store_todos_final(Lista,3), decode_final(T1,T2,M), R = [M,T1,T2].


ambiguo(Lista,M,T1,T2):- retractall(code(_,_)),
findall(S,
         (ambiguo_final2(Lista,R),
         maplist(length,R,Lengths),
         nth0(0,Lengths,El),
         pairs_keys_values(Pairs,[El],[R]),
         msort(Pairs,S)),Bag),
         msort(Bag, Si),
         nth0(0,Si,Solucao),
pairs_keys_values(Solucao,[El],Value),
Value = [[M,T1,T2]].
                          
% helper funcs
%https://www.researchgate.net/publication/220637777_Logic_programming_for_combinatorial_problems

% nPIr(L, R, LL) generates permutations of L with element repetition, taken R  
% elements at a time giving LL. e.g., nPIr([a, b], 2, [[a, a], [a, b], [b, a], [b, b]]).  

nPIr(_, 0, [[ ]]). 
nPIr(L, R, LL) :- 
  R >= 1, 
  pisub(L, L, R, LL). 
 
% pisub(Ls, L, R, LL), where Ls is a subset of L, generates all sequences of R elements  
% starting with an element in Ls followed by length R – 1, element-repeated permutations  
% of L, giving LL.  
% e.g., pisub([b, c], [a, b, c], 2, [[b, a], [b, b], [b, c], [c, a], [c, b], [c, c]]).  

pisub([ ], _, _, [ ]). 
pisub([X | Lt], L, R, LL) :- 
  R1 is R - 1, 
  nPIr(L, R1, LL1),   % Generates element-repeated permutations of length R – 1.  
  pisub(Lt, L, R, LL2),   % LL2 has permutations of length R not starting with X.  
  addx(LL1, X, LL2, LL).   % Inserts X at the beginning of the LL1 permutations, appends 

addx([ ], _, LLa, LLa). 
addx([L | LLt], X, LLa, [[X | L] | LL1]) :- addx(LLt, X, LLa, LL1).  

%----------------------------------------------------------------------------------------
nPr(_, 0, [[ ]]).
 nPr(L, R, LL) :-   
 R >= 1,
    permsub(L, L, R, LL).

permsub([ ], _, _, [ ]).  
permsub([X | Lt], L, R, LL):-
R1 is R - 1,
deletex(L, X, L1),
nPr(L1, R1, LL1),
permsub(Lt, L, R, LL2),
addx(LL1, X, LL2, LL).

deletex([X | Lt], X, Lt). 
 deletex([X | Lt], Y, [X | Ls]) :- deletex(Lt, Y, Ls).