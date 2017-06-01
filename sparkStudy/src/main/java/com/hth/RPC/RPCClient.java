package com.hth.RPC;

import com.hth.hadoop.BizProtocol;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.net.InetSocketAddress;

public class RPCClient {
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		BizProtocol proxy = RPC.getProxy(BizProtocol.class, 10010, new InetSocketAddress("localhost", 8888), conf);
		String result = proxy.hello("world");
		System.out.println(result);
		RPC.stopProxy(proxy);
	}

}
