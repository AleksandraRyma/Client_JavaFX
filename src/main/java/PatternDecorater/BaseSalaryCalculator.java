package PatternDecorater;

public class BaseSalaryCalculator implements SalaryCalculator {
    private double baseSalary;
    private double totalBonus;
    private double vacationBonus;
    private int taxPercentage;

    public BaseSalaryCalculator(double baseSalary, double totalBonus, double vacationBonus, int taxPercentage) {
        this.baseSalary = baseSalary;
        this.totalBonus = totalBonus;
        this.vacationBonus = vacationBonus;
        this.taxPercentage = taxPercentage;
    }
@Override
    public double calculateSalary() {
        double gross = baseSalary + totalBonus + vacationBonus;
        double tax = gross * taxPercentage/100.0;
        return gross - tax;
}
}
