package com.company;

import java.util.LinkedList;

/**
 * Created by DrBAX_000 on 13.05.2015.
 */
public class SizeList<E> extends LinkedList<E> implements Comparable{

    @Override
    public int compareTo(Object o) {
        LinkedList<E> list = (LinkedList<E>)o;
        if(this.size() > list.size())
            return 1;
        else if(this.size() < list.size())
            return -1;
        else  return 0;
    }
}
