import java.util.Random;
public class algPesquisa{

    public static void main(String[] args){
        int[] vetor = geraVetor(10000, 1000000);
        testaAlg(vetor, 0);
        testaAlg(vetor, 1);

        int[] vetorGrande = geraVetor(1000000, 1000000);
        testaAlg(vetorGrande, 0);
        testaAlg(vetorGrande, 1);


    }

    private static void testaAlg(int[] vetor, int tipo){ //tipo=0 para pesquisa direta, tipo=1 para binary search
        if (tipo==0){
        long t0Direta = System.nanoTime();
        int iteracoesPesqDireta = pesqDireta(vetor, 54245);
        long t1Direta = System.nanoTime();
        long tempoDireta = t1Direta - t0Direta;
        System.out.println("Iteracoes por pesquisa direta em um vetor de 10000 posicoes: " + iteracoesPesqDireta + " tempo de execucao: " + (tempoDireta/1000) + " ms");
        }
        else if (tipo==1){
            long t0Binary = System.nanoTime();
            iteracoesBinary = binarySearch(vetor, 54245);
            long t1Binary = System.nanoTime();
            long tempoBinary = t1Binary - t0Binary;
            System.out.println("Iteracoes por pesquisa binaria em um vetor de 10000 posicoes: " + iteracoesBinary + " tempo de execucao: " + (tempoBinary/1000) + " ms");
        }
    }

    private static int pesqDireta(int [] vet, int valor) {
        int contaItera = 0;
        int res = -1, i; //+1
        int contaInstruc = 2;
        for (i = 0; ((i < vet.length) && (vet[i] != valor)); i++){
            contaInstruc += 7;
            contaItera++;
        }
        contaInstruc += 2;
        if (i < vet.length){
            res = i;
            contaInstruc++;
        }
        contaInstruc++;
        //return res;
        //return contaInstruc;
        return contaItera;
    }


    private static int binarySearch(int [] v, int value){
        int contaInstruc = 0;
        int contaItera = 0;
        int low = 0; //+1
        int high = v.length - 1; //+3
        int p = low + ((high-low)/2); //+4
        contaInstruc += 8;
        while (low <= high){ //+1
            contaInstruc++;
            contaItera++;
            if (v[p] > value){ //+2
                high = p - 1; //+2
                contaInstruc += 4;
            }
            else if (v[p] < value){ //+2
                low = p + 1; //+2
                contaInstruc += 6;
            }   
            else{ 
                contaInstruc++;
                //return p; //+1        
            }
            p = low + ((high - low)/2); //+4
            contaInstruc += 8;      
        }
        contaInstruc++;
        return contaItera;
        //return contaInstruc;
        //return -1; //+1
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