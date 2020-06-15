public class Telefone extends Produto{

    private boolean isImportado;

    public Telefone(int codigo, String descricao, double preco, boolean isImportado){
        super(codigo, descricao, preco);
        this.isImportado = isImportado;
    }

    public boolean isImportado(){
        return isImportado;
    }

    @Override
    public double getImposto(){
        if(isImportado)
            return ((super.getImposto())*1.5);
        else
            return super.getImposto();
    } 
}