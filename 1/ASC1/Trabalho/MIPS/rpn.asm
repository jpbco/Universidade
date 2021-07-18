.data

##	Operacoes 
add_msg: .asciiz "+"  # + Adição 
sub_msg: .asciiz "-" #  Subtracção 
mult_msg: .asciiz "*" # Multiplicação 
div_msg: .asciiz "/" # Divisão
neg_msg: .asciiz "neg" # Calcula o simétrico
swap_msg: .asciiz "swap" # Troca posição dos dois operandos do topo da pilha
dup_msg: .asciiz "dup" # Duplica o operando do topo da pilha
drop_msg: .asciiz "drop" # Elimina um operando do topo da pilha
clear_msg: .asciiz "clear" # Limpa toda a pilha (fica vazia)
off_msg: .asciiz "off" # Desliga a calculadora (termina o programa)
help_msg: .asciiz "help" # Mostra prompt ajuda

## 	Mensagens    
nl: .asciiz "\n"
stack_msg: .asciiz "Stack:\n"
stack_vazia_msg: .asciiz "(vazia)\n"
seta_input: .asciiz "-> "
goodbye: .asciiz "Bye!\n"
erro_div_zero: .asciiz "Erro, divisão por 0\n"
erro_stack_vazia: .asciiz "Stack is empty, operation not allowed"
popped: .asciiz "popped: "
prompt1: .asciiz "************************************************\n"
prompt2: .asciiz "*** RPN - Reverse Polish Notation Calculator ***\n"
prompt3: .asciiz "***       João Cavaco e António Barroso      ***\n"
prompt4: .asciiz "***           42470        44445             ***\n"
prompt5: .asciiz "***             ASC1 2019/2020               ***\n"
prompt6: .asciiz "************************************************\n"
prompt7: .asciiz "type 'help' for available commands\n\n"
prompt_help1: .asciiz "\nNumbers separated by space are pushed onto the stack.\n"
prompt_help2: .asciiz "Operators are applied to the top of the stack.\n"
prompt_help3: .asciiz "Available operators:  + - * / neg swap dup drop clear off.\n"
prompt_help4: .asciiz "Examples: \n"
prompt_help5: .asciiz "'2*(3+4)' == '3 4 + 2 *' == '2 3 4 + *'\n"
prompt_help6: .asciiz "'(3+4)/2' == '3 4 + 2 /' == '2 3 4 + swap /'\n\n"

## 	Buffers    
input_string: .space 130 # Espaço em memória para guardar até 256 ints 
stack:  .align 2
	.space 1024 # Espaço em memória para guardar até 256 ints 
token:	.space 130
	
# registo $t9 -> apontador para o topo da pilha
# registo $t8 -> tamanho da pilha

.text


main:
	jal prompt		#	Dá as "boas vindas" ao utilizador
	nop
	
	jal stack_new		#	Inicializa a pilha
	nop
	
	main_loop:		#	Loop principal do programa
		
		jal stack_print	#	Mostra o estado atual da pilha
		nop
		
    	jal input 	# 	Recebe o input do utilizador
		nop
		
		la $a0, input_string
		la $a1, token

		jal strtok	#	Divide o input em tokens e avalia cada um deles
		nop
	
		jal main_loop	#	Loop infinito que só é quebrado caso o token lido seja "off"
		nop



##  Estrutura de dados Pilha


# Função stack_new
# Argumentos: nenhum
# Retorna: nenhum
# Função que inicializa a Pilha
stack_new: 
	la $t9, stack
	li $t1, 0x30	# inicia a pilha com um 0 para marcar a base 
	sw $t1, 0($t9)
	move $t8, $zero # inicializa o tamanho da pilha
	jr $ra
	nop


# Função stack_push
# Argumentos: $a0 - valor do tipo Integer 
# Retorna: nenhum
# Função que empilha um elemento na Pilha
stack_push: 
	add $t9, $t9, 4	# aumenta o topo da pilha 
	sw $a0, 0($t9)	# adiciona o elemento $a0 
	add $t8, $t8, 1	# incrementa o tamanho da pilha
	jr $ra
	nop


# Função stack_pop
# Argumentos: nenhum
# Retorna: $v0 -  valor do tipo Integer
# Função que remove e retorna o elemento do topo da pilha	
stack_pop: 
	lw $v0, 0($t9)	# guarda o elemento em $v0
	sub $t9, $t9, 4	# decrementa o topo
	sub $t8, $t8, 1	# decrementa o tamanho da pilha
	jr $ra
	nop
	
	
# Função stack_pop
# Argumentos: nenhum
# Retorna: $v0 -  valor do tipo Integer
# Função que retorna o elemento do topo da pilha sem o remover		
stack_peek: 
	lw $v0, 0($t9)	# guarda o elemento em $v0
	jr $ra
	nop
	

# Função stack_print
# Argumentos: nenhum
# Retorna: nenhum
# Função que mostra o aspeto atual da Pilha
stack_print:
	sub $sp, $sp, 8
	sw $ra, 0($sp)
	sw $a0, 4($sp)
	
	la $a0, stack_msg				
	li $v0, 4
	syscall
	
	beq $t8, 0, stack_print_empty	# se a pilha estiver vazia 
	nop
	move $t3, $zero	# número de elementos percorridos
		
	la $t1, stack
	add $t1, $t1, 4	# ignora o 0 na base da pilha
stack_print_loop:	
	beq $t3,$t8, stack_print_end	# se contador == tamanho da pilha
	nop	
	lw $t0, 0($t1)	#	elemento a ser printed
	move $a0, $t0
	li $v0, 1	
	syscall # print de um elemento
	la $a0, nl
	li $v0, 4
	syscall	# print de um \n 
	addi $t1, $t1, 4	# avançar na pilha
	addi $t3, $t3, 1	# incrementar o contador
	j stack_print_loop
	nop
	
	stack_print_empty: 
		la $a0, stack_vazia_msg
		li $v0, 4
		syscall
		j stack_print_end
		nop
	
	stack_print_end:	
		lw $ra, 0($sp)
		lw $a0, 4($sp)
		add $sp, $sp, 8
		jr $ra
		nop

# Função print_str
# Argumentos: $a0 - endereço de memória de uma String
# Retorna: nenhum
# Faz print da string no endereço de memória passado como argumento
print_str: 
	li $v0, 4
	syscall
	jr $ra
	nop
	
	
# Função input
# Argumentos: nenhum
# Retorna: nenhum
# Recebe input do stdin e guarda-o no buffer input_string
input:
	la $a0, seta_input
	li $v0, 4	
	syscall
	
	la $a0, input_string
	li $a1, 128	
	li $v0, 8	
	syscall
	
	jr $ra
	nop
	
	
# Funcao prompt
# Argumentos: nenhum
# Retorna: nenhum
# Função inicial de "boas vindas" que mostra algumas informações sobre o programa
prompt:
	la $a0, prompt1
	li $v0, 4	
	syscall

	la $a0, prompt2
	li $v0, 4	
	syscall

	la $a0, prompt3
	li $v0, 4	
	syscall
	
	la $a0, prompt4
	li $v0, 4	
	syscall

	la $a0, prompt5
	li $v0, 4	
	syscall

	la $a0, prompt6
	li $v0, 4	
	syscall

	la $a0, prompt7
	li $v0, 4	
	syscall
	jr $ra
	nop


# Função atoi
# Argumentos: $a0: endereço de memória de uma String
# Retorna: $v0: conversão da string para um inteiro
atoi: 
	sub $sp, $sp,4
	sw $ra, 0($sp)
	move $t0, $a0
	li $v0, 0
	atoi_loop: 
		lb $t1, ($t0)

		blt $t1, 48, endloop # '0'
		bgt $t1, 57, endloop # '9'
		nop
		# atualiza o $v0 = v0*10 + $t1 - 48
		mul $v0, $v0, 10
		add $v0, $v0, $t1
		sub $v0, $v0, 48

		add $t0, $t0, 1
		j atoi_loop
		nop
	endloop:
		lw $ra, 0($sp)
		add $sp, $sp, 4
		jr $ra
		nop


# Função que empilha a soma de dois elementos do topo da Pilha
# Argumentos: nenhum
# Retorna: nada
soma:
	sub $sp, $sp, 4
	sw $ra, 0($sp)
	blt $t8, 2, soma_end # A pilha tem de ter pelo menos dois elementos
	nop	
	jal stack_pop
	nop	
	move $t0, $v0
	jal stack_pop
	nop	
	move $t1, $v0	
	add $v0, $t1, $t0
	move $a0, $v0
	jal stack_push
	nop	
	soma_end:
		lw $ra, 0($sp)
		add $sp, $sp, 4
		jr $ra
		nop

# Função que empilha a subtração de dois elementos do topo da Pilha
# Argumentos: nenhum
# Retorna: nada
subtracao:
	sub $sp, $sp, 4
	sw $ra, 0($sp)
	blt $t8, 2, subtracao_end # A pilha tem de ter pelo menos dois elementos
	nop
	jal stack_pop
	nop
	move $t0, $v0
	jal stack_pop
	nop
	move $t1, $v0
	sub $v0, $t1, $t0
	move $a0, $v0
	jal stack_push
	nop
	subtracao_end:
		lw $ra, 0($sp)
		add $sp, $sp, 4
		jr $ra
		nop


# Função que empilha a multiplicação de dois elementos do topo da Pilha
# Argumentos: nenhum
# Retorna: nada
multiplicacao:
	sub $sp, $sp, 4
	sw $ra, 0($sp)
	blt $t8, 2, multiplicacao_end # A pilha tem de ter pelo menos dois elementos
	nop
	jal stack_pop
	nop
	move $t0, $v0
	jal stack_pop
	nop
	move $t1, $v0
	mul $v0, $t1, $t0
	move $a0, $v0
	jal stack_push
	nop
	multiplicacao_end:
		lw $ra, 0($sp)
		add $sp, $sp, 4
		jr $ra
		nop


# Função que empilha a divisão de dois elementos do topo da Pilha
# Argumentos: nenhum
# Retorna: nada
divisao:
	sub $sp, $sp, 8
	sw $ra, 0($sp)
	sw $a0, 4($sp)
	blt $t8, 2, divisao_end # A pilha tem de ter pelo menos dois elementos
	nop
	jal stack_peek
	nop
	beqz $v0,divisao_by_zero # avalia se o divisor não for 0
	nop
	jal stack_pop
	nop
	move $t0, $v0
	jal stack_pop
	nop
	move $t1, $v0
	div $v0, $t1, $t0
	move $a0, $v0
	jal stack_push
	nop
	j divisao_end
	nop
	divisao_by_zero:
		la $a0, erro_div_zero
		li $v0, 4
		syscall

	divisao_end:
		lw $ra, 0($sp)
		lw $a0, 4($sp)
		add $sp, $sp, 8
		jr $ra
		nop


# Função que empilha o simétrico de um elemento do topo da Pilha
# Argumentos: nenhum
# Retorna: nada
negacao:
	sub $sp, $sp, 4
	sw $ra, 0($sp)
	
	blt $t8, 1, negacao_end # A pilha tem de ter pelo menos um elemento
	nop
	
	jal stack_pop
	nop
	
	sub $v0, $zero, $v0
	
	move $a0, $v0
	jal stack_push
	nop
	
	negacao_end:
		lw $ra, 0($sp)
		add $sp, $sp, 4
		jr $ra
		nop


# Função que troca os dois elementos do topo da Pilha
# Argumentos: nenhum
# Retorna: nada
troca:
	sub $sp, $sp, 4
	sw $ra, 0($sp)
	
	blt $t8, 2, troca_end # A pilha tem de ter pelo menos dois elementos
	nop
	
	jal stack_pop
	nop
	move $t0, $v0
	
	jal stack_pop
	nop
	move $t1, $v0
	
	move $a0, $t0
	jal stack_push
	nop
	
	move $a0, $t1
	jal stack_push
	nop
	
	troca_end:
		lw $ra, 0($sp)
		add $sp, $sp, 4
		jr $ra
		nop
	

# Funçãoo que duplica o elemento no topo da Pilha
# Argumentos: nenhum
# Retorna: nada
duplicacao:
	sub $sp, $sp, 4
	sw $ra, 0($sp)
	
	blt $t8, 1, duplicacao_end # A pilha tem de ter pelo menos um elemento
	nop
	
	jal stack_peek
	nop
	
	move $a0, $v0
	jal stack_push
	nop
	
	duplicacao_end:
		lw $ra, 0($sp)
		add $sp, $sp, 4
		jr $ra
		nop


# Função que remove o elemento do topo da Pilha
# Argumentos: nenhum
# Retorna: nada
remover:
	sub $sp, $sp, 4
	sw $ra, 0($sp)
	
	blt $t8, 1, remover_end # A pilha tem de ter pelo menos um elemento
	nop
	
	jal stack_pop
	nop
	
	remover_end:
		lw $ra, 0($sp)
		add $sp, $sp, 4
		jr $ra
		nop


# Função que remove todos os elementos da Pilha
# Argumentos: nenhum
# Retorna: nada
limpar:
	sub $sp, $sp, 4
	sw $ra, 0($sp)
	jal stack_new
	nop
	lw $ra, 0($sp)
	add $sp, $sp, 4
	jr $ra
	nop


# Função que mostra ao utilizador aquilo que o programa faz
# Argumentos: nenhum
# Retorna: nada
ajuda:

	la $a0, prompt_help1
	li $v0, 4	
	syscall

	la $a0, prompt_help2
	li $v0, 4	
	syscall
	
	la $a0, prompt_help3
	li $v0, 4	
	syscall

	la $a0, prompt_help4
	li $v0, 4	
	syscall

	la $a0, prompt_help5
	li $v0, 4	
	syscall

	la $a0, prompt_help6
	li $v0, 4	
	syscall
	
	jr $ra
	nop
	
# Função que termina o programa
# Argumentos: nenhum
# Retorna: nada
off:
	la $a0, goodbye
	li $v0, 4
	syscall 
		
	li $v0, 10
	syscall


# Funcao que compara as duas strings passadas como argumentos.
# Argumentos: a0 Endereco de memoria de uma String  , a1 - Endereco de memoria de uma String
# Retorna: 1 ou 0 - True or False 
strequals:
	sub $sp, $sp, 12 
	sw $ra, 0($sp)	
	sw $a0, 4($sp)
	sw $a1, 8($sp)
	
	jal strlen
	nop		
	move $t2, $v0	# t2 = comprimento do argumento a0
	move $a0, $a1
	jal strlen
	nop
	move $t3, $v0	# t3 = comprimento do argumento a1
	lw $a0, 4($sp)	# Recupera os valores originais 
	lw $a1, 8($sp)	# de ambas as strings	
	li $v0, 0	# a função retorna false por default
	bne $t2,$t3, strequals_end # Se os comprimentos forem diferente salta para o fim da função
	nop
	strequals_loop:	# comparação de caracteres
		lb $t0, 0($a0)	
		lb $t1, 0($a1)		# a segunda string é a string que contém a operação
		beq $t1, 0x00, strequals_true	# se chegar ao fim da string de operação retorna true
		nop
		bne $t0, $t1, strequals_end	# se os chars comparados forem diferentes retorna false
		nop
		addi $a0, $a0, 1	# avança para o próximo byte da string a0 
		addi $a1, $a1, 1	# avança para o próximo byte da string a1
		j strequals_loop
		nop
	strequals_true:	
		li $v0, 1	
	strequals_end:	
		lw $ra, 0($sp)
		lw $a0, 4($sp)
		lw $a1, 8($sp)
		addi $sp, $sp, 12	
		jr $ra
		nop
	
	
# Função que calcula o tamanho de uma string
# Argumentos: $a0(Array de String)
# Retorna: $v0(tamanho do Array)
strlen:
	sub $sp, $sp, 4
	sw $ra, 0($sp)	
	move $v0, $zero
	strlen_loop:	
		lb $t0, 0($a0)
		beq $t0, 0x00, strlen_end # termina se encontrar o fim da string\0
		beq $t0, 0x0a, strlen_end # termina se encontrar outro "fim" da string \n
		nop
		addi $a0, $a0, 1	# avança para o próximo byte da string
		addi $v0, $v0, 1	# incrementa o contador
		j strlen_loop
		nop
	strlen_end:
		lw $ra, 0($sp)	
		addi $sp,$sp, 4	
		jr $ra
		nop


# Função strtok 
# Argumentos:
# a0 - endereço de memória de uma String que contém o input do utilizador
# a1 - endereço de memória do buffer token
# Return: none
# Separa a string em substrings que são guardadas no buffer token e avaliadas
strtok:
	sub $sp, $sp, 12
	sw $ra, 0($sp)	
	sw $a0, 4($sp)	
	sw $a1, 8($sp)	
	
	strtok_loop:
		lb $t0, 0($a0) 
		beq $t0,0x00, strtok_end # salta se encontrar para o final se encontrar o char \0
		beq $t0, 0x0a, strtok_end # salta se encontrar para o final se encontrar o char \n
		beq $t0, 0x20, strtok_space # salta se encontrar para o final se encontrar o char Espaço(0x20)
		nop
		sb $t0, 0($a1)		# guarda o char no buffer token
		addi $a0, $a0, 1	# avança para o próximo byte da string de input
		addi $a1, $a1, 1	# avança para o próximo byte do token
		j strtok_loop
		nop
	strtok_space:
		li $t0, 0x00		# \0
		sb $t0, 0($a1)		# termina a substring
		sub $sp, $sp, 4		# aloca espaço para guardar as posições
		sw $a0, 0($sp)		# guarda a posição atual da string de input
		la $a0, token	
		jal avalia		# avalia a substring guardada no token
		nop
		lw $a0, 0($sp)		# recupera a posição atual da string
		la $a1, token		# guarda em a1 o endereço de memória inicial do token
		addi $sp, $sp, 4	# remove o espaço alocado para guardar as posições
		addi $a0, $a0, 1	# o próximo loop começará no byte seguinte da string de input
		j strtok_loop
		nop
	strtok_end:
		li $t0, 0x00	 # \0
		sb $t0, 0($a1)	 # termina a substring 
		la $a0, token
		jal avalia	 # avalia a substring guardada no token
		nop
		lw $ra, 0($sp)	
		lw $a0, 4($sp)	
		lw $a1, 8($sp)	
		addi $sp, $sp, 12
		jr $ra
		nop
	
	
# Funcao avalia 
# Argumentos: a0 - Endereco do token
# Retorna: nada
# Função que consoante o valor do token executa uma operação da pilha
avalia:
	sub $sp, $sp, 24
	sw $a0, 0($sp)
	sw $ra, 4($sp)
	sw $s0, 8($sp)
	sw $s1, 12($sp)
	sw $s2, 16($sp)
	sw $s3, 20($sp)
	
	jal strlen
	nop
	move $s0, $v0				# s0 = comprimento do token
	lw $a0, 0($sp)				# repõe o valor de a0 inicial
	slti $s1, $s0, 2			# verifica se o token apenas tem um char
	beq $s1, $zero, avalia_maior_que_um	# salta se o token tiver mais que um char 
	nop				
# Casos para token com comprimento 1	
	lb $s2, 0($a0)				# s2 = char a ser avaliado
	bne $s2, '*', caso_soma			# se s2 == * não salta e realiza a operação
	nop
	jal multiplicacao			# executa a operação
	nop
	j avalia_end				# termina a avaliação do token
	nop
	caso_soma:	
		bne $s2, '+', caso_subtracao	
		nop
		jal soma			
		nop
		j avalia_end			
		nop	
	caso_subtracao:	bne $s2, '-', caso_divisao	
		nop
		jal subtracao
		nop
		j avalia_end
		nop
	caso_divisao:	bne $s2, '/', caso_numero	
		nop
		jal divisao
		nop
		j avalia_end
		nop
	caso_numero:				# caso s2 seja um número
		slti $s1, $s2, 0x30		# verifica se s2 é menor que o de 0
		slti $s3, $s2, 0x40		# verifica se s2 é maior que o de 9
		beq $s1, $s3, avalia_end	
		nop
		la $a0, token	
		jal atoi			# converte o token para um inteiro
		nop
		move $a0, $v0
		jal stack_push			# empilha o valor no topo da pilha
		nop
		j avalia_end			# termina a avaliação do token
		nop
# Casos para token com comprimento maior que 1
	avalia_maior_que_um: 	
			la $a1, neg_msg	 		# operação de negação
			jal strequals			# compara o token com a string da operação a executar
			nop
			beq $v0, $zero, caso_off	# salta para o proóximo caso
			nop
			jal negacao
			nop
			j avalia_end
			nop
		caso_off:	
			la $a1, off_msg			# operação que termina o programa
			jal strequals
			nop
			beq $v0, $zero, caso_swap
			nop
			jal off
			nop
			j avalia_end
			nop
		caso_swap:	
			la $a1, swap_msg		# operação que troca dois elementos
			jal strequals
			nop
			beq $v0, $zero, caso_ajuda	# salta para o proóximo caso
			nop
			jal troca
			nop
			j avalia_end
			nop
		caso_ajuda:	
			la $a1, help_msg		# operação de ajuda
			jal strequals
			nop
			beq $v0, $zero, caso_remover	# salta para o proóximo caso
			nop
			jal ajuda
			nop
			j avalia_end
			nop
		caso_remover:	
			la $a1, drop_msg		# operação para remover um elemento da pilha
			jal strequals
			nop
			beq $v0, $zero, caso_limpar	# salta para o proóximo caso
			nop
			jal remover
			nop
			j avalia_end
			nop
		caso_limpar:	
			la $a1, clear_msg 		# operação para limpar a pilha
			jal strequals
			nop
			beq $v0, $zero, caso_duplicar	# salta para o proóximo caso
			nop
			jal limpar
			nop
			j avalia_end
			nop
		caso_duplicar:	
			la $a1, dup_msg			# operação de duplicação
			jal strequals
			nop
			beq $v0, $zero, caso_num_dois	# salta para o proóximo caso
			nop
			jal duplicacao
			nop
			j avalia_end
			nop
		caso_num_dois:				# Caso seja um número com mais de 1 algarismo
			caso_num_dois_loop:
				lb $s0, 0($a0)
				beq $s0, 0x00, caso_num_dois_true	# caso chege ao final da string(\0)	
				nop
				slti $s1, $s0, 0x30			# verifica se s2 é menor que o de 0	
				slti $s2, $s0, 0x40			# verifica se s2 é menor que o de 9
				beq $s1, $s2, avalia_end		
				nop		
				addi $a0, $a0,1				# avança para o próximo algarismo
				j caso_num_dois_loop
				nop
			caso_num_dois_true:	# caso seja um número
			la $a0, token
			jal atoi		# converte a string para inteiro
			nop
			move $a0, $v0
			jal stack_push		# empilha o valor
			nop
			j avalia_end		# termina a avaliação
			nop
	
	avalia_end:
	lw $a0, 0($sp)
	lw $ra, 4($sp)
	lw $s0, 8($sp)
	lw $s1, 12($sp)
	lw $s2, 16($sp)
	lw $s3, 20($sp)
	addi $sp, $sp, 24
	jr $ra
	nop
	
# Fim do Programa
