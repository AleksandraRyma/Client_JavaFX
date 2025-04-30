package PatternDecorater;

import model.Employee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UnionNewYearBonusDecorator extends SalaryDecorator{
    private Employee employee;
    private String paymentDate;

    public UnionNewYearBonusDecorator(SalaryCalculator wrapped, Employee employee, String paymentDate) {
        super(wrapped);
        this.employee = employee;
        this.paymentDate = paymentDate;
    }

    @Override
    public double calculateSalary() {
        double salary = super.calculateSalary();

        if (!"true".equalsIgnoreCase(employee.getIs_union_member())) {
            return salary;
        }

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(paymentDate);
            SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
            String month = monthFormat.format(date);

            if ("12".equals(month)) {
                salary += 200.0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return salary;
    }
}
