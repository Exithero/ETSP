import java.util.Arrays;




public class Client {
	public static void main(String args[]){		
		long t1 =System.currentTimeMillis()+1650;
		Kattio io = new Kattio(System.in,System.out);
		int N = io.getInt();
		double[][] points= new double[2][N];
		for(int i=0;i<N;i++){
			points[0][i]=io.getDouble();
			points[1][i]=io.getDouble();
		}
		
		//read test data from http://www.tsp.gatech.edu/vlsi/page2.html
//		double[][] points=readOtherData(io);
//		int N=points[0].length;
		
		
		
		UndirectedGraph ug=new UndirectedGraph(points,N);
		SortedEdges se=new SortedEdges(ug);
		Path tour1= new Path(ug);
		TSP_Tour tsp=new TSP_Tour(ug,se,tour1);
		tsp.setGreedyTour();
		int naive=tsp.pathDistance();
//		tsp.twoOpt();
//		ug.twoOptTimed(t1);
//		ug.twoOptAnnelingTimed(t1);
	
		
		
//		int[][] distances =calculateDistances(points,N);
////		int[] tour=greedyTour(distances,N);
//		int[] tour= MinimumSpanningTree.solve(distances, N);
////		io.println("greedy tour");
//		int mstDist=tourLength(distances,tour);
//
////		boolean improved = true;
////		while(improved&&System.currentTimeMillis()<t1){
////			improved =swapOpt(tour,distances);
////		}
//		
////		int[] tour2= greedyTour(distances, N);
////		int greedDist=tourLength(distances,tour2);
////		if(mstDist>greedDist){
////			tour=tour2;
////		}
		boolean improved = true;
		int count=0;
		while(improved&&System.currentTimeMillis()<t1){
			count++;
			improved =tsp.twoOpt();
		}
		int[] tour2=tsp.getPathCopy();
		int OptPoint=tsp.pathDistance();
		int optimal=3558;
		double score=(OptPoint-optimal);
		score=score/(double)(naive-optimal);
		score= Math.pow(0.02d, score);
		
//		
//	
//		
//		
////		io.println("greedyTour length "+tourLength(distances,tour));
//////		io.println("MST tour");
////		for(int i=0;i<N;i++){
////			io.println(superTour[i]);
////		}
		
		
		for(int i=0;i<N;i++){
			io.println(tour2[i]);
		}
//		io.println("2Opt Count: "+count);
//		io.println("MST tour length "+tsp.pathDistance());
//		io.println("Points given: "+score*50);
		io.flush();
		io.close();
		
		
			
		
	}
	
	public static double[][] readPoints(Kattio io, int N){
//		int N = io.getInt();
		double[][] points= new double[2][N];
		for(int i=0;i<N;i++){
			points[0][i]=io.getDouble();
			points[1][i]=io.getDouble();
		}
		return points;
	}
	
	
	/**
	 * read data points from http://www.tsp.gatech.edu/vlsi/page5.html
	 * @param io
	 * @return
	 */
	public static double[][] readOtherData(Kattio io){
		String tmp;
		double[][] points=null;
		int N=0;
		
		//read until reached the EOF line
		while(!(tmp=io.readLine()).equals("EOF")){
			String sarr[]=tmp.split(" ");
			
			//try to read the dimension
			if(points==null && sarr[0].equals("DIMENSION")){
				N = Integer.parseInt(sarr[2]);
				points = new double[2][N];
			}
			
			//try to read
			if(points!=null){
				try{
					int a=Integer.parseInt(sarr[0]);
					int b=Integer.parseInt(sarr[1]);
					int c=Integer.parseInt(sarr[2]);
					points[0][a-1]=b;
					points[1][a-1]=c;
				} catch(Exception e){
					//ignore
				}
			}
		}
		return points;
	}
	
	public void printArray(double[][] arr){
		System.out.println("[");
		for(int i=0;i<arr.length;i++){
			System.out.println(Arrays.toString(arr[i]));
		}
		System.out.println("]");
	}
	
	public static void printArray(int[][] arr){
		System.out.println("[");
		for(int i=0;i<arr.length;i++){
			System.out.println(Arrays.toString(arr[i]));
		}
		System.out.println("]");
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
//		System.out.println(tourLength(dist,tour));
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
//			System.out.println(before+" swapped "+after);
//			System.out.println(tourLength(dist,tour));
//			System.out.println("after "+tourLength(dist,tour));
			return true;
		}
		
	}
	
	/**
	 * can handle if from or to has gone beyond the edge once
	 * @param dist
	 * @param from
	 * @param to
	 * @param n
	 * @return
	 */
	public static int distanceHelp(int[] tour,int[][] dist,int from ,int to){
		int n=tour.length;
		
		if(from<0){
//			System.out.println("converting index (from) "+from+" to index "+(n+from));
			from=n+from;
		}
//		if(to<0){
//			System.out.println("converting index "+to+" to index "+(n+to));
//			to=n+to;
//		}
//		if(from>=n){
//			from=from-n;
//		}
		if(to>=n){
//			System.out.println("converting index (to) "+to+" to index "+(to-n));
			to=to-n;
		}
		
		return dist[tour[from]][tour[to]];
		
	}
	
	public static boolean swapIfBetterTwoOpt(int[] tour,int[][] dist,int from,int to){
//		System.out.println("before "+tourLength(dist,tour));
		//calculate local distance before
		int before=distanceHelp(tour,dist,from-1,from)+distanceHelp(tour,dist,to,to+1);
//		System.out.println("before edges "+new Point(from-1,from)+" , "+new Point(to,to+1));
		swap(tour, from,to);
		//calculate local distance after
		int after=distanceHelp(tour,dist,from-1,from)+distanceHelp(tour,dist,to,to+1);
		//swap back if no improvement
		if(before<=after){
			swap(tour, from,to);
			return false;
		} else {
//			System.out.println(before+" swapped "+after);
			
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
	
	public static boolean twoOpt(int[] tour,int[][] dist){
		
		boolean better=false;
		for(int current=0;current<tour.length;current++){
			
			int nextVerticeIndex=(current+1)%tour.length;
			int swapWith=(current+2)%tour.length;
			
			for(int iters=0;iters<tour.length-3;iters++){
				//check if edge to swapwith is better than edge from current to next
				if(dist[current][swapWith]<dist[current][nextVerticeIndex]){
//					System.out.println("before " +tourLength(dist,tour));
					//check if second swap really was better
					if(swapIfBetterTwoOpt(tour,dist,nextVerticeIndex,swapWith)){
						//it was better so complete the reversing
						int innerFrom=(nextVerticeIndex+1)%tour.length;
						int innerTo= swapWith==0 ? tour.length-1 : swapWith-1; 
						reverse(tour, innerFrom, innerTo);
//						System.out.println("after " +tourLength(dist,tour));
						better=true;
					}
					
					
				}
				
				
				swapWith++;if(swapWith>=tour.length){ swapWith=0; }
			}
		}
		return better;
	}
	
	
	
	/**
	 * reverse, even if the interval goes over the edge
	 * @param arr
	 * @param from
	 * @param to
	 */
	public static void reverse(int[] arr, int from, int to){
		int iters=0;
		if(from>to){
			iters=((arr.length-from)+to+1)/2;
		} else {
			iters=(to-from+1)/2;
		}
		
		while(iters>0){
			swap(arr,from,to);
			iters--;
			if(++from>=arr.length){ from=0; }
			if(--to<0){ to=arr.length-1; }
		}
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
	
	private static int[][] calculateDistances2(double[][] points,int N) {
		int[][] distanceMatrix= new int[N][N];
		for(int i=0; i<N;i++){
			for(int j=0;j<N;j++){
				if(j==i){
					distanceMatrix[i][j]=0;
				} else if(i<j){
					distanceMatrix[i][j]=distance(points[0][i],points[0][j],points[1][i],points[1][j]);
				} else {//i>j
					distanceMatrix[i][j]=distanceMatrix[j][i];
				}
				
			}
		
		}
		return distanceMatrix;
	}

	public int getVertice(int[] arr,int index){
		index=mod2(index,arr.length);
		return arr[index];
	}
	//FROM INTERNET
	public static int mod2(int x,int n){
		int r = x % n;
		if (r < 0)
		{
		    r += n;
		}
		return r;
	}
	
	private static int distance(double x1, double x2,double y1,double y2){
		double dx= x1-x2;
		double dy= y1-y2;
		return (int)Math.round(Math.sqrt(dx*dx+dy*dy));
		
	}
}
