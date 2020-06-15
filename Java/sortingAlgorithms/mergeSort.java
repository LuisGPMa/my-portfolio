import java.util.Random;
public class mergeSort{

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
    mergeSort(vAux);
    long tf = System.nanoTime();
    long ttotal = (tf-ti)/1000; 
    //System.out.print("vetor ordenado: ");
    //imprimeVetor(vAux);             
    System.out.println("tempo de execucao do merge para um vetor com " + vAux.length + " posicoes: " + ttotal+ " ms");
    }

public static void mergeSort(int [] v){
    mergeSort(v, 0, v.length-1);
}

public static void mergeSort(int [] v, int inicio, int fim){
    if (inicio < fim){
        int meio = (inicio + fim) / 2;
        mergeSort(v, inicio, meio);
        mergeSort(v, meio+1, fim);
        merge(v, inicio, meio, fim);
    }
}

private static void merge(int [] v, int inicio, int meio, int fim) {
int nL = meio-inicio+1; //+3
int nR = fim-meio; //+2
int [] L = new int[nL]; //+2
int [] R = new int[nR]; //+2
System.arraycopy(v, inicio, L, 0, nL); //+1
System.arraycopy(v, meio+1, R, 0, nR); //+1
int iL = 0; //+1
int iR = 0; //+1
for (int k=inicio; k<=fim; k++) { //+4
    if (iR < nR) { //+1
        if (iL < nL) { //+1
            if (L[iL] < R[iR]) //+3 
                v[k] = L[iL++]; //+5
            else 
                v[k] = R[iR++]; //+5
        }
        else 
            v[k] = R[iR++]; //+5
    }
    else 
        v[k] = L[iL++]; //+5
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
