package org.jarvis.pprpc.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Marcus
 * @date 2021/2/17-12:47
 * @description this is function
 */
public class HelloServiceImpl implements HelloService {
    private Logger LOGGER = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public void sayHelloWorld() {
        LOGGER.info("HelloWorld in {}", this.getClass().getCanonicalName());
    }

    @Override
    public void sayHi() {

    }

    @Override
    public void sayHello() {

    }
}
