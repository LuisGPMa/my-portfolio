import java.util.Random;
public class bubbleSort{    
    
    public static void main(String[] args){
        int[] v = geraVetor(10, 1000000);
        testaAlg(v, 1);
        testaAlg(v, 2);

        int[] ve = geraVetor(1000, 1000000);
        testaAlg(ve, 1);
        testaAlg(ve, 2);

        int[] vet = geraVetor(100000, 1000000);
        testaAlg(vet, 1);
        testaAlg(vet, 2);
    }  

     public static int[] bubbleSort1(int [] v){
        int aux = 0; //+1
        int[] resp = v;  //+1 
        int contaInstruc = 3;            
        for (int i=0; i<resp.length; i++) { //+5
            contaInstruc+=4;
            for (int j=0; j<resp.length-1; j++) { //+6
                if (resp[j] > resp[j+1]){ //+4
                    aux = resp[j]; //+2       
                    resp[j] = resp[j+1]; //+4
                    resp[j+1] =aux; //+3
                }
            }
        }        
        return resp;
    }

    private static void testaAlg(int[] vetor, int tipo){ //tipo=1 para bubble 1, tipo=2 para bubble 2
        if (tipo==1){
        int[] vAux = vetor;
        long tibubble1 = System.nanoTime();
        bubbleSort1(vAux);
        long tfbubble1 = System.nanoTime();
        long tempobubble1 = (tfbubble1 - tibubble1)/1000;
        System.out.println("tempo para ordenar um vetor de " + vAux.length + " posicoes usando bubble1: "+ tempobubble1 +" ms");
        }
        else if (tipo==2){            
            int[] vAux = vetor;
            long tibubble2 = System.nanoTime();
            bubbleSort2(vAux);
            long tfbubble2 = System.nanoTime();
            long tempobubble2 = (tfbubble2 - tibubble2)/1000;
            System.out.println("tempo para ordenar um vetor de " + vAux.length + " posicoes usando bubble2: "+ tempobubble2 +" ms");
        }
    }
    

    public static int[] bubbleSort2(int [] v){
        int aux;
        int[] resp = v;
        boolean trocou = true;
        while (trocou) {
            trocou = false;
            for (int i=0; i<resp.length-1; i++) {
                if (resp[i] > resp[i+1]){
                    aux = resp[i];        
                    resp[i] = resp[i+1];
                    resp[i+1] = aux;
                    trocou = true;
                }
            }
        }
        return resp;
    }

    public static void imprimeVetor(int[] v){
        for (int i=0; i<v.length; i++){
            System.out.print(v[i] + ", ");
        }
    } 

    public static int[] geraVetor(int nroElem, int lim){
        int dummy;
        int [] res = null;
        int cont = 0;
        Random rnd = new Random(System.nanoTime() * System.currentTimeMillis());
     
        if (nroElem >= 0) {
     
                    res = new int[nroElem];
     
                    while (cont < nroElem) {
                                  dummy = rnd.nextInt(lim)+1;
                                  dummy = rnd.nextInt(lim)+1;
                                  res[cont++] = rnd.nextInt(lim)+1;
                   }
        }
        return res;
     }
}