import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class App {
    public static List<Integer> processaListaInt(List<Integer> lstInt,ProcInt func){
        ListIterator<Integer> it = lstInt.listIterator();
        List<Integer> nova = new LinkedList<>();
        int n1,n2,novo;

        n1 = it.next();
        while(it.hasNext()){
            n2 = it.next();
            novo = func.processa(n1,n2);
            nova.add(novo);
            n1 = n2;
        }
        return nova;
    }    

    public static void main(String args[]){
        List<Integer> numeros = new LinkedList<>();

        numeros.add(10);       
        numeros.add(30);       
        numeros.add(20);       
        numeros.add(50);       
        numeros.add(40);       
        numeros.add(60);
        System.out.println(numeros);
        List<Integer> somas = processaListaInt(numeros,(n1,n2)->n1+n2);
        System.out.println(somas);
        List<Integer> medias = processaListaInt(numeros,(n1,n2)->(n1+n2)/2);
        System.out.println(medias);
        List<Integer> maiores = processaListaInt(numeros,(n1,n2)->(n1>n2)?n1:n2);
        System.out.println(maiores);
    }
}