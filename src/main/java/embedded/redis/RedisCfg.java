package embedded.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Protocol;
import redis.embedded.RedisServer;

/**
 * @author Maksim Kostromin <daggerok@gmail.com> on 4/12/16.
 */
@Configuration
public class RedisCfg {

    @Bean
    public RedisServer redisServer() {
        RedisServer.builder().reset();

        return RedisServer.builder().port(Protocol.DEFAULT_PORT).build();
    }
}