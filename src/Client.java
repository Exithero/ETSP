
public class Client {
	public static void main(String args[]){
		Kattio io = new Kattio(System.in,System.out);
		int N = io.getInt();
		double[][] points= new double[2][N];
		for(int i=0;i<N;i++){
			points[0][i]=io.getDouble();
			points[1][i]=io.getDouble();
		}
		int[][] distances =calculateDistances(points,N);
		int[] tour=greedyTour(distances,N);
		for(int i=0;i<N;i++){
			io.println(tour[i]);
		}
		io.flush();
		io.close();
			
		
	}
	
//	GreedyTour
//	   tour[0] = 0
//	   used[0] = true
//	   for i = 1 to n-1
//	      best = -1
//	      for j = 0 to n-1
//	         if not used[j] and (best = -1 or dist(tour[i-1],j) < dist(tour[i-1],best))
//		    best = j
//	      tour[i] = best
//	      used[best] = true
//	   return tour
	
	private static int[] greedyTour(int[][] dist,int N){
		int[] tour = new int[N];
		boolean[] used = new boolean[N];
		tour[0]=0;
		used[0]=true;
		for(int i=1;i<N;i++){
			int best= -1;
			for(int j=0; j<N;j++){
				if(!used[j]&&(best==-1 || dist[tour[i-1]][j] < dist[tour[i-1]][best])){
					best=j;
				}
			}
			tour[i]=best;
			used[best]=true;
		}
		return tour;
	}
	
	private static int[][] calculateDistances(double[][] points,int N) {
		int[][] distanceMatrix= new int[N][N];
		for(int i=0; i<N;i++){
			for(int j=0;j<N;j++){
				distanceMatrix[i][j]=distance(points[0][i],points[0][j],points[1][i],points[1][j]);
			}
		
		}
		return distanceMatrix;
	}
	
	
	private static int distance(double x1, double x2,double y1,double y2){
		double dx= x1-x2;
		double dy= y1-y2;
		return (int)Math.round(Math.sqrt(dx*dx+dy*dy));
		
	}
}
