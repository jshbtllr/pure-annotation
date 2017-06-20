package com.exercise9.core.service;
import java.util.List;

public interface CrudServiceInterface <T> {
	public T create(T created);
	public List <T> read(Integer sort, Boolean order);
	public T delete(Long id);
	public T update(T updated);
	public T get(Long id);
}