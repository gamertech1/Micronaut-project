package com.micronautlearning.product;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record Product (
    Integer id,
    String name,
    Type type
){ public enum Type {COFFEE,TEA,OTHERS}}
