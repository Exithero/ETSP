
/**
	 * should probably be returned from Path.
	 * Contains the information necessary to define a subPath
	 * @author mbernt
	 *
	 */
	public class SubPath {
		private Path p;
		int left;
		int right;
		
		public SubPath(Path p, int left, int right){
			this.p=p;
			this.left=left;
			this.right=right;
		}
		
		public String pathString(){
			StringBuilder sb=new StringBuilder("[");
			for(int i=left;;i=Help.circleIncrement(i, p.length())){
				sb.append(p.getVertice(i));
				if(i==right){
					break;
				}
				sb.append(", ");
			}
			sb.append("]");
			return sb.toString();
		}
		
		public String toString(){
			return "subPath: "+left+" "+right+" "+this.pathString();
		}
		
		public int getRightVerticeIndex(boolean reversed){
			if(reversed){
				return left;
			} else {
				return right;
			}
		}
		
		public int getLeftVerticeIndex(boolean reversed){
			if(reversed){
				return right;
			} else {
				return left;
			}
		}
		
		public int length(){
			return Help.iters(left, right, p.length());
		}
	}
