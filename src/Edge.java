
public class Edge implements Comparable<Edge> {
	int from;
	int to;
	int weight;
	public Edge(int from, int to, int weight){
		this.from=from;
		this.to=to;
		this.weight=weight;
	}
	
	@Override
	public int compareTo(Edge o) {
		if(this==o){ return 0; }
		if(this.weight==o.weight){
			return 0;
		}
		if(this.weight>o.weight){
			return 1;
		} else {
			return -1;
		}
	}
}
