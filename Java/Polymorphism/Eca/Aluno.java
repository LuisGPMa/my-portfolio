public abstract class Aluno{
    private String nome;
    private int n1,n2,n3;

    public Aluno(String nome,int n1,int n2,int n3){
        this.nome = nome;
        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
    }

    public abstract double media();

    public boolean aprovado(){
        return media() >= 7.0;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @return the n1
     */
    public int getN1() {
        return n1;
    }

    /**
     * @return the n2
     */
    public int getN2() {
        return n2;
    }

    /**
     * @return the n3
     */
    public int getN3() {
        return n3;
    }

}