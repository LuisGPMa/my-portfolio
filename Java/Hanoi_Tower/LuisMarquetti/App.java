import java.util.Scanner;
public class App{
    public static void launch(){
        boolean continua = true;
        Scanner input = new Scanner(System.in);
        TorreHanoi jogo;
        int resposta;
        String aux;
        String auxNova;
        int qtdDiscos;
        while(continua){
            System.out.print("Pra comecar, digite a quantidade de discos que desejas no jogo. Os valores permitidos sao de 3 a 7 discos: ");
            qtdDiscos = input.nextInt();
            while(qtdDiscos<3 || qtdDiscos>7){
                System.out.print("Os valores permitidos sao de 3 a 7 discos: ");
                qtdDiscos = input.nextInt();
            }
            jogo = new TorreHanoi(qtdDiscos);
            System.out.println("\n" + jogo);
            while(!(jogo.isFinished())){
                do{              
                    System.out.print("Indique de que torre tirar o disco. Digite 'a' para torre da esquerda, 'b' para a do meio e 'c' para a da direita: ");
                    aux = input.nextLine();
                }                
                while(!(aux.equalsIgnoreCase("a") || aux.equalsIgnoreCase("b") || aux.equalsIgnoreCase("c")));

                do{
                    System.out.println("\nAgora indique para qual torre deseja mover o disco: ");
                    auxNova = input.nextLine();
                }
                while(!(auxNova.equalsIgnoreCase("b") || auxNova.equalsIgnoreCase("b") || auxNova.equalsIgnoreCase("c")));

                while(!(jogo.isAllowed(aux, auxNova))){
                    System.out.println("Jogada ilegal. Digite entradas aceitaveis.");
                    System.out.print("Indique de que torre tirar o disco:");
                    aux = input.nextLine();
                    System.out.println("\nAgora indique para qual torre deseja mover o disco: ");
                    auxNova = input.nextLine();
                }
                jogo.movimento(aux, auxNova);
                System.out.println("\n" + jogo);
            }
            System.out.print("Desejas jogar de novo? Digite 1 pra sim e 0 pra nao.\n");
            resposta = input.nextInt();
            while(resposta != 0 && resposta != 1){
                System.out.print("Desejas jogar de novo? Digite 1 pra sim e 0 pra nao.\n");
                resposta = input.nextInt();
            }
            if(resposta == 0)
                continua = false;
        }
    }

    public static void main(String[] args){
        launch();
    }
}