 .text   
         .globl  main            
         
 main:  la	$t0,A		# Carrega endereco de A em $t0 - PSEUDO-INSTRUCAO
 	lw	$t1,0($t0)	# Le valor de A para $t1
 	la	$t2,B		# Carrega endereco de B em $t2 - PSEUDO-INSTRUCAO
 	lw	$t3,0($t2)	# Le valor de B para $t3
 	addu	$t4,$t1,$t3	# $t4 recebe A+B
 	addiu	$t6, $t1, 8
 	la	$t5,C		# Carrega endereco de C em $t5 - PSEUDO-INSTRUCAO
 	sw	$t4,0($t5)	# C recebe A+B
 	
 	
 	jr	$ra		# volta para o kernel do simulador
 	
 	#li	$v0, 10
 	# syscall
 	
 	
 	
 	.data
 A:	.word	32
 B:	.word	0x05
 C:	.word	0
        
 
 
