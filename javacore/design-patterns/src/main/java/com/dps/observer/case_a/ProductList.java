package com.dps.observer.case_a;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * This class represents an observable object, or "data"
 * in the model-view paradigm. It can be subclassed to represent an
 * object that the application wants to have observed.
 * 可被观察的对象（封装对象）
 */
public class ProductList extends Observable {
    
    private List<String> productList = null;//产品列表
    
    private static ProductList instance;//类唯一实例
    
    private ProductList() {}//构建方法私有化
    
    /**
     * 取得唯一实例
     * @return 产品列表唯一实例
     */
    public static ProductList getInstance() {
        if (instance == null) {
            instance = new ProductList();
            instance.productList = new ArrayList<>();
        }
        return instance;
    }
    
    /**
     * 增加观察者（电商接口）
     * @param observer 观察者
     */
    public void addProductListObserver(Observer observer) {
        this.addObserver(observer);
    }
    
    /**
     *  新增产品
     * @param newProduct 新产品 
     */
    public void addProudct(String newProduct) {
        productList.add(newProduct);
        System.err.println("产品列表新增了产品："+newProduct);
        this.setChanged();//设置被观察对象发生变化
        this.notifyObservers(newProduct);//通知观察者，并传递新产品
    }
}
