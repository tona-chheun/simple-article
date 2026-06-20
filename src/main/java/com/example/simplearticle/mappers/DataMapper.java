package com.example.simplearticle.mappers;

public interface DataMapper<E, Req, Res> {
    E toEntity(Req payload);
    Res toResponse(E entity);
}
