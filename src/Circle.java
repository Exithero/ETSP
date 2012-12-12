
/**
 * This is a modulo circle object that can be used for positive and negative modulo.
 * It's advantageous to use this when the length will be constant,
 * because then the length only has to be specified at construction time.
 * 
 * Also it contains some convenience functions that can be performed on
 * a modulo circle.
 * 
 * 2012-12-06
 * @author mbernt
 *
 */
public class Circle {
	private final int length;
	private final int maxIndex;
//	final int offset;
//	final int rotation;
	
	/**
	 * Constructs a modulu circle with the given length/size
	 * @param length
	 * @throws Exception 
	 */
	public Circle(final int length) throws Exception{
		if(length<1){
			throw new Exception("length must be greater than 0");
		}
		this.length=length;
		this.maxIndex=length-1;
	}
	
	/**
	 * returns the length of the circle.
	 * @return
	 */
	public int length(){
		return length;
	}
	
	/**
	 * returns the maxmum index of the circle,
	 * which is length -1
	 * @return
	 */
	public int maxIndex(){
		return maxIndex;
	}
	
	/**
	 * Treats the index as if it's on a circular array,
	 * so that if the index is negative it continues left
	 * from the end of the array and it continues right if
	 * the index is to large.
	 * @param index
	 * @return
	 */
	public int map(final int index){
		return mod2(index, length);
	}
	
	/**
	 * same as map but only works for numbers between
	 * -length<=index<2*length
	 * This is not checked so
	 * @param index
	 * @return
	 */
	public int unsafeMap(final int index){
		return circleIndex(index, length);
	}
	
	/**
	 * Returns the number of steps to go from fromIndex to toIndex,
	 * but from and to but are not allowed to be
	 * out of bounds (but this isn't checked).
	 * @param fromIndex
	 * @param toIndex
	 * @return
	 */
	public int circleDist(final int fromIndex ,final int toIndex){
		return distBetween(fromIndex, toIndex, length);
	}
	
	/**
	 * returns the incremented value, but index are not allowed to be
	 * out of bounds (but this isn't checked).
	 * @param index
	 * @return
	 */
	public int increment(final int index){
		return circleIncrement(index, this.maxIndex);
	}
	
	/**
	 * returns the decremented value, but index are not allowed to be
	 * out of bounds (but this isn't checked).
	 * @param index
	 * @return
	 */
	public int decrement(final int index){
		return circleDecrement(index, this.maxIndex);
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
	private static int circleIndex(final int index, final int length){
		if(index<0){//negative
			return length+index;
		} else if(index>=length){//to large
			return index-length;
		} else {//within array
			return index;
		}
	}
	
	private static int distBetween(final int from, final int to, final int length){
		if(from>to){
			return ((length-from)+to)+1;
		} else {
			return (to-from)+1;
		}
	}
	
	/**
	 * only works if the number isn't out of bounds
	 * @param x
	 * @param maxIndex
	 * @return
	 */
	public static int circleIncrement(final int x, final int maxIndex){
		if(x<maxIndex){
			return x+1;
		} else {
			return 0;
		}
	}
	
	/**
	 * only works if the number isn't out of bounds
	 * @param x
	 * @param maxIndex
	 * @return
	 */
	public static int circleDecrement(final int x, final int maxIndex){
		if(x>0){
			return x-1;
		} else {
			return maxIndex;
		}
	}
	
	//FROM INTERNET
	private static int mod2(int x,int n){
		int r = x % n;
		if (r < 0)
		{
			r += n;
		}
		return r;
	}
	
	
}
