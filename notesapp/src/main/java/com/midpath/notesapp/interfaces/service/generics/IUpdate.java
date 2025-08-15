package com.midpath.notesapp.interfaces.service.generics;

public interface IUpdate<U, R, T, I> {
    public T update(U user, I id, R request);
}
