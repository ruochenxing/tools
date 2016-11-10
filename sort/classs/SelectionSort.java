package com.sorts.classs;

/**
 * 简单选择排序 
 * 基本思想：在要排序的一组数中，选出最小的一个数与第一个位置的数交换；
 * 然后在剩下的数当中再找最小的与第二个位置的数交换，如此循环到倒数第二个数和最后一个数比较为止。
 */
public class SelectionSort extends Sorter{

    @Override
    public <T extends Comparable<? super T>> void sort(T[] a) {
        for(int i = 0; i < a.length; i++){
            int min = i;
            //找最小值下标
            for(int j = i+1; j<a.length; j++)
                if(a[j].compareTo(a[min]) < 0)
                    min = j;
            
            swap(a,min,i);
        }
    }
}