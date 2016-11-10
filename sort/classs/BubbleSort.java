package com.sorts.classs;

/**
 * 
 * 冒泡排序
 * 升序
 */
public class BubbleSort extends Sorter{
    
    @Override
    public <T extends Comparable<? super T>> void sort(T[] a){
        boolean swapped = true;
        int i = a.length-1;
        while(swapped && i>=0){
            swapped = false;
            for(int j = 0; j < i; j++){
                if(a[j].compareTo(a[j+1]) > 0){//如果前面的大于后面的，则交换
                    swap(a,j,j+1);
                    swapped = true;
                }
            }
            i--;
        }
    }
}