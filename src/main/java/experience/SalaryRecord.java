package experience;

public class SalaryRecord {
    private String firstName;
    private double baseSalary;
    private double totalBonus;
    private double vacationBonus;
    private double netSalary;
    private int taxPercentage;
    private int employeeID;
    private String paymentDate;

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public SalaryRecord(String firstName, double baseSalary, double totalBonus, double vacationBonus, double netSalary, int taxPercentage) {
        this.firstName = firstName;
        this.baseSalary = baseSalary;
        this.totalBonus = totalBonus;
        this.vacationBonus = vacationBonus;
        this.netSalary = netSalary;
        this.taxPercentage = taxPercentage;
    }

    public SalaryRecord(){

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public void setTotalBonus(double totalBonus) {
        this.totalBonus = totalBonus;
    }

    public void setVacationBonus(double vacationBonus) {
        this.vacationBonus = vacationBonus;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public void setTaxPercentage(int taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public double getTotalBonus() {
        return totalBonus;
    }

    public double getVacationBonus() {
        return vacationBonus;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public int getTaxPercentage() {
        return taxPercentage;
    }
}
