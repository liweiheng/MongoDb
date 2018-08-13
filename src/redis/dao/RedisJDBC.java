package redis.dao;

import redis.clients.jedis.Jedis;

public class RedisJDBC {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());
        jedis.set("dd","11111111111");
        System.out.println(jedis.get("dd"));
    }
}
