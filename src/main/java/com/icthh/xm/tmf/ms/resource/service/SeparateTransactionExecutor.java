package com.icthh.xm.tmf.ms.resource.service;

import java.util.concurrent.Callable;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.function.Supplier;

@Component
public class SeparateTransactionExecutor {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Map<String, Object> doInSeparateTransaction(Supplier<Map<String, Object>> task) {
        return task.get();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @SneakyThrows
    public <T> T doInSeparateTransaction(Callable<T> task) {
        return task.call();
    }

}
