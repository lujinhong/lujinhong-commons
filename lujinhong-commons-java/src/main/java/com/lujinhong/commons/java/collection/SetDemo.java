package com.lujinhong.commons.java.collection;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * AUTHOR: LUJINHONG
 * CREATED ON: 17/3/6 11:57
 * PROJECT NAME: lujinhong-commons
 * DESCRIPTION:
 */
public class SetDemo {

    public static void main(String[] args) {
        Set<Integer> result = new HashSet<>();
        Set<Integer> set1 = new HashSet<Integer>(){{
            add(1);
            add(3);
            add(5);
        }};

        Set<Integer> set2 = new HashSet<Integer>(){{
            add(1);
            add(2);
            add(3);
        }};

        result.clear();
        result.addAll(set1);
        result.retainAll(set2);
        System.out.println(Arrays.toString(result.toArray()));

    }
}
