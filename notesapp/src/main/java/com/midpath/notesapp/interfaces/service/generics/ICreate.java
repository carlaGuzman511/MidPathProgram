package com.midpath.notesapp.interfaces.service.generics;

public interface ICreate<U, T, R> {
    public T create(U user, R request);
}
