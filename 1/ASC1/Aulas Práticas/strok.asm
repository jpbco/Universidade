#strtok


.data 
string:
	.asciiz "ola,adeus"
delim:
	.asciiz ","

.text

main:
	
	la $a0, string
	la $t0, delim
	lb $a1, 0($t0)
	jal strtok
	nop

	li $v0, 10
	syscall

strtok:
	sub $sp, $sp, 12
	sw $ra, 0($sp)	
	sw $a0, 4($sp)	
	sw $a1, 8($sp)	
	
	strtok_loop:
		lb $t0, 0($a0) 
		beq $t0,0x00, strtok_end 
		beq $t0, 0x0a, strtok_end 
		beq $t0, $a1, strtok_space 
		nop
		addi $a0, $a0, 1	
		j strtok_loop
		nop
	strtok_space:
		li $t0, 0x00		
		sb $t0, 0($a0)		
		addi $a0, $a0, 1	
		j strtok_loop
		nop
	strtok_end:
		
		lw $ra, 0($sp)	
		lw $a0, 4($sp)	
		lw $a1, 8($sp)	
		addi $sp, $sp, 12
		jr $ra
		nop