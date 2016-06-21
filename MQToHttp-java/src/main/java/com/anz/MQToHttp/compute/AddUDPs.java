/**
 * 
 */
package com.anz.MQToHttp.compute;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;

import com.anz.MQToHttp.transform.PreTransformBLSample;

import com.anz.common.cache.impl.CacheHandlerFactory;
import com.anz.common.compute.TransformType;
import com.anz.common.compute.impl.CommonJavaCompute;
import com.anz.common.transform.ITransformer;
import com.ibm.broker.config.proxy.BrokerProxy;
import com.ibm.broker.config.proxy.ExecutionGroupProxy;
import com.ibm.broker.config.proxy.MessageFlowProxy;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.config.proxy.AttributeConstants;

/**
 * @author sanketsw & psamon
 *
 */
public class AddUDPs extends CommonJavaCompute {
	
	private static final Logger logger = LogManager.getLogger();

	/* (non-Javadoc)
	 * @see com.anz.common.compute.impl.CommonJsonJsonTransformCompute#getTransformer()
	 */
	@Override
	public void execute(MbMessageAssembly inAssembly,
			MbMessageAssembly outAssembly) throws Exception {
		
		logger.info("AddUDPs:execute()");
		
		// Create LocalEnvironment/Destination
		MbElement destination = outAssembly.getLocalEnvironment().getRootElement()
				.createElementAsFirstChild(MbElement.TYPE_NAME_VALUE, "Destination","");
				
		// Create LocalEnvironement/Destination/HTTP
		MbElement http = destination.createElementAsFirstChild(MbElement.TYPE_NAME_VALUE, "HTTP","");
		
		// Set HTTP Method
		MbElement requestMethod = http.createElementAsFirstChild(MbElement.TYPE_NAME_VALUE, "RequestLine", "")		
				.createElementAsFirstChild(MbElement.TYPE_NAME_VALUE, "Method", "")	;
		requestMethod.setValue(getUserDefinedAttribute("HTTP_METHOD"));	
		
		// Set HTTP URL
		MbElement requestURL = http.createElementAsFirstChild(MbElement.TYPE_NAME_VALUE, "RequestURL", "");
		requestURL.setValue(getUserDefinedAttribute("HTTP_URL"));
	
		// Get replyToQ
		MbElement replyToQ = outAssembly.getMessage().getRootElement().getFirstElementByPath("/MQMD/ReplyToQ");
		
		if(replyToQ == null || replyToQ.getValueAsString().trim().isEmpty()){
			
			replyToQ.setValue(getUserDefinedAttribute("OUTPUT_QUEUE"));
			
		}
		
		// Get replyToQMgr
		MbElement replyToQMgr = outAssembly.getMessage().getRootElement().getFirstElementByPath("/MQMD/ReplyToQMgr");
		
		if(replyToQMgr == null || replyToQMgr.getValueAsString().trim().isEmpty()){
			
			replyToQMgr.setValue(getUserDefinedAttribute("OUTPUT_QUEUE_MGR"));
			
		}		
		
		// Create Content-type HTTP Input Header
		MbElement contentType = outAssembly.getMessage().getRootElement()
				.getFirstElementByPath("/BLOB")
				.createElementBefore(MbElement.TYPE_NAME_VALUE, "HTTPInputHeader", "")
				.createElementAsFirstChild(MbElement.TYPE_NAME_VALUE, "Content-Type", "");
		contentType.setValue("application/json");
		
		// Create Transaction ID HTTP Input Header
		MbElement transId = outAssembly.getMessage().getRootElement()
				.getFirstElementByPath("/BLOB")
				.createElementBefore(MbElement.TYPE_NAME_VALUE, "HTTPInputHeader", "")
				.createElementAsFirstChild(MbElement.TYPE_NAME_VALUE, "Transaction-Id", "");
		
		// Get Message Id
		MbElement msgId = outAssembly.getMessage().getRootElement()
				.getFirstElementByPath("/MQMD/MsgId");
		
		logger.info("msgId value = {}, string = {}", msgId.getValue(), msgId.getValueAsString());
		
		// Set transaction Id to Message Id
		transId.setValue(msgId.getValueAsString());
		
		logger.info("transId = {}", transId.getValue());
		
		logger.info("\"region\":\"AU\"");
				
	}

	@Override
	public TransformType getTransformationType() {
		// TODO Auto-generated method stub
		return TransformType.MQ_MQ;
	}
}
