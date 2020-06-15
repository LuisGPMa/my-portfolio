import java.util.*;
import java.util.Map.Entry;

import java.io.*;
import java.nio.file.Paths;
import java.nio.charset.*;
import java.nio.file.Files;

public class Decodificador{
    public static void main (String[] args) throws IOException{
        decodifica();
        System.out.println("biblia original e igual a decodificada? " + leArquivo("king_james.txt").equals(leArquivo("bibliaDecodificada.txt")));
    }

    public static void decodifica(){
        HuffmanTree huff = new HuffmanTree();
        HashMap<Character, String> codigos = huff.getCodigos();
        HashMap<String, Character> decoder = new HashMap<String, Character>();
        for(Entry<Character, String> e: codigos.entrySet()){
            decoder.put(e.getValue(), e.getKey());
        }
        File arqOutput = null;
        FileWriter fw = null;
        String code = "";
        try{
            arqOutput = new File("bibliaDecodificada.txt");
            fw = new FileWriter(arqOutput);
            fw.flush();
            char[] vetChar = leArquivo("bibliaBinaria.txt").toCharArray();
            for(char c: vetChar){
                code += c;
                if(decoder.containsKey(code)){
                    fw.write(decoder.get(code));                    
                    code = "";
                }
            }
        }catch(IOException e){
            System.out.println("erro de leitura de arquivo");
        }finally{            
            try{
                fw.close();
            }catch(IOException e){

            }
        }
    }

    public static String leArquivo(String arquivo) throws IOException{
        return new String(Files.readAllBytes(Paths.get(arquivo)), Charset.forName("ASCII"));
    }

}