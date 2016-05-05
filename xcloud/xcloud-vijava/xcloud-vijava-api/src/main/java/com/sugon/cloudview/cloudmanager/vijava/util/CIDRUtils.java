package com.sugon.cloudview.cloudmanager.vijava.util;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that enables to get an IP range from CIDR specification. 
 * It supports both IPv4 and IPv6.
 */
public class CIDRUtils {
	
    private final String cidr;

    private InetAddress inetAddress;
    private InetAddress startAddress;
    private InetAddress endAddress;
    private final int prefixLength;


    public InetAddress getStartAddress() {
		return startAddress;
	}


	public void setStartAddress(InetAddress startAddress) {
		this.startAddress = startAddress;
	}


	public CIDRUtils(String cidr) throws UnknownHostException {

        this.cidr = cidr;

        /* split CIDR to address and prefix part */
        if (this.cidr.contains("/")) {
            int index = this.cidr.indexOf("/");
            String addressPart = this.cidr.substring(0, index);
            String networkPart = this.cidr.substring(index + 1);

            inetAddress = InetAddress.getByName(addressPart);
            prefixLength = Integer.parseInt(networkPart);

            calculate();
        } else {
            throw new IllegalArgumentException("not an valid CIDR format!");
        }
    }


    private void calculate() throws UnknownHostException {

        ByteBuffer maskBuffer;
        int targetSize;
        if (inetAddress.getAddress().length == 4) {
            maskBuffer = ByteBuffer.allocate(4).putInt(-1);
            targetSize = 4;
        } else {
            maskBuffer = ByteBuffer.allocate(16).putLong(-1L).putLong(-1L);
            targetSize = 16;
        }

        BigInteger mask = (new BigInteger(1, maskBuffer.array())).not().shiftRight(prefixLength);

        ByteBuffer buffer = ByteBuffer.wrap(inetAddress.getAddress());
        BigInteger ipVal = new BigInteger(1, buffer.array());

        BigInteger startIp = ipVal.and(mask);
        BigInteger endIp = startIp.add(mask.not());

        byte[] startIpArr = toBytes(startIp.toByteArray(), targetSize);
        byte[] endIpArr = toBytes(endIp.toByteArray(), targetSize);

        this.startAddress = InetAddress.getByAddress(startIpArr);
        this.endAddress = InetAddress.getByAddress(endIpArr);

    }

    private byte[] toBytes(byte[] array, int targetSize) {
        int counter = 0;
        List<Byte> newArr = new ArrayList<Byte>();
        while (counter < targetSize && (array.length - 1 - counter >= 0)) {
            newArr.add(0, array[array.length - 1 - counter]);
            counter++;
        }

        int size = newArr.size();
        for (int i = 0; i < (targetSize - size); i++) {

            newArr.add(0, (byte) 0);
        }

        byte[] ret = new byte[newArr.size()];
        for (int i = 0; i < newArr.size(); i++) {
            ret[i] = newArr.get(i);
        }
        return ret;
    }

    public String getNetworkAddress() {

        return this.startAddress.getHostAddress();
    }

    public String getBroadcastAddress() {
        return this.endAddress.getHostAddress();
    }

    public boolean isInRange(String ipAddress) throws UnknownHostException {
    	
        InetAddress address = InetAddress.getByName(ipAddress);
        BigInteger start = new BigInteger(1, this.startAddress.getAddress());
        BigInteger end = new BigInteger(1, this.endAddress.getAddress());
        BigInteger target = new BigInteger(1, address.getAddress());

        int st = start.compareTo(target);
        int te = target.compareTo(end);

        return (st == -1 || st == 0) && (te == -1 || te == 0);
    }
    
    public String getNetMask(int x){
    	
    	int netmask = 0;
        for (int j = 0; j < x; ++j) {
            netmask |= (1 << 31 - j);
        }
    	return format(toArray(netmask));
    }
    
    public String getNetMask(){
    	
    	int netmask = 0;
        for (int j = 0; j < prefixLength; ++j) {
            netmask |= (1 << 31 - j);
        }
    	return format(toArray(netmask));
    }
    
    
    private int[] toArray(int val) {
        int ret[] = new int[4];
        for (int j = 3; j >= 0; --j) {
            ret[j] |= ((val >>> 8*(3-j)) & (0xff));
        }
        return ret;
    }
    
    private String format(int[] octets) {
        StringBuilder str = new StringBuilder();
        for (int i =0; i < octets.length; ++i){
            str.append(octets[i]);
            if (i != octets.length - 1) {
                str.append(".");
            }
        }
        return str.toString();
    }

    
    public static void main(String args[]) throws UnknownHostException{
    	
    	CIDRUtils cidrUtils = new CIDRUtils("10.77.12.11/18");
    	System.out.println(cidrUtils.getNetworkAddress());
    	String networkAddress = cidrUtils.getNetworkAddress();
    	String broadcastAddress = cidrUtils.getBroadcastAddress();
    	boolean isInRange = cidrUtils.isInRange("20.77.12.11");
    	System.out.println("networkAddress = " + networkAddress);
    	System.out.println("broadcastAddress = " + broadcastAddress);
    	System.out.println("isInRange = " + isInRange);
//    	for(int i = 16; i <= 32; i++)
//        	System.out.println(i + " = " + cidrUtils.getNetMask(i));
    	System.out.println(cidrUtils.getNetMask());
    }
}
