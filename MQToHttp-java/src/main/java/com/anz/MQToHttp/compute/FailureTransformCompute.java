/**
 * 
 */
package com.anz.MQToHttp.compute;

import com.anz.common.compute.TransformType;
import com.anz.common.compute.impl.CommonErrorTransformCompute;

/**
 * @author root
 *
 */
public class FailureTransformCompute extends CommonErrorTransformCompute {


	@Override
	public TransformType getTransformationType() {
		// TODO Auto-generated method stub
		return TransformType.MQ_HTTP;
	}

}
