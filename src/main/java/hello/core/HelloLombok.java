package hello.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HelloLombok {

    private String name;
    private int age;

    public static void main(String[] args) {
        HelloLombok helloLombok = new HelloLombok();
        helloLombok.setName("jhkim");
        helloLombok.setAge(27);

        System.out.println("helloLombok = " + helloLombok);
        // toString lombok 추가 결과
        // -> helloLombok = HelloLombok(name=jhkim, age=27)

        

    }

}
