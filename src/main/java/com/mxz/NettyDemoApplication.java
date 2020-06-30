package com.mxz;


import com.mxz.server.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetSocketAddress;

@Slf4j
@SpringBootApplication
public class NettyDemoApplication implements CommandLineRunner {


	@Value("${netty.port}")
	private int port;

	@Value("${netty.url}")
	private String url;

	@Autowired
	private NettyServer server;

	public static void main(String[] args) {
		log.info("main 方法启动");
		SpringApplication.run(NettyDemoApplication.class, args);
		System.out.println("冲鸭。。。");
	}


	@Override
	public void run(String... strings) throws Exception {
		log.info("run 方法启动");
		InetSocketAddress address = new InetSocketAddress(url, port);
		server.start(address);
	}
}
