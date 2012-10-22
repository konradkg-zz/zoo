package zoo.daroo.autonumber;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class AutonumberGeneratorBase<T extends Number> implements IAutonumberGenerator<T> {
	
	private ConcurrentMap<Integer, AutoId> nodesMap = new ConcurrentHashMap<Integer, AutoId>();

	@Override
	public T getCachedAutoId(int node, int range) {
		AutoId autoId = nodesMap.get(node);
		if(autoId != null)
			return autoId.getNextId();
		
		autoId = new AutoId(node, range);
		if(nodesMap.putIfAbsent(node, autoId) == null) {
			return autoId.getNextId();
		} else {
			return nodesMap.get(node).getNextId();
		}
	}

	@Override
	public T getAutoId(int node, int range) {
		return getAutoId0(node, range);
	}

	@Override
	public T getCachedAutoId(int node) {
		return getCachedAutoId(node, 1);
	}
	
	@Override
	public T getAutoId(int node) {
		return getAutoId(node, 1);
	}

	protected abstract T getAutoId0(int node, int range);
	protected abstract T convertToTargetType(Number number);
	
	
	private class AutoId {
		private final int range;
		private final int node;
		
		private final AtomicLong current = new AtomicLong();
		private final AtomicLong last = new AtomicLong(Long.MIN_VALUE);
		
		
		AutoId(int node, int range) {
			this.node = node;
			this.range = range;
		}
		
		public T getNextId() {
			long lastId = last.get();
			long nextId = current.incrementAndGet();
			if(nextId <= lastId)
				return convertToTargetType(nextId);
			
			synchronized(this) {
				if(last.compareAndSet(lastId, lastId)) {
					final T externalNextId = getAutoId0(node, range);
					nextId = externalNextId.longValue();
					current.set(nextId);
					last.set(nextId + range);
					return externalNextId;
				}
			}
			
			return getNextId();
		}
	}

}
