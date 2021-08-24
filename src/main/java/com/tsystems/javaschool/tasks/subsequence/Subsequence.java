package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {

        try {
            for (int i = 0, j=0; i < y.size() && !x.isEmpty(); i++) {
                if (y.get(i) == x.get(j)){
                    x.remove(j);
                }
            }
            if (x.isEmpty()){
                return true;
            }
        }
        catch (Exception e){
            throw new IllegalArgumentException();
        }
        return false;
    }
}
