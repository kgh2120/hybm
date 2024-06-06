package com.dragontrain.md.common.lock;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@RequiredArgsConstructor
@Repository
public class RedisLockRepositoryImpl implements LockRepository{

	private final RedisTemplate<String, String> redisTemplate;
	@Override
	public Boolean getLock(String functionName, Object... key) {
		return redisTemplate.opsForValue().setIfAbsent(generateKey(functionName, key), "lock", Duration.ofMillis(3000));
	}
	@Override
	public Boolean releaseLock(String functionName, Object... key) {
		return redisTemplate.delete(generateKey(functionName, key));
	}

	private String generateKey(String functionName, Object... key) {
		StringBuilder sb = new StringBuilder();
		sb.append("LOCK::").append(functionName).append("::");
		for (Object k : key) {
			sb.append(k.toString());
		}
		return sb.toString();
	}

}
