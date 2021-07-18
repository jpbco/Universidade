
.data
origem:  
    .asciiz "Ola"
destino:
    .asciiz "ABC"
.text

main:
    la  $a1, destino        
    la  $a0, origem         
loop:
    lb  $s0, ($a0)        
    beq $s0, $zero, end  
    nop       
    sb  $s0, ($a1)         
    add $a0, $a0, 1     
    add $a1, $a1, 1      
    j   loop
    nop
end:

