package com.guidemo;
import java.util.ArrayList;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        String input = "cricket";
        switch(input){
            case "bat":
                System.out.println("ball");
                break;
            case "match":
                System.out.println("T20");
            case "Boundry":
                System.out.println("six or four");
                break;
            case "out":
                System.out.println("umpire decision");
            default:
                System.out.println(input);

                List<String> list = new ArrayList<String>();
                list.add("1\n");
                list.add("2\n");
                list.add("3\n");
                list.add("4\n");
                for (int i = 0; i < list.size(); i++)
                    System.out.print(list.remove(i));
        }
    }

}
