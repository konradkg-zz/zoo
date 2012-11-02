package zoo.daroo.csv.nio;

public interface FieldResultHandler<T> {
	
	public boolean onResult(T result);
}
