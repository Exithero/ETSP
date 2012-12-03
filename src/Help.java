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
	
	public void insert(int[]arr,int fromIndex, int toIndex){
		return;
		
	}
	
	public static int iters(int from, int to, int n){
		int iters=0;
		if(from>to){
			iters=((n-from)+to)+1;
		} else {
			iters=(to-from)+1;
		}
		return iters;
	}
	
	public static void moveSubArray(int[] arr, int from, int to, int offset){
		int iters=0;
		if(from>to){
			iters=((arr.length-from)+to)+1;
		} else {
			iters=(to-from)+1;
		}
		if(offset==1){
			int previous=circleIncrement(to, arr.length);
			for(int i=0;i<iters;i++){
				swap(arr,to,previous);
				previous=to;
				to=circleDecrement(to,arr.length);
			}
		}
		if(offset==-1){
			int previous=circleDecrement(from, arr.length);
			for(int i=0;i<iters;i++){
				swap(arr,from,previous);
				previous=from;
				from=circleIncrement(from,arr.length);
			}
		}
	}
	
//	public void moveSubArray(int[] arr, int fromIndex, int toIndex, int offset){
//		if(offset==1){
//			int tmp=arr[toIndex+offset];
//			
//		}
//		if(offset==-1){
//			
//		}
//		replace=arr[fromIndex];
//		for()
//	}
//	
//	public void shiftSubArray(int[] arr, int from, int to, int offset){
//		int iters=0;
//		if(from>to){
//			iters=((arr.length-from)+to);
//		} else {
//			iters=(to-from);
//		}
//		if(offset>0){
//			for(int i=0;i<iters;i++){
//				arr[to+offset]=arr[to];
//				to = circleDecrement(to, arr.length);
//			}
//		} else {
//			for(int i=0;i<iters;i++){
//				arr[from+offset]=arr[from];
//				from = circleIncrement(from, arr.length);
//			}
//		}
//		
//	}
	
	public static int circleIncrement(int x, int n){
		x++;
		if(x>=n){ x=0; }
		return x;
	}
	
	public static int circleDecrement(int x, int n){
		x--;
		if(x<0){ x=n-1; }
		return x;
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
