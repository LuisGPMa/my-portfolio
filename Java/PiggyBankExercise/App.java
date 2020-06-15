import java.util.Random;
public class App{
    public static void imprimeSituacaoCofre(Cofrinho cofre){
        System.out.println("qtd de moedas de 1 centavo: " + cofre.getQtdTipo(1));
        System.out.println("qtd de moedas de 5 centavos: " + cofre.getQtdTipo(5));
        System.out.println("qtd de moedas de 10 centavos: " + cofre.getQtdTipo(10));
        System.out.println("qtd de moedas de 25 centavos: " + cofre.getQtdTipo(25));
        System.out.println("qtd de moedas de 50 centavos: " + cofre.getQtdTipo(50));
        System.out.println("qtd de moedas de 1 real: " + cofre.getQtdTipo(100));
        System.out.println("qtd total de moedas: " + cofre.getTotalMoedas());
        System.out.println("qtd total de reais no cofrinho: R$" + cofre.getValorTotal());
    }

    public static void addMoedas(Cofrinho cofre, Random gerador){
        int i;         
        cofre.addMoeda(1, gerador.nextInt(21));     //adicionando um numero aleatorio entre 0 e 20 de cada moeda ao cofrinho
        cofre.addMoeda(5, gerador.nextInt(21));       
        cofre.addMoeda(10, gerador.nextInt(21));        
        cofre.addMoeda(25, gerador.nextInt(21));        
        cofre.addMoeda(50, gerador.nextInt(21));        
        cofre.addMoeda(100, gerador.nextInt(21));
        
    }

    public static void balancaCofrinho(Cofrinho cofre, Random gerador){
        int i;
        for (i=0; i<gerador.nextInt(11); i++){ //balanca o cofrinho uma qtd aleatoria (entre 0 e 10) de vezes
            System.out.println("moeda aleatoria retirada:" + cofre.balancaCofrinho() + "centavos");
        }
    }
    public static void main(String[] args){
        int i;
        Cofrinho cofre = new Cofrinho();
        Random gerador = new Random();

        addMoedas(cofre, gerador);

        System.out.println("moedas na ordem em que foram inseridas:");
        System.out.println(cofre.moedasOrdem());

        System.out.println("situacao do cofrinho antes de balancar:");
        imprimeSituacaoCofre(cofre);

        balancaCofrinho(cofre, gerador);

        System.out.println("situacao do cofrinho apos balancar:");
        imprimeSituacaoCofre(cofre);        
    }
}