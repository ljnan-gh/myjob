package myjob.common.util;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import myjob.common.util.entity.Connection;

public class ConnectionPool extends GenericObjectPool<Connection>{

	public ConnectionPool(PooledObjectFactory<Connection> factory) {
		super(factory);
	}
	public ConnectionPool(PooledObjectFactory<Connection> factory,GenericObjectPoolConfig config) {
		super(factory,config);
	}
	public ConnectionPool(PooledObjectFactory<Connection> factory,GenericObjectPoolConfig config,AbandonedConfig abandonedConfig) {
		super(factory,config,abandonedConfig);
	}
}
