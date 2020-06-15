import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Montagem_Desmontagem{

    private static HashMap<String, String> opcodesTipoI;
    private static HashMap<String, String> opcodesTipoR;
    private static HashMap<String, String> funcs;
    private static HashMap<String, String> opcodesTipoJ;
    private static HashMap<String, String> registradores;
    private static Set<String> tipoR;
    private static Set<String> tipoI;
    private static Set<String> tipoJ;


    public static void main(String[] args){
        buildHashMaps();
        int opcao = montarOuDesmontar();
        String arquivo = nomeDoArquivo();
        String entrada = "";
        try{
            entrada = leitor(arquivo);
        }catch(Exception e){
            e.printStackTrace();
        }
        if(opcao==0)
            montador(entrada);
        else
            desmontador(entrada);
        
    }

    public static int montarOuDesmontar(){ //pergunta ao usuario se ele quer montar ou desmontar codigo
        Scanner userInput = new Scanner(System.in);
        System.out.println(">-Digite 0 para montar codigo, e 1 para desmontar: ");       
        return userInput.nextInt();
    }

    public static String nomeDoArquivo(){ //pergunta ao usuario e recebe o nome do arquivo
        Scanner userInput = new Scanner(System.in);
        System.out.println(">-Diga o nome do arquivo que contem o codigo a ser montado/desmontado");
        return userInput.nextLine();        
    }

    public static String leitor(String arquivo) throws IOException{ //metodo usado para ler o arquivo
        return new String(Files.readAllBytes(Paths.get(arquivo)), Charset.forName("ASCII"));
    }

    //Método que cuida da logica necessária para agrupar corretamente as linhas montadas e escrever
    //elas no arquivo "assembly_output.txt". Ele tambem é responsável por montar as instruções j e jr.
    public static void montador(String programa){
        Scanner programScanner = new Scanner(programa);
        String linha = "";
        String instruc;
        int linhaAtual = 0;
        String[] progMontado;
        ArrayList<String> labels = new ArrayList<String>();
        Scanner lineCounter = new Scanner(programa);
        Scanner labelDetector = new Scanner(programa);
        String line;
        while(labelDetector.hasNextLine()){
            line = labelDetector.nextLine();
            if(line.contains(":")){
                labels.add(line.substring(0, line.indexOf(":")+1) + "crrntline*" + linhaAtual);
            }
            linhaAtual++;
        }
        int numLinhas = 0;
        while(lineCounter.hasNextLine()){
            lineCounter.nextLine();
            numLinhas++;
        }
        progMontado = new String[numLinhas];
        linhaAtual = 0;
        while(programScanner.hasNextLine()){
            linha = programScanner.nextLine();
            linha = tiraEspacos(linha);
            instruc = "";
            if(linha.equals(".globlmain") || linha.equals(".text") || linha.equals("main:") || linha.isEmpty()){

            }else{
                if(linha.contains(":")){
                    if(!(linha.contains("$")))
                        instruc = "j";
                    else
                        instruc = linha.substring(linha.indexOf(":")+1, linha.indexOf("$"));
                }else if(!linha.contains("$") && (!(linha.contains(".text") || linha.contains(".globl")))){
                    instruc = "j";
                }else if(!(linha.contains(".text") || linha.contains(".globl"))){
                    instruc = linha.substring(0, linha.indexOf("$"));
                }
                if(tipoR.contains(instruc)){
                    progMontado[linhaAtual] += montaLinhaTipoR(linha);
                }else if(tipoI.contains(instruc)){
                    if(instruc.equals("beq") || instruc.equals("bne")){
                        String linhaMontada = montaLinhaTipoI(linha);
                        String trgtLabel = linhaMontada.substring(linhaMontada.indexOf(":")+1);
                        for(String s: labels){
                            if(s.startsWith(trgtLabel)){
                                int linhaComLabel = Integer.valueOf(s.substring(s.indexOf("*")+1));
                                int offset = linhaComLabel - linhaAtual;
                                String aux1 = linhaMontada.substring(0, linhaMontada.indexOf("t"));
                                progMontado[linhaAtual] += binToHex(aux1, 4) + decToHex(offset, 4);
                            }
                        }
                    }else{
                        progMontado[linhaAtual] += montaLinhaTipoI(linha);
                    }
                }else if(tipoJ.contains(instruc)){
                    String targetLabel = linha.substring(linha.indexOf("j")+1);
                    String binTarget = "";
                    for(String s: labels){
                        if(s.startsWith(targetLabel)){
                            if(targetLabel.equals("main")){
                                int labelIndex = Integer.valueOf(s.substring(s.indexOf("*")+1));
                                int instrucAmnt = labelIndex-2;
                                int targetAdress = instrucAmnt*4 + Integer.valueOf(hexToDecimal("00400000"));
                                binTarget = decimalToBin(String.valueOf(targetAdress), 32);
                                binTarget = binTarget.substring(4, 30);
                            }else{
                                int labelIndex = Integer.valueOf(s.substring(s.indexOf("*")+1));
                                int instrucAmnt = labelIndex-3;
                                int targetAdress = instrucAmnt*4 + Integer.valueOf(hexToDecimal("00400000"));                            
                                binTarget = decimalToBin(String.valueOf(targetAdress), 32);
                                binTarget = binTarget.substring(4, 30);
                                break;
                            }
                        }
                    }
                    progMontado[linhaAtual] += binToHex("000010" + binTarget, 8);
                }else if(instruc.equals("jr")){
                    String rs = linha.substring(linha.indexOf("$"));
                    rs = registradores.get(rs);
                    progMontado[linhaAtual] = binToHex(("000000" + rs + "0000000000000000" + "01000"), 8);
                }
            }
            linhaAtual++;
        }        
        programScanner.close();
        File file = new File("assembly_output.txt");
        FileWriter fw = null;
        try{
            file.createNewFile();
            fw = new FileWriter(file);     
        }catch(Exception e){
            e.printStackTrace();
        }
        for(String s: progMontado){
            if(s != null && !(s.isEmpty())){
                try{
                    fw.write(s.replaceAll("null","") + "\n");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        try{
            fw.flush();
            fw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(">-Programa montado e gravado em \"assembly_output.txt\"");
    }

    //metodo que cuida da logica necessária para agrupar corretamente as linhas desmontadas e escrever
    //elas no arquivo "disassembly_output.txt"
    public static void desmontador(String programa){
        Scanner codeScanner = new Scanner(programa);
        String hexCode = "";
        String binCode = "";
        String opcode = "";
        int currentLabel = 1;
        Scanner counter = new Scanner(programa);
        Scanner labelIdentifier = new Scanner(programa);        
        int lineCounter = 3;
        while(counter.hasNextLine()){
            counter.nextLine();
            lineCounter++;
        }
        String[] programaDesmontado = new String[lineCounter+3];
        int progIndex = 3;

        programaDesmontado[0] = ".text";
        programaDesmontado[1] = ".globl main";
        programaDesmontado[2] = "main:";
        String linha = "";
        while(codeScanner.hasNextLine()){
            linha = codeScanner.nextLine();
            if(!linha.isEmpty()){
                hexCode = formataNumHex(linha);
                binCode = hexToBin(hexCode, 32);
                opcode = binCode.substring(0, 6);
                if(tipoI.contains(opcode)){
                    String linhaI = desmontaLinhaTipoI(hexCode);
                    if(linhaI.startsWith("beq") || linhaI.startsWith("bne")){
                        int offset = new Scanner(linhaI.substring(linhaI.indexOf("*")+1)).nextInt();
                        String branchStatement = linhaI.substring(0, linhaI.indexOf("*"));
                        programaDesmontado[progIndex] += branchStatement;
                        //progIndex++;
                        if(programaDesmontado[progIndex+offset].equals("main:")){
                            programaDesmontado[progIndex] += ",main";
                        }else{
                            programaDesmontado[progIndex] += ",label_" + currentLabel;               
                            programaDesmontado[progIndex+offset+1] = "label_" + currentLabel + ": " + programaDesmontado[progIndex+offset+1];
                            currentLabel++;
                        }
                        progIndex++;
                    }else{
                        programaDesmontado[progIndex] += desmontaLinhaTipoI(hexCode);
                        progIndex++;
                    }                            
                }else if(opcode.equals("000000") && tipoR.contains(binCode.substring(26))){
                    programaDesmontado[progIndex] += desmontaLinhaTipoR(hexCode);                
                    progIndex++;
                }else if(opcode.equals("000010")){ //j
                    String l = desmontaLinhaTipoJ(hexCode);
                    String target = l.substring(l.indexOf("@")+1);
                    int inicioAoLabel = (Integer.valueOf(hexToDecimal(target)) - Integer.valueOf(hexToDecimal("00400000")))/4;
                    String labelAtual = "";
                    inicioAoLabel+=3;
                    if(programaDesmontado[inicioAoLabel-1].equals("main:")){
                        labelAtual = "main";
                    }else if(!programaDesmontado[inicioAoLabel].contains(":")){
                        labelAtual = "label_" + currentLabel + ": ";
                        programaDesmontado[inicioAoLabel] = labelAtual + programaDesmontado[inicioAoLabel];
                        currentLabel++;
                    }
                    programaDesmontado[progIndex] += "j " + labelAtual.replaceAll(":", "");
                    progIndex++;
                }else if(opcode.equals("000000") && binCode.substring(27).equals("01000")){ //jr
                    String rs = "$" + binToDecimal(binCode.substring(6, 11));
                    programaDesmontado[progIndex] = "jr " + rs;
                    progIndex++;
                }
            }
        }
        File file = new File("disassembly_output.txt");
        FileWriter fw = null;
        try{
            file.createNewFile();
            fw = new FileWriter(file);     
        }catch(Exception e){
            e.printStackTrace();
        }
        for(String s: programaDesmontado){
            if(s != null)
                if(!s.isEmpty()){
                    try{
                        fw.write(s.replaceAll("null","") + "\n");
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
        }
        try{
            fw.flush();
            fw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(">-Programa desmontado e gravado em \"disassembly_output.txt\"");
    }

    //metodo que recebe uma linha montada e previamente detectada como sendo do
    //tipo R. Ele a desmonta e retorna o codigo como na coluna "Basic" do MARS
    public static String desmontaLinhaTipoR(String hexCode){
        hexCode = formataNumHex(hexCode);
        String opcode, rs, rt, rd, shamt, func;
        String binCode = hexToBin(hexCode, 32);
        String linhaDesmontada = "";
        opcode = tiraEspacos(binCode.substring(0, 6));
        rs = tiraEspacos(binCode.substring(6, 11));
        rt = tiraEspacos(binCode.substring(11, 16));
        rd = tiraEspacos(binCode.substring(16, 21));
        shamt = tiraEspacos(binCode.substring(21, 26));
        func = tiraEspacos(binCode.substring(26));
        if(Integer.parseInt(shamt)==0){
            linhaDesmontada = funcs.get(func);
            linhaDesmontada = linhaDesmontada + " $" + binToDecimal(rd);
            linhaDesmontada = linhaDesmontada + ",$" + binToDecimal(rs);
            linhaDesmontada = linhaDesmontada + ",$" + binToDecimal(rt);
        }
        else{
            linhaDesmontada = funcs.get(func);
            linhaDesmontada = linhaDesmontada + " $" + binToDecimal(rd);
            linhaDesmontada = linhaDesmontada + ",$" + binToDecimal(rt);
            linhaDesmontada = linhaDesmontada + "," + binToDecimal(shamt);
        }
        return linhaDesmontada;
    }

    //metodo que recebe uma linha montada e previamente detectada como sendo do
    //tipo I. Ele a desmonta e retorna o codigo como na coluna "Basic" do MARS
    public static String desmontaLinhaTipoI(String hexCode){
        hexCode = formataNumHex(hexCode);
        String opcode, rs, rt, immediate;
        String binCode = hexToBin(hexCode, 32);
        String linhaDesmontada = "";
        opcode = binCode.substring(0, 6);
        rs = binCode.substring(6, 11);
        rt = binCode.substring(11, 16);
        immediate = binCode.substring(16);
        if(opcode.equals("001001") || opcode.equals("001101") || opcode.equals("001100")){ //addiu, ori, andi
            linhaDesmontada = opcodesTipoI.get(opcode);
            linhaDesmontada = linhaDesmontada + " $" + binToDecimal(rt);
            linhaDesmontada = linhaDesmontada + ",$" + binToDecimal(rs);
            linhaDesmontada = linhaDesmontada + "," + binToDecimal(immediate);
        }else if (opcode.equals("001111")){ //lui
            linhaDesmontada = opcodesTipoI.get(opcode);
            linhaDesmontada = linhaDesmontada + " $" + binToDecimal(rt);
            linhaDesmontada = linhaDesmontada + "," + "0x" + binToHex(immediate, 8);
        }else if(opcode.equals("000100") || opcode.equals("000101")){ //beq, bne
            linhaDesmontada = opcodesTipoI.get(opcode);
            linhaDesmontada = linhaDesmontada + " $" + binToDecimal(rs);
            linhaDesmontada = linhaDesmontada + ",$" + binToDecimal(rt);
            linhaDesmontada = linhaDesmontada + "*" + binToDecimal(immediate);
        }else if(opcode.equals("100011") || opcode.equals("101011")){ //lw, sw
            linhaDesmontada = opcodesTipoI.get(opcode);
            linhaDesmontada = linhaDesmontada + " $" + binToDecimal(rt);
            linhaDesmontada  = linhaDesmontada + "," + binToDecimal(immediate);
            linhaDesmontada = linhaDesmontada + "($" + binToDecimal(rs) + ")";
        }
        return linhaDesmontada;
    }

    //metodo que recebe uma linha montada e previamente detectada como sendo do
    //tipo J. Ele a desmonta e retorna o codigo como na coluna "Basic" do MARS
    public static String desmontaLinhaTipoJ(String hexCode){
        hexCode = formataNumHex(hexCode);  
        String linhaDesmontada = "";
        String opcode, target;
        String binCode = hexToBin(hexCode, 32);
        opcode = binCode.substring(0, 6);
        target = "0000" + binCode.substring(7) + "00";
        target = binToHex(target, 8);
        linhaDesmontada = linhaDesmontada + opcodesTipoJ.get(opcode);
        linhaDesmontada = linhaDesmontada + "@" + target;
        return linhaDesmontada;
    }

    //metodo que recebe uma linha desmontada e previamente detectada como sendo do
    //tipo R. Ele a monta e retorna o codigo como na coluna "Code" do MARS
    public static String montaLinhaTipoR(String linha){
        String linhaMontada = "";
        linha = tiraEspacos(linha);
        String instruc = "";
        String binCode = "";
        if(linha.contains(":")){
            instruc = linha.substring(linha.indexOf(":")+1, linha.indexOf("$"));
        }else{
            instruc = linha.substring(0, linha.indexOf("$"));
        }
        String rs, rt, rd, shamt, func;
        rs="";
        rt="";
        rd="";
        shamt="";
        func="";
        String aux;        
        if(instruc.equals("sll") || instruc.equals("srl")){
            rs = "00000";
            aux = linha.substring(linha.indexOf("$"), linha.indexOf(","));
            rd = registradores.get(linha.substring(linha.indexOf("$"), linha.indexOf(",")));
            aux = linha.substring((linha.indexOf(",")+1));
            rt = registradores.get(aux.substring(aux.indexOf("$"), aux.indexOf(",")));
            shamt = aux.substring(aux.indexOf(",")+1);
            shamt = formataNumHex(shamt);
            shamt = hexToBin(shamt, 5);
            func = funcs.get(instruc);
        }else{
            rd = registradores.get(linha.substring(linha.indexOf("$"), linha.indexOf(",")));
            aux = linha.substring((linha.indexOf(",")+1));
            rs = registradores.get(aux.substring(aux.indexOf("$"), aux.indexOf(",")));
            rt = registradores.get(aux.substring((aux.indexOf(",")+1)));
            shamt = "00000";
            func = funcs.get(instruc);
        }
        binCode = "000000" + rs + rt + rd + shamt + func;
        linhaMontada = binToHex(binCode, 8);
        return linhaMontada;
    }

    //metodo que recebe uma linha desmontada e previamente detectada como sendo do
    //tipo I. Ele a monta e retorna o codigo como na coluna "Code" do MARS
    public static String montaLinhaTipoI(String linha){
        String linhaMontada = "";
        linha = tiraEspacos(linha);
        String instruc = "";
        String binCode = "";
        if(linha.contains(":")){
            instruc = linha.substring(linha.indexOf(":")+1, linha.indexOf("$"));
        }else{
            instruc = linha.substring(0, linha.indexOf("$"));
        }
        String opcode, rs, rt, immediate;
        opcode = opcodesTipoI.get(instruc);
        rs = "";
        rt = "";
        immediate = "";
        String aux;
        if(instruc.equals("addiu") || instruc.equals("ori") || instruc.equals("andi")){
            rt = registradores.get(linha.substring(linha.indexOf("$"), linha.indexOf(",")));
            aux = linha.substring((linha.indexOf(",")+1));
            rs = registradores.get(aux.substring(aux.indexOf("$"), aux.indexOf(",")));
            immediate = aux.substring(aux.indexOf(",")+1);
            immediate = formataNumHex(immediate);
            immediate = hexToBin(immediate, 16);
            return binToHex(opcode + rs + rt + immediate, 8);
        }else if(instruc.equals("lui")){
            rt = registradores.get(linha.substring(linha.indexOf("$"), linha.indexOf(",")));
            rs = "00000";
            immediate = linha.substring(linha.indexOf(",")+1);
        }else if(instruc.equals("lw") || instruc.equals("sw")){
            rt = registradores.get(linha.substring(linha.indexOf("$"), linha.indexOf(",")));
            immediate = linha.substring((linha.indexOf(",")+1), linha.indexOf("("));
            immediate = formataNumHex(immediate);
            immediate = hexToBin(immediate, 16);
            aux = linha.substring(linha.indexOf("("));
            rs = aux.substring(aux.indexOf("$"), aux.indexOf(")"));
            rs = registradores.get(rs);
            return binToHex(opcode + rs + rt + immediate, 8);
        }else{
            rs = registradores.get(linha.substring(linha.indexOf("$"), linha.indexOf(",")));
            aux = linha.substring((linha.indexOf(",")+1));
            rt = aux.substring(aux.indexOf("$"), aux.indexOf(","));
            rt = registradores.get(rt);
            immediate = "targetLabel:" + aux.substring(aux.indexOf(",")+1);
            
        }
        if(instruc.equals("beq") || instruc.equals("bne")){
            linhaMontada = opcode + rs + rt + immediate;
            return linhaMontada;
        }
        immediate = formataNumHex(immediate);
        immediate = hexToBin(immediate, 16);
        binCode = opcode + rs + rt + immediate;
        linhaMontada = binToHex(binCode, 8);
        return linhaMontada;
    }

    //bota as informacoes relevantes sobre os registradores, opcodes e funcs em seus respectivos hashmaps
    public static void buildHashMaps(){
        opcodesTipoI = new HashMap<String, String>();
        opcodesTipoR = new HashMap<String, String>();
        opcodesTipoJ = new HashMap<String, String>();
        funcs = new HashMap<String, String>();
        registradores = new HashMap<String, String>();
        tipoR = new TreeSet<String>();
        tipoI = new TreeSet<String>();
        tipoJ = new TreeSet<String>();

        opcodesTipoI.put("001001", "addiu");
        opcodesTipoI.put("001111", "lui");
        opcodesTipoI.put("001101", "ori");
        opcodesTipoI.put("001100", "andi");
        opcodesTipoI.put("000100", "beq");
        opcodesTipoI.put("000101", "bne");
        opcodesTipoI.put("100011", "lw");
        opcodesTipoI.put("101011", "sw");
        opcodesTipoI.put("addiu", "001001");
        opcodesTipoI.put("lui", "001111");
        opcodesTipoI.put("ori", "001101");
        opcodesTipoI.put("andi", "001100");
        opcodesTipoI.put("beq", "000100");
        opcodesTipoI.put("bne", "000101");
        opcodesTipoI.put("lw", "100011");
        opcodesTipoI.put("sw", "101011");

        opcodesTipoR.put("000000", "sll");
        opcodesTipoR.put("000000", "srl");
        opcodesTipoR.put("000000", "addu");
        opcodesTipoR.put("000000", "xor");
        opcodesTipoR.put("000000", "and");
        opcodesTipoR.put("000000", "slt");
        funcs.put("000000", "sll");
        funcs.put("000010", "srl");
        funcs.put("100001", "addu");
        funcs.put("100110", "xor");
        funcs.put("100100", "and");
        funcs.put("101010", "slt");
        
        funcs.put("sll", "000000");
        funcs.put("srl", "000010");
        funcs.put("addu", "100001");
        funcs.put("xor", "100110");
        funcs.put("and", "100100");
        funcs.put("slt", "101010");
        
        opcodesTipoJ.put("000010", "j");
        opcodesTipoJ.put("j", "000010");

        tipoI.add("addiu");
        tipoI.add("lui");
        tipoI.add("ori");
        tipoI.add("andi");
        tipoI.add("beq");
        tipoI.add("bne");
        tipoI.add("lw");
        tipoI.add("sw");
        tipoI.add("001001");
        tipoI.add("001111");
        tipoI.add("001101");
        tipoI.add("001100");
        tipoI.add("000100");
        tipoI.add("000101");
        tipoI.add("100011");
        tipoI.add("101011");

        tipoR.add("sll");
        tipoR.add("srl");
        tipoR.add("addu");
        tipoR.add("xor");
        tipoR.add("and");
        tipoR.add("slt");
        tipoR.add("000000");
        tipoR.add("010000");
        tipoR.add("100001");
        tipoR.add("100110");
        tipoR.add("100100");
        tipoR.add("101010");

        tipoJ.add("000010");
        tipoJ.add("j");

        registradores.put("$zero", "00000");
        registradores.put("$at", "00001");
        registradores.put("$v0", "00010");
        registradores.put("$v1", "00011");
        registradores.put("$a0", "00100");
        registradores.put("$a1", "00101");
        registradores.put("$a2", "00110");
        registradores.put("$a3", "00111");
        registradores.put("$t0", "01000");
        registradores.put("$t1", "01001");
        registradores.put("$t2", "01010");
        registradores.put("$t3", "01011");
        registradores.put("$t4", "01100");
        registradores.put("$t5", "01101");
        registradores.put("$t6", "01110");
        registradores.put("$t7", "01111");
        registradores.put("$s0", "10000");
        registradores.put("$s1", "10001");
        registradores.put("$s2", "10010");
        registradores.put("$s3", "10011");
        registradores.put("$s4", "10100");
        registradores.put("$s5", "10101");
        registradores.put("$s6", "10110");
        registradores.put("$s7", "10111");
        registradores.put("$t8", "11000");
        registradores.put("$t9", "11001");
        registradores.put("$k0", "11010");
        registradores.put("$k1", "11011");
        registradores.put("$gp", "11100");
        registradores.put("$sp", "11101");
        registradores.put("$fp", "11110");
        registradores.put("$ra", "11111");
        registradores.put("$0", "00000");
        registradores.put("$1", "00001");
        registradores.put("$2", "00010");
        registradores.put("$3", "00011");
        registradores.put("$4", "00100");
        registradores.put("$5", "00101");
        registradores.put("$6", "00110");
        registradores.put("$7", "00111");
        registradores.put("$8", "01000");
        registradores.put("$9", "01001");
        registradores.put("$10", "01010");
        registradores.put("$11", "01011");
        registradores.put("$12", "01100");
        registradores.put("$13", "01101");
        registradores.put("$14", "01110");
        registradores.put("$15", "01111");
        registradores.put("$16", "10000");
        registradores.put("$17", "10001");
        registradores.put("$18", "10010");
        registradores.put("$19", "10011");
        registradores.put("$20", "10100");
        registradores.put("$21", "10101");
        registradores.put("$22", "10110");
        registradores.put("$23", "10111");
        registradores.put("$24", "11000");
        registradores.put("$25", "11001");
        registradores.put("$26", "11010");
        registradores.put("$27", "11011");
        registradores.put("$28", "11100");
        registradores.put("$29", "11101");
        registradores.put("$30", "11110");
        registradores.put("$31", "11111");


    }

    /*
    Tipo R: sll, srl, addu, xor, and, slt
    Tipo I: lui, addiu, beq, bne, ori, andi, lw, sw
    Tipo J: j 
    */

    //Metodos para conversão de bases entre si
    public static String binToDecimal(String bin){
        String resp = new BigInteger(bin, 2).toString(10);
        if(Integer.valueOf(resp)>32767){
            resp = String.valueOf((Integer.valueOf(resp)-65536));
        }
        return resp;
    }

    public static String decimalToBin(String dec, int numBits){
        String resp = new BigInteger(dec, 10).toString(2);
        int binLength = resp.length();
        if(binLength!=numBits){
            int zerosAFrente = numBits - binLength;
            String zeros = "";
            for(int i=0; i<zerosAFrente; i++){
                zeros = zeros + "0";
            }
            resp = zeros + resp;
        }
        return resp;
    }

    public static String hexToDecimal(String hex){
        return new BigInteger(hex, 16).toString(10);
    }

    public static String binToHex(String bin, int numWords){
        String hex = new BigInteger(bin, 2).toString(16);
        int length = hex.length();
        if(length!=numWords){
            int zerosAFrente = numWords - length;
            String zeros = "";
            for(int i=0; i<zerosAFrente; i++){
                zeros = zeros + "0";
            }
            hex = zeros + hex;
        }
        return hex;
    }

    public static String decToHex(int dec, int numWords){
        String resp = Integer.toHexString(dec);
        if(resp.length()>numWords){
            while(resp.length()>numWords){
                resp = resp.substring(1);
            }
        }
        return resp;
    }

    public static String hexToBin(String s, int numBits){ //converte string em hexa para binario, adicionando zeros a frente se necessario
        String bin = new BigInteger(s, 16).toString(2);   //para alcancar o numero de bits desejado
        int binLength = bin.length();
        if(binLength!=numBits){
            int zerosAFrente = numBits - binLength;
            String zeros = "";
            for(int i=0; i<zerosAFrente; i++){
                zeros = zeros + "0";
            }
            bin = zeros + bin;
        }
        return bin;
    }

    //metodos de formatacao de entrada
    public static String formataNumHex(String hexCode){ //remove espaços e o "0x" na frente dos numeros em hexa
        hexCode = tiraEspacos(hexCode);
        if(hexCode.startsWith("0x")){
            hexCode = hexCode.substring(2);
        }
        return hexCode;
    }

    public static String tiraEspacos(String programa){
        return programa.replaceAll("\\s","");
    }    

}