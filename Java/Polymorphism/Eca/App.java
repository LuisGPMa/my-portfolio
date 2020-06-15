import java.util.ArrayList;

public class App{
    /*
    public static void impAluno(Aluno a){
        System.out.println("Nome: "+a.getNome()+" media: "+a.media()+" aprovado: "+a.aprovado());
    }
    */

    public static void main(String args[]){
        ArrayList<Aluno> alunos = new ArrayList<>();

        Aluno a1 = new AlunoTurmaTeorica("Huguinho",7,5,6,9);
        alunos.add(a1);
        alunos.add(new AlunoTurmaPratica("Zezinho",8,4,10));
        alunos.add(new AlunoTurmaTeorica("Luizinho",9,9,6,9));
        alunos.add(new AlunoTurmaPratica("Huguinho",3,5,6));
 
        /*
        for(int i=0;i<alunos.size();i++){
            System.out.println("Nome: "+alunos.get(i).getNome()+
                               " media: "+alunos.get(i).media()+
                               " aprovado: "+alunos.get(i).aprovado());        

        }
        */
        for(Aluno a:alunos){
            System.out.println("Nome: "+a.getNome()+
                               " classe: "+a.getClass().getName()+
                               " media: "+a.media()+
                               " aprovado: "+a.aprovado());  
            //if (a.getClass().getName().equals("AlunoTurmaTeorica")){
            if (a instanceof AlunoTurmaTeorica){
                AlunoTurmaTeorica aux = (AlunoTurmaTeorica)a;
                System.out.println(aux.getTF());
            }      
        }
    }
}

        /*
        Aluno a1 = new AlunoTurmaTeorica("Huguinho",7,5,6,9);
        //System.out.println(a1.getNome());
        //System.out.println(a1.getTF());

        Aluno a2 = new AlunoTurmaPratica("Zezinho",8,4,10);
        Aluno a3 = new AlunoTurmaTeorica("Luizinho",9,9,6,9);
        Aluno a4 = new AlunoTurmaPratica("Huguinho",3,5,6);

        impAluno(a1);
        impAluno(a2);
        impAluno(a3);
        impAluno(a4);
        */
