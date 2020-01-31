package com.meric.deepinjava.lpool;

import jdk.management.resource.ResourceContext;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class ResourcePooledObjectFactory extends BasePooledObjectFactory<Resource> {
    @Override
    public Resource create() throws Exception {
        return new Resource();
    }

    @Override
    public PooledObject<Resource> wrap(Resource resource) {
        return new DefaultPooledObject<>(resource);
    }
}
