%1)    
    %Condições
    viagem1_c1(c1, lisboa).
    viagem2_c1(c1, porto).
    viagem1_c2(c2, lisboa).
    viagem2_c2(c2, evora).
    
    %Fluentes
    na_cidade(obj, cidade).
    no_comboio(obj, comboio).
    comboio_cidade(comboio, cidade).



    %Ações
    carregar(obj, comboio).
    descarregar(obj, comboio).
    viagem(comboio, cidade).


%2)
    estado_inicial([comboio_cidade(c1, lisboa), comboio_cidade(c2, lisboa), na_cidade(obj1, porto), na_cidade(obj2, lisboa), 
                    na_cidade(obj3, lisboa), na_cidade(obj4, evora), na_cidade(obj5, evora)]).

    estado_final([na_cidade(obj1, evora), na_cidade(obj2, porto), na_cidade(obj3, evora), na_cidade(obj4, porto),
                  na_cidade(obj5, lisboa)]).


%3)
    estado_1([(na_cidade(obj1, lisboa), na_cidade(obj2, lisboa), na_cidade(obj3, lisboa), na_cidade(obj4, evora),
    na_cidade(obj5, evora)]).