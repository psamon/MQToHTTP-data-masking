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
public class OrganiseHeaders extends CommonJavaCompute {
	
	private static final Logger logger = LogManager.getLogger();

	/* (non-Javadoc)
	 * @see com.anz.common.compute.impl.CommonJsonJsonTransformCompute#getTransformer()
	 */
	@Override
	public void execute(MbMessageAssembly inAssembly,
			MbMessageAssembly outAssembly) throws Exception {
		
		logger.info("ShuffleHeaders:execute()");
		
		// Get Message
		MbElement message = outAssembly.getMessage().getRootElement();
		
		// Get HTTPResponseHeader
		MbElement response = message.getFirstElementByPath("/HTTPResponseHeader");
		
		// Create HTTPResponseHeader before BLOB element
		MbElement newResponse = message.getLastChild().createElementBefore(MbElement.TYPE_NAME_VALUE, "HTTPResponseHeader","");
		
		newResponse.copyElementTree(response);
		
		// Detach and delete original HTTPResponseHeader
		response.delete();
		
	}

	@Override
	public TransformType getTransformationType() {
		// TODO Auto-generated method stub
		return TransformType.MQ_MQ;
	}
}
