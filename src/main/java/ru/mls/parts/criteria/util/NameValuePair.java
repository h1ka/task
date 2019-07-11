package ru.mls.parts.criteria.util;

public class NameValuePair<T> {

    private String name;

    private T value;


    public NameValuePair(final String name, final T value) {
        this.name = name;
        this.value = value;
    }


    public String getName() {
        return this.name;
    }


    public T getValue() {
        return this.value;
    }


    public void setName(final String name) {
        this.name = name;
    }


    public void setValue(final T value) {
        this.value = value;
    }
}
