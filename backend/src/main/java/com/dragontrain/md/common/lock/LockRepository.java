package com.dragontrain.md.common.lock;

public interface LockRepository {

	Boolean getLock(String functionName,Object... key);

	Boolean releaseLock(String functionName, Object... key);
}
