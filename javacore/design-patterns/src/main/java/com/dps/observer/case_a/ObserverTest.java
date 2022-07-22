package com.dps.observer.case_a;

public class ObserverTest {
	
	public static void main(String[] args) {
		ProductList observable = ProductList.getInstance(); //被观察者（被构造一个实例，可以添加观察者，改变自身行为）
		TaoBaoObserver taoBaoObserver = new TaoBaoObserver();
		JingDongObserver jdObserver = new JingDongObserver();
		observable.addObserver(jdObserver); //添加一个观察者
		observable.addObserver(taoBaoObserver); //添加一个观察者
		observable.addProudct("新增产品1"); //改变被观察者行为
	}
}
