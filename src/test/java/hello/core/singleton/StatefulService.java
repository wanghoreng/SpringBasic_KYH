package hello.core.singleton;

public class StatefulService {

    //private int price; // 상태를 유지하는 필드 - 무상태 유지위해 주석

    public int order(String name, int price) {
        System.out.println("name = " + name + ", price = " + price);
//        this.price = price;
        return price; // 지역변수가 되어 다른 객체와 충돌이 나지 않는다.
    }

    /*public int getPrice() {
        return price;
    }*/
}
