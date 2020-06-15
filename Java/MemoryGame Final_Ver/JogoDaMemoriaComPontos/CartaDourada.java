import javafx.scene.image.ImageView;

public class CartaDourada extends BotaoCarta { //carta que vale 2 pontos

    public CartaDourada(String nomeFigura){
        super(nomeFigura);
    }

    @Override
    public int calculaPontosPro(Carta outra) {
        return 2;
    }
}