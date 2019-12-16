package wenjing.lambda;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.thymeleaf.expression.Lists;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * @Author: Salexal.fww
 * @Date: 2019/12/12 14:03
 * @Version 1.0
 * @Type
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class Employee {

    private String name;
    private int salary;
    private String office;
}


/**
 * @Author: Salexal.fww
 * @Date: 2019/12/12 14:03
 * @Version 1.0
 * @Type
 */
public class ExampleEmployee {

    private static List<Employee> employeeList = new ArrayList<>();

    static {
        employeeList.add(Employee.builder().name("Matt").salary(5000).office("New York").build());
        employeeList.add(Employee.builder().name("Steve").salary(6000).office("London").build());
        employeeList.add(Employee.builder().name("Carrie").salary(20000).office("New York").build());
        employeeList.add(Employee.builder().name("Peter").salary(7000).office("New York").build());
        employeeList.add(Employee.builder().name("Pat").salary(8000).office("London").build());
        employeeList.add(Employee.builder().name("Tammy").salary(29000).office("Shanghai").build());
    }

    public static void main(String[] args) {
        //anyMatch
        boolean isMatch = employeeList.stream().anyMatch(employee -> "London".equals(employee.getOffice()));
        System.out.println(isMatch);

        //所有salary大于6000?
        boolean matched = employeeList.stream().allMatch(employee -> employee.getSalary() > 6000);
        System.out.println(matched);

        //所有salary大于8000
        List<Employee> employeeLists = employeeList.stream().filter((Employee e) -> e.getSalary() > 8000).collect(toList());
        System.out.println(employeeLists);

        //找出工资最高
        Optional<Employee> hightestSalary = employeeList.stream().max(Comparator.comparingInt(Employee::getSalary));
        System.out.println(hightestSalary);

        //返回姓名列表
        List<String> names = employeeList.stream().map(Employee::getName).collect(toList());
        System.out.println(names);

        //List转换成Map
        Map<String, Employee> employeeMap = employeeList.stream().collect(Collectors.toMap((Employee::getName), (value -> value)));
        employeeMap.forEach((key, value) -> System.out.println(key + " = " + value));

        //统计办公室是New York的个数
        long officeCount = employeeList.stream().filter(employee -> "New York".equals(employee.getOffice())).count();
        System.out.println(officeCount);

        //List转换为Set
        Set<String> officeSet = employeeList.stream().map(Employee::getOffice).collect(Collectors.toSet());
        System.out.println(officeSet);

        // 查找办公室地点是New York的员工 找到即返回
        // 注意找不到会报错 @NotNull 使用Optional可以避免 java.util.NoSuchElementException: No value present
        Optional<Employee> allMatchedEmployees = employeeList.stream().filter(employee -> "New".equals(employee.getOffice())).findAny();
        System.out.println(allMatchedEmployees);

        //按照工资的降序来列出员工信息
        List<Employee> sortEmployeeList = employeeList.stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed()).collect(toList());

        //按照名字的升序列出员工信息
        List<Employee> sortEmployeeByName = employeeList.stream().sorted(Comparator.comparing(Employee::getName)).collect(toList());
        System.out.println(sortEmployeeList);
        System.out.println("按照名字的升序列出员工信息:" + sortEmployeeByName);

        //获取工资最高的前2条员工信息
        List<Employee> top2EmployeeList = employeeList.stream()
                .sorted((e1, e2) -> Integer.compare(e2.getSalary(), e1.getSalary()))
                .limit(2)
                .collect(toList());
        System.out.println(top2EmployeeList);

        //获取平均工资
        OptionalDouble averageSalary = employeeList.stream().mapToInt(Employee::getSalary).average();
        System.out.println("平均工资:" + averageSalary);

        //查找New York
        OptionalDouble averageSalaryByOffice = employeeList.stream().filter(employee -> "New York"
                .equals(employee.getOffice()))
                .mapToInt(Employee::getSalary)
                .average();
        System.out.println("New York办公室平均工资:" + averageSalaryByOffice);

        // 下面为补充

        // 将相同的office的人员分类存放
        Map<String, List<Employee>> listMap = employeeList.stream().collect(Collectors.groupingBy(Employee::getOffice));
        System.out.println("将相同的office的人员分类存放");
        System.out.println(listMap.keySet());
        System.out.println(listMap.values());

        // filter中 使用 Predicate接口 筛选在NewYork salary>7000的人
        // Predicate接口 and , or , negate 是 按照我们写的顺序来顺序执行的
        Predicate<Employee> p1 = e -> "New York".equals(e.getOffice());
        Predicate<Employee> p2 = p1.and(e -> e.getSalary() > 7000);
        List<Employee> list = employeeList.stream().filter(p2).collect(toList());
        System.out.println("筛选在NewYork salary>7000的人");
        System.out.println(list);

        //函数复合
        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        Function<Integer, Integer> h1 = f.andThen(g);
        Function<Integer, Integer> h2 = f.compose(g);
        System.out.println("Function函数复合 andThen, compose");
        System.out.println(h1.apply(1));
        System.out.println(h2.apply(1));

        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);
        List<int[]> pairs = numbers1.stream().flatMap(i -> numbers2.stream().map(j -> new int[]{i, j})).collect(toList());

        //reduce （初始值，一个BinaryOperator<T>）没有初始值是 要是用Optional避免 NoSuchElementException
        // 其实有比这个更好的方法可以直接使用 sum 和 min
        List<Integer> numbers = Arrays.asList(1, 2, 3,4,5,6,7,8,9);
        System.out.println("reduce 相乘");
        System.out.println(numbers.stream().reduce(1,(a,b)->a*b));
        Optional<Integer> sum = numbers.stream().reduce(Integer::sum);
        Optional<Integer> min = numbers.stream().reduce(Integer::min);
        Optional<Integer> mins = numbers.stream().min(Integer::compareTo);
        if (sum.isPresent()) {
            System.out.println(sum.get());
            System.out.println(mins.get());
        } else {
            System.out.println("空数据");
        }

        // range 和 rangeClosed 前者不包括结尾
        System.out.println("range 和 rangeClosed 的区别");
        System.out.println(IntStream.range(1,100).filter(i -> i%2 == 0).count());
        System.out.println(IntStream.rangeClosed(1,100).filter(i -> i%2 == 0).count());

        // 生成勾股数
        IntStream.rangeClosed(1,100)
                .boxed()
                .flatMap(a ->
                        IntStream.rangeClosed(a,100)
                                .filter(b -> Math.sqrt(a * a + b* b) % 1 == 0)
                                .mapToObj(b ->
                                        new int[]{a, b, (int) Math.sqrt(a*a+b*b)}))
                .limit(5)
                .forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));
    }
}