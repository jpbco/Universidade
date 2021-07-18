# 2a
#1
sub $t1, $zero, $t0

##2
lui $t0, 0x1234 #t0 = 0x12340000
ori $t0, $t0, 0x5678  #t0 = 0x12345678
ori $t1, $zero, 0x1234 # t1 = 00001234
sll $t1, $t1, 16 # t1 =  0x12340000
ori $t1, $t1, 0x5677 # t1 =0x12345677
sub $t2, $t1, $t0 # t2 = 0xffffffff


##3
lui $t0, 0x0001 #t0 = 0x00010000
ori $t0, $t0, 0xffff #t0 = 0x0001ffff
lui $t1, 0x0002 # t1 = 0x00020000
ori $t1, $t1, 0x0ff7 # t1 = 0x00020ff7
or $t2, $zero, $t0 # t2 = 0x 0001ffff
or $t0, $t1, $zero # t0 = 0x00020ff7
or $t1, $t2, $t2 # t1 = 0x0001ffff

##4

# $t0=0x00107fff e $t1=0x80000000

#a
sra $t2, $t0, 4
# t2 = 0x000107ff

#b
srl $t2, $t0, 2
# t2 = 0x00041FFF

#c
sra $t2, $t1, 4
# t2 = 0xf8000000

#d
srl $t2, $t1, 2
# t2 = 0x20000000

##5
# PC = 0x00400000
# t0 = 0xffffffff
# t1 = 0x80000000

lui $t0, 0x1234         # t0 = 0x12340000
ori $t0, $t0, 0x5678    # 0x00005678
                        # 0x12345678

# PC = 0x00400008
# t0 = 0x12345678
# t1 = 0x80000000
# t2 = nao sabemos...