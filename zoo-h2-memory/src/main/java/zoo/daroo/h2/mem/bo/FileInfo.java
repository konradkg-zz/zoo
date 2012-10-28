package zoo.daroo.h2.mem.bo;

import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

public class FileInfo {
	
	private Integer id;
	private String path;
	private long lastModifiedTime;
	private String lastModifiedTimeText;
	private long size;
	
	public static FileInfo createInstance(String path, BasicFileAttributes attributes) {
		final FileInfo result = new FileInfo();
		result.setPath(path);
		
		final FileTime lastModifiedTime = attributes.lastModifiedTime();
		result.setLastModifiedTime(lastModifiedTime.toMillis());
		result.setLastModifiedTimeText(lastModifiedTime.toString());
		result.setSize(attributes.size());
		return result;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(long lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public String getLastModifiedTimeText() {
		return lastModifiedTimeText;
	}

	public void setLastModifiedTimeText(String lastModifiedTimeText) {
		this.lastModifiedTimeText = lastModifiedTimeText;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
}
