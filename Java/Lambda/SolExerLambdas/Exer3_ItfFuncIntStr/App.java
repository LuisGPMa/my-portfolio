import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class App {
    public static List<String> processaLista(List<Integer> lstValores,IntToStr funcStr){
        List<String> nova = new LinkedList<>();

        for(Integer v:lstValores){
            String aux = funcStr.processa(v);
            nova.add(aux);
        }
        return nova;
    }    

    public static void main(String args[]){
        /*
        List<Integer> valores = new LinkedList<>();

        valores.add(1000);
        valores.add(-5000);
        valores.add(11000);
        valores.add(1320);
        valores.add(-9800);
        */
        List<Integer> valores = Arrays.asList(1000,-5000,11000,1320,-9800);
        
        System.out.println(valores);
        List<String> valoresReais = processaLista(valores,v->"R$ "+v);
        System.out.println(valoresReais);
        List<String> valoresCD = processaLista(valores,v->Math.abs(v)+((v>=0)?" <C>":" <D>"));
        System.out.println(valoresCD);
    }
}