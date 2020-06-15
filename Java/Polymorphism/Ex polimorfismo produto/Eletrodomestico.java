public class Eletrodomestico extends Produto{
    private boolean is220v;

    public Eletrodomestico(int codigo, String descricao, double preco, boolean is220v){
        super(codigo, descricao, preco);
        this.is220v = is220v;
    }

    public boolean is220v(){
        return is220v;
    }
}