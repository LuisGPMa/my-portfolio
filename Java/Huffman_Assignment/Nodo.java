public class Nodo implements Comparable<Nodo>{
    private String element;
    private long freq;
    private Nodo right;
    private Nodo left;   
    private Nodo father;

    public Nodo(String e, int f) {
        element = e;
        freq = f;
        right = null;
        left = null;
        father=null;
    }

    public void setElement(String e) {element = e; }
    public void setFreq(int f){freq = f; }
    public void setLeft (Nodo l) { left = l; }
    public void setRight (Nodo r) { right = r; }
    public void setPos(Nodo n, NodePosition pos){
        if(pos==NodePosition.LEFT)
            left = n;
        else
            right = n;
    }

    @Override
    public int compareTo(Nodo outro){
        if(this.getFreq()>outro.getFreq())
            return 1;
        else if(this.getFreq()<outro.getFreq())
            return -1;
        else 
            return 0;
    }

    public void setFather (Nodo p) {father = p;}
    public String getElement() { return element; }
    public long getFreq(){ return freq; }
    public Nodo getRight() { return right; }
    public Nodo getLeft() { return left;}
    public Nodo getPos(NodePosition pos){ 
        if(pos==NodePosition.LEFT)
            return left;
        else
            return right;
    }
    public Nodo getFather() { return father;}

    public boolean isLeaf(){
        return (left == null && right == null);
    }
    
    public String toString(){
        int nFilhos = 0;
        if(left != null)
            nFilhos++;
        if(right != null)
            nFilhos++;
        return ("elem: " + element + "  freq: " + freq + " no de filhos: " + nFilhos);
    }
}