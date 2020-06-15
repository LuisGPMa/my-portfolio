

import java.util.*;

public class Tube { // dag
    // eliminar canos que nao interferem
    private ArrayList<Link> links; // lista ordenada de links, ordenados de acordo com a posicao em que começam nesse tubo
    private ArrayList<Link> linksEstadoInicial; //lista ordenada de links que representa o estado inicial do problema, para ser possivel voltar ao estado inicial
    private int id; //id do tubo na maquina
    private int tubeSize; // o tamanho do tubo, que tambem e o numero maximo de links que podem sair ou
                          // chegar nesse tubo
    private boolean finishedHere; // indica se a bolinha caiu por esse tubo

    public Tube(int size, int id) {
        this.tubeSize = size;
        this.id = id;
        links = new ArrayList<Link>(size);
        linksEstadoInicial = new ArrayList<Link>(size);
        finishedHere = false;
    }
    
    public int getId(){
        return id;
    }

    public void setEstadoInicial(){
        linksEstadoInicial = (ArrayList<Link>)links.clone();
    }

    public void reset(){
        links = (ArrayList<Link>)linksEstadoInicial.clone();
        finishedHere = false;
    }

    public int size() {
        return tubeSize;
    }

    public int tubeQuantity() {
        return links.size();
    }

    public void validate() throws NoMoreSpaceException {
        if (links.size() >= tubeSize) {
            throw (new NoMoreSpaceException("nao ha mais espaco"));
        }
    }

    public boolean setLinks(ArrayList<Link> links) {
        if (links.size() > tubeSize)
            return false;
        this.links = links;
        Collections.sort(links);
        return true;
    }

    public void sortTube(){ //metodo deve ser chamado uma vez que todos os links forem adicionados a esse tubo
        Collections.sort(links);
    }

    public void addLink(Link link) {
        try {
            validate();
        } catch (NoMoreSpaceException e) {
            // TO DO Auto-generated catch block
            e.printStackTrace();
        }
        links.add(link);
    }
    

    public boolean finishedHere(){
        return finishedHere;
    }

    //metodo sera chamado quando um outro tubo direcionar uma bola ate esse tubo
    //ele recebe a posicao em que a bola chega e remove todos os tubos que
    //nao serao utilizados, e retorna a posicao do proximo link que a bola passara
    private int nextLink(int posChegada){
        if(links.isEmpty()){
            finishedHere = true;
            return -1;
        }
        while(links.get(0).getStartingPoint() <= posChegada){            
            links.remove(0);
            if(links.isEmpty()){                
                this.finishedHere = true;
                return -1;
            }
        }
        return 0;
    }

    public void dropIn(){
        if(links.size()>0)
            goThroughLink(0);
    }

    public void goThroughLink(int linkPos){
        Link link = links.remove(linkPos);
        Tube destTube = link.getDestination();
        int nextLinkPos = destTube.nextLink(link.getEndPoint());
        if(destTube.finishedHere())
            return;
        destTube.goThroughLink(nextLinkPos);
    }
}