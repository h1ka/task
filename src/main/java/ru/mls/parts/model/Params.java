package ru.mls.parts.model;

import java.util.*;
import java.util.stream.Collectors;

public class Params<T> {
    private Map<String, T> params;

    public Params() {
        params = new HashMap<>();
    }

    public boolean isEmpty() {
        return params.isEmpty();
    }

    public List<T> getValuesParams() {
        if (params.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(params.values());
    }

    public void addParam(String name, T value) {
        params.put(name, value);
    }

    public T getParam(String paramName) {
        return params.get(paramName);
    }

    public List<String> getListParams() {
        return params.isEmpty() ? Collections.emptyList() : new ArrayList<>(params.keySet());
    }

    public Params createParamsWithoutEmptyValues() {
        boolean isEmpty = params.values().stream()
                .allMatch(x -> x == null || x.equals(""));
        if (!isEmpty) {
            params = params.entrySet().stream()
                    .filter(e -> !((e.getValue() == null) || (e.getValue().equals(""))))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            return this;
        }
        return new Params();
    }


}
