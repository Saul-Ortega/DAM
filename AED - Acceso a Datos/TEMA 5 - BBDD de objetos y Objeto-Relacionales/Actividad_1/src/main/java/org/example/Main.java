package org.example;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import java.util.ArrayList;
import java.util.List;

public class Main {
    static void main() {
        //OPEN THE DB4O FILE OR CREATES IT IF NOT EXISTS
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "src/main/resources/db.db40");

        //CREATE DEPARTMENTS
        Department accountingDepartment = new Department("Accounting", "Canarias", null);
        Department salesDepartment = new Department("Sales", "Canarias", null);
        Department humanResourcesDepartment = new Department("Human Resources", "Canarias", null);
        Department taxesDepartment = new Department("Taxes", "Canarias", null);

        //CREATE EMPLOYEES
        Employee accountingEmployee1 = new Employee("123", "Roberto", "Lopez", "Las Palmas", "666999333", 800, accountingDepartment);
        Employee accountingEmployee2 = new Employee("124", "Jorge", "Ramirez", "Telde", "666999444", 700, accountingDepartment);
        Employee accountingEmployee3 = new Employee("125", "Javier", "Tarrassa", "Agüimes", "666999555", 1100, accountingDepartment);

        List<Employee> accountingEmployees = new ArrayList<>();
        accountingEmployees.add(accountingEmployee1);
        accountingEmployees.add(accountingEmployee2);
        accountingEmployees.add(accountingEmployee3);

        accountingDepartment.setEmployees(accountingEmployees);

        Employee salesEmployee1 = new Employee("126", "Lucas", "Martin", "Telde", "666999666", 500, salesDepartment);
        Employee salesEmployee2 = new Employee("127", "Ainhoa", "Martin", "Las Palmas", "666999777", 1200, salesDepartment);
        Employee salesEmployee3 = new Employee("128", "Alejandro", "Mendez", "Agüimes", "777999666", 1150, salesDepartment);

        List<Employee> salesEmployees = new ArrayList<>();
        salesEmployees.add(salesEmployee1);
        salesEmployees.add(salesEmployee2);
        salesEmployees.add(salesEmployee3);

        salesDepartment.setEmployees(salesEmployees);

        Employee humanResourcesEmployee1 = new Employee("129", "Tomas", "Herrera", "Agüimes", "666888666", 799, humanResourcesDepartment);
        Employee humanResourcesEmployee2 = new Employee("130", "Yedra", "Ramirez", "Telde", "666777666", 1330, humanResourcesDepartment);
        Employee humanResourcesEmployee3 = new Employee("131", "Omar", "Moreno", "Las Palmas", "999888666", 1210, humanResourcesDepartment);

        List<Employee> humanResourcesEmployees = new ArrayList<>();
        humanResourcesEmployees.add(humanResourcesEmployee1);
        humanResourcesEmployees.add(humanResourcesEmployee2);
        humanResourcesEmployees.add(humanResourcesEmployee3);

        humanResourcesDepartment.setEmployees(humanResourcesEmployees);

        Employee taxesEmployee1 = new Employee("132", "Raul", "Lopez", "Las Palmas", "111888666", 1114, taxesDepartment);
        Employee taxesEmployee2 = new Employee("133", "Saul", "Ortega", "Telde", "222888666", 780, taxesDepartment);
        Employee taxesEmployee3 = new Employee("134", "Gabriel", "Travieso", "Agüimes", "333888666", 1240, taxesDepartment);

        List<Employee> taxesEmployees = new ArrayList<>();
        taxesEmployees.add(taxesEmployee1);
        taxesEmployees.add(taxesEmployee2);
        taxesEmployees.add(taxesEmployee3);

        taxesDepartment.setEmployees(taxesEmployees);

        //INSERT DEPARTMENTS
        System.out.println("Inserting departments...");

        //TEST IF DEPARTMENTS ALREADY EXISTS
        ObjectSet<Department> accountingDepartmentExists = db.queryByExample(accountingDepartment);
        ObjectSet<Department> salesDepartmentExists = db.queryByExample(salesDepartment);
        ObjectSet<Department> humanResourcesDepartmentExists = db.queryByExample(humanResourcesDepartment);
        ObjectSet<Department> taxesDepartmentExists = db.queryByExample(taxesDepartment);

        if ( accountingDepartmentExists.isEmpty() ) db.store(accountingDepartment);
        if ( salesDepartmentExists.isEmpty() ) db.store(salesDepartment);
        if ( humanResourcesDepartmentExists.isEmpty() ) db.store(humanResourcesDepartment);
        if ( taxesDepartmentExists.isEmpty() ) db.store(taxesDepartment);

        System.out.println("Departments inserted");

        //INSERT EMPLOYEES
        System.out.println("Inserting employees...");

        //TEST IF EMPLOYEES ALREADY EXISTS
        ObjectSet<Employee> accountEmployee1Exists = db.queryByExample(accountingEmployee1);
        ObjectSet<Employee> accountEmployee2Exists = db.queryByExample(accountingEmployee2);
        ObjectSet<Employee> accountEmployee3Exists = db.queryByExample(accountingEmployee3);
        ObjectSet<Employee> salesEmployee1Exists = db.queryByExample(salesEmployee1);
        ObjectSet<Employee> salesEmployee2Exists = db.queryByExample(salesEmployee2);
        ObjectSet<Employee> salesEmployee3Exists = db.queryByExample(salesEmployee3);
        ObjectSet<Employee> humanResourcesEmployee1Exists = db.queryByExample(humanResourcesEmployee1);
        ObjectSet<Employee> humanResourcesEmployee2Exists = db.queryByExample(humanResourcesEmployee2);
        ObjectSet<Employee> humanResourcesEmployee3Exists = db.queryByExample(humanResourcesEmployee3);
        ObjectSet<Employee> taxesEmployee1Exists = db.queryByExample(taxesEmployee1);
        ObjectSet<Employee> taxesEmployee2Exists = db.queryByExample(taxesEmployee2);
        ObjectSet<Employee> taxesEmployee3Exists = db.queryByExample(taxesEmployee3);

        if ( accountEmployee1Exists.isEmpty() ) db.store(accountingEmployee1);
        if ( accountEmployee2Exists.isEmpty() ) db.store(accountingEmployee2);
        if ( accountEmployee3Exists.isEmpty() ) db.store(accountingEmployee3);
        if ( salesEmployee1Exists.isEmpty() ) db.store(salesEmployee1);
        if ( salesEmployee2Exists.isEmpty() ) db.store(salesEmployee2);
        if ( salesEmployee3Exists.isEmpty() ) db.store(salesEmployee3);
        if ( humanResourcesEmployee1Exists.isEmpty() ) db.store(humanResourcesEmployee1);
        if ( humanResourcesEmployee2Exists.isEmpty() ) db.store(humanResourcesEmployee2);
        if ( humanResourcesEmployee3Exists.isEmpty() ) db.store(humanResourcesEmployee3);
        if ( taxesEmployee1Exists.isEmpty() ) db.store(taxesEmployee1);
        if ( taxesEmployee2Exists.isEmpty() ) db.store(taxesEmployee2);
        if ( taxesEmployee3Exists.isEmpty() ) db.store(taxesEmployee3);

        System.out.println("Employees inserted");

        //SHOW ALL DEPARTMENTS AND CONTENTS
        ObjectSet<Department> departmentResult = db.queryByExample(new Department());

        System.out.println("Searching departments...");
        while ( departmentResult.hasNext() ) {
            Department department = departmentResult.next();
            System.out.println("Department: " + department.getName() +
                               "\nLocalization: " + department.getLocalization());
            System.out.println("Employees: ");
            department.getEmployees().forEach(System.out::println);
            System.out.println("==========================================");
        }

        //SHOW ALL EMPLOYEES WHO WORK IN A CERTAIN LOCALIZATION
        ObjectSet<Employee> employeeResult = db.queryByExample(new Employee(null, null, null, "Las Palmas", null, 0, null));

        System.out.println("Searching employees by localization...");
        while ( employeeResult.hasNext() ) {
            Employee employee = employeeResult.next();
            System.out.println(employee);
        }

        //UPDATE AN EMPLOYEE'S NAME
        employeeResult = db.queryByExample(new Employee("132", null, null, null, null, 0, null));

        System.out.println("Updating employee with Id: 132...");
        if ( employeeResult.size() == 0 ) {
            System.out.println("Employee with Id: 132 does not exist");
        } else {
            Employee employee = employeeResult.next();
            employee.setName("ChangedName");
            db.store(employee);
            System.out.println("Employee's name updated");
        }

        //SHOW ALL EMPLOYEES NAMES
        employeeResult = db.queryByExample(new Employee());
        System.out.println("Getting al employees names...");
        while ( employeeResult.hasNext() ) {
            Employee employee = employeeResult.next();
            System.out.println(employee.getName());
        }
        
        db.close();
    }
}
