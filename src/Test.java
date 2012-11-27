import java.util.Arrays;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		System.out.println("HEJ");
//		int[] arr={1,2,3,4,5,6};
//		int[] arr1;
//		System.out.println(mod2(-9,3));
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
		
		Kattio io = new Kattio(System.in,System.out);
//		int N=io.getInt();
//		double[][] points=readPoints(io,N);
//		
//		int[][] dist1=calculateDistances(points,N);
//		int[][] dist2=calculateDistances2(points,N);
//		printArray(dist1);
//		printArray(dist2);
		
		printArray(readOtherData(io));
	}
	
	
	/**
	 * read data points from http://www.tsp.gatech.edu/vlsi/page5.html
	 * @param io
	 * @return
	 */
	public static double[][] readOtherData(Kattio io){
		String tmp;
		double[][] points=null;
		int N=0;
		
		//read until reached the EOF line
		while(!(tmp=io.readLine()).equals("EOF")){
//			System.out.println(tmp);
			String sarr[]=tmp.split(" ");
//			System.out.println(Arrays.toString(sarr));
			
			//try to read the dimension
			if(points==null && sarr[0].equals("DIMENSION")){
//				System.out.println(tmp);
//				System.out.println(Arrays.toString(sarr));
				N = Integer.parseInt(sarr[2]);
				points = new double[2][N];
			}
			
			//try to read
			if(points!=null){
				try{
					int a=Integer.parseInt(sarr[0]);
					int b=Integer.parseInt(sarr[1]);
					int c=Integer.parseInt(sarr[2]);
					points[0][a-1]=b;
					points[1][a-1]=c;
				} catch(Exception e){
					
				}
			}
		}
		return points;
	}
	
	public static void printArray(double[][] arr){
		System.out.println("[");
		for(int i=0;i<arr.length;i++){
			System.out.println(Arrays.toString(arr[i]));
		}
		System.out.println("]");
	}
	
	public static void printArray(int[][] arr){
		System.out.println("[");
		for(int i=0;i<arr.length;i++){
			System.out.println(Arrays.toString(arr[i]));
		}
		System.out.println("]");
	}
	
	public static double[][] readPoints(Kattio io, int N){
//		int N = io.getInt();
		double[][] points= new double[2][N];
		for(int i=0;i<N;i++){
			points[0][i]=io.getDouble();
			points[1][i]=io.getDouble();
		}
		return points;
	}
	
	private static int[][] calculateDistances(double[][] points,int N) {
		int[][] distanceMatrix= new int[N][N];
		for(int i=0; i<N;i++){
			for(int j=0;j<N;j++){
				distanceMatrix[i][j]=distance(points[0][i],points[0][j],points[1][i],points[1][j]);
			}
		}
		return distanceMatrix;
	}
	
	private static int[][] calculateDistances2(double[][] points,int N) {
		int[][] distanceMatrix= new int[N][N];
		for(int i=0; i<N;i++){
			for(int j=0;j<N;j++){
				if(j==i){
					distanceMatrix[i][j]=0;
				} else if(i<j){
					distanceMatrix[i][j]=distance(points[0][i],points[0][j],points[1][i],points[1][j]);
				} else {//i>j
					distanceMatrix[i][j]=distanceMatrix[j][i];
				}
				
			}
		}
		return distanceMatrix;
	}
	
	private static int distance(double x1, double x2,double y1,double y2){
		double dx= x1-x2;
		double dy= y1-y2;
		return (int)Math.round(Math.sqrt(dx*dx+dy*dy));
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
