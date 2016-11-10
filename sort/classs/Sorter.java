package com.sorts.classs;


/**
 * Information about each algorithm's time/memory complexity and stability
 * is provided in their respective classes.
 * 
 * n is the number of records to be sorted.
 * 
 * A sorting algorithm is stable if 
 * whenever there are two records R and S with the same key 
 * and with R appearing before S in the original list, 
 * R will appear before S in the sorted list.
 * 排序抽象类
 */

public abstract class Sorter {
    
    /**
     * Swap the contents of a[i] and a[j]
     */
    protected void swap(Object[] a, int i, int j){
        Object tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }    
    public abstract <T extends Comparable<? super T>> void sort(T[] a);
}