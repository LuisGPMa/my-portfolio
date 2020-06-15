#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>		// Para usar strings
#include <time.h>

#ifdef WIN32
#include <windows.h>    // Apenas para Windows
#endif

#ifdef __APPLE__
#define GL_SILENCE_DEPRECATION
#include <OpenGL/gl.h>
#include <OpenGL/glu.h>
#include <GLUT/glut.h>
#else
#include <GL/gl.h>     // Funções da OpenGL
#include <GL/glu.h>    // Funções da GLU
#include <GL/glut.h>   // Funções da FreeGLUT
#endif

// SOIL é a biblioteca para leitura das imagens
#include "SOIL.h"

// Um pixel Pixel (24 bits)
typedef struct {
    unsigned char r, g, b;
} Pixel;

// Uma imagem Pixel
typedef struct {
    int width, height;
    Pixel* img;
} Img;

//Uma semente
typedef struct{
    int xs, ys;
    Pixel color;
} Seed;

// Protótipos
void load(char* name, Img* pic);
void valida();

// Funções da interface gráfica e OpenGL
void init();
void draw();
void keyboard(unsigned char key, int x, int y);

//Função para calcular a cor media entre dois pontos que sera
//usada para calibrar um pouco melhor a cor dos pixels do output
Pixel corMedia();

// Largura e altura da janela
int width, height;

// Identificadores de textura
GLuint tex[2];

// As 2 imagens
Img pic[2];

// Imagem selecionada (0,1)
int sel;

// Carrega uma imagem para a struct Img
void load(char* name, Img* pic)
{
    int chan;
    pic->img = (Pixel*) SOIL_load_image(name, &pic->width, &pic->height, &chan, SOIL_LOAD_RGB);
    if(!pic->img)
    {
        printf( "SOIL loading error: '%s'\n", SOIL_last_result() );
        exit(1);
    }
    printf("Load: %d x %d x %d\n", pic->width, pic->height, chan);
}

int main(int argc, char** argv)
{
    if(argc < 1) {
        printf("artistic [im. entrada]\n");
        exit(1);
    }
	glutInit(&argc,argv);

	// Define do modo de operacao da GLUT
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB);

	// pic[0] -> imagem de entrada
	// pic[1] -> imagem de saida

	// Carrega a imagem
    load(argv[1], &pic[0]);

    width = pic[0].width;
    height = pic[0].height;

    // A largura e altura da imagem de saída são iguais às da imagem de entrada (0)
    pic[1].width  = pic[0].width;
    pic[1].height = pic[0].height;
	pic[1].img = calloc(pic[1].width * pic[1].height, 3); // W x H x 3 bytes (Pixel)

	// Especifica o tamanho inicial em pixels da janela GLUT
	glutInitWindowSize(width, height);

	// Cria a janela passando como argumento o titulo da mesma
	glutCreateWindow("Arte Computacional");

	// Registra a funcao callback de redesenho da janela de visualizacao
	glutDisplayFunc(draw);

	// Registra a funcao callback para tratamento das teclas ASCII
	glutKeyboardFunc (keyboard);

    // Exibe as dimensões na tela, para conferência
    printf("Origem  : %s %d x %d\n", argv[1], pic[0].width, pic[0].height);
    sel = 0; // entrada

	// Define a janela de visualizacao 2D
	glMatrixMode(GL_PROJECTION);
	gluOrtho2D(0.0,width,height,0.0);
	glMatrixMode(GL_MODELVIEW);

    // Converte para interpretar como matriz
    Pixel (*in)[width] = (Pixel(*)[width]) pic[0].img;
    Pixel (*out)[width] = (Pixel(*)[width]) pic[1].img;

    //o usuario decide quantas sementes em cada direçao ele deseja criar
    //em cada direção. O algoritmo as distribui igualmente pela imagem, de modo que o numero
    //total de sementes é o produto das duas variaveis a seguir.
    int nSeedsHorizontal;
    int nSeedsVertical;
    printf(">-Diga o numero de sementes na direcao horizontal que deseja usar. \n");
    printf(">-Cuidado, pois o uso de numeros que nao sao divisores de %d pode resultar em erro\n", width);
    printf(">-Alguns divisores de %d: [5, 10, 20, 25, 40, 50, 80, 100, 160, 200]\n", width);
    printf("Qtd de sementes horizontais: ");
    scanf("%d", &nSeedsHorizontal);
    printf(">-Agora diga o numero de sementes na direcao vertical que deseja usar. \n");
    printf(">-Cuidado, pois o uso de numeros que nao sao divisores de %d pode resultar em erro\n", height);
    printf(">-Alguns divisores de %d: [5, 10, 15, 20, 25, 40, 50, 60, 75, 100, 120, 150, 200]\n", height);
    printf("Qtd de sementes verticais: ");
    scanf("%d", &nSeedsVertical);

    // Declarando e gerando as seeds
    int NUM_SEEDS = nSeedsHorizontal*nSeedsVertical;
    printf(">-Quantidade de sementes utilizadas: %d\n", NUM_SEEDS);
    Seed seeds[NUM_SEEDS]; //vetor contendo as seeds
    int seedIndex = 0; //indice para o vetor
    int espacoEntreSeedsHorizontal = width/nSeedsHorizontal;
    int espacoEntreSeedsVertical = height/nSeedsVertical;
    srand(time(NULL));
    int randx; //o elemento randomico em x
    int randy; //o elemento randomico em y
    int randRangex, randRangey; //define ate onde o elemento randomico pode ir
    printf(">-Escolha um numero inteiro para representar o elemento de aleatoriedade em cada direcao (x e y). \n>-Eles podem ou nao ser o mesmo\n");
    printf(">-Quanto mais sementes tiveres escolhido, menor sera o impacto da aleatoriedade no resultado final\n");
    printf(">-De modo geral, numeros entre 10 e 100 modificam a imagem levemente, e 0 nao introduz nenhuma aleatoriedade\n");
    printf("Diga quanta aleatoriedade quer na horizontal: ");
    scanf("%d", &randRangex);
    printf("Agora diga quanta aleatoriedade quer na vertical: ");
    scanf("%d", &randRangey);
    randRangex++;
    randRangey++;
    int xseed;
    int yseed;

    for(int ys=0; ys<height; ys+=espacoEntreSeedsVertical){
        for(int xs=0; xs<width; xs+=espacoEntreSeedsHorizontal){
            randx = (rand() % randRangex) - (randRangex/2);
            randy = (rand() % randRangey) - (randRangey/2);
            yseed=ys+randy;
            xseed=xs+randx;
            if(xseed>=width){
                xseed=width-1;
            }else if(xseed<0){
                xseed=0;
            }
            if(yseed>=height){
                yseed=height-1;
            }else if(yseed<0){
                yseed=0;
            }
            seeds[seedIndex].color = in[yseed][xseed];
            seeds[seedIndex].xs = xseed;
            seeds[seedIndex].ys = yseed;
            seedIndex++;
        }
    }

	// Aplica o algoritmo e gera a saida em out (pic[1].img)
	// ...
	// ...
    // Exemplo: copia apenas o componente vermelho para a saida

    Seed maisProx;
    double distanciaMaisProx;
    double distanciaAtual;
    for(int y=0; y<height; y++){
        for(int x=0; x<width; x++){
            maisProx = seeds[0];
            distanciaMaisProx = sqrt((double)(pow(x-seeds[0].xs, 2) + pow(y-seeds[0].ys, 2)));
            for(int i = 1; i<NUM_SEEDS; i++){
                distanciaAtual = sqrt((double)(pow(x-seeds[i].xs, 2) + pow(y-seeds[i].ys, 2)));
                if(distanciaAtual<distanciaMaisProx){
                    distanciaMaisProx = distanciaAtual;
                    maisProx = seeds[i];
                }
            }
            out[y][x].r = maisProx.color.r*0.96 + in[y][x].r*0.04; //na escolha das cores eu usei uma media ponderada das cores da entrada e da seed.
            out[y][x].g = maisProx.color.g*0.96 + in[y][x].g*0.04; //Usando um peso bem maior para a cor da seed se obtém uma saída mais estilizada,
            out[y][x].b = maisProx.color.b*0.96 + in[y][x].b*0.04; //menos parecida com a real
        }
    }
	// Cria texturas em memória a partir dos pixels das imagens
    tex[0] = SOIL_create_OGL_texture((unsigned char*) pic[0].img, width, height, SOIL_LOAD_RGB, SOIL_CREATE_NEW_ID, 0);
    tex[1] = SOIL_create_OGL_texture((unsigned char*) pic[1].img, width, height, SOIL_LOAD_RGB, SOIL_CREATE_NEW_ID, 0);

	// Entra no loop de eventos, não retorna
    glutMainLoop();
}

Pixel corMedia(Pixel seed, Pixel px, double pesoSeed){ //pesoSeed é um número de 0 a 1 que corresponde ao quanto a cor da
    Pixel resp;                                        //seed afeta a cor do pixel que está na sua região

    return resp;
}

// Gerencia eventos de teclado
void keyboard(unsigned char key, int x, int y)
{
    if(key==27) {
      // ESC: libera memória e finaliza
      free(pic[0].img);
      free(pic[1].img);
      exit(1);
    }
    if(key >= '1' && key <= '2')
        // 1-2: seleciona a imagem correspondente (origem ou destino)
        sel = key - '1';
    glutPostRedisplay();
}

// Callback de redesenho da tela
void draw()
{
    glClearColor(0.0f, 0.0f, 0.0f, 1.0f);  // Preto
    glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);

    // Para outras cores, veja exemplos em /etc/X11/Pixel.txt

    glColor3ub(255, 255, 255);  // branco

    // Ativa a textura corresponde à imagem desejada
    glBindTexture(GL_TEXTURE_2D, tex[sel]);
    // E desenha um retângulo que ocupa toda a tela
    glEnable(GL_TEXTURE_2D);
    glBegin(GL_QUADS);

    glTexCoord2f(0,0);
    glVertex2f(0,0);

    glTexCoord2f(1,0);
    glVertex2f(pic[sel].width,0);

    glTexCoord2f(1,1);
    glVertex2f(pic[sel].width, pic[sel].height);

    glTexCoord2f(0,1);
    glVertex2f(0,pic[sel].height);

    glEnd();
    glDisable(GL_TEXTURE_2D);

    // Exibe a imagem
    glutSwapBuffers();
}
