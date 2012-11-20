
public class Client {
	public static void main(String args[]){		
		long t1 =System.currentTimeMillis()+1550;
		Kattio io = new Kattio(System.in,System.out);

		int N = io.getInt();
		double[][] points= new double[2][N];
		for(int i=0;i<N;i++){
			points[0][i]=io.getDouble();
			points[1][i]=io.getDouble();
		}
		int[][] distances =calculateDistances(points,N);
//		int[] tour=greedyTour(distances,N);
		int[] tour= MinimumSpanningTree.solve(distances, N);
//		io.println("greedy tour");
		int mstDist=tourLength(distances,tour);

		boolean improved = true;
		while(improved&&System.currentTimeMillis()<t1){
			improved =swapOpt(tour,distances);
		}
		
//		int[] tour2= greedyTour(distances, N);
//		int greedDist=tourLength(distances,tour2);
//		if(mstDist>greedDist){
//			tour=tour2;
//		}
		for(int i=0;i<N;i++){
			io.println(tour[i]);
			
		}
		
//		io.println("greedyTour length "+tourLength(distances,tour));
////		io.println("MST tour");
//		for(int i=0;i<N;i++){
//			io.println(superTour[i]);
//		}
////		io.println("MST tour length "+tourLength(distances,superTour));
		io.flush();
		io.close();
			
		
	}
	public static boolean swapOpt(int tour[],int[][] dist){
		boolean improved=false;
		//			System.out.println("improving!");
		for(int i=0; i<tour.length;i++){
			for(int j=i+1; j<tour.length;j++){
				//					swapIfBetter(tour,dist,i,j);
				improved = swapIfBetter(tour,dist,i,j) || improved;
			}
		}
		return improved;


	}
	public static boolean swapIfBetter(int[] tour,int[][] dist,int from,int to){
//		System.out.println("before "+tourLength(dist,tour));
		//calculate local distance before
		System.out.println(tourLength(dist,tour));
		int before=adistance(from, tour,dist)+adistance(to, tour,dist);
		swap(tour, from,to);
		//calculate local distance after
		int after=adistance(from, tour,dist)+adistance(to, tour,dist);
		//swap back if no improvement
		if(before<=after){
			swap(tour, from,to);
//			System.out.println("after "+tourLength(dist,tour));
			return false;
		} else {
			System.out.println(before+" swapped "+after);
			System.out.println(tourLength(dist,tour));
//			System.out.println("after "+tourLength(dist,tour));
			return true;
		}
		
	}
	
	public static void swap(int[] arr, int from, int to){
		int tmp=arr[from];
		arr[from]=arr[to];
		arr[to]=tmp;
	}
	
	/**
	 * calculates the distance from the previous and the next edge,
	 * adjacent to this vertice.
	 * @param vertice
	 * @param tour
	 * @param dist
	 * @return
	 */
	public static int adistance(int vertice, int[] tour,int[][] dist){
		int distance=0;
		int from, to;
		//previous edge
		from = (vertice==0) ? (tour.length-1) : (vertice-1);//the previous vertice in the tour
		to=vertice;
		distance=distance+dist[tour[from]][tour[to]];
		//next edge
		from=vertice;
		to= (vertice == tour.length - 1) ? 0 : (vertice+1);//the next vertice in the tour
		distance=distance+dist[tour[from]][tour[to]];
		return distance;
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
	private static int tourLength(int[][] dist,int[] tour){
		int from=tour[0];
		int distance=0;
		for(int i=1;i<tour.length;i++){
			int to=tour[i];
			distance = distance+dist[from][to];
			from=to;
		}
		distance=distance+dist[tour[tour.length-1]][tour[0]];
		return distance;
	}
	
	
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
