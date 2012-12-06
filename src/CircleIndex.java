/**
 * This class can be used as an index if the index is required to wrap around the edge of
 * for example an array.
 * 
 * example use
 * int[] arr={1,2,3,4,5}
 * CircleIndex ci=new CircleIndex(arr.length)
 * ci.set(5);
 * for(int i=0;i<arr.length;i++){
 * 	System.out.println(arr[ci.get()]);
 * 	ci.increment();
 * }
 * 
 * For maximum performance in nested loops it's recommended to construct the CircleIndex objects
 * before the loops, and setting the indexes just before entering the loop.
 * @author mbernt
 *
 */
public class CircleIndex {
	//the internal index
	int index;
	//the circle object, used to perform modulu operations.
	final Circle circle;
	
	//add a boolean allowing or disallowing unsafe operations?
	
	/**
	 * constructs a circle index with index=0 and a circle with length.
	 * @param startIndex
	 * @throws Exception If length is smaller than 1.
	 */
	public CircleIndex(int length) throws Exception{
		this.circle=new Circle(length);
		index=0;
//		this.set(startIndex);
//		index=c.map(startIndex);
	}
	
	/**
	 * constructs a circle index with the given startIndex and circle length.
	 * @param startIndex
	 * @throws Exception If length is smaller than 1.
	 */
	public CircleIndex(int startIndex, int length) throws Exception{
		this.circle=new Circle(length);
		this.set(startIndex);
//		index=c.map(startIndex);
	}
	
	/**
	 * constructs a circle index with the index=0 and the given circle.
	 * The index can later be set with the set method.
	 * @param startIndex
	 * @param circle
	 */
	public CircleIndex(Circle circle){
		this.circle=circle;
		index=0;
//		this.set(startIndex);
//		index=c.map(startIndex);
	}
	
	/**
	 * constructs a circle index with the given startIndex and circle.
	 * @param startIndex
	 * @param circle
	 */
	public CircleIndex(int startIndex, Circle circle){
		this.circle=circle;
		this.set(startIndex);
//		index=c.map(startIndex);
	}
	
	/**
	 * sets the index to the value and maps it unto the circle
	 * @param value The value that will be mapped to the circle.
	 * @return The new index.
	 */
	public int set(final int value){
		index=circle.map(value);
		return index;
	}
	
	/**
	 * sets the index to the value and then uses unsafeMap to map it unto the circle,
	 * this may leave the circle index in an inconsistent state.
	 * @param value The value that will be mapped to the circle.
	 * @return The new index.
	 */
	public int unsafeSet(final int value){
		index=circle.fastMap(value);
		return index;
	}
	
	/**
	 * returns the index
	 * @return The index.
	 */
	public int get(){
		return index;
	}
	
	/**
	 * returns the circle object in this circleIndex
	 * @return
	 */
	public Circle getCircle(){
		return this.circle;
	}
	
	/**
	 * increments the index.
	 * @return The new index.
	 */
	public int increment(){
		index=circle.increment(index);
		return index;
	}
	
	/**
	 * decrements the index
	 * @return The new index.
	 */
	public int decrement(){
		index=circle.decrement(index);
		return index;
	}
	
	/**
	 * adds the value to the index and maps it unto the circle.
	 * @param value The value that will be added to the index.
	 * @return The new index.
	 */
	public int add(final int value){
		index=circle.map(index+value);
		return index;
	}
	
	/**
	 * adds the value to the index and then uses fastMap to map it unto the circle,
	 * this may leave the circle index in an inconsistent state.
	 * 
	 * The value can be a negative value.
	 * @param value The value that will be added to the index.
	 * @return The new index.
	 */
	public int unsafeAdd(final int value){
		index=circle.fastMap(index+value);
		return index;
	}
	
	/**
	 * returns true if they have compatible circles, they are compatible if they
	 * have the same length.
	 * @param ci
	 * @return
	 */
	private boolean hasCompatibleCircle(final CircleIndex ci){
		return ci.getCircle().length()==this.getCircle().length();
	}
	
	/**
	 * BONUS
	 * 
	 * returns the number of iterations this circleIndex has to make to become equal,
	 * but throws an exception if their circles aren't compatible.
	 * to ci.
	 * @param ci
	 * @return
	 * @throws Exception 
	 */
	public int distTo(final CircleIndex ci, final boolean safe) throws Exception{
		if(safe || hasCompatibleCircle(ci)){
			return circle.circleDist(this.get(), ci.get());
		} else {
			throw new Exception("Incompatible circles");
		}
	}
}
