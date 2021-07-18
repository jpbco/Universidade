.data
origem:  
    .asciiz "ABC"
destino:
    .asciiz "EFG"
.text

main:
    la  $a1, destino        
    la  $a0, origem         
    addi $a2, $zero, 3 # tamanho
loop:
    lb  $s0, ($a0)          
    beq $s0, $zero, end   
    nop      
    beq $a2, $zero, end
    nop
    subi $a2, $a2, 1
    sb  $s0, ($a1)          
    add $a0, $a0, 1       
    add $a1, $a1, 1      
    j   loop
    nop
end:
    sb  $zero, ($a1) 

