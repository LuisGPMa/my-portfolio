 import javax.swing.plaf.synth.SynthToolTipUI;

public class ListSimpleLinked<E> implements ListTAD<E>{
    private Nodo prim,ult;
    private int totalElementos;

private class Nodo {
    private E element;
    private Nodo next;

    public Nodo(E e) {
        element = e;
        next = null;
    }

    public void setElement(E e) {element = e; }
    public void setNext (Nodo n) { next = n; }
    public E getElement() { return element; }
    public Nodo getNext() { return next; }
}
    public ListSimpleLinked(){
        prim = null;
        ult = null;
        totalElementos = 0;
    }

    public void add(E elem){
        if (prim==null){
            prim = ult = new Nodo(elem);
            totalElementos++;
        }
        else{
            Nodo aux = new Nodo(elem);
            ult.setNext(aux);
            ult = aux;
            totalElementos++;
        }
    }

    public void addLast(E elem){
        this.add(elem);
    }

    @Override
    public void add(int index, E elem){
        if(index>=0 && index<=totalElementos){
            Nodo novoNodo = new Nodo(elem);
            if(totalElementos==0){
                prim = ult = novoNodo;
            }
            else if(index==0){               
                novoNodo.setNext(prim);
                prim = novoNodo;                   
            }
            else if(index==totalElementos){
                ult.setNext(novoNodo);
                ult = novoNodo;
            }            
            else{            
                Nodo anterior = getNodo(index-1);
                Nodo proximo = anterior.getNext();            
                novoNodo.setNext(proximo);
                anterior.setNext(novoNodo);
            }
            totalElementos++;
        }
    }

    public int size(){
        return totalElementos;
    }

    public void addFirst(E elem){
        if (prim==null){
            prim = ult = new Nodo(elem);
            totalElementos++;
        }
        else{
            Nodo novoPrim = new Nodo(elem);
            novoPrim.setNext(prim);
            prim = novoPrim;
            totalElementos++;
        }
    }

    public E getFirst(){
        return prim.getElement();
    }

    public E getLast(){
        return ult.getElement();
    }

    public E get(int ind){
        Nodo atual=prim;
        if(ind==0)
            return atual.getElement();
        else{
            for(int i=0; i<ind; i++){
                atual = atual.getNext();
            }
            return atual.getElement();
        }
    }

    public E search(E elem){
        Nodo atual = prim;
        if(totalElementos==0){
            return null;
        }
        for(int i=0; i<totalElementos; i++){
            if(atual.getElement().equals(elem)){
                return atual.getElement();
            }
            atual = atual.getNext();
        }
        return null;
    }

    private Nodo getNodo(int ind){
        Nodo atual=prim;
        if(ind==0)
            return atual;
        else{
            for(int i=0; i<ind; i++){
                atual = atual.getNext();
            }
            return atual;
        }
    }

    public E remove(int ind){
        E auxE = null;
        if (ind>=0 && ind<totalElementos){
            if(totalElementos==0){
                return null;
            }
            else if(totalElementos==1){
                auxE = prim.getElement();
                prim = ult = null;
                totalElementos--;              
                return auxE;
            }
            else if(ind == totalElementos-1){
                Nodo anterior = getNodo(ind-1);            
                Nodo removido = anterior.getNext();
                auxE = removido.getElement();
                anterior.setNext(null);
                ult = anterior; 
                totalElementos--;               
                return auxE;
            }
            else if(ind == 0){
                auxE = prim.getElement();
                prim = prim.getNext();
                totalElementos--;
                return auxE;
            }
            else{
                Nodo anterior = getNodo(ind-1);            
                Nodo removido = anterior.getNext();
                auxE = removido.getElement();
                anterior.setNext(removido.getNext());
                totalElementos--;
                return auxE;               
            }                   
        }
        else{
            return null;
        }        
    }

    public void set(int index, E elem){
        Nodo mudado = getNodo(index);
        mudado.setElement(elem);
    }

    public E removeFirst(){
        if(prim == null)
            return null;
            
        else if(totalElementos==1){
            E auxE = prim.getElement();
            prim = ult = null;
            totalElementos--;
            return auxE;           
        }
        else{
            E auxE = prim.getElement();
            prim = prim.getNext();
            totalElementos--;
            return auxE;
        }
    }

    public void clean(){
        prim = ult = null;
        totalElementos = 0;
    }

    public E remove(E elem){
        if(totalElementos==0){
            return null;
        }
        Nodo atual = prim;        
        for(int i=0; i<totalElementos; i++){
            if(atual.getElement().equals(elem)){
                if(totalElementos==1){
                    E auxE = atual.getElement();
                    prim = ult = null;
                    totalElementos--;
                    return auxE;                    
                }
                if(atual.equals(prim)){
                    E auxE = atual.getElement();
                    prim = atual.getNext();
                    totalElementos--;
                    return auxE;
                }
                else if(atual.equals(ult)){
                    Nodo anterior = getNodo(i-1);
                    E auxE = atual.getElement();
                    anterior.setNext(null);
                    ult = anterior;
                    totalElementos--;
                    return auxE;
                }
                else{
                    Nodo anterior = getNodo(i-1);
                    E auxE = atual.getElement();
                    Nodo posterior = anterior.getNext().getNext();
                    anterior.setNext(posterior);
                    totalElementos--;
                    return auxE;
                }
            }
            atual = atual.getNext();
        }
        return null;
    }

    public boolean isEmpty(){
        boolean res = false;
        if(totalElementos==0)
            res = true;
        return res;
    }

    public int count(E elem){
        Nodo atual = prim;
        int contador = 0;
        for(int i = 0; i<totalElementos; i++){
            if(atual.getElement().equals(elem)){
                contador++;
            }
            atual = atual.getNext();
        }
        return contador;
    }

    public String toString(){
        String resp = "";
        Nodo atual = prim;
        for (int i=0; i<totalElementos; i++){
            resp += atual.getElement();
            atual = atual.getNext();
        }
        return resp;
    }

    public E removeLast(){
        if(totalElementos == 0){
            return null;
        }
        if(totalElementos==1){
            Nodo aux = prim;
            prim = ult = null;
            totalElementos--;
            return aux.getElement();            
        }
        else{
            Nodo aux = ult;
            Nodo penultimo = getNodo(totalElementos-2);
            penultimo.setNext(null);
            ult = penultimo;
            totalElementos--;
            return aux.getElement();
        }
    }

    public static void main(String args[]){
        ListSimpleLinked<String> l1 = new ListSimpleLinked<>();
        l1.add("teste1");
        l1.add("teste2");
        l1.add("teste3");
        l1.add("teste4");
        l1.add("teste5");
        imprime(l1);
        l1.remove(0);
        imprime(l1);
        l1.remove(3);
        imprime(l1);
        l1.remove(0);
        imprime(l1);
        l1.remove(1);
        imprime(l1);
        l1.remove(0);
        imprime(l1);
        l1.remove(0);
        imprime(l1);
        l1.addLast("ultimo");
        imprime(l1);
        l1.addLast("novoultimo");
        imprime(l1);
        l1.add(0,"teste1");
        imprime(l1);
        l1.add(1,"teste2");
        imprime(l1);
        l1.add(2,"teste3");
        imprime(l1);
        l1.add(2,"teste3");
        imprime(l1);
        l1.add(2,"teste4");
        imprime(l1);
        l1.add(0,"teste5");
        imprime(l1);
        l1.add(0,"teste5");
        imprime(l1);
        l1.add(0,"teste5");
        imprime(l1);
        l1.addLast("testeultimo");
        imprime(l1);
        
    }

    public static void imprime(ListSimpleLinked lista){
        for (int i=0; i<lista.size(); i++){
            System.out.print(lista.get(i) + ", ");
        }
        System.out.println("");
    }

}