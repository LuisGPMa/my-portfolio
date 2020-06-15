.text
.globl main

main:	la $t0, vet #$t0 contem posicao inicial do vetor
	la $t1, size
	lw $t1, 0($t1) #$t1 contem tamanho do vetor
	li $t2, 0 #$t2 contera as posicoes do vetor
	li $t3, 0 #$t3 contera a quantidade de valores impares
	li $t4, 0 #$t4 contera a quantidade de valores pares
	
loop:	beq $t2, $t1, fim
	li $t5, 4
	mul $t6, $t2, $t5 #t6 recebe o deslocamento que e o indice desejado vezes 4
	add $t7, $t0, $t6 #t7 recebe o endereco da posicao atual
	lw $s0, 0($t7) #s0 contem o conteudo da posicao atual
	andi $s1, $s0, 0x0001 #s1 contera 1 se o numero atual for impar e 0 se o numero atual for par
	addiu $t2, $t2, 1 #atualiza a posicao atual
	bnez $s1, impar
	addiu $t4, $t4, 1
	j loop
impar:	addiu $t3, $t3, 1
	j loop
	
fim:
	la $a0, print1
	li $v0, 4
	syscall
	
	move $a0, $t4
	li $v0, 1
	syscall
	
	la $a0, print2
	li $v0, 4
	syscall
	
	move $a0, $t3
	li $v0, 1
	syscall
	
	li $v0, 10
	syscall
	
.data 
vet: .word 0x01 0x02 0x03 0x04 0x05 0x06 0x07 0x08 0x09 0x0a
size: .word 0x0a
print1: .asciiz "quantidade de valores pares: "
print2: .asciiz "\nquantidade de valores impares: "