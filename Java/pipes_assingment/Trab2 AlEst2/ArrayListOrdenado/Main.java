

public class Main {
    public static void main(String[] args){
        Machine tst = new Machine("caso7.txt");
        long tInicial = System.nanoTime();
        tst.dropBallInEachTube();
        long tFinal = System.nanoTime();
        double tTotal = (tFinal - tInicial)/1000000000.0;
        System.out.println("Tempo de execucao: " + tTotal);
        tst.mostFrequentTube();
    }
}