package com.coco;

import sun.misc.SharedSecrets;

import java.util.*;
import java.util.function.Consumer;

/**
 * ArrayList源码分析
 */
public class ArrayList_analysis<E> extends ArrayList<E> {

    //===============================成员变量分析==============================
    /**
     * 默认初始化容量
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 共享的空数组实例
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * 默认大小的空数组实例，区分EMPTY_ELEMENTDATA，以便知道添加第一个元素的时候，膨胀了多少
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
     * 数组缓冲区，ArrayList的容量就是这个数组的长度；
     * 当添加第一个元素的时候，ArrayList被扩展为默认容量，elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA
     */
    transient Object[] elementData;

    /**
     * ArrayList包含元素的个数
     */
    private int size;

    //===============================构造方法分析==============================
    /**
     * Constructs an empty list with an initial capacity of ten?
     * TODO 调用空参数构造方法，到底容量是多少？
     */
    public ArrayList_analysis() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    /**
     * 指定容量的构造方法
     */
    public ArrayList_analysis(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+ initialCapacity);
        }
    }

    /**
     * 使用其他通用集合，构造一个ArrayList
     */
    public ArrayList_analysis(Collection<? extends E> c) {
        //转成数组
        Object[] a = c.toArray();
        if ((size = a.length) != 0) {
            if (c.getClass() == ArrayList.class) {
                elementData = a;
            } else {
                //数组元素复制
                elementData = Arrays.copyOf(a, size, Object[].class);
            }
        } else {
            elementData = EMPTY_ELEMENTDATA;
        }
    }

    //===============================添加元素分析==============================
    public boolean add(E e) {
        ensureCapacityInternal(size + 1);
        elementData[size++] = e;
        return true;
    }

    public void ensureCapacity(int minCapacity) {
        int minExpand = (elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA) ? 0 : DEFAULT_CAPACITY;
        if (minCapacity > minExpand) {
            ensureExplicitCapacity(minCapacity);
        }
    }

    private static int calculateCapacity(Object[] elementData, int minCapacity) {
        //第一次添加元素，默认容量10
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        //后续添加
        return minCapacity;
    }

    private void ensureCapacityInternal(int minCapacity) {
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
    }

    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;
        if (minCapacity - elementData.length > 0)
            grow(minCapacity); //现在容量不够，执行扩容（第一次扩容在第11次添加元素 11-10>0）
    }

    /**
     * The maximum size of array to allocate. OutOfMemoryError: Requested array size exceeds VM limit
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * 扩容确保使用最小容量容纳元素
     */
    private void grow(int minCapacity) {
        int oldCapacity = elementData.length; //临时存储原有容量
        int newCapacity = oldCapacity + (oldCapacity >> 1); //新容量，如 新容量 = 10 + 10/2 = 15，扩容1.5倍
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity; //只有第一次添加元素时
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity); //需要的最大容量
        elementData = Arrays.copyOf(elementData, newCapacity); //元素复制
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0)
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE; //最大扩容到2147483647
    }

    //===============================查找元素分析==============================
    public E get(int index) {
        rangeCheck(index); //边界检查
        return elementData(index); //返回元素
    }

    private void rangeCheck(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }

    E elementData(int index) {
        return (E) elementData[index];
    }

    //===============================删除元素分析==============================
    //删除第一次出现的指定元素

    //===============================遍历元素分析==============================
    public Iterator<E> iterator() {
        return new ArrayList_analysis.Itr();
    }

    /**
     * 内部类
     */
    private class Itr implements Iterator<E> {
        int cursor;       // 下次要返回的元素的索引
        int lastRet = -1; // 上次返回元素的索引，若没有，返回 -1
        int expectedModCount = modCount;

        Itr() {}

        //通过cursor判断是否还有下一个元素
        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        public E next() {
            checkForComodification();
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = ArrayList_analysis.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                ArrayList_analysis.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public void forEachRemaining(Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
            final int size = ArrayList_analysis.this.size;
            int i = cursor;
            if (i >= size) {
                return;
            }
            final Object[] elementData = ArrayList_analysis.this.elementData;
            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            while (i != size && modCount == expectedModCount) {
                consumer.accept((E) elementData[i++]);
            }
            // update once at end of iteration to reduce heap write traffic
            cursor = i;
            lastRet = i - 1;
            checkForComodification();
        }

        //迭代的同时进行增删操作，抛出并发修改异常【即迭代过程中，Java需要容器保持不动，会通过修改次数检查判断】
        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    //===============================序列化元素分析==============================

    //transient Object[] elementData; 只序列化元素个数和实际元素，而非整个ArrayList，避开Java序列化机制，只存储实际元素，节省空间和时间

    //序列化 ObjectOutputStream
    private void writeObject(java.io.ObjectOutputStream s)
            throws java.io.IOException{
        // Write out element count, and any hidden stuff
        int expectedModCount = modCount;
        s.defaultWriteObject();

        // Write out size as capacity for behavioural compatibility with clone()
        s.writeInt(size);

        // Write out all elements in the proper order.
        for (int i=0; i<size; i++) {
            s.writeObject(elementData[i]);
        }

        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }

    //反序列化 ObjectInputStream
    private void readObject(java.io.ObjectInputStream s)
            throws java.io.IOException, ClassNotFoundException {
        elementData = EMPTY_ELEMENTDATA;

        s.defaultReadObject();

        s.readInt();

        if (size > 0) {
            // be like clone(), allocate array based upon size not capacity
            int capacity = calculateCapacity(elementData, size);
            SharedSecrets.getJavaOISAccess().checkArray(s, Object[].class, capacity);
            ensureCapacityInternal(size);

            Object[] a = elementData;
            // Read in all elements in the proper order.
            for (int i=0; i<size; i++) {
                a[i] = s.readObject();
            }
        }
    }
}
