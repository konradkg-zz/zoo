package zoo.jersey.jetty;

import java.lang.management.ManagementFactory;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.Metric;
import com.yammer.metrics.core.MetricName;
import com.yammer.metrics.core.MetricsRegistryListener;

//http://docs.oracle.com/javase/1.5.0/docs/guide/management/mxbeans.html
public class JMX implements NotificationListener, NotificationFilter, MetricsRegistryListener {
	Log log = LogFactory.getLog(getClass());
	
	final MBeanServer platformMBeanServer;
	
	public JMX() throws Exception {
		this.platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
		Metrics.defaultRegistry().addListener(this);
		
		//ObjectName name = new ObjectName("zoo.jersey.jetty.rest:type=TestGetDetailsResource,name=get");
//		ObjectName name = new ObjectName("zoo.jersey.jetty.rest:type=TestGetDetailsResource");
//		platformMBeanServer.addNotificationListener(name, this, this, "handback");
		//platformMBeanServer.
	}
	//zoo.jersey.jetty.rest:type=TestGetDetailsResource,name=get

	public void test() throws Exception{
		
		
		String[] domains = platformMBeanServer.getDomains();
		ObjectName name = new ObjectName("zoo.jersey.jetty.rest:type=TestGetDetailsResource,name=get");
//		MBeanInfo info = platformMBeanServer.getMBeanInfo(name);
//		ObjectInstance instance = platformMBeanServer.getObjectInstance(name);
		//
		
		domains.toString();
	}
	
	@Override
	public void handleNotification(Notification notification, Object handback) {
		log.info("notification: " + notification);
	}

	@Override
	public boolean isNotificationEnabled(Notification notification) {
		log.info("is enabled notification: " + notification);
		return true;
	}

	@Override
	public void onMetricAdded(MetricName name, Metric metric) {
		log.info("metrics added: " + name.getMBeanName() + ", name=" +name.getName() + ", type=" + name.getType() + ", scope=" + name.getScope() + ", group=" + name.getGroup());
		try {
			ObjectName on = new ObjectName(name.getMBeanName());
			MBeanInfo info = platformMBeanServer.getMBeanInfo(on);
			MBeanAttributeInfo[] attr = info.getAttributes();
			for(MBeanAttributeInfo ai : attr) {
				Object value = platformMBeanServer.getAttribute(on, ai.getName());
				log.info("Name: " + ai.getName() + ", type: " +ai.getType() + ", desc: " + ai.getDescription() 
						+ ", value: " + value);
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	@Override
	public void onMetricRemoved(MetricName name) {
		log.info("metrics removed: " + name.getMBeanName() );
		
		
	}

}
