# aula 3

##1.a

    bne $t0, $zer0, $zer0
    nop
    add $t0, $zero, $zero   # x = 0
    beq $zero, $zero, END
    nop
ELSE:
    add %t0, $t1, $zero     # y = 0
END:

## 1.b

    slt $t2, $t0, $zero
    beq $t2, $zero, ELSE
    nop
    sub $t1, $zero, $t0
    j END
    nop
ELSE: add $t1, $t0, $zero
END:

## 1.c

        add $t1, $zero, $zero   # y = 0
        addi $t0, $zer0, 1      # x = 1
FOR:    slti $t2, $t0, 11       #x<=10 mesmo que x<11
        beq $t2, $zero, END
        nop
        add $t1, $t1, $t0       #y += x
        addi $t0, $t0, 1
        j FOR
        nop
END:
