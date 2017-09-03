package daggerok;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static java.util.stream.Collectors.joining;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class EmbeddedRedisApplication {

  final RedisServer redisServer;
  final StringRedisTemplate template;

  public static void main(String[] args) {
    SpringApplication.run(EmbeddedRedisApplication.class, args);
  }

  @PostConstruct
  public void start() {

    log.info("starting redis...");
    if (!redisServer.isActive()) redisServer.start();
    log.info("redis listen ports: {}", redisServer.ports().stream()
                                                  .map(Object::toString).collect(joining(",")));
  }

  @PreDestroy
  public void stop() {

    log.info("shutting down redis...");
    redisServer.stop();
    log.info("bye!");
  }

  @Bean
  public CommandLineRunner run() throws Exception {
    ValueOperations<String, String> operations = template.opsForValue();
    String key = "spring.boot.redis.test";

    if (!template.hasKey(key)) {
      operations.set(key, "foo");
    }
    System.out.printf("Found key %s, value=%s\n", key, operations.get(key));

    return args -> System.exit(0);
  }
}
