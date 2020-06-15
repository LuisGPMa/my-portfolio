.text
.globl main

main:	la $t0, vet #$t0 contem o endereço incial do vetor
	la $t1, size 
	lw $t1, 0($t1) # $t1 contem size
	li $t2, 0 #$t2 contera as posicoes do vetor
	la $t3, valor
	lw $t3, 0($t3) #$t3 contem o valor a ser adicionado
	
loop:	beq $t2, $t1, fim
	li $t4, 4
	mul $t5, $t2, $t4 #t5 recebe o deslocamento que e o indice desejado vezes 4
	add $t6, $t0, $t5 #t6 recebe o endereco da posicao atual
	lw $t7, 0($t6)
	add $t7, $t7, $t3
	sw $t7, 0($t6)
	addiu $t2, $t2, 1
	j loop
fim:
	li $v0, 10
	syscall
	
.data
vet: .word 0x01 0x02 0x03 0x04 0x05 0x06 0x07 0x08 0x09 0x0a 0x0b
size: .word 11
valor: .word 0x0A
