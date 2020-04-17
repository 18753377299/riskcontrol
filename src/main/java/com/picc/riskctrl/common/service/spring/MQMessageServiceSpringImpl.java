package com.picc.riskctrl.common.service.spring;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.picc.riskctrl.common.service.MQMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.*;
import java.util.Locale;
import java.util.ResourceBundle;

@Service("MQMessageService")
@Transactional
public class MQMessageServiceSpringImpl implements MQMessageService{
	
	public static final Logger LOGGER = LoggerFactory.getLogger("RISKCONTROLLOG");
	
	/**
	 * @功能：发送MQ消息
	 * @author 马军亮
	 * @param riskFileNo 风控单号
	 * @throws
	 * @日期：2018-07-09
	 */

	@Override
    public void sendMQMessage(String param) {
		
		ResourceBundle bundle = ResourceBundle.getBundle("config.sendMessage",
				Locale.getDefault());
		String userid = bundle.getString("userid");
		String password = bundle.getString("password");
		String hostName = bundle.getString("hostName");
		int port = Integer.parseInt(bundle.getString("port"));
		String channel = bundle.getString("channel");
		int CCSID = Integer.parseInt(bundle.getString("CCSID"));
		String queueManager = bundle.getString("queueManager");
		int transportType = Integer.parseInt(bundle.getString("transportType"));
		String queue = bundle.getString("queue");
		
//		String userid = "fcfk0000";
//		String password = "fcfk0000";
		
		//1、初始化连接工厂
		MQQueueConnectionFactory sendFactory = new MQQueueConnectionFactory();
		JMSContext context = null;
		try {
			sendFactory.setHostName(hostName);
			sendFactory.setPort(port);
			sendFactory.setChannel(channel);
			sendFactory.setCCSID(CCSID);
			sendFactory.setQueueManager(queueManager);
			sendFactory.setTransportType(transportType);
		
//			context= sendFactory.createContext(userid,password,Session.AUTO_ACKNOWLEDGE);
			context= sendFactory.createContext(userid,password,Session.SESSION_TRANSACTED);
			Destination dest = context.createQueue(queue);
//			for(int i = 0; i<10; i++) {
				TextMessage message = context.createTextMessage(param);
				context.createProducer().send(dest, message);
				System.out.println("========  send success! =========   "+ message);
//			}
			context.commit();
		} catch (JMSException e) {
			LOGGER.info("发送MQ消息异常：" + e.getMessage() ,e);
			e.printStackTrace();
			throw new RuntimeException("发送MQ消息异常:"+e);
		} 
		finally {
			if(null != context) {
				context.close();
			}
		}
	
		
	}
	
}
