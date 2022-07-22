package com.coco;

import java.util.Collection;
import java.util.LinkedList;

/**
 * LinkedList源码分析
 */
public class LinkedList_analysis<E> extends LinkedList<E> {

    //===============================成员变量分析==============================
    //集合元素个数/结点数
    transient int size = 0;
    //指向第一个结点，内部prev为null
    transient Node<E> first;
    //指向最后一个结点，内部next为null
    transient Node<E> last;

    //===============================内部类Node==============================
    //所以LinkedList是一个双向链表
    private static class Node<E> {
        E item;//数据元素
        Node<E> next;//指向前面元素
        Node<E> prev;//指向后边元素

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    //=================================构造方法================================
    public LinkedList_analysis() {
        super();
    }

    //将已有Collection实例添加到LinkedList
    public LinkedList_analysis(Collection<? extends E> c) {
        this();
        addAll(c);
    }

    //=================================添加元素================================
    /**
     * 追加元素到链表末尾，即默认是尾插法
     */
    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    /**
     * 让新增结点作为尾结点
     */
    void linkLast(E e) {
        final Node<E> l = last;//暂存链表此时的尾部结点
        final Node<E> newNode = new Node<>(l, e, null);//构造新节点，指向此时的尾部节点
        last = newNode;//新结点作为新的尾结点
        if (l == null)
            first = newNode;//如果链表为空，把新结点作为头节点，因为只有它一个元素，所以此时也是尾部结点，头尾引用均是null
        else
            l.next = newNode;//不为空，此时的尾部结点next指向新结点
        size++;//结点数+1
        modCount++;
    }

    /**
     * 指定位置插入元素
     */
    public void add(int index, E element) {
        checkPositionIndex(index);//索引越界检查

        if (index == size)
            linkLast(element);//如果索引=集合大小，直接链到尾部
        else
            linkBefore(element, node(index));//索引位置!=集合大小,在索引位置之前链入元素
    }

    void linkBefore(E e, Node<E> succ) {
        final Node<E> pred = succ.prev;//索引处元素的前驱结点
        final Node<E> newNode = new Node<>(pred, e, succ);//构造新节点，产生关联
        succ.prev = newNode;
        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;
        size++;
        modCount++;
    }

    /**
     * 定位到index位置的结点
     */
    Node<E> node(int index) {
        if (index < (size >> 1)) {//如果位置小于集合大小的一半
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;//从第一个元素开始移动，直到找到index位置的节点
            return x;
        } else {//如果位置大于集合大小的一半，从后边开始找
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }

    //=================================查找元素================================
    public E get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }


    //=================================删除元素================================
    public E remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    E unlink(Node<E> x) {//将结点数据置null，修改前驱结点的next引用和后继结点的prev引用
        final E element = x.item;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }
        x.item = null;
        size--;
        modCount++;
        return element;
    }

}
