package myjob.common.util;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;

import myjob.common.util.entity.Connection;

/**
 * 对象池，对common-pool2进行封装
 * @author ljnan
 *2022-01-11
 */
public class ConnectionFactory extends BasePooledObjectFactory<Connection>{
	
	private AtomicInteger idCount = new AtomicInteger(0);
	@Override
	public Connection create() throws Exception {
		return new Connection(idCount.getAndAdd(1));
	}

	@Override
	public PooledObject<Connection> wrap(Connection obj) {
		return new DefaultPooledObject<Connection>(obj);
	}

}
