public class EdgeWithFlow extends Edge {

	public int flow;
	public int capacity;
	public EdgeWithFlow reverse;
	
	public EdgeWithFlow(int x, int y, int capacity) {
		super(x, y);
		this.capacity = capacity;
		this.flow = 0;
	}
	
	public int residual() {
		return capacity - flow;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.from).append(" ").append(this.to).append(" ")
		.append(this.flow);
		return builder.toString();
	}
	
	
}
