import java.util.Arrays;




public class Kswap2 {
	boolean improved=false;
	int best;
	int[] bestSolution;
	boolean[] bestReversed;
	
	
	
	Path p;
	
	public Kswap2(Path p){
		this.p=p;
//		this.best=oldEdgeDist;
	}
	
	public boolean startFindBestSolution(int[] rightCuts){
		

		//
		SubPath[] subPaths=getSubPaths(rightCuts,p);
//		System.out.println("subPaths: "+Arrays.toString(subPaths));
		
		
		//temporary
		int[] solution=new int[rightCuts.length];
		boolean[] reversed=new boolean[rightCuts.length];
		boolean[] used=new boolean[rightCuts.length];
		
		//fill because want to use the dist method to calculate oldEdgeDist
		for(int i=0;i<rightCuts.length;i++){
			solution[i]=i;
		}
		//calculate oldEdgeDist
		int oldEdgeDist=0;
		for(int i=rightCuts.length-2;i>=-1;i--){
			oldEdgeDist += dist(subPaths, solution, reversed, i);
		}
		
		//the best results
		this.improved=false;
		this.best=oldEdgeDist;
		this.bestSolution=new int[rightCuts.length];
		this.bestReversed=new boolean[rightCuts.length];
		
		
		//find the best k swap
		findBestPermutation(subPaths, solution, reversed, 
				used, rightCuts.length-1, 0);
		
		if(improved){
//			System.out.println("was better performing move: ");
//			System.out.println("previous path: "+Arrays.toString(p.getPathCopy()));
//			System.out.println(Arrays.toString(subPaths));
//			System.out.println(Arrays.toString(bestSolution));
//			System.out.println(Arrays.toString(bestReversed));
			p.performSubPathChanges(subPaths, bestSolution, bestReversed);
			improved=false;
			return true;
		} else {
			return false;
		}
		
	}
	
	public SubPath[] getSubPaths(int[] cuts, Path p){
		SubPath[] res=new SubPath[cuts.length];
		
		
		
		for(int i=0;i<cuts.length-1;i++){
			int left=Help.circleIncrement(cuts[i], p.length());
			int right=cuts[i+1];
			res[i]=new SubPath(p,left,right);
		}
		int left=Help.circleIncrement(cuts[cuts.length-1], p.length());
		int right=cuts[0];
		res[cuts.length-1]=new SubPath(p,left,right);
		
		
		return res;
	}
	

	
	/**
	 * start with depth=sub.length
	 * @param subPaths
	 * @param solution
	 * @param reversed
	 * @param depth
	 */
	private void findBestPermutation(SubPath[] subPaths, int[] solution, boolean[] reversed, 
			boolean[] used, int depth, int dist){
		
		//add the new distance from the edge added by the caller of this method.
		//also works for the last depth when the depth is -1
		if(depth<subPaths.length-2){
			dist += dist(subPaths, solution, reversed, depth+1);
			//it can't become a better solution then quit
			if(dist>=best){
				return;
			}
		}
		
		//maximum depth check
		if(depth<0){
			
			dist += dist(subPaths, solution, reversed, depth);
			
			//check if it's a best solution
			if(dist<best){
				//save solution
				this.improved=true;
				best=dist;
				bestSolution=Arrays.copyOf(solution, solution.length);
				bestReversed=Arrays.copyOf(reversed, reversed.length);
				
				//DEBUG
//				System.out.println("a k swap:");
//				System.out.println("best "+best);
//				System.out.println(dist+" is best: "+(dist<best));
//				System.out.println(Arrays.toString(solution));
//				System.out.println(Arrays.toString(reversed));
			}
			return;
		}
		
		//first time
		if(depth==subPaths.length-1){
			
			//can keep the first fixed
			solution[depth]=depth;
			reversed[depth]=false;
			used[depth]=true;
			findBestPermutation(subPaths, solution, reversed, used, depth-1,dist);
			used[depth]=false;
			return;
		}
		
		//try all permutations for not used subpaths
		for(int i=0;i<subPaths.length;i++){
			if(!used[i]){
				//set to true before recursion
				used[i]=true;
				
				
				solution[depth]=i;
				//do recursion with the subpath reversed
				reversed[depth]=true;
				findBestPermutation(subPaths, solution, reversed, used, depth-1,dist);
				//do recursion with the subpath not reversed
				reversed[depth]=false;
				findBestPermutation(subPaths, solution, reversed, used, depth-1,dist);
				
				
				//restore after recursion
				used[i]=false;
			}
		}
		
		
		
	}
	
	/**
	 * returns distance between the subpath at the index=depth and the
	 * subpath at index=depth+1.
	 * @param subPaths
	 * @param solution
	 * @param reversed
	 * @param depth
	 */
	public int dist(SubPath[] subPaths, int[] solution, boolean[] reversed, int depth){
		int index1=depth;
		
		if(index1<0){ index1=subPaths.length-1; }//compensate for wrap around
		
		int index2=depth+1;
		
		int from=subPaths[solution[index1]].getRightVerticeIndex(reversed[index1]);
		int to=subPaths[solution[index2]].getLeftVerticeIndex(reversed[index2]);
		
//		if(from==to){
//			System.out.println("WTF!");
//		}
		
//		System.out.println("dist: "+from+" "+to);
		
//		return dist(subPaths, solution[index1], reversed[index1], solution[index2], reversed[index2]);
		return p.indexDistance(from,to);
	}
	
//	/**
//	 * takes two sub path indexes and if they are reversed or not, and returns the distance for the edge between them.
//	 * @param subPaths
//	 * @param sp1Index
//	 * @param reversed1
//	 * @param sp2Index
//	 * @param reversed2
//	 * @return
//	 */
//	public int dist(SubPath[] subPaths, int sp1Index, boolean reversed1, int sp2Index, boolean reversed2){
//		return p.indexDistance(
//				subPaths[sp1Index].getRightVerticeIndex(reversed1),
//				subPaths[sp2Index].getLeftVerticeIndex(reversed2)
//				);
//	}
//	
	
	
//	public int newEdgeDist(SubPath[] sub, int[] solution, boolean[] reversed){
//		return 0;
//	}
	
	
	
//	public class SubPathHolder{
//		Path p;
//		int[] subPaths;
//		final int length;
//		public SubPathHolder(Path p, int[] cuts){
//			this.p=p;
//			this.length=cuts.length;
//			
//			subPaths=new int[cuts.length];
//			for(int i=0, j=1;i<cuts.length;i++,j+=2){
//				subPaths[i]=cuts[i];
//				subPaths[j]=Help.circleIncrement(cuts[i], cuts.length);
//			}
//		}
//		
//		public int dist(int fromSubPath,int toSubPath){
//			
//		}
//		
//		public int length(){
//			return length;
//		}
//	}
	
	
}
