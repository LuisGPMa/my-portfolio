import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class App {
    public static List<String> processaLista(List<String> lst, ProcStr func){
        List<String> nova = new LinkedList<>();

        for(String s:lst){
            String aux = func.processa(s);
            nova.add(aux);
        }
        return nova;
    }

    public static void main(String args[]){ 
        List<String> nomes = Arrays.asList("Ze "," Outro Ze "," Zezinho "," Ze Maria","Ziraldo ");

        System.out.println(nomes);
        //List<String> semEspacos = processaLista(nomes,s->s.trim());
        nomes = processaLista(nomes,s->s.trim());
        System.out.println(nomes);
        nomes = processaLista(nomes,s->s.toUpperCase());
        System.out.println(nomes);
        nomes = processaLista(nomes,s->(s.indexOf(" ")!=-1)?"["+s+"]":s);
        System.out.println(nomes);      
    }

}