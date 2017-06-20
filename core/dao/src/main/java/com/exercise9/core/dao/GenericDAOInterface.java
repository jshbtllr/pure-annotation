package com.exercise9.core.dao;

public interface GenericDAOInterface <T> {
	public T add(T added);
	public T update(T updated);
	public T delete(T deleted);
	public T get(Class<T> entity, Long id);
}

