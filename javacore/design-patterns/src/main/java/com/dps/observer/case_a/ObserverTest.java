package com.dps.observer.case_a;

public class ObserverTest {
	
	public static void main(String[] args) {
		ProductList observable = ProductList.getInstance(); //���۲��ߣ�������һ��ʵ����������ӹ۲��ߣ��ı�������Ϊ��
		TaoBaoObserver taoBaoObserver = new TaoBaoObserver();
		JingDongObserver jdObserver = new JingDongObserver();
		observable.addObserver(jdObserver); //���һ���۲���
		observable.addObserver(taoBaoObserver); //���һ���۲���
		observable.addProudct("������Ʒ1"); //�ı䱻�۲�����Ϊ
	}
}
