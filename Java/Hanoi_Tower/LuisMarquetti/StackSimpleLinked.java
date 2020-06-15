import java.util.EmptyStackException;

public class StackSimpleLinked<E>{

    private ListSimpleLinked<E> lista;

    public StackSimpleLinked(){
        lista = new ListSimpleLinked<>();
    }

    public int size(){
        return lista.size();
    }

    public boolean empty(){
        return (lista.size()==0);
    }

    public E peek(){
        if(!(lista.size()==0)){
            return lista.getFirst();
        }
        else{
            return null;
        }
    }

    public void push(E elem){
        lista.addFirst(elem);
    }

    public E pop(){
        if(!(this.empty())){
            E resp = lista.getFirst();
            lista.removeFirst();
            return resp;
        }
        else{
            throw new EmptyStackException();
        }
    }
}