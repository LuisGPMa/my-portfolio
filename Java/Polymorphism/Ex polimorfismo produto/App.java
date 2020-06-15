import java.util.ArrayList;
public class App{
    public static void main(String args[]){
        ArrayList<Produto> produtos = new ArrayList<>();

        String d1 = "aspirador de po 300W";
        produtos.add(new Eletrodomestico(1101, d1, 1000, false));

        String d2 = "geladeira inox";
        produtos.add(new Eletropesado(1201, d2, 1000));

        String d3 = "samsung h4 pro 219 Gb";
        produtos.add(new Telefone(1301, d3, 1000, true));

        for(int i=0; i<produtos.size(); i++){
            System.out.println(dados(produtos.get(i)));
        }
    }

    public static String dados(Produto prod){
        String str = "";
        double valor = prod.getPreco()+prod.getImposto()+prod.getMargemLucro();
        str = "Descricao do produto: " + prod.getDescricao() + ", valor: " + valor; 
        return str;
    }
}