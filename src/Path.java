import java.util.Arrays;


public class Path {
	int[] path=null;
	UndirectedGraph ug;
	
	public Path(UndirectedGraph ug){
		this.ug=ug;
	}
	
	public void setPath(int[] path){
		this.path=path;
	}
	/**
	 * direction should be +1 or -1, this is not checked
	 * @param vertice
	 * @param direction
	 * @return
	 */
	public int pathDistDirection(int vertice, int direction){
		//it dosen't matter in this case which is to and which is from because 
		//the graph is undirected
		int a = path[vertice];
		int b = path[circleIndex(vertice + direction, path.length)];
		return ug.dist(a,b);
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

	
}
