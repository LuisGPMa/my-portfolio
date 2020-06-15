
public class TorreHanoi{
    
    private StackSimpleLinked<Integer> pilhaA;
    private StackSimpleLinked<Integer> pilhaB;
    private StackSimpleLinked<Integer> pilhaC;
    
    private String[] torreA;
    private String[] torreB;
    private String[] torreC;
    
    private int qtdDiscos;
    private int contaMovimentos;
    
    public TorreHanoi(int qtdDiscos){
        contaMovimentos = 0;
        pilhaA = new StackSimpleLinked<Integer>();
        pilhaB = new StackSimpleLinked<Integer>();
        pilhaC = new StackSimpleLinked<Integer>();
        torreA = new String[qtdDiscos];
        torreB = new String[qtdDiscos];
        torreC = new String[qtdDiscos];
        this.qtdDiscos = qtdDiscos;
        if(qtdDiscos>=3 && qtdDiscos<=7){
            for(int i=qtdDiscos; i>0; i--){
                pilhaA.push(i);
                torreA[Math.abs(i-qtdDiscos)] = Integer.toString(i);
            }
            for(int i=0; i<qtdDiscos; i++){
                torreB[i] = "|";
                torreC[i] = "|";
            }
        }

    }

    public boolean isFinished(){
        return (pilhaB.size() == qtdDiscos || pilhaC.size() == qtdDiscos);
    }

    private boolean mensagemVitoria(){
        if(pilhaB.size() == qtdDiscos || pilhaC.size() == qtdDiscos){
            System.out.println("Parabens, voce conseguiu!!!");
            return true;
        }
        return false;
    }

    public boolean movimento(String pilha, String novaPilha){ //metodo pega o disco de cima da pilha e bota na nova pilha
        if(pilha.equalsIgnoreCase(novaPilha)){
            return false;
        }

        StackSimpleLinked<Integer> aux = null;
        StackSimpleLinked<Integer> auxNovo = null;
        String[] torreAux = null;
        String[] torreAuxNova = null;

        if(pilha.equalsIgnoreCase("A")){                                
            aux = pilhaA;
            torreAux = torreA;
        }
        else if(pilha.equalsIgnoreCase("B")){
            aux = pilhaB;
            torreAux = torreB;
        }
        else if (pilha.equalsIgnoreCase("C")){
            aux = pilhaC;
            torreAux = torreC;
        }
        else
            return false;

        if(novaPilha.equalsIgnoreCase("A")){
            auxNovo = pilhaA;
            torreAuxNova = torreA;
        }
        else if(novaPilha.equalsIgnoreCase("B")){
            auxNovo = pilhaB;
            torreAuxNova = torreB;
        }
        else if(novaPilha.equalsIgnoreCase("C")){
            auxNovo = pilhaC;
            torreAuxNova = torreC;
        }
        else
            return false;

        if(aux.size()==0){
            return false;
        }
        else if(auxNovo.size()>=qtdDiscos){
            return false;
        }
        else if(auxNovo == null || auxNovo.size() == 0 || (aux.peek())<(auxNovo.peek())){
            Integer temp = aux.peek();
            for(int i=0; i<qtdDiscos; i++){
                if(torreAux[i].equals(Integer.toString(temp))){
                    torreAux[i] = "|";
                    break;
                }
            }
            for(int j = 0; j<qtdDiscos; j++){
                if(torreAuxNova[j].equals("|")){
                    torreAuxNova[j] = Integer.toString(temp);
                    break;
                }
            }
            auxNovo.push(aux.pop());
            contaMovimentos++;
            mensagemVitoria();
            return true;
        }
        else
            return false;        
    }

    public boolean isAllowed(String pilha, String novaPilha){

        if(pilha.equalsIgnoreCase(novaPilha)){
            return false;
        }

        StackSimpleLinked<Integer> aux = null;
        StackSimpleLinked<Integer> auxNovo = null;

        if(pilha.equalsIgnoreCase("A"))                             
            aux = pilhaA;
        else if(pilha.equalsIgnoreCase("B"))
            aux = pilhaB;   
        else if (pilha.equalsIgnoreCase("C"))
            aux = pilhaC;
        else
            return false;

        if(novaPilha.equalsIgnoreCase("A"))
            auxNovo = pilhaA;  
        else if(novaPilha.equalsIgnoreCase("B"))
            auxNovo = pilhaB;  
        else if(novaPilha.equalsIgnoreCase("C"))
            auxNovo = pilhaC;  
        else
            return false;

        if(aux.size()==0)
            return false;
        else if(auxNovo.size()>=qtdDiscos)
            return false;
        else if(auxNovo == null || auxNovo.size() == 0 || (aux.peek())<(auxNovo.peek()))
            return true;
        else
            return false;
    }

    public int getMovimentos(){
        return contaMovimentos;
    }

    public String toString(){
        String resp = "";
        for(int i=qtdDiscos-1; i>=0; i--){
            resp += torreA[i] + "   " + torreB[i] + "   " + torreC[i] + "\n";
        }
        if(!(isFinished()))
            resp += "=========\nA   B   C\nMovimentos ate agora: " + contaMovimentos;
        else
            resp += "=========\nA   B   C\nTotal de movimentos: " + contaMovimentos;
        return resp;
    }
}
