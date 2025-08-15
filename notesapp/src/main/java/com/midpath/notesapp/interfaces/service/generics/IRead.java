package com.midpath.notesapp.interfaces.service.generics;

public interface IRead<U, T, L> {
    public T getById(U user, L id);
}
