/**
 * 
 */
package com.anz.MQToHttp.transform;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;

import com.anz.MQToHttp.transform.pojo.NumbersInput;
import com.anz.MQToHttp.transform.pojo.Result;

import com.anz.common.compute.ComputeInfo;
import com.anz.common.transform.ITransformer;
import com.anz.common.transform.TransformUtils;

import com.anz.common.cache.impl.CacheHandlerFactory;

import com.anz.common.dataaccess.models.iib.IFXCode;
import com.anz.common.dataaccess.models.iib.Operation;
import com.anz.common.domain.IFXCodeDomain;
import com.anz.common.domain.OperationDomain;
import com.anz.common.transform.ITransformer;
import com.anz.common.transform.TransformUtils;



/**
 * @author sanketsw
 * 
 */
public class PostTransformBLSample implements ITransformer<String, String> {

	private static final Logger logger = LogManager.getLogger();
	
	/* (non-Javadoc)
	 * @see com.anz.common.transform.IJsonJsonTransformer#execute(java.lang.String)
	 */
	
	
	public String execute(String inputJson, Logger appLogger, ComputeInfo metadata) throws Exception {
		Result json = (Result) TransformUtils.fromJSON(inputJson,
				Result.class);
		
		json.setComment("The sum of left and right is " + json.getResult() + " .");
		
		// Add 100 to result
		Integer newResult = Integer.parseInt(json.getResult()) + 100;
		
		json.setResult(newResult.toString());
		
		logger.info("result = {}", json.getResult());
		
		json.setComment(json.getComment() + " The result plus 100 is " + json.getResult() + ".");
		
		logger.info("comment: {}", json.getComment());
		
		
		String out = TransformUtils.toJSON(json);
		return out;
	}
}