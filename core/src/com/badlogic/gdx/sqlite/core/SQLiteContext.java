package com.badlogic.gdx.sqlite.core;

public final class SQLiteContext<T> {

    private T context;

    public SQLiteContext() {
    }

    public SQLiteContext(T context) {
	this.context = context;
    }

    public T getContext() {
	return context;
    }

}
