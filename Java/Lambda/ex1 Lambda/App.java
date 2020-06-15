import java.util.List;
import java.util.LinkedList;
public class App{
    public App(){}
    
    public static List<String> novaLista(List<String> lst, ProcessadorStr ps){
        List<String> nova = new LinkedList<>();

        for(String s:lst){
            String aux = ps.processa(s);
            nova.add(aux);
        }
        return nova;
    }

    public static void main(String[] args){
        List<String> nomes = new LinkedList<String>();
        nomes.add("Ze");
        nomes.add("douglas");
        nomes.add("zefa   ");
        nomes.add("dilma");
        nomes.add("lula");
        nomes.add("");
        System.out.println(nomes);
        List<String> semEspacos = novaLista(nomes,s->s.trim());
        System.out.println(semEspacos);
        List<String> tudoMaiusculo = novaLista(semEspacos,s->s.toUpperCase());
        System.out.println(tudoMaiusculo);
        List<String> maiMin = novaLista(nomes,s->(s.indexOf(" ")!=-1)?s.toLowerCase():s); //if ternario, pode usar o if normal
        System.out.println(maiMin);        
    }
}