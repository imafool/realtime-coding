package com.coco;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * HashMap源码分析
 */
public class HashMap_analysis<K, V> extends HashMap<K, V> {
    //========================================哈希表========================================
    /**
     * 哈希表：
     * 1. 新增、删除、查找等操作，时间复杂度均为 O(1) 常数阶
     * 2. 用数组作为主干，新增或者查找某个元素时，通过当前元素的关键字，通过哈希函数 f(key) 得到元素存储下标，一次定位元素
     *
     * 哈希表的哈希冲突：
     * 1. 两个不同元素，通过哈希函数得到相同值
     * 2. 好的哈希函数，尽可能使得 计算简单 & 散列地址均匀
     *
     * 处理哈希冲突的几种方式：
     * 1. 开放定制法：发生冲突，继续寻找下一块未被占用的存储地址
     * 2. 再散列法
     * 3. 链地址法：HashMap采用了该方法
     */

    //========================================HashMap========================================
    //是一个利用Hash表原理存储键值对元素的集合，遇到Hash冲突时，HashMap采用的链地址法来处理的，JDK1.8种HashMap是由数组+链表+红黑树构建的。

    //========================================static属性分析========================================

    //默认初始化容量 16，必须是2的倍数
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

    //最大容量，小于2^30，必须是2的倍数
    static final int MAXIMUM_CAPACITY = 1 << 30;

    //装载系数，构造函数没有指定时，默认0.75
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    //用树时的桶计数阈值，当结点树至少这么多时，桶被转成树，比2大，至少是8个
    static final int TREEIFY_THRESHOLD = 8;

    //调整容量时，不用树时的桶计数阈值，应该比TREEIFY_THRESHOLD，即用树时的小，最多6个
    static final int UNTREEIFY_THRESHOLD = 6;

    //桶被树形化的时候，哈希表容量至少应该这么多。否则如果一个桶中太多结点，哈希表被resize，至少应该4 * TREEIFY_THRESHOLD，才能避免resize时和树形化容量的冲突
    static final int MIN_TREEIFY_CAPACITY = 64;


    //========================================一般属性分析========================================

    //第一次被使用时初始化，如有必要会调整容量，长度总是2的倍数，允许为0
    transient Node<K,V>[] table;

    //Holds cached entrySet(). Note that AbstractMap fields are used for keySet() and values().
    transient Set<Entry<K,V>> entrySet;

    //集合中键值对的个数
    transient int size;

    //集合被修改次数，应对并发修改异常
    transient int modCount;

    //resize之后集合中键值对的个数 capacity * load factor
    int threshold;

    //hash表的装载系数
    //final
    float loadFactor;

    //基础哈希表的桶结点，有TreeNode子类
    static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        V value;
        Node<K,V> next;

        Node(int hash, K key, V value, Node<K,V> next) {
            this.hash = hash; //结点的hash
            this.key = key; //结点的key
            this.value = value; //结点的value
            this.next = next; //下一个结点的引用
        }

        public final K getKey()        { return key; }
        public final V getValue()      { return value; }
        public final String toString() { return key + "=" + value; }

        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value); //重写hashCode，key和value的hashCode值的异或
        }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        public final boolean equals(Object o) { //重写equals方法：Entry类型，并且key和value均相同时，两个Node元素相同
            if (o == this)
                return true;
            if (o instanceof Map.Entry) {
                Map.Entry<?,?> e = (Map.Entry<?,?>)o;
                if (Objects.equals(key, e.getKey()) &&
                        Objects.equals(value, e.getValue()))
                    return true;
            }
            return false;
        }
    }

    //========================================构造方法分析========================================

    //空参数构造方法 default initial capacity (16) and the default load factor (0.75)
    public HashMap_analysis() {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
    }

    //指定容量的构造方法 default load factor (0.75)
    public HashMap_analysis(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public HashMap_analysis(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity); //初始化容量小于0，抛异常
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY; //初始化容量如果大于最大容量，按最大容量处理
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor); //装载系数得合法
        this.loadFactor = loadFactor;
        this.threshold = tableSizeFor(initialCapacity);
    }

    //返回一个比给定容量大，且最接近2的次幂的一个正整数
    static int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1; // >>> 无符号右移  | 按位运算
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    //========================================添加元素分析========================================

    public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
    }

    //获取key的哈希值
    static int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    /**
     * @param hash key的哈希值
     * @param key key
     * @param value value
     * @param onlyIfAbsent
     * @param evict
     */
    final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
        Node<K,V>[] tab;//Node数组
        Node<K,V> p; //临时结点
        int n, i;
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length; //第一次添加元素，进入resize方法时，为了初始化，n=16
            //        Node<K,V>[] oldTab = table;
            //        int oldCap = (oldTab == null) ? 0 : oldTab.length;
            //        int oldThr = threshold;
            //        int newCap, newThr = 0;
            //        ...
            //        else {
            //            newCap = DEFAULT_INITIAL_CAPACITY; 默认容量16
            //            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY); 计算阈值16*0.75=12
            //        threshold = newThr; 表阈值12
            //        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap]; //构建默认容量大小的数组，哈希表的主干数组分配空间
            //        table = newTab;
        //通过key的hash，计算结点即将存入数组中索引，(n - 1) & hash代替hash%tab.length取模运算，提高效率！
        if ((p = tab[i = (n - 1) & hash]) == null)
            //如果计算出数组位置为空（还没有存放任何结点），构造新结点，存入数组
            tab[i] = newNode(hash, key, value, null);
        //通过key的hash，计算出相应位置已经存在了结点
        else {
            Node<K,V> e;
            K k;
            //该位置上已经存在结点（hash值和key相同）
            if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k))))
                //结点相同直接覆盖
                e = p;
            //是树结点
            //else if (p instanceof TreeNode)
                //树中添加该结点
                //e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            else {
                //是链表，遍历
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
                        //将结点链到链表后边
                        p.next = newNode(hash, key, value, null);
                        //如果链到树形化的阈值，进行树形化
                        //if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            //treeifyBin(tab, hash);
                        break;
                    }
                    //key已经存在，结束
                    if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            //修改已经存在key的value
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
        if (++size > threshold) //计算容量，是否超过 最大容量*装载系数，即threshold阈值，超过了就resize
            resize();
        afterNodeInsertion(evict);
        return null;
    }

    /**
     * 添加元素主要流程:
     * 1. 先判断数组是否为null，执行resize，按照初始容量和装载系数初始化数组；
     * 2. 根据key，计算hash值，计算即将插入的数组索引；
     *      1. 如果索引处为null，直接构建结点，添加结点
     *      2. 如果索引处不是null，判断key是否一样，
     *          1. 如果一样，即hash，key，equals都一样，覆盖相应的value，否则继续判断，
     *          2. 如果不一样，索引处是否是TreeNode，如果是按照树的方式插入键值对，否则继续判断
     *          3. 继续判断如果是链表，将结点链到尾部，判断链表长度是否大于8，大于把链表树形化，在树中插入，否则链表方式插入，如果其中发现相同结点，直接覆盖相应的value；
     * 3. 最后，集合大小+1，判断是否超出阈值，超出resize操作，否则结束；
     *
     * 注意两个点：
     * 1. 判断元素是否相同的时候的技巧：if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
     * 2. 计算散列位置时候的技巧：
     *      1.首先，h & (length-1) 等价于 h % length，位运算效率高
     *      2.其次是规定数组长度必须是2的倍数，这样 & 运算的时候，不会收到数组长度的影响，从而由hash值决定索引位置，减小碰撞的几率，提高效率
     */

    //resize的逻辑是用更大的数组代替原有数组
    final Node<K,V>[] resize() {
        Node<K,V>[] oldTab = table;//原数组暂存
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int oldThr = threshold;
        int newCap, newThr = 0;
        if (oldCap > 0) {//容量判断
            if (oldCap >= MAXIMUM_CAPACITY) {//上限
                threshold = Integer.MAX_VALUE;
                return oldTab;
            }
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY && oldCap >= DEFAULT_INITIAL_CAPACITY)
                newThr = oldThr << 1; //容量和阈值扩大两倍
        }
        else if (oldThr > 0)
            newCap = oldThr;
        else {
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        if (newThr == 0) {
            float ft = (float)newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                    (int)ft : Integer.MAX_VALUE);
        }
        threshold = newThr;
        @SuppressWarnings({"all"})
        //创建一个新容量的数组
        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
        table = newTab;
        if (oldTab != null) {
            //遍历旧数组，迁移到新数组上，可能包括结点、链表、树的迁移，然后就数组相应位置置空，便于垃圾回收
            for (int j = 0; j < oldCap; ++j) {
                Node<K,V> e;
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null;
                    if (e.next == null)
                        newTab[e.hash & (newCap - 1)] = e;
                    //else if (e instanceof TreeNode)
                        //((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                    else { // preserve order
                        Node<K,V> loHead = null, loTail = null;
                        Node<K,V> hiHead = null, hiTail = null;
                        Node<K,V> next;
                        do {
                            next = e.next;
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            }
                            else {
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        //把新数组返回
        return newTab;
    }

    /**
     * resize主要逻辑：
     * 1. 原数组暂存，
     * 2. 容量和阈值处理：如果原数组为null，用默认值初始化数组；如果容量到最大，就最大了；否则容量和阈值扩大为原来的2倍；
     * 3. 使用新的容量构造新数组，遍历原数组，对数据进行迁移，包括可能涉及到的结点、链表、树等类型
     * 4. 最后把新数组返回
     */

    Node<K,V> newNode(int hash, K key, V value, Node<K,V> next) {
        return new Node<>(hash, key, value, next);
    }

    void afterNodeAccess(Node<K,V> p) { }

    void afterNodeInsertion(boolean evict) { }

    //========================================查找元素分析========================================
    //通过key查找value
    public V get(Object key) {
        Node<K,V> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;//计算key的hash值，定位到数组中的位置
    }

    final Node<K,V> getNode(int hash, Object key) {
        Node<K,V>[] tab;
        Node<K,V> first, e;
        int n;
        K k;
        if ((tab = table) != null && (n = tab.length) > 0 && (first = tab[(n - 1) & hash]) != null) {
            if (first.hash == hash && ((k = first.key) == key || (key != null && key.equals(k))))//判断位置上第一个结点，相同就返回
                return first;
            if ((e = first.next) != null) { //否则判断后边的树
                //if (first instanceof TreeNode)
                    //return ((TreeNode<K,V>)first).getTreeNode(hash, key);
                do {
                    if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null); //或者链表
            }
        }
        return null;
    }

    //========================================删除元素分析========================================
    //同理

    //========================================遍历元素分析========================================
    //通过遍历map.entrySet()获取每一个HashMap.Entry，通过entry.getKey()/entry.getValue()

    //最后，hashCode()和equals()方法的重写：
    //两个对象相同，hashCode一定相同，所以重写equals方法的时候必须重写hashCode，确保equals结果为true时，二者hashCode一定相等；
    //两个对象不同，hashCode也相同，这就产生了哈希碰撞；
}
