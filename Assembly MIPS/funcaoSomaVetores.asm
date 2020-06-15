.text
.globl main

main:	la $a0, vet1 #$a0 contem o endereco inicial de vet1
	la $a1, vet2 #$a1 contem o endereco inicial de vet2
	la $t0, vet3 #$t0 contem o endereco inicial de vet3
	li $a2, 0 #$a2 contem as posicoes dos vetores a serem percorridas (posicao atual)
	la $t1, size
	lw $t1, 0($t1) #$t1 contem o tamanho dos vetores
	
loop:	beq $a2, $t1, fim
	jal soma_vet
	addu $s2, $t2, $t0 #$s2 recebe o endereco da posicao atual do vet3
	sw $v0, 0($s2)
	addu $a2, $a2, 1
	j loop	
	
soma_vet:
	mulu $t2, $a2, 4 #$t2 recebe o deslocamento em relacao ao endereco inicial necessario
	addu $s0, $t2, $a0 #$s0 recebe o endereco da posicao atual do vet1
	addu $s1, $t2, $a1 #$s1 recebe o endereco da posicao atual do vet2
	lw $t3, 0($s0) #$t3 recebe o valor atual do vet1
	lw $t4, 0($s1) #$t4 recebe o valor atual do vet2
	addu $v0, $t3, $t4 #soma e armazena em $v0
	jr $ra

fim:
	li $v0, 10
	syscall

.data
vet1: .word 0x01 0x02 0x03 0x04 0x05
vet2: .word 0x04 0x03 0x02 0x01 0x00
vet3: .word 0x00 0x00 0x00 0x00 0x00
size: .word 0x05
