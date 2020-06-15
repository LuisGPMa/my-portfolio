

public class Link implements Comparable<Link>{
    private Tube startingTube;
    private Tube destinationTube; 
    private int startingPoint; 
    private int endingPoint;

    public Link(Tube start, int startPoint, Tube destination, int endPoint){
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
        return ("" + startingTube.getId() + "  " + startingPoint + "  " + destinationTube.getId() + "  " + endingPoint);
    }

    public int compareTo(Link anotherLink){
        if(this.startingPoint > anotherLink.getStartingPoint())
            return 1;
        else if(this.startingPoint < anotherLink.getStartingPoint())
            return -1;
        else
            return 0;
    }

    public void setStartingTube(Tube start){
        startingTube = start;
    }

    public void setDestinationTube(Tube destination){
        destinationTube = destination;
    }

    public int getStartingPoint(){
        return startingPoint;
    }

    public int getEndPoint(){
        return endingPoint;
    }

    public Tube getDestination(){
        return destinationTube;
    }

    public Tube getStartingTube(){
        return startingTube;
    }

}