import java.util.Arrays;
import java.util.Random;


public class TSP_Tour {
	UndirectedGraph ug;
	SortedEdges se;
	Path tour;
	Random rand;
	public TSP_Tour(UndirectedGraph ug,SortedEdges se,Path tour){
		this.ug=ug;
		this.se=se;
		this.tour=tour;
		this.rand= new Random();
		
	}
	

	
	private static int[] greedyTour(UndirectedGraph ug){
		int N= ug.vertices;
		int[] tour = new int[N];
		boolean[] used = new boolean[N];
		tour[0]=0;
		used[0]=true;
		for(int i=1;i<N;i++){
			int best= -1;
			for(int j=0; j<N;j++){
				if(!used[j]&&(best==-1 || ug.dist(tour[i-1], j) < ug.dist(tour[i-1],best))){
					best=j;
				}
			}
			tour[i]=best;
			used[best]=true;
		}
		return tour;
	}
	
	public int pathDistance(){
		return tour.pathDistance();
	}
	
	public void setGreedyTour(){
		tour.setPath(greedyTour(ug));
	}
	
	


	
	public int[] getPathCopy(){
		return this.tour.getPathCopy();
	}
	
	/**
	 * not true anniling...
	 * @param limit
	 */
	public void twoOptAnnelingTimed(final long limit){
		int swaps=tour.length()/2;
		while(swaps>0&&System.currentTimeMillis()<limit){
			System.out.println("swaps: "+swaps);
			randomSwaps(swaps);
			twoOpt();
			swaps=swaps/2;
		}
		twoOptTimed(limit);
	}
	
	/**
	 * can be used to randomize the path
	 * @param iters
	 */
	public void randomSwaps(int iters){
		for(int i=0;i<iters;i++){
			tour.swap(rand.nextInt(tour.length()),rand.nextInt(tour.length()));
		}
	}
	
	
	public boolean twoOpt(){
		int pathLength=tour.length();
		
		boolean better=false;
		for(int current=0;current<pathLength;current++){
			int nextVerticeIndex=(current+1)%pathLength;
			//skip the current and the nextVerticeIndex
			int swapWith=(current+2)%pathLength;
			
			int distOldEdge=ug.dist(current,nextVerticeIndex);
			
			//goes through all other edges
			for(int iters=0;iters<pathLength-3;iters++){
				
				int distNewEdge=ug.dist(current,swapWith);
				//check if edge to swapwith is better than edge from current to next
				if(distNewEdge < distOldEdge ){
//					System.out.println("before " +tourLength(dist,tour));
					//check if second edge swap if it really is an improvement
					if(swapIfBetterTwoOpt(nextVerticeIndex,swapWith)){
						//it was better so complete the reversing
						int innerFrom=(nextVerticeIndex+1)%pathLength;
						int innerTo= swapWith==0 ? pathLength-1 : swapWith-1; 
//						reverse(path, innerFrom, innerTo);
						tour.reverseSubPath(innerFrom, innerTo);
//						System.out.println("after " +tourLength(dist,tour));
						better=true;
						
						
						//go back one step
					}
					
					
				}
				
				//increment the index, but do a wraparound if it reaches the end
				swapWith++;if(swapWith>=pathLength){ swapWith=0; }
			}
		}
		return better;
	}
	
	

	
	
	
	
//	/**
//	 * only works if path has been set.
//	 * @param from
//	 * @param to
//	 * @return
//	 */
//	public int distance(int from,int to){
//		int n=path.length;
//		return dist[circleIndex(from, n)][circleIndex(to, n)];
//	}
	
	public void twoOptTimed(final long limit){
		boolean improved = true;
		while(improved&&System.currentTimeMillis()<limit){
			improved = twoOpt();
		}
	}
	
	public boolean swapIfBetterTwoOpt(int from,int to){
//		System.out.println("before "+tourLength(dist,tour));
		//calculate local distance before
		int before = tour.pathDistDirection(from, -1) + tour.pathDistDirection(to, +1);
//		System.out.println("before edges "+new Point(from-1,from)+" , "+new Point(to,to+1));
		tour.swap(from,to);
		//calculate local distance after
		int after = tour.pathDistDirection(from, -1) + tour.pathDistDirection(to, +1);
		//swap back if no improvement
		if(before<=after){
			tour.swap(from,to);
			return false;
		} else {
//			System.out.println(before+" swapped "+after);
//			System.out.println("after "+tourLength(dist,tour));
			return true;
		}
		
	}
	
	


}
