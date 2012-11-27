import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class UndirectedGraph {
	//the graph
	int[][] dist;
	int vertices;
	
//	//the path
//	int[] path=null;//is inserted later
//	
//	//the random
//	Random rand=new Random();
	
	public UndirectedGraph(double[][] points, int nrOfVertices){
		this.vertices=nrOfVertices;
		this.dist=calculateDist2(points,nrOfVertices);
	}
	
	public UndirectedGraph(int[][] edges, int nrOfVertices){
		this.dist=edges;
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
		this.dist=edges;
		this.vertices=nrOfVertices;
	}
	

	public int dist(final int from, final int to){
		return dist[from][to];
	}
	
	/**
	 * old
	 * @param points
	 * @param N
	 * @return
	 */
	private static int[][] calculateDist(double[][] points,int N) {
		int[][] distanceMatrix= new int[N][N];
		for(int i=0; i<N;i++){
			for(int j=0;j<N;j++){
				distanceMatrix[i][j]=calculateDistance(points[0][i],points[0][j],points[1][i],points[1][j]);
			}
		}
		return distanceMatrix;
	}
	
	/**
	 * new optimized
	 * @param points
	 * @param N
	 * @return
	 */
	private static int[][] calculateDist2(double[][] points,int N) {
		int[][] distanceMatrix= new int[N][N];
		for(int i=0; i<N;i++){
			for(int j=0;j<N;j++){
				if(j==i){
					distanceMatrix[i][j]=0;
				} else if(i<j){
					distanceMatrix[i][j]=calculateDistance(points[0][i],points[0][j],points[1][i],points[1][j]);
				} else {//i>j
					distanceMatrix[i][j]=distanceMatrix[j][i];
				}
				
			}
		}
		return distanceMatrix;
	}
	
	private static int calculateDistance(double x1, double x2,double y1,double y2){
		double dx= x1-x2;
		double dy= y1-y2;
		return (int)Math.floor(Math.sqrt(dx*dx+dy*dy)+0.5d);
//		return (int)Math.round(Math.sqrt(dx*dx+dy*dy));
	}
	
}
