.text
.globl main

main:	la $t0, vet1 #$t0 contem o endereco inicial de vet1
	la $t1, vet2 #$t1 contem o endereco inicial de vet2
	la $t2, vet3 #$t2 contem o endereco inicial de vet3
	li $s0, 0 #$s0 contem as posicoes dos vetores a serem percorridas (posicao atual)
	la $s1, size
	lw $s1, 0($s1) #$s1 contem o tamanho dos vetores
	
loop:	beq $s0, $s1, fim
	addiu $sp, $sp, -12 #abrindo espaco na pilha
	sw $t0, 0($sp) 
	sw $t1, 4($sp)
	sw $s0, 8($sp)
	jal soma_vet
	lw $s4, 0($sp)
	addiu $sp, $sp, 4
	sw $s4, 0($t2)
	addiu $t2, $t2, 4
	addu $s0, $s0, 1
	j loop	
	
soma_vet:
	lw $t3, 0($sp) #t3 recebe o end inicial do vet1
	lw $t4, 4($sp) #t4 recebe o end inicial do vet2
	lw $t5, 8($sp) #t5 recebe o indice desejado
	addiu $sp, $sp, 12
	mul $t5, $t5, 4 #t5 recebe o deslocamento desejado
	addu $t6, $t3, $t5 #t6 recebe o endereco atual em vet1
	addu $t7, $t4, $t5 #t7 recebe o endereco atual em vet2
	lw $t6, 0($t6) #t6 recebe o valor atual do vet1
	lw $t7, 0($t7) #t7 recebe o valor atual do vet2
	addiu $sp, $sp, -4
	addu $s3, $t6, $t7
	sw $s3, 0($sp)
	jr $ra

fim:
	li $v0, 10
	syscall

.data
vet1: .word 0x01 0x02 0x03 0x04 0x05
vet2: .word 0x04 0x03 0x02 0x01 0x00
vet3: .word 0x00 0x00 0x00 0x00 0x00
size: .word 0x05
