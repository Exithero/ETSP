
import java.util.Arrays;
import java.util.Random;


public class TSP_Tour {
	UndirectedGraph ug;
	SortedEdges se;
	Path tour;
	Random rand;
	
	long timeLimit;//TEST
	
	
	
	public TSP_Tour(UndirectedGraph ug,SortedEdges se,Path tour,long timeLimit){
		this.ug=ug;
		this.se=se;
		this.tour=tour;
		this.rand= new Random();
		this.timeLimit=timeLimit;//TEST
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
	
	
	public boolean twoOpt(){
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
	
	public boolean startKopt(int k,Kswap2 kswap){
		int[] rightCuts=new int[k];
		
		return kopt3(kswap, rightCuts, 0, tour.length(),k,0, true);
	}
	
	public boolean kopt(Kswap2 kswap, int[] rightCuts, int i, int length,int k,int index, boolean first){
		if(k==0){
//			System.out.println("doing k swap on: "+Arrays.toString(rightCuts));
			return kswap.startFindBestSolution(rightCuts);
		}
		int limit=length;
		boolean improved=false;
		for(int iters=0; iters<limit; iters++){
			rightCuts[index]=i;
			if(first){
				//length -3 because are really choosing 2 vertices and the second vertice makes 
				//it so that one extra place is forbidden
				//<-xOxn->
				improved = kopt(kswap,rightCuts, Help.mod2(i+2, tour.length()), length-3,k-1, index+1, false) 
						|| improved;
			} else {
				//<-xOx(iters)Oxn->
				improved = kopt(kswap,rightCuts, Help.mod2(i+2, tour.length()), length-iters-2,k-1, index+1, false) 
						|| improved;
			}
			
			i=Help.circleIncrement(i, tour.length());
		}
		return improved;
	}
	
	/**
	 * will contain various improvments
	 * @param kswap
	 * @param rightCuts
	 * @param i
	 * @param length
	 * @param k
	 * @param index
	 * @param first
	 * @return
	 */
	public boolean kopt3(Kswap2 kswap, int[] rightCuts, int i, int length,int k,int index, boolean first){
		if(k==0){
//			System.out.println("doing k swap on: "+Arrays.toString(rightCuts));
			return kswap.startFindBestSolution(rightCuts);
		}
		if(index<=1&&System.currentTimeMillis()>=this.timeLimit){ return false; }//Time limit
		int limit=length;
		boolean improved=false;
		for(int iters=0; iters<limit; iters++){
			rightCuts[index]=i;
			if(checkOptimization(rightCuts,index)){
				if(first){
					improved = kopt3(kswap,rightCuts, Help.mod2(i+2, tour.length()), length-3,k-1, index+1, false) 
							|| improved;
				} else {
					improved = kopt3(kswap,rightCuts, Help.mod2(i+2, tour.length()), length-iters-2,k-1, index+1, false) 
							|| improved;
				}
			}
			
			
			i=Help.circleIncrement(i, tour.length());
		}
		return improved;
	}
	
	/**
	 * rightCuts at the index need to be filled before calling this method.
	 * @param rightCuts
	 * @param index
	 * @return
	 */
	public boolean checkOptimization(int[] rightCuts, int index){
		final int opt=index+1;
		if(opt==1){//no opt
			return true;
		} else if(opt==2){//2-opt check
			//d(t 1 ,t 2 ) > d(t 2 ,t 3 )
			int t1=rightCuts[0];
			int t2=Help.circleIncrement(rightCuts[0], tour.length());
			int t3=rightCuts[1];
			
			int oldDist=tour.indexDistance(t1, t2);
			int newDist=tour.indexDistance(t2, t3);
			
			return newDist<oldDist;
		} else if(opt==3){//3-opt check
			//d(t 1 ,t 2 ) + d(t 3 ,t 4 ) > d(t 2 ,t 3 ) + d(t 4 ,t 5 ).
			int t1=rightCuts[0];
			int t2=Help.circleIncrement(rightCuts[0], tour.length());
			int t3=rightCuts[1];
			int t4=Help.circleIncrement(rightCuts[1], tour.length());
			int t5=rightCuts[2];
			
			int oldDist=tour.indexDistance(t1, t2)+tour.indexDistance(t3, t4);
			int newDist=tour.indexDistance(t2, t3)+tour.indexDistance(t4, t5);
			
			return newDist<oldDist;
		}
		//add more opts here
		
		
		return true;
	}
	
	
//	public boolean kopt2(Kswap2 kswap, int[] rightCuts, int fromVertice, int length,int k,int index, boolean first){
//		if(k==0){
////			System.out.println("doing k swap on: "+Arrays.toString(rightCuts));
//			return kswap.startFindBestSolution(rightCuts);
//		}
//		
//		int limit=10;
//		for(int i=0;i<limit;i++){
//			int nextVertice=se.getXNeighbor(fromVertice, i);
//		}
//		
//		int limit=length;
//		boolean improved=false;
//		for(int iters=0; iters<limit; iters++){
//			rightCuts[index]=i;
//			if(first){
//				improved = kopt(kswap,rightCuts, Help.mod2(i+2, tour.length()), length-3,k-1, index+1, false) 
//						|| improved;
//			} else {
//				improved = kopt(kswap,rightCuts, Help.mod2(i+2, tour.length()), length-iters-2,k-1, index+1, false) 
//						|| improved;
//			}
//			
//			i=Help.circleIncrement(i, tour.length());
//		}
//		return improved;
//	}
	
	public boolean isntNear(int[] rightCuts, int k){
		return false;
	}
	
//	public boolean kopt(int k){
//		boolean improved=false;
//		for(int i1=0;i1<tour.length();i1++){
//			int limit1=Help.mod2(i1-1, tour.length());
//			for(int i2=Help.mod2(i1+2,tour.length()); i2!=limit1; Help.circleIncrement(i2, tour.length())){
//				for(int i3=Help.mod2(i2+2,tour.length()); i2!=limit1; Help.circleIncrement(i2, tour.length())){
//					
//				}
//			}
//			
//			
//			int limit1=Help.circleDecrement(i, tour.length());
//			for(int j=Help.circleIncrement(i, tour.length()); true ;j=Help.circleIncrement(j, tour.length())){
//				
//				if(j==limit1){
//					break;
//				}
//			}
//		}
//		return improved;
//	}
//	
	
	
	public boolean twoOpt555(){
		int pathLength=tour.length();
		int k=Math.min(pathLength, pathLength);

		int progress=0;
		
		boolean better=false;
		outer:
		for(int currentVerticeIndex=0; currentVerticeIndex<pathLength;currentVerticeIndex++){
//		for(int currentVerticeIndex=0; progress<pathLength; progress++){
//			currentVerticeIndex=Help.mod2(progress, pathLength);
			
			int nextVerticeIndex=(currentVerticeIndex+1)%pathLength;
			int previousVerticeIndex=Help.mod2(currentVerticeIndex-1, pathLength);
			int distOldEdge=tour.indexDistance(currentVerticeIndex, nextVerticeIndex);

			//goes the edges in sorted order
			for(int iters=0;iters<k;iters++){
				//want index in path instead
				int toVertice=se.getXNeighbor(tour.getVertice(currentVerticeIndex), iters);

				if(toVertice!=tour.getVertice(currentVerticeIndex) && 
						toVertice!=tour.getVertice(previousVerticeIndex) &&
						toVertice!=tour.getVertice(nextVerticeIndex)){
					//					
					int distNewEdge=ug.dist(tour.getVertice(currentVerticeIndex), toVertice);
					if(distNewEdge>=distOldEdge){
						break;
					}



					//check if edge to swapwith is better than edge from current to next
					if(distNewEdge < distOldEdge ){

						int swapWithIndexInPath=Help.indexInArray(tour.path, toVertice);
						//check if second edge swap if it really is an improvement
						if(swapIfBetterTwoOpt(nextVerticeIndex,swapWithIndexInPath)){
							//it was better so complete the reversing
							int innerFrom=(nextVerticeIndex+1)%pathLength;
							int innerTo= swapWithIndexInPath==0 ? pathLength-1 : swapWithIndexInPath-1; 

							tour.reverseSubPath(innerFrom, innerTo);
							
							better=true;
							// test!
//							currentVerticeIndex= currentVerticeIndex==0 ? pathLength-2 :  currentVerticeIndex-1;
						
						
							
						}


					}
				}


			}

		}
		return better;
	}
	
	
	public boolean twoPointFiveOpt(){
		int pathLength=tour.length();
		boolean improved=false;
		
		for(int fromIndex=0;fromIndex<pathLength;fromIndex++){
			int toIndex=Help.circleIncrement(fromIndex, pathLength);

			for(int j=0;j<pathLength-3;j++){
				toIndex=Help.circleIncrement(toIndex, pathLength);
				if(tour.isBetterToInsert(fromIndex, toIndex)){
				
					int before=tour.pathDistance();

					tour.insert(fromIndex, toIndex);
		
					int after= tour.pathDistance();
					if(before<after){
						System.out.println("WTF MAN");
					}
					
					improved=true;		
				}			
			}
		
		}
		return improved;
	}
	
//	public boolean threeOpt(){
//		boolean improved=false;
//		int pathLength= tour.length();
//		for(int vi1=0;vi1<pathLength;vi1++){
//			for(int vi2=0;vi2<pathLength;vi2++){
//				for(int vi3=0;vi3<pathLength;vi3++){
//					if(swap3opt(vi1,vi2, vi3)){
//						
//					}
//				}
//			}
//		}
//		return  improved;
//	}
	
//	public boolean threeoptmove(int vi1, int vi2, int vi3){
//		//find best of the 8 permutations
//		
//		return true;
//	}
	
//	public boolean swap3opt(int vi1, int vi2, int vi3){
//		return true;
//	}
	

	
	
	
	
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
