package zoo.daroo.autonumber;

public interface IAutonumberGenerator<T extends Number> {
	public T getAutoId(int node, int range);
	public T getAutoId(int node);
	public T getCachedAutoId(int node, int range);
	public T getCachedAutoId(int node);
	
}
