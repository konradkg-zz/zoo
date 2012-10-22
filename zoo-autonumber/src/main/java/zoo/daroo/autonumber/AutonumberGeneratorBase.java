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
				long lastId = last;
				long nextId = current.incrementAndGet();
				if (nextId <= lastId)
					return convertToTargetType(nextId);
				
				r.unlock();
				w.lock();
				try {
					lastId = last;
					nextId = current.incrementAndGet();
					if (nextId <= lastId)
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
	
	/**
	 * class CachedData {
   Object data;
   volatile boolean cacheValid;
   ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

   void processCachedData() {
     rwl.readLock().lock();
     if (!cacheValid) {
        // Must release read lock before acquiring write lock
        rwl.readLock().unlock();
        rwl.writeLock().lock();
        // Recheck state because another thread might have acquired
        //   write lock and changed state before we did.
        if (!cacheValid) {
          data = ...
          cacheValid = true;
        }
        // Downgrade by acquiring read lock before releasing write lock
        rwl.readLock().lock();
        rwl.writeLock().unlock(); // Unlock write, still hold read
     }

     use(data);
     rwl.readLock().unlock();
   }
 }
 
ReentrantReadWriteLocks can be used to improve concurrency in some uses of some kinds of Collections. This is typically worthwhile only when the collections are expected to be large, accessed by more reader threads than writer threads, and entail operations with overhead that outweighs synchronization overhead. For example, here is a class using a TreeMap that is expected to be large and concurrently accessed. 

 class RWDictionary {
    private final Map m = new TreeMap();
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();

    public Data get(String key) {
        r.lock();
        try { return m.get(key); }
        finally { r.unlock(); }
    }
    public String[] allKeys() {
        r.lock();
        try { return m.keySet().toArray(); }
        finally { r.unlock(); }
    }
    public Data put(String key, Data value) {
        w.lock();
        try { return m.put(key, value); }
        finally { w.unlock(); }
    }
    public void clear() {
        w.lock();
        try { m.clear(); }
        finally { w.unlock(); }
    }
 }}

	 * @author daru
	 *
	 */

	/*private class AutoId {
		private final int range;
		private final int node;

		private final AtomicLong current = new AtomicLong();
		private final AtomicLong last = new AtomicLong(Long.MIN_VALUE);

		//private final AtomicBoolean updating = new AtomicBoolean(false);

		AutoId(int node, int range) {
			this.node = node;
			this.range = range;
		}

		public T getNextId() {
			long lastId = last.get();
			long nextId = current.incrementAndGet();

			if (nextId <= lastId)
				return convertToTargetType(nextId);

			synchronized (this) {
				if (last.compareAndSet(lastId, lastId)) {
					final T externalNextId = getAutoId0(node, range);
					nextId = externalNextId.longValue();
					current.set(nextId);
					last.set(nextId + range - 1);
					return externalNextId;
				}
			}

			return getNextId();
		}

		public int getRange() {
			return range;
		}
	}*/
}
