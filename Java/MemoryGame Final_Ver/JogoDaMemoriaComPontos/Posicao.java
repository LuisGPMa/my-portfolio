public class Posicao{
    private int linha;
    private int coluna;

    public Posicao(int linha, int coluna){
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha(){
        return linha;
    }

    public int getColuna(){
        return coluna;
    }

    public boolean equals(Posicao outra){
        return (linha==outra.getLinha() && coluna==outra.getColuna());
    }
}