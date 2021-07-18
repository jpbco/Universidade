# strcat
.data
string:  
    .asciiz "AB"
string1:
    .asciiz "cd"
.text

main:
	la  $a0, string        
	la  $a1, string1       
loop:        
	lb $t0, 0($a0)            
	beq $t0, $zero, cat 
	nop         
	addi $a0, $a0, 1            
	j loop   
	nop
cat:          
	lb $t0, 0($a1)      
	sb $t0, 0($a0)            
	addi $a1, $a1, 1    
	addi $a0,$a0, 1
	bne $t0, $zero, cat   
	nop
