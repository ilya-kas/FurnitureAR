package com.furniturear;

import java.util.Iterator;

public class LinkedList implements Iterable{
    private class Item {
        Object item;    //ссылка на хранящийся объект
        Item next,prev; //ссылки на соседные элементы

        Item(Object x){
            item = x;
        }
    }

    private Item root,last;
    private int size = 0;

    LinkedList(){
        root = new Item(null);
        last = root;
    }

    void add(Object o){
        last.next = new Item(o);  //добавляем элемент в конец и двигаем ссылку на последний элемент
        last.next.prev = last;
        last = last.next;
        if (root.item==null)   //если это единственный элемент в списке, то двигаем ссылку начала списка
            root=root.next;
        size++;
    }

    boolean isEmpty(){
        return root.item==null;
    }

    int size(){
        return size;
    }

    void clear(){
        root = new Item(null);
        last = root;
        size = 0;
    }

    void remove(int nom){ //удаляет по номеру
        if (nom>size)
            return;
        Item x;
        for (x = root;nom>0;x=x.next){  //ищем
            nom--;
        }

        if (x==last) {        //удаляем
            last = x.prev;
        }
        size--;
        if (x.prev!=null)
            x.prev.next = x.next;
        if (x.next!=null)
            x.next.prev = x.prev;
        if (x==root)
            root = root.next;
        x=null;
        if (root==null)
            root = new Item(null);
        if (last==null)
            last = root;
        if (root.item==null&&root.next!=null)   //если удалили первый элемент, то сдвигаем его
            root=root.next;
    }

    Object get(int nom){  //достаём элемент по номеру
        if (nom>size)
            return null;
        Item x;
        for (x = root;nom>0;x=x.next){
            nom--;
        }
        return x.item;
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {
            Item now;
            @Override
            public boolean hasNext() {
                if (now==null)
                    return root.next!=null;
                return now.next!=null;
            }

            @Override
            public Object next() {
                if (now==null)
                    now = root;
                else
                    now = now.next;
                return now.item;
            }
        };
    }
}
