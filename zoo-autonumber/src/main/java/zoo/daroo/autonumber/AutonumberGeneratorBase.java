package zoo.daroo.autonumber;

public abstract class AutonumberGeneratorBase<T extends Number> implements IAutonumberGenerator<T> {

	@Override
	public T getAutoId(int node, int range) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getCachedAutoId(int node, int range) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getCachedAutoId(int node) {
		return getCachedAutoId(node, 1);
	}
	
	@Override
	public T getAutoId(int node) {
		return getAutoId(node, 1);
	}
}
