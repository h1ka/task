package ru.mls.parts.dbservice;

import ru.mls.parts.model.Params;

import java.util.List;

public interface DBService<T> {

    List<T> loadAll(String tableName, Class<T> clazz);

    List<T> loadAll(String tableName, Params params, Class<T> clazz);

}
