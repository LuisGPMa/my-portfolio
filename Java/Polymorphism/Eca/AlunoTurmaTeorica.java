public class AlunoTurmaTeorica extends Aluno{
    private int tf;

    public AlunoTurmaTeorica(String nome,int n1,int n2,int n3,int tf){
        super(nome,n1,n2,n3);
        this.tf = tf;
    }

    public int getTF(){
        return tf;
    }

    @Override
    public double media(){
        return (getN1()+getN2()+getN3()+getTF())/4.0;
    }
}
