package com.lujinhong.commons.storm.tridentstate;

import java.io.Serializable;

public class DATA implements Serializable  {

	private static final long serialVersionUID = -7969344906806994003L;
	public long getRx_pkgs() {
		return Rx_pkgs;
	}
	public void setRx_pkgs(long rx_pkgs) {
		Rx_pkgs = rx_pkgs;
	}
	public long getRx_bytes() {
		return Rx_bytes;
	}
	public void setRx_bytes(long rx_bytes) {
		Rx_bytes = rx_bytes;
	}
	public long getTx_pkgs() {
		return Tx_pkgs;
	}
	public void setTx_pkgs(long tx_pkgs) {
		Tx_pkgs = tx_pkgs;
	}
	public long getTx_bytes() {
		return Tx_bytes;
	}
	public void setTx_bytes(long tx_bytes) {
		Tx_bytes = tx_bytes;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public void Add(DATA datanew){
		Rx_bytes=this.getRx_bytes()+datanew.getRx_bytes();
		Rx_pkgs=this.getRx_pkgs()+datanew.getRx_pkgs();
		Tx_bytes=this.getTx_bytes()+datanew.getTx_bytes();
		Tx_pkgs=this.getTx_pkgs()+datanew.getTx_pkgs();
		
	}
	
	
	
	
	long Rx_pkgs;//
	long Rx_bytes;//
	long Tx_pkgs;//
	long Tx_bytes;//


}
