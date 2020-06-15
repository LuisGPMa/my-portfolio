import java.util.*;
import java.io.*;
import java.nio.file.Paths;
import java.nio.charset.*;
import java.nio.file.Files;

public class ContaFrequencias{
    public static void main(String[] args){
        String nome = "king_james.txt";
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        try{            
            map = contaArquivo(nome);
        }catch(IOException exc){
            System.out.println("error: file not found");
        }
        finally{
            map.entrySet().forEach(entry->{
                System.out.println(entry.getKey() + " " + entry.getValue());  
            });
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