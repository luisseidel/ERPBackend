package com.seidelsoft.ERPBackend.system.service;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseService<T, K> implements IService<T> {

    @Autowired
    protected K repository;

}
