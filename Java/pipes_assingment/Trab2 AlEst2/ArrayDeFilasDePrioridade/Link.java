
public class Link implements Comparable<Link>{
    private int startingTube;
    private int destinationTube; 
    private int startingPoint; 
    private int endingPoint;

    public Link(int start, int startPoint, int destination, int endPoint){
        startingTube = start;
        startingPoint = startPoint;
        destinationTube = destination;
        endingPoint = endPoint;
    }

    public Link(int startPoint, int endPoint){
        startingPoint = startPoint;
        endingPoint = endPoint;
    }

    @Override
    public String toString(){
        return ("" + startingTube + "  " + startingPoint + "  " + destinationTube + "  " + endingPoint);
    }

    public int compareTo(Link anotherLink){
        if(this.startingPoint > anotherLink.getStartingPoint())
            return 1;
        else if(this.startingPoint < anotherLink.getStartingPoint())
            return -1;
        else
            return 0;
    }

    public void setStartingTube(int start){
        startingTube = start;
    }

    public void setDestinationTube(int destination){
        destinationTube = destination;
    }

    public int getStartingPoint(){
        return startingPoint;
    }

    public int getEndPoint(){
        return endingPoint;
    }

    public int getDestination(){
        return destinationTube;
    }

    public int getStartingTube(){
        return startingTube;
    }

}