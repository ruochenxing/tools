package com.sorts.classs;

/**
 * 快速排序 
 * 基本思想：选择一个基准元素,通常选择第一个元素或者最后一个元素,
 * 通过一趟扫描，将待排序列分成两部分,一部分比基准元素小,一部分大于等于基准元素,
 * 此时基准元素在其排好序后的正确位置,然后再用同样的方法递归地排序划分的两部分。直到各区间只有一个数。
 */
public class QuickSort extends Sorter{

    @Override
    public <T extends Comparable<? super T>> void sort(T[] a) {
        sort(a, 0, a.length-1);
    }

    private <T extends Comparable<? super T>> void sort(T[] a, int s_low, int s_high) {
        int low = s_low;
        int high = s_high;
        if(low == high-1){
            if(a[low].compareTo(a[high]) > 0)
                swap(a,low,high);
        }
        else if(low < high){
            T pivot = a[(low+high)/2];
            a[(low+high)/2] = a[high];
            a[high] = pivot;
            while(low < high){
                while((a[low].compareTo(pivot) < 1) && (low < high))
                    low++;
                while((pivot.compareTo(a[high]) < 1) && (low < high))
                    high--;
                if(low < high)
                    swap(a,low,high);
            }
            a[s_high] = a[high];
            a[high] = pivot;
            sort(a,s_low,low-1);
            sort(a,high+1,s_high);
        }
    }   
}