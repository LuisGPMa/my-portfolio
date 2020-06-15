import java.util.random;
public class bubbleSort{
    private float tempo;
    private int contaInstruc;
    private int contaIteracoes;

    public bubbleSort(){
        contaInstruc = 0;
        contaIteracoes = 0;
    }

    public static int[] bubbleSort1(int [] v){
        tempo = System.nanoTime();
        int aux = 0; //+1
        int[] resp = v;  //+1 
        contaInstruc += 3;            
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

}