.text
.globl main
main:
lui $1,0x00001001     
ori $8,$1,0x00000000       
lw $9,0x00000000($8) 
lui $1,0x00001001     
ori $10,$1,0x00000004      
lw $11,0x00000000($10)
addu $12,$9,$11
label: lui $1,0x00001001     
ori $13,$1,0x00000008      
sw $12,0x00000000($13)
beq $9, $10, main
jr $ra
j label