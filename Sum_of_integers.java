import java.util.*;
class SumOfIntegers {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> numbers = new ArrayList<>();
        System.out.println("Enter integers separated by space:");
        String[] inputs = sc.nextLine().split(" ");
        for (String s : inputs) numbers.add(Integer.parseInt(s));
        int sum = 0;
        for (Integer n : numbers) sum += n;
        System.out.println("Sum: " + sum);
    }
}
