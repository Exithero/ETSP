import java.util.Arrays;


public class SortedEdges {
	SortableVertice[][] sortedDistances;
	UndirectedGraph ug;
	public SortedEdges(	UndirectedGraph ug){
		this.ug=ug;
		this.sortedDistances=makeSortedDistances(ug);
	}
	public SortableVertice[][] makeSortedDistances(UndirectedGraph ug){
		SortableVertice[][] sorted= new SortableVertice[ug.vertices][ug.vertices];	
		for(int i=0; i<ug.vertices;i++){
			for(int j=0; j<ug.vertices;j++){
				sorted[i][j]=new SortableVertice(j,ug.dist(i,j));
			}
			Arrays.sort(sorted[i]);
		}
		return sorted;
	}

	
	public int getTovertice(int from, int index){
		return sortedDistances[from][index].to;
	}

}
