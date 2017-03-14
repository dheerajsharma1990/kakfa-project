package com.dheeraj.kafka.producer.domain;

import java.io.Serializable;

public interface DomainId<T> extends Serializable {

    T getId();

}
