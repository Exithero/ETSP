
public class Help {
	public Help(){
		
	}
	
	/**
	 * reverse, even if the interval goes over the edge
	 * @param arr
	 * @param from
	 * @param to
	 */
	public static void reverse(int[] arr, int from, int to){
		//determine the number of swaps
		int iters=0;
		if(from>to){
			iters=((arr.length-from)+to+1)/2;
		} else {
			iters=(to-from+1)/2;
		}
		//do the swaps
		while(iters>0){
			swap(arr,from,to);
			iters--;
			if(++from>=arr.length){ from=0; }//increment with wraparound
			if(--to<0){ to=arr.length-1; }//decrement with wraparound
		}
	}
	
	public static void swap(int[] arr, int from, int to){
		int tmp=arr[from];
		arr[from]=arr[to];
		arr[to]=tmp;
	}
	
	

}
