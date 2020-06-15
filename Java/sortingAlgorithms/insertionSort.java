import java.util.Random;
public class insertionSort{

public static void main(String args[]){
    int[] v = geraVetor(10, 1000000);
    testaAlg(v);
    int[] ve = geraVetor(1000, 1000000);
    testaAlg(ve);
    int[] vet = geraVetor(100000, 1000000);
    testaAlg(vet);
}

private static void testaAlg(int[] v){             
    int[] vAux = v;
    //System.out.print("vetor nao ordenado: ");
    //imprimeVetor(v);
    //System.out.println();
    long ti = System.nanoTime();
    insertionSort(vAux);
    long tf = System.nanoTime();
    long ttotal = (tf-ti)/1000; 
    //System.out.print("vetor ordenado: ");
    //imprimeVetor(vAux);             
    System.out.println("tempo de execucao do insertion para um vetor com " + vAux.length + " posicoes: " + ttotal+ " ms");
    }

public static void insertionSort(int [] v){
    int i, j, chave, contaInstruc;
    contaInstruc++;
    for (j=1; j<v.length; j++){ //+5
        chave = v[j]; //+2
        i = j - 1; //+2
        contaInstruc += 12;
        while ((i >= 0) && (v[i] > chave)){ //+4            
            v[i+1] = v[i]; //+4
            i = i-1; //+2
            contaInstruc+=6;
        }
        v[i+1] = chave; //+3
        contaInstruc+=3;
    }
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
