import java.util.Arrays;


public class Path {
	int[] path=null;
	UndirectedGraph ug;
	
	public Path(UndirectedGraph ug){
		this.ug=ug;
	}
	
	public void insert(int fromIndex, int toIndex){
		if(fromIndex<toIndex){
			Help.moveSubArray(path, Help.circleIncrement(fromIndex, path.length), toIndex, -1);
		}
		if(fromIndex>toIndex){
			Help.moveSubArray(path, toIndex, Help.circleDecrement(fromIndex, path.length), 1);
		}
		
		return;
	}
	
	/**
	 * returns the improvment
	 * @param fromIndex
	 * @param toIndex
	 * @return
	 */
	public boolean isBetterToInsert(int fromIndex, int toIndex){
		int oldEdges=0;
		int newEdges=0;
		if(fromIndex>toIndex){
			oldEdges=easyDist(fromIndex, fromIndex-1) + easyDist(fromIndex,fromIndex+1) + easyDist(toIndex,toIndex-1);	
			newEdges=easyDist(fromIndex-1, fromIndex+1) + easyDist(toIndex-1,fromIndex) + easyDist(fromIndex,toIndex);
		}
		
		if(fromIndex<toIndex){
			oldEdges=easyDist(fromIndex, fromIndex-1) + easyDist(fromIndex,fromIndex+1) + easyDist(toIndex,toIndex+1);	
			newEdges=easyDist(fromIndex-1, fromIndex+1) + easyDist(toIndex,fromIndex) + easyDist(fromIndex,toIndex+1);
		}
//		System.out.println("insert edges "+ newEdges+" "+oldEdges);
		return (newEdges-oldEdges)<0;
		
	}
	
	
	
	
	
	public void setPath(int[] path){
		this.path=path;
	}
	/**
	 * direction should be +1 or -1, this is not checked
	 * @param verticeIndex
	 * @param direction
	 * @return
	 */
	public int pathDistDirection(int verticeIndex, int direction){
		//it dosen't matter in this case which is to and which is from because 
		//the graph is undirected
		int a = path[verticeIndex];
		int b = path[circleIndex(verticeIndex + direction, path.length)];
		return ug.dist(a,b);
	}
	
	/**
	 * calculates dist between vertice at index a and at index b,
	 * and it handles index out of bounds gracefully 
	 * (if the indexes are -this.length()<a<2*this.length()).
	 * @param verticeIndex
	 * @param direction
	 * @return
	 */
	public int easyDist(int verticeIndexA, int VerticeIndexB){
		//it dosen't matter in this case which is to and which is from because 
		//the graph is undirected
		int verticeA = this.getVertice(circleIndex(verticeIndexA, this.length()));
		int verticeB = this.getVertice(circleIndex(VerticeIndexB, this.length()));
//		a = this.getVertice(Help.mod2(a, path.length));
//		b = this.getVertice(Help.mod2(b, path.length));
		return ug.dist(verticeA,verticeB);
	}
	
	
	/**
	 * reverses a subs path in the path. 
	 * O(n)
	 * @param fromIndex
	 * @param toIndex
	 */
	public void reverseSubPath(int fromIndex, int toIndex){
		Help.reverse(path, fromIndex, toIndex);
	}
	/**
	 * can be used if you wan't negative indexes to wrap around to the end
	 * or to large indexes to wrap around to the beginning.
	 * 
	 * Warning: only works for interval index<length*2, and index>=-length
	 * 
	 * @param index
	 * @return
	 */
	private static int circleIndex(int index, int length){
		if(index<0){
			index=length+index;
		} else if(index>=length){
			index=index-length;
		}
		return index;
	}
	
	public int length(){
		return path.length;
	}
	public void swap(int from, int to){
		Help.swap(path, from, to);
	}
	
	public int indexDistance(int from,int to){
		return ug.dist(path[from], path[to]);
	}
	

	
	public int pathDistance(){
		return tourDistance(ug,path);
	}
	public int[] getPath(){
		return path;
	}
	public int[] getPathCopy(){
		return Arrays.copyOf(path, path.length);
	}
	private static int tourDistance(UndirectedGraph ug,int[] tour){
		int from=tour[0];
		int distance=0;
		for(int i=1;i<tour.length;i++){
			int to=tour[i];
			distance = distance+ug.dist(from,to);
			from=to;
		}
		distance=distance+ug.dist(tour[tour.length-1],tour[0]);
		return distance;
	}
	
	public int getVertice(int index){
		return path[index];
	}

	
}
