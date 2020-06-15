import java.util.Random;
public class sortingAlgs{
    private int contaInstruc;
    private float tempo;
    private int iteracoes;

    public sortingAlgs(){}
    
    public void bubbleSort1(int [] v){
        float tempoi = System.nanoTime();
        int aux = 0; //+1
        int[] resp = v;   
        contaInstruc = 2; 
        iteracoes = 0;            
        for (int i=0; i<resp.length; i++) { //+5
            contaInstruc+=5;
            iteracoes++;
            for (int j=0; j<resp.length-1; j++) { //+6
                contaInstruc+=9;
                iteracoes++;                
                if (resp[j] > resp[j+1]){ //+4
                    aux = resp[j]; //+2       
                    resp[j] = resp[j+1]; //+4
                    resp[j+1] =aux; //+3
                    contaInstruc+=9;
                }                
            }            
        } 
        float tempof = System.nanoTime();    
        tempo = tempof-tempoi;           
    }

    public void bubbleSort2(int [] v){
        float tempoi = System.nanoTime();
        int aux;        
        int[] resp = v;
        boolean trocou = true; //+1
        contaInstruc = 1;
        iteracoes = 0;
        while (trocou) { //1
            iteracoes++;
            trocou = false; //+1
            contaInstruc+=3;
            for (int i=0; i<resp.length-1; i++) { //+6
                iteracoes++;
                contaInstruc+=9;
                if (resp[i] > resp[i+1]){ //+4
                    aux = resp[i]; //+2     
                    resp[i] = resp[i+1]; //+4
                    resp[i+1] = aux; //+3
                    trocou = true; //+1
                    contaInstruc+=10;
                }                
            }            
        }
        float tempof = System.nanoTime();
        tempo = tempof-tempoi;
    }

    public void insertionSort(int [] v){
        float tempoi = System.nanoTime();
        int i, j, chave;
        contaInstruc=1;
        iteracoes = 0;
        for (j=1; j<v.length; j++){ //+5
            iteracoes++;
            chave = v[j]; //+2
            i = j - 1; //+2
            contaInstruc += 8;
            while ((i >= 0) && (v[i] > chave)){ //+4            
                v[i+1] = v[i]; //+4
                i = i-1; //+2
                contaInstruc+=10;
                iteracoes++;
            }
        v[i+1] = chave; //+3
        contaInstruc+=3;
        }
        float tempof = System.nanoTime();
        tempo = tempof-tempoi;
    }

    public void quicksort(int [] v, int low, int high) {
        int pivot;
        float tempoi = System.nanoTime();
        iteracoes = 0;
        contaInstruc = 2;
        if ((high-low) > 0) { //+2
            contaInstruc += 6;
            pivot = partition(v, low, high); //+2
            quicksort(v, low, pivot-1); //+2
            quicksort(v, pivot+1, high); //+2
        }
        float tempof = System.nanoTime();
        tempo = tempof - tempoi;
    }

    public int partition(int [] v, int low, int high) {
        int i, p, firsthigh;        
        firsthigh = low; //+1
        p = high; //+1
        contaInstruc+=3;
        for (i=low; i<high; i++){ //+4
            iteracoes++;
            contaInstruc+=6;
            if (v[i] < v[p]) { //+3
                contaInstruc+=3;
                swap(v, i, firsthigh); //+1
                firsthigh = firsthigh + 1; //+2
            }
        }
        contaInstruc++;
        swap(v, p, firsthigh); //+1
        return firsthigh;
    }

public void swap(int[] v, int a, int b){
    int aux = v[a];
    v[a] = v[b];
    v[b] = aux;
    contaInstruc+=7;
}

public void mergeSort(int [] v){
    float tempoi = System.nanoTime();
    iteracoes = 0;
    contaInstruc = 2;
    mergeSort(v, 0, v.length-1);
    float tempof = System.nanoTime();
    tempo = tempof-tempoi;
}

public void mergeSort(int [] v, int inicio, int fim){
    contaInstruc++;
    if (inicio < fim){
        contaInstruc+=7;
        int meio = (inicio + fim) / 2;
        mergeSort(v, inicio, meio);
        mergeSort(v, meio+1, fim);
        merge(v, inicio, meio, fim);
    }
}

private void merge(int [] v, int inicio, int meio, int fim) {
int nL = meio-inicio+1; //+3
int nR = fim-meio; //+2
int [] L = new int[nL]; //+2
int [] R = new int[nR]; //+2
contaInstruc+=14;
System.arraycopy(v, inicio, L, 0, nL); //+1
System.arraycopy(v, meio+1, R, 0, nR); //+1
int iL = 0; //+1
int iR = 0; //+1
for (int k=inicio; k<=fim; k++) { //+4
contaInstruc+=4;
iteracoes++;
    if (iR < nR) { //+1
        contaInstruc++;
        if (iL < nL) { //+1
            contaInstruc+=3;
            if (L[iL] < R[iR]){ //+3
                contaInstruc+=5 ; 
                v[k] = L[iL++]; //+5
            } 
            else{ 
                contaInstruc+=5;
                v[k] = R[iR++]; //+5
            }
        }
        else{ 
            contaInstruc+=5;
            v[k] = R[iR++]; //+5
        }
    }
    else{ 
        contaInstruc+=5;
        v[k] = L[iL++]; //+5
    }
    }
}

public String toString(){
    return "\niteracoes: " + iteracoes + "\n instrucoes: " + contaInstruc + "\n tempo: " + tempo + "\n"; 
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

public static void main(String[] Args){
    sortingAlgs s = new sortingAlgs();
    s.bubbleSort1(geraVetor(1000,1000));
    System.out.println("bubbleSort1 para vetor com tamanho 10:" + s);
}

}