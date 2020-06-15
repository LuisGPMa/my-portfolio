import java.util.LinkedList;
import java.util.List;

public class App{

    
    public static void imprime(List<Integer> lista, IntToStr func){
        List<Integer> nova = new LinkedList<>();
        for (Integer i: lista){
            resp += i.processa();
        }
    }

    public static void main(String[] args){
        List<Integer> l1 = new LinkedList<Integer>();
        for(Integer i=0; i<20; i++){
            l1.add(i+1);
        }
        imprime(l1, (int n)->"R$" + Integer.toString(n));

        imprime(l1, (int n) -> (n>0)? Integer.toString(Math.abs(n)) + "<C>" : Integer.toString(Math.abs(n)) + "<D>");
    }
}