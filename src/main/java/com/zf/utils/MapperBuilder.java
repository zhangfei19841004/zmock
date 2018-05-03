package com.zf.utils;

import java.util.HashMap;
import java.util.Map;

public class MapperBuilder {

    public static <K, V> Builder<K, V> getBuilder() {
        return new Builder<K, V>();
    }

    public static class Builder<K, V> {

        private Map<K, V> map;

        private Builder() {
            map = new HashMap<K, V>();
        }

        public Builder<K, V> put(K k, V v) {
            map.put(k, v);
            return this;
        }

        public Map<K, V> build() {
            return map;
        }

    }

    public static void main(String[] args) {
        Map<Integer, Integer> map = MapperBuilder.<Integer, Integer>getBuilder().put(1, 2).put(2, 3).build();
        System.out.println(map.get(1));
    }

}
