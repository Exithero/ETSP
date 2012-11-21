import java.util.Arrays;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		System.out.println("HEJ");
		int[] arr={1,2,3,4,5,6};
		int[] arr1;
		System.out.println(mod2(-9,3));
//		arr1=Arrays.copyOf(arr, arr.length);
//		reverse(arr1,3,1);
//		System.out.println(Arrays.toString(arr1));
//		
//		arr1=Arrays.copyOf(arr, arr.length);
//		reverse(arr1,1,3);
//		System.out.println(Arrays.toString(arr1));
//		
//		arr1=Arrays.copyOf(arr, arr.length);
//		reverse(arr1,1,2);
//		System.out.println(Arrays.toString(arr1));
		
//		arr1=Arrays.copyOf(arr, arr.length);
//		reverse(arr1,1,0);
//		System.out.println(Arrays.toString(arr1) +" "+1+" "+0);
		
//		for(int from=0; from < arr.length; from++){
//			for(int to=0;to < arr.length; to++){
//				arr1=Arrays.copyOf(arr, arr.length);
//				reverse(arr1,from,to);
//				System.out.println(Arrays.toString(arr1) +" "+from+" "+to);
//			}
//		}
	}
	
	/**
	 * reverse, even if the interval goes over the edge
	 * @param arr
	 * @param from
	 * @param to
	 */
	public static void reverse(int[] arr, int from, int to){
		int iters=0;
		if(from>to){
			iters=((arr.length-from)+to+1)/2;
		} else {
			iters=(to-from+1)/2;
		}
		
		while(iters>0/*to!=from && to+1!=from && (to==0&&from==arr.length-1)*/){
			swap(arr,from,to);
			iters--;
			if(++from>=arr.length){ from=0; }
			if(--to<0){ to=arr.length-1; }
		}
	}
	public static int mod2(int x,int n){
		int r = x % n;
		if (r < 0)
		{
		    r += n;
		}
		return r;
	}
	
	
	
	public static void swap(int[] arr, int from, int to){
		int tmp=arr[from];
		arr[from]=arr[to];
		arr[to]=tmp;
	}

}
