package model;

import java.io.Serializable;

public class Employee implements Serializable {
 private int employee_id;
 private int position_id;
 private int role_id;
 private String first_name;
 private String last_name;
 private String email;
 private String login;
 private String password;
 private String hire_date;
 private String is_union_member;
 private int access;
private Salary salary;
private double work_experience;

    public double getWork_experience() {
        return work_experience;
    }

    public void setWork_experience(double work_experience) {
        this.work_experience = work_experience;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    private String position;
 private String role;
 private String access_level;
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAccess_level() {
        return access_level;
    }

    public void setAccess_level(String access_level) {
        this.access_level = access_level;
    }

    public Employee(int employee_id, String first_name, String last_name, String email, String login, String hire_date, String password, String is_union_member, String position, String role, String access_level) {
        this.employee_id = employee_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.login = login;
        this.hire_date = hire_date;
        this.password = password;
        this.is_union_member = is_union_member;
        this.position = position;
        this.role = role;
        this.access_level = access_level;
    }



    public Employee() {
        this.employee_id = -1;
        this.position_id = -1;
        this.role_id = -1;
        this.first_name = "";
        this.last_name = "";
        this.email = "";
        this.login = "";
        this.password = "";
        this.hire_date = "";
        this.is_union_member = "";
        this.access = 0;
    }

    public Employee(int employee_id, int position_id, int role_id, String first_name, String last_name, String email, String login, String password, String hire_date, String is_union_member, int access) {
        this.employee_id = employee_id;
        this.position_id = position_id;
        this.role_id = role_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.hire_date = hire_date;
        this.is_union_member = is_union_member;
        this.access = access;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getPosition_id() {
        return position_id;
    }

    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHire_date() {
        return hire_date;
    }

    public void setHire_date(String hire_date) {
        this.hire_date = hire_date;
    }

    public String getIs_union_member() {
        return is_union_member;
    }

    public void setIs_union_member(String is_union_member) {
        this.is_union_member = is_union_member;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }
    @Override
    public String toString() {
        return "Employee " + employee_id + ", login=" + login + ", password=" + password +
                " , first_name=" + first_name + ", last_name=" + last_name + ", email=" +
                email + ", role_id=" + role_id + ", access=" + access +
                "hire_date=" + hire_date + ", is_union_member=" + is_union_member
                + ", position=" + position + ", position_id=" + position_id;
    }
}
