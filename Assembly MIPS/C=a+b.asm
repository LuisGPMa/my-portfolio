.text   
         .globl  main            
         
 main:  la $a0, LC1
 	li $v0, 4
 	syscall
 	
 	li $v0, 5
 	syscall
 	move	$t0,$v0		# Carrega valor de A lido em $t0
 	
 	la $a0, LC2
 	li $v0, 4
 	syscall
 	
 	li $v0, 5
 	syscall
 	move $t1,$v0		#Carrega valor de B lido em $t1
 	
 	addu	$t2,$t0,$t1	# $t2 recebe A+B
 	
 	la $a0, LC3
 	li $v0, 4
 	syscall
 	
 	move $a0, $t2
 	li $v0, 1
 	syscall
 	
 	
 	
 	
 	#jr	$ra		# volta para o kernel do simulador
 	
 	li	$v0, 10
 	syscall
 	
 	
 	
 	.data
 LC1:   .asciiz "Digite o valor A: "
 LC2:	.asciiz "Digite o valor B: "
 LC3:   .asciiz "soma= "  