package com.sorts.classs;

/**
 * 归并排序
 * 归并（Merge）排序法是将两个（或两个以上）有序表合并成一个新的有序表，
 * 即把待排序序列分为若干个子序列，每个子序列是有序的
 * 然后再把有序子序列合并为整体有序序列。
 * */
public class MergeSort extends Sorter{

    @Override
    public <T extends Comparable<? super T>> void sort(T[] a) {
        // Generic array creation is not possible! (grr)
        Comparable[] b = new Comparable[a.length];
        sort(a,b,0,a.length-1);
    }

    private <T extends Comparable<? super T>> void sort(T[] a, T[] b, int low, int high) {
        if(low < high){
            int middle = (low+high) / 2;
            sort(a,b,low,middle);
            sort(a,b,middle+1,high);
            
            //合并两段有序数列
            int s_high = middle+1;//第一段有序数列的开始
            int s_low = low;//第二段有序数列的开始
            for(int i = low; i <= high; i++){
            	//将两段数列中的最小值放入b中
                if((s_low <= middle) && ((s_high > high) || (a[s_low].compareTo(a[s_high]) < 0)))
                    b[i] = a[s_low++];
                else
                    b[i] = a[s_high++];
            }
            //将b中（合并后）的值复制到a中
            for(int i = low; i <= high; i++)
                a[i] = b[i];
        }
    }
}