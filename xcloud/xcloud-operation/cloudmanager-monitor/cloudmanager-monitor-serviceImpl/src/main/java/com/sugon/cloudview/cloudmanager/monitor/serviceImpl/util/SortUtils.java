/**
 * 
 */
package com.sugon.cloudview.cloudmanager.monitor.serviceImpl.util;

public class SortUtils implements Comparable<SortUtils>{
	int index;
	double value;
	String other;
	
	/**
	 * 
	 */
	public SortUtils(int index,double value) {
		// TODO Auto-generated constructor stub
		this.index = index;
		this.value = value;
	}
	
	public SortUtils(double value,String other) {
		// TODO Auto-generated constructor stub
		this.value = value;
		this.other = other;
	}
	
	/**
	 * @return the other
	 */
	public String getOther() {
		return other;
	}
	/**
	 * @param other the other to set
	 */
	public void setOther(String other) {
		this.other = other;
	}
		
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	@Override
	public int compareTo(SortUtils o) {
		// TODO Auto-generated method stub
		if(Math.abs(this.value - o.value) < 0.0001){
			return 0;
		}else if(this.value > o.value){
			return -1;
		}
		return 1;
	}

}
