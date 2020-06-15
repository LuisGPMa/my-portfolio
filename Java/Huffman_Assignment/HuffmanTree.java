import java.util.*;
import java.util.Map.Entry;

import java.io.*;
import java.nio.file.Paths;
import java.nio.charset.*;
import java.nio.file.Files;

public class HuffmanTree{

    private Nodo root;
    private PriorityQueue<Nodo> queue;
    private HashMap<Character, Integer> charFrequency;
    private HashMap<Character, String> charHuffmanCode;
    
    public HuffmanTree(){
        queue = new PriorityQueue<Nodo>();
        charFrequency = new HashMap<Character, Integer>();
        charHuffmanCode = new HashMap<Character, String>();        
        pqInicialize();
    }

    private void pqInicialize(){
        try{
            charFrequency = contaArquivo("king_james.txt");
        }
        catch(IOException exc){
            System.out.println("erro de leitura de arquivo");
        }
        finally{
            Nodo novo;
            for(Entry<Character,Integer> e: charFrequency.entrySet()){
                novo = new Nodo(e.getKey().toString(), e.getValue());
                queue.add(novo);
            }
            
        }
        treeBuilder();
    }

    public void treeBuilder(){
        Nodo n1, n2, pai;
        while(queue.size()>1){
            n1 = queue.poll();
            n2 = queue.poll();
            pai = new Nodo("" + n1.getElement() + n2.getElement(), (int)(n1.getFreq() + n2.getFreq()));
            pai.setLeft(n1);
            pai.setRight(n2);
            queue.add(pai);
        }
        if(queue.size()==1){
            root = queue.peek();
        }
        codifica();
    }

    public void codifica(){ //esq += 0, dir += 1
        codifica(root, "");
        FileWriter fw = null;
        File arq = null;
        try{
            arq = new File("codeList.txt");
            fw = new FileWriter(arq);
            fw.flush();
            for(Entry<Character, String> e: charHuffmanCode.entrySet()){
                fw.write(e.getKey().toString() + " -> " + e.getValue() + "\n");
            }
        }
        catch(IOException e){
            System.out.println("erro de leitura de arquivo");
        }
        finally{
            try{
                fw.close();
            }catch(IOException e){

            }
        }     
        codificaTexto();
    }

    private void codifica(Nodo aux, String codigo){
        if(aux.getLeft() != null)
            codifica(aux.getLeft(), codigo + "0");
        if(aux.getRight() != null)
            codifica(aux.getRight(), codigo + "1");
        if(aux.isLeaf())
            charHuffmanCode.put(aux.getElement().charAt(0), codigo);
    }

    public void codificaTexto(){
        FileWriter fw = null;
        File arq = null;
        try{
            arq = new File("bibliaBinaria.txt");
            fw = new FileWriter(arq);
            fw.flush();
            String biblia = leitor("king_james.txt");
            for(char c: biblia.toCharArray()){
                fw.write(charHuffmanCode.get(c));
            }
        }catch(IOException e){
            System.out.println("Erro de leitura de arquivo");
        }
        finally{
            try{
                fw.close();
            }catch(IOException e){

            }
        }
    }

    public void imprimeCodigos(){
        
        for(Entry<Character, String> e: charHuffmanCode.entrySet()){
            System.out.println(e.getKey() + "  " + e.getValue());
        }
    }

    public HashMap<Character, String> getCodigos(){        
        return (HashMap<Character, String>)charHuffmanCode.clone();
    }

    public List<Nodo> traversalPre(){ //Visita, Esquerda, Direita
        List<Nodo> resp = new LinkedList<Nodo>();
        Nodo aux;
        if(root!=null)
            aux = root;
        else
            return null;
        traversalPre(aux, resp);
        return resp;
    }

    private void traversalPre(Nodo aux, List<Nodo> resp){
        if(aux!=null){
            resp.add(aux);        
            traversalPre(aux.getLeft(), resp);        
            traversalPre(aux.getRight(), resp);
        }
    }

    public static HashMap<Character, Integer> contaArquivo(String nome) throws IOException{
        HashMap<Character, Integer> resp = new HashMap<Character, Integer>();
        String str = leitor(nome);
        
        for(char c: str.toCharArray()){
            if(!(resp.containsKey(c))){
                resp.put(c, 1);
            }
            else{
                resp.replace(c, resp.get(c) + 1);
            }
        }
        return resp;
    }

    public static String leitor(String arquivo) throws IOException{
        return new String(Files.readAllBytes(Paths.get(arquivo)), Charset.forName("ASCII"));
    }
}

   