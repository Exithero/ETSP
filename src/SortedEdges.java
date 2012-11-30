import java.util.Arrays;


public class SortedEdges {
	SortableVertice[][] sortedDistances;
//	int[][] sortedDistances;
	UndirectedGraph ug;
	
	public SortedEdges(	UndirectedGraph ug){
		this.ug=ug;
		this.sortedDistances=makeSortedDistances(ug);
	}
	
	
	public SortableVertice[][] makeSortedDistances(UndirectedGraph ug){
		SortableVertice[][] sorted= new SortableVertice[ug.vertices][ug.vertices];	
		for(int from=0; from<ug.vertices;from++){
			for(int to=0; to<ug.vertices;to++){
				sorted[from][to]=new SortableVertice(to,ug.dist(from,to));
			}
			Arrays.sort(sorted[from]);
		}
		return sorted;
	}
	
//	/**
//	 * calculates the nearest neighbor matrix
//	 * 
//	 * matrix[from][neighBorAtSortPos]
//	 * @param ug
//	 * @return
//	 */
//	public int[][] makeSortedDistances(UndirectedGraph ug){
//		//used to sort based on distance but still having the to vertice nr
//		SortableVertice[] tmpTo= new SortableVertice[ug.vertices];
//		//used to hold the things
//		int[][] res=new int[ug.vertices][ug.vertices];
//		
//		//calculate the nearest neighbor matrix.
//		for(int fromVertice=0; fromVertice<ug.vertices;fromVertice++){
//			//get sorted by distance
//			for(int toVertice=0; toVertice<ug.vertices;toVertice++){
//				tmpTo[toVertice]=new SortableVertice(toVertice,ug.dist(fromVertice,toVertice));
//			}
//			Arrays.sort(tmpTo);
//			//retrieve the to vertices and place them in the sorted order
//			for(int index=0; index<ug.vertices;index++){
//				res[fromVertice][index]=tmpTo[index].to;
//			}
//		}
//		return res;
//	}

	
	public int getXNeighbor(int from, int index){
		return sortedDistances[from][index].to;
	}
	
//	/**
//	 * get the x-nearest neighbor
//	 * @param fromVertice
//	 * @param x
//	 * @return
//	 */
//	public int getXNeighbor(final int fromVertice, final int x){
//		return sortedDistances[fromVertice][x];
//	}

}
