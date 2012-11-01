package zoo.jersey.jetty.rest.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="resp")
public class SimpleResponse {
	@XmlElement
	private String text;

//	public String getText() {
//		return text;
//	}

	public void setText(String text) {
		this.text = text;
	}
	
	

}
