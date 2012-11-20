import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;


public class MinimumSpanningTree {

	
	public static int[] solve(int[][] dist, int N){
		ArrayList<Edge> mst= prims2(dist, N);
		UndirectedGraph ug=new UndirectedGraph(mst,N);
		ArrayList<Integer> dp=new ArrayList<Integer>();
		doubleWalk(0,new boolean[N],ug,dp);
		ArrayList<Integer> path=getTSPPath(new boolean[N], dp);
		return toArray(path);
	}
	
	public static int[] toArray(ArrayList<Integer> list){
		int[] res=new int[list.size()];
		for(int i=0;i<list.size();i++){
			res[i]=list.get(i);
		}
		return res;
	}
	
	public static ArrayList<Edge> prims(int[][] dist, int N){

		ArrayList<Integer> vNew= new ArrayList<Integer>();
		ArrayList<Integer> vTo=init(N);
		ArrayList<Edge> eNew= new ArrayList<Edge>();
		PriorityQueue<Edge> q= new PriorityQueue<Edge>();
		boolean[] added=new boolean[N];
		
		
		vNew.add(0);
		vTo.remove((Integer)0);
		while(eNew.size()<N-1){
			Edge e=findMinEdge(vNew,vTo,dist);
			
			vNew.add(e.to);
			vTo.remove((Integer)e.to);
			eNew.add(e);
			
			
		}
		
		return eNew;
	}
	
	public static ArrayList<Edge> prims2(int[][] dist, int N){

		ArrayList<Integer> vNew= new ArrayList<Integer>();
		ArrayList<Integer> vTo=init(N);
		ArrayList<Edge> eNew= new ArrayList<Edge>();
		PriorityQueue<Edge> q= new PriorityQueue<Edge>();
		boolean[] added=new boolean[N];
		
		added[0]=true;
		addToQueue(0, dist,q, added);
		vNew.add(0);
		
		
		vTo.remove((Integer)0);
		while(eNew.size()<N-1){
			Edge e=q.poll();
			
			//add
			if(!added[e.to]){
				added[e.to]=true;
				addToQueue(e.to, dist,q, added);
				vNew.add(e.to);
				eNew.add(e);
			}
			
			
			
		}
		
		return eNew;
	}
	
	public static void addToQueue(int fromVertice, int[][] dist,PriorityQueue<Edge> q, boolean[] added){
		for(int to=0;to<added.length;to++){
			if(!added[to]){
				q.offer(new Edge(fromVertice,to,dist[fromVertice][to]));
			}
		}
	}
	
	static Edge findMinEdge(ArrayList<Integer> vNew,ArrayList<Integer> tos,int[][] dist){
		int answerTo=-1;
		int answerFrom=-1;
		int min=Integer.MAX_VALUE;
		for(int from: vNew){
			for(int to: tos){
				if(dist[from][to]<min){
					min=dist[from][to];
					answerTo=to;
					answerFrom=from;
				}
			}
		}
		return new Edge(answerFrom,answerTo,min);
	}
	
	
	
	static ArrayList<Integer> init(int N){
		ArrayList<Integer> res= new ArrayList<Integer>();
		for(int i=0; i<N ;i++){
			res.add(i);
		}
		return res;

	}
	
	public static void doubleWalk(int current, boolean[] visited, UndirectedGraph ug, ArrayList<Integer> doublePath){
		if(visited[current]){ return; }
		doublePath.add(current);
		visited[current]=true;
		for(int i=0;i<ug.edges[current].length;i++){
			int to=ug.edges[current][i];
			if(!visited[to]){
				doubleWalk(to,visited, ug, doublePath);//add going down
				doublePath.add(current);//add going up
			}
		}
	}
	
	public static ArrayList<Integer> getTSPPath(boolean[] visited, ArrayList<Integer> doublePath){
		ArrayList<Integer> res=new ArrayList<Integer>();
		for(int i=0;i<doublePath.size();i++){
			int vertice=doublePath.get(i);
			if(!visited[vertice]){
				res.add(vertice);
				visited[vertice]=true;
			}
		}
		return res;
	}
}
