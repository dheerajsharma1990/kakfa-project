package com.dheeraj.kafka.producer.domain;

import java.io.Serializable;

public interface Domain<T> extends Serializable {

    T getDomainObject();

}
