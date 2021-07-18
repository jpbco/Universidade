#strncmp
.data
string1:  
	.asciiz "ABC"
string2:
	.asciiz "ABZ"
.text

main:
	la  $a0, string1
	la  $a1, string2       
	add $s0, $zero, $zero
	addi $s1, $zero, 2	# tamanho a comparar
loop:
	lb  $t0, ($a0)       
	lb  $t1, ($a1)
	jal comparar
	nop
	jal fimstring
	nop
	addi $a0, $a0, 1      
	addi $a1, $a1, 1   
	addi $s0, $s0, 1
	beq $s0, $s1, end
	nop
	j   loop
	nop
	
fimstring:
	beq $t0, $zero, end
	nop
	jr $ra
	nop
	
comparar:
	slt $t3, $t0, $t1  # t3 = 1 se t0<t1 senao
	bne $t3, $zero, menor 
	nop
	sgt $t3, $t0, $t1 # # t3 = 1 se t0>t1 senao
	bne $t3, $zero, maior 
	nop
	j igual
	nop
menor:
	addi $v0, $zero, -1	
	j end
	nop
	
maior:
	addi $v0, $zero, 1
	j end
	nop
igual:
	add $v0, $zero, $zero
	jr $ra
	nop	
end:
