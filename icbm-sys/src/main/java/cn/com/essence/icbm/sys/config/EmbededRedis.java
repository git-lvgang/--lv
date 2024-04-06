//package cn.com.essence.ambs.basic.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import redis.embedded.RedisServer;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//import java.io.IOException;
//
///**
// * 内嵌redis服务
// *
// * NOTE: 用于测试，请于在生产使用
// * @author jqkun
// * @version Jan 14, 2019
// * @Copyright (C)2019 , ESSENCE SECURITIES Co. Ltd.,
// */
//@Configuration
//public class EmbededRedis {
//
//  @Value("${spring.redis.port}")
//  private int redisPort;
//
//  private RedisServer redisServer;
//
//  @PostConstruct
//  public void startRedis() throws IOException {
//    redisServer = new RedisServer(redisPort);
//    redisServer.start();
//  }
//
//  @PreDestroy
//  public void stopRedis() {
//    redisServer.stop();
//  }
//}
