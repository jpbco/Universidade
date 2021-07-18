% Testar
%  set_prolog_flag(answer_write_options,[max_depth(0)]).
% ambiguo([(a, [0,1,0]), (c, [0,1]), (j, [0,0,1]), (l, [1,0]), (p, [0]), (s, [1]), (v, [1,0,1])],M,T1,T2).
% ambiguo([(a, [0,1,1,0]),(b, [0,1,1,1,1,1]),(c, [1,1,0,0,1,1,1,1]),(f, [1,0,1,1,1,0]),(j, [0,1,0]),(l, [0,1,0,0]),(r, [0,1,1,1,0])], M, T1, T2).

:- (dynamic code/2).

store_code([]).
store_code([H|T]) :-
    arg(1, H, X),
    arg(2, H, Y),
    assertz(code(X, Y)),
    store_code(T).

get_permutations(Lista, N) :-
    findall(Codigos, code(_, Codigos), X),
    nPIr(X, N, Lista), !.

get_ambiguous_codes(T1, N) :-
    get_permutations(Lista, N),
    member(T1, Lista),
    flatten(T1, M),
    findall(Bin, code(_, Bin), Todos),
    select(M, Todos, _).

code_to_char(C, [Ch]) :-
    code(Ch, C).

get_ambiguous_messages(Result) :-
    between(2, 6, N),
    findall(T, get_ambiguous_codes(T, N), R),
    length(R, L),
    L>0, !,
    member(Meb, R),
    maplist(code_to_char, Meb, AA),
    flatten(AA, T2),
    flatten(Meb, M),
    code(T1, M),
    Result=[M, [T1], T2].
  
ambiguo(Lista, M, T1, T2) :-
    retractall(code(_, _)),
    store_code(Lista),
    findall(S,
            ( get_ambiguous_messages(R),
              maplist(length, R, Lengths),
              nth0(0, Lengths, El),
              pairs_keys_values(Pairs, [El], [R]),
              msort(Pairs, S)
            ),
            Bag),
    msort(Bag, Si),
    nth0(0, Si, Solucao),
    pairs_keys_values(Solucao, [El], Value),
    Value=[[M, T1, T2]].

% External Predicates
%https://www.researchgate.net/publication/220637777_Logic_programming_for_combinatorial_problems
nPIr(_, 0, [[]]). 
nPIr(L, R, LL) :-
    R>=1,
    pisub(L, L, R, LL). 

pisub([], _, _, []). 
pisub([X|Lt], L, R, LL) :-
    R1 is R-1,
    nPIr(L, R1, LL1),
    pisub(Lt, L, R, LL2),
    addx(LL1, X, LL2, LL).   

addx([], _, LLa, LLa). 
addx([L|LLt], X, LLa, [[X|L]|LL1]) :-
    addx(LLt, X, LLa, LL1).  
