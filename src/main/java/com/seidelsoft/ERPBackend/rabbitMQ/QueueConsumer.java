package com.seidelsoft.ERPBackend.rabbitMQ;

public interface QueueConsumer<T> {

    void consumir(T mensagem);
}
