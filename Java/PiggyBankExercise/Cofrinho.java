import java.util.Random;
import java.util.ArrayList;

public class Cofrinho{

    private ArrayList<Integer> moedas;
    private boolean moedasOK;

    public Cofrinho(){
        moedas = new ArrayList<Integer>();
        moedasOK = false;
    }

    public void addMoeda(Integer tipo, int qtd){
        if (tipo==1 || tipo==5 || tipo==10 || tipo==25 || tipo==50 || tipo==100 && qtd>0){
            for(int i=0; i<qtd; i++){
            this.moedas.add(tipo);
            }
        }
    }

    public int getTotalMoedas(){
        return (moedas.size());
    }

    public int getQtdTipo(int tipo){ //retorna a quantidade de moedas do tipo do parametro
        int conta = 0;
        for(int i=0;i<moedas.size();i++){ 
            if (moedas.get(i)==tipo){
                conta++;} 
            }
        return conta;
    }

    private void moedasok(){
        if (getTotalMoedas()>0){ 
            moedasOK = true;
        }
        else {moedasOK = false;}
    }

    public double getValorTotal(){
        double valorTotal = 0;
        for (int i=0; i<moedas.size(); i++){
            valorTotal += moedas.get(i);
        }
        return (valorTotal/100);
    }

    public double balancaCofrinho(){
        moedasok();
        if (!moedasOK){
            return -1.0;
        }
        else{
        Random gerador = new Random();
        int indice = gerador.nextInt(moedas.size());
        int resp = moedas.get(indice);
        moedas.remove(indice); 
        return (resp);
                       
        }
    }

    public String moedasOrdem(){
        String resp = String.valueOf(moedas.get(0));
        for(int i=1; i<moedas.size(); i++){
            resp = resp + ", " + String.valueOf(moedas.get(i));
        }
        return resp;
    }
}
