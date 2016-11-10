package com.sorts.classs;

/**
 * 直接插入排序 
 * 升序
 * 基本思想：在要排序的一组数中，假设前面(n-1)[n>=2] 个数已经是排好顺序的
 * 现在要把第n个数插到前面的有序数中，使得这n个数 也是排好顺序的。如此反复循环，直到全部排好顺序。
 */
public class InsertionSort extends Sorter{

    @Override
    public <T extends Comparable<? super T>> void sort(T[] a) {
        for(int i = 1; i < a.length; i++){
            int j = i;//j为要插入的值最初所在的位置下标
            T o = a[i];//要插入到 “前面的有序数列” 中的值
            //将插入的数依次与前面的数（从后往前）比较，如果要插入的数（o）小，则将大于o的值后移一位
            //意思就是，如果一个队伍是已经要身高升序排好对的，现在我来了
            //那我就跟前面的人比较，如果他比我高，他就往后退，空出他所在的位置，直到遇到比我矮的为止
            while((j > 0) && o.compareTo(a[j-1]) < 0){
                a[j] = a[j-1];
                j--;
            }
            a[j] = o;
        }
    }
}