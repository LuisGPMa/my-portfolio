import javafx.scene.image.ImageView;

public class CartaAmaldicoada extends BotaoCarta { //carta que remove pontos de quem abrir seu par
    private String nomeFigura;
    private CardState state;
    private Posicao posicao;

    public CartaAmaldicoada(String nomeFigura){
        super(nomeFigura);
    }

    @Override
    public int calculaPontosPro(Carta outra) {
        return -1;
    }
}