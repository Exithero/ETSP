
public class Graph2 {
	
	
	public class Vertice {
		int id;
		Edge e1;
		Edge e2;
	}
	
	public class Edge{
		int weight;
		Vertice from;
		Vertice to;
		public Edge(Vertice from, Vertice to, int weight){
			this.weight=weight;
			this.from=from;
			this.to=to;
		}
	}
}
