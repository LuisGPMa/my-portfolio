public class AlunoTurmaPratica extends Aluno{

    public AlunoTurmaPratica(String nome,int n1,int n2,int n3){
        super(nome,n1,n2,n3);
    }

    @Override
    public double media(){
        return (getN1()+getN2()+getN3())/3.0;
    }
    
    @Override
    public boolean aprovado(){
        return media() >= 5.0;
    }
}