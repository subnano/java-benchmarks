package net.subnano.benchmarks;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;

/**
 * @author Mark Wardell
 */
public class RedisWriter {

    private static final String URI = "redis://localhost:6379";
    private static final String SOCKET = "/var/run/redis/redis-server.sock";

    private final RedisClient redisClient;
    private final StatefulRedisConnection<String, String> redisConnection;
    private final RedisAsyncCommands<String, String> writerAsync;

    public RedisWriter() {
        RedisURI redisUri = RedisURI.Builder
                .socket(SOCKET)
                .build();
        this.redisClient = RedisClient.create(redisUri);
        redisConnection = redisClient.connect();
        writerAsync = redisConnection.async();
    }

    public RedisFuture<String> set(String key, String value) {
        RedisFuture<String> future = writerAsync.set(key, value);
        return future;
    }
}
