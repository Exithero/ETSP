
public class SortableVertice implements Comparable<SortableVertice> {
	public int to;
	public int distance;
	
	public SortableVertice(int to,int distance){
		this.to=to;
		this.distance=distance;
	}
	
	@Override 
	public int compareTo(SortableVertice other) {
		if(this.distance>other.distance){
			return 1;
		}
		else if(this.distance<other.distance){
			return -1;
		}
		else{
			return 0;
		}
	}

}
