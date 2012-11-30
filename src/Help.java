import java.util.Random;


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
			//update indexes
			from++;
			to--;
			//check for wrap around
			if(from>=arr.length){ from=0; }//increment with wraparound
			if(to<0){ to=arr.length-1; }//decrement with wraparound
		}
	}
	
	public static void swap(int[] arr, int from, int to){
		int tmp=arr[from];
		arr[from]=arr[to];
		arr[to]=tmp;
	}

	//FROM INTERNET
	public static int mod2(int x,int n){
		int r = x % n;
		if (r < 0)
		{
			r += n;
		}
		return r;
	}
	public static int indexInArray(int[] arr,int number){
		for(int i=0;i<arr.length;i++){
			if(arr[i]==number){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * from
	 * http://www.codinghorror.com/blog/2007/12/the-danger-of-naivete.html
	 * @param arr
	 * @param rand
	 */
	public static void randomShuffle(int[] arr, Random rand){
		for(int i=arr.length-1; i>0;i--){
			Help.swap(arr,i,rand.nextInt(i+1));
		}
	}
	
	public static int[] indexFilledArray(int size){
		int[] arr=new int[size];
		for(int i=0;i<size;i++){
			arr[i]=i;
		}
		return arr;
	}
}
