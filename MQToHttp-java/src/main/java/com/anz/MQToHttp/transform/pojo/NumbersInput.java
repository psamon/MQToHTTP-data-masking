package com.anz.MQToHttp.transform.pojo;

import com.anz.common.transform.ITransformPojo;

/**
 * @author sanketsw
 *
 */
public class NumbersInput implements ITransformPojo {
	
	int left;
	
	int right;
	
	int sum;
	
	/**
	 * @return the left
	 */
	public int getLeft() {
		return left;
	}
	/**
	 * @param left the left to set
	 */
	public void setLeft(int left) {
		this.left = left;
	}
	/**
	 * @return the right
	 */
	public int getRight() {
		return right;
	}
	/**
	 * @param right the right to set
	 */
	public void setRight(int right) {
		this.right = right;
	}
	/**
	 * @return the sum
	 */
	public int getSum() {
		return right;
	}
	/**
	 * @param sum the sum to set
	 */
	public void setSum(int right) {
		this.sum = sum;
	}
	
	

}
