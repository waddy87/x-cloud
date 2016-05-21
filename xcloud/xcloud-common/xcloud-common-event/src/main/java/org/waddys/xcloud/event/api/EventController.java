package org.waddys.xcloud.event.api;

import java.util.List;

import org.waddys.xcloud.event.type.RecvFaultException;
import org.waddys.xcloud.event.type.SendRefuseException;

public interface EventController {
	
	/**
	 * 启动控制器，启动监听功能
	 */
	public void start();
	
	
	/**
	 * 停止控制器，停止监听功能
	 */
	public void stop();
	
	
	/**
	 * 消息发送接口
	 * 	@param routeKey
	 * @param eventContent
	 * @throws SendRefuseException
	 */
	void send(String routeKey, Object eventContent) throws SendRefuseException;
	
	
	/**
	 * 消息同步接收，不阻塞，直接返回
	 * 如果需要阻塞，自行设置轮询
	 * 如果queueName不存在，抛异常
	 * @param queueName
	 * @return 返回接收到的消息
	 * @throws RecvFaultException
	 */
	public Object recv(String queueName) throws RecvFaultException;
	
	
	/**
	 * 绑定消费程序到对应的exchange和queue
	 * @param queueName         要监听的队列
	 * @param eventProcesser    消息处理过程
	 * @return
	 */
	public EventController add(String queueName, 
			EventProcesser eventProcesser);
	
	
	/**
	 * 批量操作：绑定消费程序到对应的exchange和queue
	 * @param List<String> queueNames 要监听的队列
	 * @param eventProcesser                消息处理过程
	 */
	public EventController add(List<String> queueNames, 
			EventProcesser eventProcesser);
	
}
