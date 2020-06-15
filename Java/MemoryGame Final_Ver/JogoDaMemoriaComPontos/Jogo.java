
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class Jogo extends Application {
    public static final int CARD_WIDTH = 90;
    public static final int CARD_HEIGHT = 90;

    private int pontosHumano=0;
    private int  pontosComputador=0;
    private TextField tfPontosHumano;
    private TextField tfPontosComputador;
    private TextField tfSelecionaDificuldade;
    private ControleDeJogadas cJog;
    private static Map<String, Image> imagens;
    private Carta cartaCorrente, ultimaCartaAberta;
    private Carta primCartaComp,segCartaComp;
    private int aux = 0;

    public static void main(String[] args) {
        launch(args);
    }

    public static Image getImage(String name) {
        return imagens.get(name);
    }

    private static void loadImagens() {
        imagens = new HashMap<>();

        // Armazena a imagem das costas das cartas
        Image aux = new Image("file:Imagens\\back.jpg");
        imagens.put("back", aux);

        // Armazena a imagem da carta vazia
        imagens.put("empty", null);

        // Armazena as demais imagens
        for (int i = 1; i <= ControleDeJogadas.NUMPARES; i++) {
            aux = new Image("file:Imagens\\img" + i + ".jpg");
            imagens.put("img" + i, aux);
        }
        aux = new Image("file:Imagens\\img" + 16 + ".jpg");
        imagens.put("img" + 16, aux);
        aux = new Image("file:Imagens\\img" + 17 + ".jpg");
            imagens.put("img" + 17, aux);
    }

    @Override
    public void start(Stage palco){
        // Carrega imagens
        loadImagens();        
        VBox seletorDeCartas = new VBox(10);

        // Cria o controle do jogo 
        cJog = new ControleDeJogadas();
        ultimaCartaAberta = null;
        cartaCorrente = null;  

        //textos da selecao das cartas
        TextField selecioneCartas = new TextField();
        selecioneCartas.setText("Selecione a quantidade de cada tipo especial de carta");
        selecioneCartas.setEditable(false);
        TextField descreveDouradas = new TextField();
        descreveDouradas.setText("Quantidade de cartas douradas. Essas cartas sao especiais e valem 2 pontos.");
        descreveDouradas.setEditable(false);
        TextField descreveAmaldicoadas = new TextField();
        descreveAmaldicoadas.setText("Quantidade de cartas amaldicoadas. Essas cartas sao assustadoras e devem ser evitadas.");
        descreveAmaldicoadas.setEditable(false);

        //criando botoes que definem a quantidade de cartas douradas e amaldicoadas
        HBox botoesQtdDouradas = new HBox(93);
        HBox botoesQtdAmaldicoadas = new HBox(93);
        ArrayList<Button> botoesQtdDourada = new ArrayList<Button>(5);
        ArrayList<Button> botoesQtdAmaldicoada = new ArrayList<Button>(5);
        for(int i = 0; i<5; i++){
            botoesQtdDourada.add(new Button("" + i));
            botoesQtdAmaldicoada.add(new Button("" + i));
        }       

        botoesQtdDourada.get(0).setOnAction(e -> setQtdCartasDouradas(0, palco));
        botoesQtdDourada.get(1).setOnAction(e -> setQtdCartasDouradas(1, palco));
        botoesQtdDourada.get(2).setOnAction(e -> setQtdCartasDouradas(2, palco));
        botoesQtdDourada.get(3).setOnAction(e -> setQtdCartasDouradas(3, palco));
        botoesQtdDourada.get(4).setOnAction(e -> setQtdCartasDouradas(4, palco));        

        botoesQtdAmaldicoada.get(0).setOnAction(e -> setQtdCartasAmaldicoadas(0, palco));
        botoesQtdAmaldicoada.get(1).setOnAction(e -> setQtdCartasAmaldicoadas(1, palco));
        botoesQtdAmaldicoada.get(2).setOnAction(e -> setQtdCartasAmaldicoadas(2, palco));
        botoesQtdAmaldicoada.get(3).setOnAction(e -> setQtdCartasAmaldicoadas(3, palco));
        botoesQtdAmaldicoada.get(4).setOnAction(e -> setQtdCartasAmaldicoadas(4, palco));        

        for(int k=0; k<5; k++){
            botoesQtdDouradas.getChildren().add(botoesQtdDourada.get(k));
            botoesQtdAmaldicoadas.getChildren().add(botoesQtdAmaldicoada.get(k));
        }

        seletorDeCartas.getChildren().add(selecioneCartas);
        seletorDeCartas.getChildren().add(descreveDouradas);
        seletorDeCartas.getChildren().add(botoesQtdDouradas);
        seletorDeCartas.getChildren().add(descreveAmaldicoadas);
        seletorDeCartas.getChildren().add(botoesQtdAmaldicoadas);

        Scene scene = new Scene(seletorDeCartas);
        palco.setScene(scene);
        palco.show();

}

    public void setQtdCartasDouradas(int qtdDouradas, Stage palco){
        aux++;
        cJog.setQtdParesCartasDouradas(qtdDouradas);
        if(aux==2){
            cJog.criaCartas();
            seletorDificuldade(palco);
        }
    }

    public void setQtdCartasAmaldicoadas(int qtdAmaldicoadas, Stage palco){
        aux++;
        cJog.setQtdParesCartasAmaldicoadas(qtdAmaldicoadas);
        if(aux==2){
            cJog.criaCartas();
            seletorDificuldade(palco);
        }
    }

    public void seletorDificuldade(Stage palco){
        //selecao inicial de dificuldade
        VBox cenaSelecao = new VBox(10);
        tfSelecionaDificuldade = new TextField();
        tfSelecionaDificuldade.setText("Selecione a dificuldade: ");
        tfSelecionaDificuldade.setEditable(false);
        

        //botoes de dificuldade
        Button dificil = new Button("Dificil");
        Button facil = new Button("Facil");
        Button medio = new Button("Medio");

        //definindo acoes para os botoes de dificuldade
        dificil.setOnAction(e -> setDificuldadeDificil(palco));
        medio.setOnAction(e -> setDificuldadeMedia(palco));
        facil.setOnAction(e -> setDificuldadeFacil(palco));

        //cenaSelecao.getChildren().add(tfSelecionaDificuldade);
        //cenaSelecao.getChildren().add(facil);
        //adicionando os botoes em uma hbox
        
        HBox linha1DificultySel = new HBox(10);
        linha1DificultySel.getChildren().add(facil);
        linha1DificultySel.getChildren().add(medio);
        linha1DificultySel.getChildren().add(dificil);
        cenaSelecao.getChildren().add(tfSelecionaDificuldade);        
        cenaSelecao.getChildren().add(linha1DificultySel);
        
        // Monta a cena e exibe
        Scene scene = new Scene(cenaSelecao);
        palco.setScene(scene);
        palco.show(); 
    }

    public void telaTabuleiro(Stage palco) {      

        
        // Configura a interface com o usuario
        palco.setTitle("Jogo da memoria");
        GridPane tab = new GridPane();
        tab.setAlignment(Pos.CENTER);
        tab.setHgap(10);
        tab.setVgap(10);
        tab.setPadding(new Insets(15, 15, 15, 15));

        // Cria os botoes das cartas
        for (int lin = 0; lin < ControleDeJogadas.NLIN; lin++) {
            for (int col = 0; col < ControleDeJogadas.NCOL; col++) {
                Carta bt = cJog.getCarta(lin, col);
                bt.setPosicao(new Posicao(lin,col));
                bt.setOnAction(e -> viraCarta(e, palco));
                tab.add(bt, col, lin);
            }
        }

        //linha para exibir os pontos do humano
        HBox lin1 = new HBox(10);
        lin1.getChildren().add(new Label("Pontos humano: "));
        tfPontosHumano = new TextField();
        tfPontosHumano.setText("" + (pontosHumano));        
        tfPontosHumano.setEditable(false);
        lin1.getChildren().add(tfPontosHumano);

        //linha para exibir os pontos do computador
        HBox lin2 = new HBox(10);
        lin2.getChildren().add(new Label("Pontos computador"));
        tfPontosComputador = new TextField();
        tfPontosComputador.setText("" + pontosComputador);
        tfPontosComputador.setEditable(false);
        lin2.getChildren().add(tfPontosComputador);

        //monta a cena como uma caixa vertical
        VBox cena = new VBox();
        cena.getChildren().add(lin1);
        cena.getChildren().add(lin2);
        cena.getChildren().add(tab);

        //muda pro jogo
        Scene scene = new Scene(cena);
        palco.setScene(scene);
        palco.show();
        
    }

    public void setDificuldadeFacil(Stage s){
        cJog.setTipoMemoria(new MemoriaCurta(cJog));
        telaTabuleiro(s);        
    }

    public void setDificuldadeMedia(Stage s){
        cJog.setTipoMemoria(new MemoriaMedia(cJog));
        telaTabuleiro(s);
    }

    public void setDificuldadeDificil(Stage s){
        cJog.setTipoMemoria(new MemoriaBoa(cJog));
        telaTabuleiro(s);
    }
    
    public void viraCarta(ActionEvent e, Stage palco) {
        if (cartaCorrente != null){ // Se esta aguardando análise, retorna
            return;
        }
        // Identifica a carta acionada e reage de acordo
        Carta but = (Carta) e.getSource();
        if (but.getState() == CardState.USADA) {
            return;
        } else if (but.getState() == CardState.ABERTA) {
            return;
        } else {
            // Abre carta
            but.abre();
            but.defineImagem();
            // anota a carta corrente
            if (ultimaCartaAberta == null){
                ultimaCartaAberta = but;
                // informa a primeira carta
                cJog.setCarta(but);
            }else{
                cartaCorrente = but;
                // Programa a analise da jogada em 2 segundo para permitr
                // que o jogador veja as cartas abertas
                Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(1500),
                                 ae -> analisaJogada(palco))
                );
                timeline.play();
            }
        }
    }

    public void analisaJogada(Stage palco) {
        GameState state = cJog.setCarta(cartaCorrente);
        switch (state) {
        case ABRIU_CARTA1:
            ultimaCartaAberta = cartaCorrente;
            cartaCorrente.defineImagem();
            return;
        case NAO_ACHOU_PAR:
            ultimaCartaAberta.defineImagem();
            cartaCorrente.defineImagem();
            break;
        case ACHOU_PAR:
            ultimaCartaAberta.defineImagem();
            cartaCorrente.defineImagem();
            if(cartaCorrente.matches(ultimaCartaAberta) && cartaCorrente.getNomeFigura().equals("img1"))
            pontosHumano--;
        else if(cartaCorrente.matches(ultimaCartaAberta) && cartaCorrente.getNomeFigura().equals("img2"))
            pontosHumano += 2;
        else if(cartaCorrente.matches(ultimaCartaAberta))
            pontosHumano++;
            tfPontosHumano.setText("" + pontosHumano);
            break;
        case FIMDEPARTIDA:
            telaFinal(palco);
        /*
            String str = "Humano: "+pontosHumano+" pontos\n";
            str += "Computador: "+pontosHumano+" pontos";
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Fim de jogo");
            alert.setHeaderText(str);
            alert.setContentText("Desejas jogar de novo?");

            ButtonType botaoSim = new ButtonType("Sim");
            ButtonType botaoNao = new ButtonType("Não");
            ButtonType botaoCancela = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(botaoSim, botaoNao, botaoCancela);
            

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == botaoSim){
                start(new Stage());
            } 
            else if (result.get() == botaoNao) {

            }
            else {

            }
            */
            //Alert msgBox = new Alert(AlertType.INFORMATION);
            //msgBox.setHeaderText("Fim de Jogo");
            //msgBox.setContentText(str);
            //msgBox.show();
            return;
        }
        // Acoes que são iguais depois de se abrir a segunda carta
        ultimaCartaAberta = null;
        cartaCorrente = null;
        // Inicia jogada do computador
        jogadaComputadorParte1(palco);
    }

    public void jogadaComputadorParte1(Stage palco){
        primCartaComp = cJog.primeiraCartaComputador();
        segCartaComp = cJog.segundaCartaComputador();
        if(primCartaComp.matches(segCartaComp) && primCartaComp.getNomeFigura().equals("img1"))
            pontosComputador--;
        else if(primCartaComp.matches(segCartaComp) && primCartaComp.getNomeFigura().equals("img2"))
            pontosComputador += 2;
        else if(primCartaComp.matches(segCartaComp))
            pontosComputador++;
        primCartaComp.defineImagem();
        segCartaComp.defineImagem();
        
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(1000),
                         ae -> jogadaComputadorParte2(palco))
        );
        timeline.play();
    }

    public void botaoSim(Stage palco, Stage telafinal){
        palco.close();
        telafinal.close();
        start(new Stage());
        pontosComputador = 0;
        pontosHumano = 0;
    }

    public void botaoNao(Stage fechado, Stage palcoAnterior){
        fechado.close();
        palcoAnterior.close();
    }

    public void telaFinal(Stage palco){
        String str1 = "Humano: "+pontosHumano+" pontos\n";
        String str2 = "Computador: "+pontosComputador+" pontos";
            
        Stage finalPartidaStage = new Stage();
        VBox finalPartidaVB = new VBox(10);

        TextField tfPtsHumano = new TextField();
        tfPtsHumano.setText(str1);
        tfPtsHumano.setEditable(false);
        TextField tfPtsComputador = new TextField();
        tfPtsComputador.setText(str2);
        tfPtsComputador.setEditable(false);
        TextField tfJogarDenovo = new TextField();
        tfJogarDenovo.setText("Desejas jogar de novo?");
        tfJogarDenovo.setEditable(false);

        HBox botoesSimENao = new HBox(50);
        Button opcaoSim = new Button("Sim");
        Button opcaoNao = new Button("Nao");

        opcaoSim.setOnAction(e -> botaoSim(palco, finalPartidaStage));
        opcaoNao.setOnAction(e -> botaoNao(finalPartidaStage, palco));
        botoesSimENao.getChildren().add(opcaoSim);
        botoesSimENao.getChildren().add(opcaoNao);

        finalPartidaVB.getChildren().add(tfPtsHumano);
        finalPartidaVB.getChildren().add(tfPtsComputador);
        finalPartidaVB.getChildren().add(tfJogarDenovo);
        finalPartidaVB.getChildren().add(botoesSimENao);

        Scene cena = new Scene(finalPartidaVB);
        finalPartidaStage.setScene(cena);
        finalPartidaStage.show();
    }

    public void jogadaComputadorParte2(Stage palco){
        if (cJog.completaJogadaComputador() == GameState.FIMDEPARTIDA){
            telaFinal(palco);
            /*
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Fim de jogo");
            alert.setHeaderText(str);
            alert.setContentText("Desejas jogar de novo?");

            ButtonType botaoSim = new ButtonType("Sim");
            ButtonType botaoNao = new ButtonType("Não");
            ButtonType botaoCancela = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(botaoSim, botaoNao, botaoCancela);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == botaoSim){
                start(new Stage());
            } 
            else if (result.get() == botaoNao) {

            }
            else {

            }
            */
            //Alert msgBox = new Alert(AlertType.INFORMATION);
            //msgBox.setHeaderText("Fim de Jogo");
            //msgBox.setContentText(str);
            //msgBox.show();
            return;
        }
        primCartaComp.defineImagem();
        segCartaComp.defineImagem();
        tfPontosComputador.setText("" + pontosComputador);
    }
}
