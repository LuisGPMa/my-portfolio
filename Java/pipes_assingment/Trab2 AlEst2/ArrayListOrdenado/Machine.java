
import java.io.*;
import java.nio.file.Paths;
import java.util.Scanner;
import java.nio.charset.*;
import java.nio.file.Files;

public class Machine{

    private Tube[] tubes;
    private int amntTubes;
    private int tubeLength;
    private int[] count; //vetor que conta quantas bolas cairam em cada tubo

    public Machine(String schematicsFile){        
        tubeCreator(schematicsFile);
    }

    public void tubeCreator(String schematicsFile){
        Scanner sc = null;
        try{
            sc = new Scanner(reader("..\\casos\\" + schematicsFile));
        }catch(IOException e){
            System.out.println("Error reading file");
        }

        amntTubes = sc.nextInt();
        tubeLength = sc.nextInt();
        tubes = new Tube[amntTubes];
        count = new int[amntTubes];
        for(int j=0; j<amntTubes; j++){
            tubes[j] = new Tube(tubeLength, j);
        }
        int startTube;
        int startPoint;
        int endTube;
        int endPoint;
        long tiConstrucao = System.nanoTime();
        while(sc.hasNextInt()){
            startTube = sc.nextInt();
            startPoint = sc.nextInt();
            endTube = sc.nextInt();
            endPoint = sc.nextInt();
            tubes[startTube].addLink(new Link(tubes[startTube], startPoint, tubes[endTube], endPoint));
        }
        long tfConstrucao = System.nanoTime();
        System.out.println("tempo para ler arquivo e criar tubos: " + (tfConstrucao-tiConstrucao)/1000000000.0);
        long tiSort = System.nanoTime();
        for(Tube t: tubes){
            t.sortTube();
            t.setEstadoInicial();
        }
        long tfSort = System.nanoTime();
        System.out.println("tempo para ordenar: " + (tfSort-tiSort)/1000000000.0);
    }

    public void dropBallInTube(int tube){
        Tube aux = tubes[tube];
        aux.dropIn();
        for(int i=0; i<tubes.length; i++){
            if(tubes[i].finishedHere()){
                count[i]++;
            }
        }
        
    }

    public void dropBallInEachTube(){
        for(int i=0; i<tubes.length; i++){
                dropBallInTube(i);
                for(Tube t: tubes){
                    t.reset();
                }         
        }
        showResults();
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

    public void showResults(){
        for(int i=0; i<count.length; i++){
            if(count[i]>0)
                System.out.println(i + "->" + count[i]);
        }
    }

    public static String reader(String arquivo) throws IOException{
        return new String(Files.readAllBytes(Paths.get(arquivo)), Charset.forName("ASCII"));
    }
}