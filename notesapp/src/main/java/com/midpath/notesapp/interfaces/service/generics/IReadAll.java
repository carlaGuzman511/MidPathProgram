package com.midpath.notesapp.interfaces.service.generics;

import java.util.List;

public interface IReadAll<U, T> {
    public List<T> getAll(U user);
}
