
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
	
	public void setRandomTour(){
		tour.setPath(randomTour(ug,rand));
	}
	
	private static int[] randomTour(UndirectedGraph ug, Random rand){
		int arr[] =Help.indexFilledArray(ug.vertices);
		Help.randomShuffle(arr, rand);
		return arr;
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
	
	
	public boolean twoOpt555(){
		int pathLength=tour.length();
		
		boolean better=false;
		for(int currentVerticeIndex=0; currentVerticeIndex<pathLength;currentVerticeIndex++){
			int nextVerticeIndex=(currentVerticeIndex+1)%pathLength;
			//skip the current and the nextVerticeIndex
			int toVerticeIndex=(currentVerticeIndex+2)%pathLength;
			
			int distOldEdge=tour.indexDistance(currentVerticeIndex, nextVerticeIndex);/*ug.dist(current,nextVerticeIndex);*/
			
			//goes through all other edges
			for(int iters=0;iters<pathLength-3;iters++){
				
				int distNewEdge=tour.indexDistance(currentVerticeIndex,toVerticeIndex);/*ug.dist(current,swapWith);*/
				//check if edge to swapwith is better than edge from current to next
				if(distNewEdge < distOldEdge ){
//					System.out.println("before " +tourLength(dist,tour));
					//check if second edge swap if it really is an improvement
					if(swapIfBetterTwoOpt(nextVerticeIndex,toVerticeIndex)){
						//it was better so complete the reversing
						int innerFrom=(nextVerticeIndex+1)%pathLength;
						int innerTo= toVerticeIndex==0 ? pathLength-1 : toVerticeIndex-1; 
//						reverse(path, innerFrom, innerTo);
						tour.reverseSubPath(innerFrom, innerTo);
//						System.out.println("after " +tourLength(dist,tour));
						better=true;
					}
					
					
				}
				
				//increment the index, but do a wraparound if it reaches the end
				toVerticeIndex++; if(toVerticeIndex>=pathLength){ toVerticeIndex=0; }
			}
			
		}
		return better;
	}
	
	public boolean twoOpt(){
		int pathLength=tour.length();
		
		boolean better=false;
		for(int currentVerticeIndex=0; currentVerticeIndex<pathLength;currentVerticeIndex++){
			int nextVerticeIndex=(currentVerticeIndex+1)%pathLength;
			int previousVerticeIndex=Help.mod2(currentVerticeIndex-1, pathLength);
//			int distOldEdge=ug.dist(current,nextVerticeIndex);
			int distOldEdge=tour.indexDistance(currentVerticeIndex, nextVerticeIndex);
			
			
			boolean gah=false;
			//goes the edges in sorted order
			for(int iters=0;iters<pathLength;iters++){
				//want index in path instead
				int toVertice=se.getXNeighbor(currentVerticeIndex, iters);
				
				if(toVertice!=tour.getVertice(currentVerticeIndex) && 
						toVertice!=tour.getVertice(previousVerticeIndex) &&
								toVertice!=tour.getVertice(nextVerticeIndex)){
//					System.out.println("swapcandidate2: "+current+" "+swapWith);
//					int distNewEdge=ug.dist(current,swapWithIndexInPath);
//					int distNewEdge=tour.indexDistance(current,swapWithIndexInPath);
					int distNewEdge=ug.dist(tour.getVertice(currentVerticeIndex), toVertice);
					
					if(distNewEdge >= distOldEdge){
						gah=true;;
					}
					
//					System.out.println("dist2 " +distOldEdge+" "+distNewEdge);
					//check if edge to swapwith is better than edge from current to next
					if(distNewEdge < distOldEdge ){
						if(gah==true){
							System.out.println("WTF! previous distance: "+distOldEdge+"("+
						currentVerticeIndex+","+iters+")"+" new Edge wtf "+distNewEdge+
						"next in sorted"+ug.dist(tour.getVertice(currentVerticeIndex), 
								se.getXNeighbor(currentVerticeIndex, (iters+1)%pathLength)));
						}
						int swapWithIndexInPath=Help.indexInArray(tour.path, toVertice);
						
//						System.out.println("before " +tourLength(dist,tour));
						//check if second edge swap if it really is an improvement
						if(swapIfBetterTwoOpt(nextVerticeIndex,swapWithIndexInPath)){
							//it was better so complete the reversing
							int innerFrom=(nextVerticeIndex+1)%pathLength;
							int innerTo= swapWithIndexInPath==0 ? pathLength-1 : swapWithIndexInPath-1; 
//							reverse(path, innerFrom, innerTo);
							tour.reverseSubPath(innerFrom, innerTo);
//							System.out.println("after " +tourLength(dist,tour));
							better=true;
						}
						
						
					}
				}
				
				
			}
			
		}
		return better;
	}
	
	
	public boolean threeOpt(){
		boolean better=false;
		return  better;
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
	
	/**
	 * fromindex and toindex should be in the swap intervall
	 * @param fromIndex
	 * @param toIndex
	 * @return
	 */
	public boolean swapIfBetterTwoOpt(int fromIndex,int toIndex){
//		System.out.println("before "+tourLength(dist,tour));
		//calculate local distance before
		int before = tour.pathDistDirection(fromIndex, -1) + tour.pathDistDirection(toIndex, +1);
//		System.out.println("before edges "+new Point(from-1,from)+" , "+new Point(to,to+1));
		tour.swap(fromIndex,toIndex);
		//calculate local distance after
		int after = tour.pathDistDirection(fromIndex, -1) + tour.pathDistDirection(toIndex, +1);
		//swap back if no improvement
		if(before<=after){
			tour.swap(fromIndex,toIndex);
			return false;
		} else {
//			System.out.println(before+" swapped "+after);
//			System.out.println("after "+tourLength(dist,tour));
			return true;
		}
		
	}
	
	


}
