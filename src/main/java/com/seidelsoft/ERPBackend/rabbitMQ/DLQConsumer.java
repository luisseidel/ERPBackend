package com.seidelsoft.ERPBackend.rabbitMQ;

public interface DLQConsumer<T> {

    void consumir(T mensagem);
}
