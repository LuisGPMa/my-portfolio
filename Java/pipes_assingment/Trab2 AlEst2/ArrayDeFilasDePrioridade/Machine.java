import java.io.*;
import java.nio.file.Paths;
import java.util.Scanner;
import java.nio.charset.*;
import java.nio.file.Files;
import java.util.*;

public class Machine{

    private int amntTubes; //qtd de tubos sera a quantidade de colunas
    private int tubeLength; //tamanho de cada tubo
    private int[] count; //vetor que conta quantas bolas cairam em cada tubo
    PriorityQueue<Link> machine[]; //cada fila representa um tubo, e os links estarao ordenados de acordo com a posicao em que saem do tubo inicial
    PriorityQueue<Link> estadoInicial[]; //a cada bolinha jogada, as filas de prioridades mudam e precisam ser resetadas para o estado inicial

    public Machine(String schematicsFile){        
        tubeCreator(schematicsFile);
    }

    //le o arquivo e cria as filas de prioridade
    public void tubeCreator(String schematicsFile){
        Scanner sc = null;
        try{
            sc = new Scanner(reader("..\\casos\\" + schematicsFile));
        }catch(IOException e){
            System.out.println("Error reading file");
        }
        amntTubes = sc.nextInt();
        tubeLength = sc.nextInt();
        machine = new PriorityQueue[amntTubes];
        estadoInicial = new PriorityQueue[amntTubes];
        for(int i=0; i<amntTubes; i++){
            machine[i] = new PriorityQueue<Link>();
            estadoInicial[i] = new PriorityQueue<Link>();
        }
        count = new int[amntTubes];
        int startTube;
        int startPoint;
        int endTube;
        int endPoint;
        Link link = null;
        while(sc.hasNextInt()){
            startTube = sc.nextInt();
            startPoint = sc.nextInt();
            endTube = sc.nextInt();
            endPoint = sc.nextInt();
            link = new Link(startTube, startPoint, endTube, endPoint);
            machine[startTube].add(link);
            estadoInicial[startTube].add(link);
        }
    }

    //metodo principal que implementa o algoritmo para percorrer os tubos e links
    public void dropBallInTube(int tube){
       PriorityQueue<Link> tuboSaida = machine[tube];
       int posChegada;
       int tuboDestino;
       Link link;
       while(tuboSaida.size()>0){
           link = tuboSaida.poll();
           tuboDestino = link.getDestination();       
           posChegada = link.getEndPoint();
           if(skippedLinksRemover(tuboDestino, posChegada)){
               count[tuboDestino]++;
               break;
           }
           tuboSaida = machine[tuboDestino];
       }
    }

    //quando uma bolinha é direcionada a um tubo, esse metodo remove todos os links que foram 'pulados' e retorna true quando a fila termina, ou seja
    //quando o tubo em questao é o tubo no qual a bolinha caiu
    private boolean skippedLinksRemover(int tuboChegada, int posChegada){ 
        PriorityQueue<Link> tube = machine[tuboChegada];
        if(tube.isEmpty())
            return true;
        while(tube.peek().getStartingPoint() <= posChegada){
            tube.poll();
            if(tube.isEmpty()){
                return true;
            }
        }
        return false;
    }

    public void showResults(){
        for(int i=0; i<count.length; i++){
            if(count[i]>0)
                System.out.println(i + "->" + count[i]);
        }
    }

    private void reset(){
        for(int i = 0; i<amntTubes; i++){
            machine[i] = new PriorityQueue<Link>(estadoInicial[i]);
        }
    }
    
    
    public void dropBallInEachTube(){
        for(int i=0; i<amntTubes; i++){
                dropBallInTube(i);
                reset();
        }
    }

    public void mostFrequentTube(){
        int indMaior = 0;
        for(int i=0; i<count.length; i++){
            if(count[i]>count[indMaior]){
                indMaior = i;
            }
        }
        System.out.println("Maximo: cano " + indMaior + ", com " + count[indMaior] + " bolinhas");
    }
    

    public static String reader(String arquivo) throws IOException{
        return new String(Files.readAllBytes(Paths.get(arquivo)), Charset.forName("ASCII"));
    }

    public static void main(String[] args){

        long tconstrInicial = System.nanoTime();
        Machine tst = new Machine("caso3.txt");
        long tconstrFinal = System.nanoTime();
        System.out.println("tempo de construcao dos tubos: " + (tconstrFinal - tconstrInicial)/1000000000.0 + "s");

        long tInicial = System.nanoTime();
        tst.dropBallInEachTube();
        long tFinal = System.nanoTime();
        double tTotal = (tFinal - tInicial)/1000000000.0;
        System.out.println("Tempo de execucao: " + tTotal + "s");

        tst.showResults();
        tst.mostFrequentTube();    
    }
}