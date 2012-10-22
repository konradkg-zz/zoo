package zoo.daroo.autonumber;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class AutonumberGeneratorBase<T extends Number> implements IAutonumberGenerator<T> {

	private ConcurrentMap<Integer, AutoId> nodesMap = new ConcurrentHashMap<Integer, AutoId>();
	private final static int DEFAULT_CACHED_RANGE = 10;

	@Override
	public T getCachedAutoId(int node) {
		return getCachedAutoIdInternal(node, DEFAULT_CACHED_RANGE).getNextId();
	}

	@Override
	public int setCachedAutoIdRange(int node, int range) {
		final AutoId autoId = getCachedAutoIdInternal(node, range);
		return autoId.getRange();
	}

	private AutoId getCachedAutoIdInternal(int node, int range) {
		AutoId autoId = nodesMap.get(node);
		if (autoId != null)
			return autoId;

		autoId = new AutoId(node, range);
		if (nodesMap.putIfAbsent(node, autoId) == null) {
			return autoId;
		} else {
			return nodesMap.get(node);
		}
	}

	@Override
	public T getAutoId(int node, int range) {
		return getAutoId0(node, range);
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
		private volatile long last = Long.MIN_VALUE;
		
		private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
		private final Lock r = rwl.readLock();
	    private final Lock w = rwl.writeLock();

		AutoId(int node, int range) {
			this.node = node;
			this.range = range;
		}

		public T getNextId() {
			r.lock();
			try {
				long nextId = current.incrementAndGet();
				if (nextId <= last)
					return convertToTargetType(nextId);
				
				r.unlock();
				w.lock();
				try {
					nextId = current.incrementAndGet();
					if (nextId <= last)
						return convertToTargetType(nextId);
					
					final T externalNextId = getAutoId0(node, range);
					nextId = externalNextId.longValue();
					current.set(nextId);
					last = nextId + range - 1;
					return externalNextId;
				} finally {
					r.lock();
					w.unlock();
				}
				
			} finally {
				r.unlock();
			}
		}

		public int getRange() {
			return range;
		}
	}
}
