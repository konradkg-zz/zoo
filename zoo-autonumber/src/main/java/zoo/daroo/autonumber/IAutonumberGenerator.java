package zoo.daroo.autonumber;

public interface IAutonumberGenerator<T extends Number> {
	public T getAutoId(int node, int range);
	public T getAutoId(int node);
	public T getCachedAutoId(int node);
	
	public int setCachedAutoIdRange(int node, int range);
	
	
}
