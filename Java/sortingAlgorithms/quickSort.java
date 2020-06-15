import java.util.Random;
public class quickSort{

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

public static void quicksort(int [] v, int low, int high) {
    int pivot;
    if ((high-low) > 0) { //+2
        pivot = partition(v, low, high); //+2
        quicksort(v, low, pivot-1); //+2
        quicksort(v, pivot+1, high); //+2
    }
}

public static int partition(int [] v, int low, int high) {
    int i, p, firsthigh;
    firsthigh = low; //+1
    p = high; //+1
    for (i=low; i<high; i++){ //+4
        if (v[i] < v[p]) { //+3
            swap(v, i, firsthigh); //+1
            firsthigh = firsthigh + 1; //+2
        }
    }
    swap(v, p, firsthigh); //+1
    return firsthigh;
    }

public static void swap(int[] v, int a, int b){
    int aux = v[a];
    v[a] = v[b];
    v[b] = aux;
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
