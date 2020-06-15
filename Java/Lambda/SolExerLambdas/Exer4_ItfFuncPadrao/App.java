import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

// Substituiu a interface IntToStr pela interface Function
public class App {
    public static List<String> processaLista(List<Integer> lstValores,Function<Integer,String> funcStr){
        Iterator<Integer> it = lstValores.iterator();
        List<String> nova = new LinkedList<>();
        while(it.hasNext()){
            Integer valor = it.next();
            String strValor = funcStr.apply(valor);
            nova.add(strValor);
        }
        return nova;
    }    

    public static void main(String args[]){
        List<Integer> valores = new LinkedList<>();

        valores.add(1000);
        valores.add(-5000);
        valores.add(11000);
        valores.add(1320);
        valores.add(-9800);

        System.out.println(valores);
        List<String> valoresReais = processaLista(valores, v->"R$ "+ v);
        System.out.println(valoresReais);
        List<String> valoresCD = processaLista(valores,v->Math.abs(v)+((v>=0)?" <C>":" <D>"));
        System.out.println(valoresCD);
    }
}