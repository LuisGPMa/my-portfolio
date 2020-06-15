#	Aluno: Luis Gabriel Pacheco Marquetti
#	PUCRS, 2020/1
#	Organização e Arquitetura de Computadores I, Turma 128
	.text
        .globl  main
main:
	la $a0, A #$a0 contem endereço inicial do vetor
	
	la $a1, prim
	lw $a1, 0($a1) #$a1 = prim
	
	la $a2, ult
	lw $a2, 0($a2) #$a2 = ult
	
	la $a3, valor
	lw $a3, 0($a3) #$a3 = valor
	
	addiu $sp, $sp, -16 #abrindo espaço na pilha
	
	sw $a0, 12($sp) #empilhando end inicial do vetor
	sw $a1, 8($sp) #empilhando prim
	sw $a2, 4($sp) #empilhando ult
	sw $a3, 0($sp) #empilhando valor
	jal bin_search
	
	addiu $sp, $sp, 20 #esvaziando a pilha
	
	move $s0, $v0 #$s0 segurará o resultado
	
	la $a0, achou
	li $v0, 4
	syscall
	
	move $a0, $s0
	li $v0, 1
	syscall
	
	j exit
	
bin_search:
	addiu $sp, $sp, -4 #abrindo espaço pro $ra
	sw $ra, 0($sp) #empilhando o $ra
	lw $t0, 16($sp) #$t0 = end inicial do vetor
	lw $t1, 12($sp) #$t1 = prim
	lw $t2, 8($sp) #$t2 = ult
	lw $t3, 4($sp) #$t3 = valor
	bgt $t1, $t2, valor_nao_encontrado #pre condicao invalida
	j busca
	
busca:
	addu $t4, $t1, $t2 #$t4 = prim+ult
	div $t4, $t4, 2 #$t4  = (prim+ult)/2 $t4 = indice no meio do vetor
	mulu $t5, $t4, 4 #$t5 = deslocamento para o valor no meio do vetor
	add $t5, $t5, $t0 #$t5 = endereço do valor no meio do vetor
	lw $t5, 0($t5) #$t5 = valor no meio do vetor
	
	beq $t3, $t5, retorno #valor foi encontrado
	
	beq $t1, $t2, valor_nao_encontrado
	
	bgt $t1, $t2, valor_nao_encontrado
	
	blt $t3, $t5, esquerda
	
	j direita
	
esquerda:
	addiu $t2, $t4, -1 #ult = meio-1
	j busca
	
direita:
	addiu $t1, $t4, 1 #prim = meio+1
	j busca
	
valor_nao_encontrado: 
	li $t4, -1
	j retorno
	
retorno: 
	add $v0, $zero, $t4
	jr $ra
	
exit:
	li $v0, 10
	syscall

	.data
A:     .word   -5 -1 5 9 12 15 21 29 31 58 250 325
	      # 0  1 2 3 4  5  6  7  8  9  10  11
prim: .word 0
ult: .word 11
valor: .word 325
achou: .asciiz "Indice do valor: "
