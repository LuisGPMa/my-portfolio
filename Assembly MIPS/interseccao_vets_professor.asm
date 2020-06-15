#
#	Algoritmo de Interseccao de 2 vetores
#
#

        .data                 
V1: .word   0x268 0xff 0x33 0x14 0x30
V2: .word   0x10 0x33 0xfe 0x268 0x31
V3: .word   0x00 0x00 0x00 0x00 0x00                             
                                
size:   .word   5 
comum:	.word	0
LC1:	.asciiz "Valores do vetor resultado (V3): "
LC2:	.asciiz "A intersecção é vazia"
LC3:	.asciiz " "
        
        .text                   
        .globl  main            
main:
        la      $t0,V1		# carrega o endereco de V1
        la      $t1,size	# carrega o endereco de size
	la      $t2,V2		# carrega o endereco de V2
	lw	$t1,0($t1)	# carrega o valor de size
	la 	$t6,V3		# carrega o endereco de V3
	

loop:   blez    $t1,print	# se size<=0 termina
        lw      $t4,0($t0)	# carrega o conteudo da posicao de memoria de V1 em $t4
	la	$t3,size	# carrega em $t3 o tamanho do vetor para servir de controle 
	lw	$t3,0($t3)	# para o 2o. vetor que sera percorrido
	la      $t2,V2		 

loop2:	blez	$t3,sai		# se $t3<=0, o 2o. vetor foi todo percorrido; senao continua comparando
	lw	$t5,0($t2)	# carrega o conteudo da posicao de memoria de V2 em $t5
	beq	$t4,$t5,acres	# se iguais salta para acres

        addiu   $t2,$t2,4       # incrementa o apontador de posicao
        addiu   $t3,$t3,-1	# subtrai um do controle   
        j       loop2            

sai:	addiu   $t0,$t0,4	# pega o proximo elemento de V1
	addiu   $t1,$t1,-1	# subtrai um do contador de V1
	j	loop

acres:	sw	$t5,0($t6)	# armazena o elemento igual no vetor V3
	addiu	$t7, $t7,1
	addiu   $t0,$t0,4
	addiu   $t1,$t1,-1
	addiu   $t6,$t6,4
	j	loop	
        
        # now, return from main
print:  la	$s0, V3

	la    $a0,LC1        
	li    $v0,4        		# Imprime "Valor do vetor resultado (V3): "
  	syscall

loop3:	beq	$t7, $zero, vazio	# caso a intersecção seja vazia
	blez	$t7, fim		# se o vetor resultado não for vazio, imprime os valores do vetor
	lw	$a0, 0($s0)	
	li      $v0, 34           
  	syscall
  	
  	la    $a0,LC3        
	li    $v0,4        		# Imprime " "
  	syscall
  	
  	
  	addiu	$t7, $t7, -1
  	addiu	$s0, $s0, 4
  	j loop3
  	

	la	$t8, comum
	sw	$t7, 0($t8)
	j	fim

vazio:  la    $s0, V3
	lw    $s0, 0($s0)
	bne   $s0, $zero, fim		# testa se a 1a. posição do vetor resultado é zerok se for zero imprime intersecção vazia

	la    $a0,LC2        
	li    $v0,4        		# Imprime "A intersecção é vazia "
  	syscall
		
fim:	li $v0, 10
	syscall
