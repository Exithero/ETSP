import java.util.ArrayList;


public class UndirectedGraph {
	int[][] edges;
	int vertices;
	public UndirectedGraph(int[][] edges, int nrOfVertices){
		this.edges=edges;
		this.vertices=nrOfVertices;
	}
	
	public UndirectedGraph(ArrayList<Edge> edges1, int nrOfVertices){
		int[] sizes=new int[nrOfVertices];
		for(int i=0;i<edges1.size();i++){
			sizes[edges1.get(i).from]++;
		}
		int[][] edges=new int[nrOfVertices][];
		for(int i=0;i<nrOfVertices;i++){
			edges[i]=new int[sizes[i]];
		}
		int indexes[]=new int[nrOfVertices];
		for(int i=0;i<edges1.size();i++){
			Edge e=edges1.get(i);
			edges[e.from][indexes[e.from]]=e.to;
			indexes[e.from]++;
		}
		this.edges=edges;
		this.vertices=nrOfVertices;
	}
	
}
