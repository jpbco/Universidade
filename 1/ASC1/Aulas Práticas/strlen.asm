# strlen
.data
string:  
    .asciiz "Ola e Adeus"

.text

main:
    la  $a0, string        
    add $v0, $zero, $zero
loop:
    lb  $s0, ($a0)       
    beq $s0, 0, end      
    nop  
    add $v0,$v0,1
    add $a0, $a0, 1      
    j   loop
    nop
end:

