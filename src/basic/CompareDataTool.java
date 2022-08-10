package basic;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2021/11/25 1:34 上午
 * @Description
 */
public class CompareDataTool {
    public static void main(String[] args) {
        int[] arr = {1, 2, 5, 6, 4, 7, 3, 8, 0, 9};
        printArray(arr);
        Arrays.sort(arr);
        printArray(arr);

        Student s1 = new Student(3, "赵一", 32);
        Student s2 = new Student(2, "赵二", 23);
        Student s3 = new Student(4, "赵三", 54);
        Student s4 = new Student(1, "赵四", 34);
        Student s5 = new Student(5, "赵五", 25);
        System.out.println("---------------------------------");
        Student[] students = {s1, s2, s3, s4, s5};
        printStudents(students);
        Arrays.sort(students, new IdComparator());
        printStudents(students);
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void printStudents(Student[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i].id + ":" + arr[i].name + "-" + arr[i].age);
        }
        System.out.println();
    }


    private static class Student {
        private int id;
        private String name;
        private int age;

        public Student(int id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }
    }

    public static class IdComparator implements Comparator<Student> {
        @Override
        public int compare(Student o1, Student o2) {
            return o1.id - o2.id;
        }
    }
}
