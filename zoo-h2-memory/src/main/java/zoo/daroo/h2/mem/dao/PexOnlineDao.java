package zoo.daroo.h2.mem.dao;

import static zoo.daroo.h2.mem.dao.PexOnlineDao.BEAN_ID;

import javax.inject.Named;

import zoo.daroo.h2.mem.bo.PexOnline;

@Named(BEAN_ID)
public interface PexOnlineDao {
	
	public final static String BEAN_ID = "PexOnlineDao";

	public void insert(PexOnline bo);
	

}
