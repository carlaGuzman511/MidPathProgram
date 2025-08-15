package com.midpath.notesapp.interfaces.service.generics;

public interface IRemove<U, T> {
    public void remove(U user, T id);
}
