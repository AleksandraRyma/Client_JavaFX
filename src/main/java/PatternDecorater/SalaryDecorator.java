package PatternDecorater;

public abstract class SalaryDecorator implements SalaryCalculator {
    protected SalaryCalculator wrapped;

    public SalaryDecorator(SalaryCalculator wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public double calculateSalary() {
        return wrapped.calculateSalary();
    }
}
