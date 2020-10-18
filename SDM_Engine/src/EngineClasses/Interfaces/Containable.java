package EngineClasses.Interfaces;

public interface Containable<K, V> {
    public boolean isExist(K key);

    public V get(K key);

    public void add(K key, V newObj);

    public Iterable<V> getIterable();
}
