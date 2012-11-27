import java.util.Arrays;


public class SortedEdges {
//	SortableVertice[][] sortedDistances;
	int[][] sortedDistances;
	UndirectedGraph ug;
	
	public SortedEdges(	UndirectedGraph ug){
		this.ug=ug;
		this.sortedDistances=makeSortedDistances(ug);
	}
	
	
//	public SortableVertice[][] makeSortedDistances(UndirectedGraph ug){
//		SortableVertice[][] sorted= new SortableVertice[ug.vertices][ug.vertices];	
//		for(int i=0; i<ug.vertices;i++){
//			for(int j=0; j<ug.vertices;j++){
//				sorted[i][j]=new SortableVertice(j,ug.dist(i,j));
//			}
//			Arrays.sort(sorted[i]);
//			
//		}
//		return sorted;
//	}
	
	/**
	 * calculates the nearest neighbor matrix
	 * 
	 * matrix[from][neighBorAtSortPos]
	 * @param ug
	 * @return
	 */
	public int[][] makeSortedDistances(UndirectedGraph ug){
		//used to sort based on distance but still having the to vertice nr
		SortableVertice[] tmpTo= new SortableVertice[ug.vertices];
		//used to hold the things
		int[][] res=new int[ug.vertices][ug.vertices];
		
		//calculate the nearest neighbor matrix.
		for(int fromVertice=0; fromVertice<ug.vertices;fromVertice++){
			//get sorted by distance
			for(int toVertice=0; toVertice<ug.vertices;toVertice++){
				tmpTo[toVertice]=new SortableVertice(toVertice,ug.dist(fromVertice,toVertice));
			}
			Arrays.sort(tmpTo);
			//retrieve the to vertices and place them in the sorted order
			for(int index=0; index<ug.vertices;index++){
				res[fromVertice][index]=tmpTo[index].to;
			}
		}
		return res;
	}

	
//	public int getTovertice(int from, int index){
//		return sortedDistances[from][index].to;
//	}
	
	/**
	 * get the x-nearest neighbor
	 * @param fromVertice
	 * @param x
	 * @return
	 */
	public int getKNeighbor(final int fromVertice, final int x){
		return sortedDistances[fromVertice][x];
	}

}
