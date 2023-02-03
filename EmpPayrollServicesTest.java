package com.blz.employeepayroll.EmpPayrollServicesTest;

import com.blz.employeepayroll.EmpPayrollData;
import com.blz.employeepayroll.EmpPayrollServices;
import org.junit.Assert;
import org.junit.Test;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EmpPayrollServicesTest {
    @Test
    public void given3Employees_WhenWrittenToFile_ShouldMatchEmployeeEntries() {
        EmpPayrollData[] arrayOfEmployees = { new EmpPayrollData(1, "Jeff Bezos", 100000.0, LocalDate.now()),
                new EmpPayrollData(2, "Bill Gates", 200000.0, LocalDate.now()),
                new EmpPayrollData(3, "Mark Zuckerberg", 300000.0, LocalDate.now()) };
        EmpPayrollServices employeePayrollService;
        employeePayrollService = new EmpPayrollServices(Arrays.asList(arrayOfEmployees));
        employeePayrollService.writeEmployeePayrollData(EmpPayrollServices.IOService.FILE_IO);

        employeePayrollService.printData(EmpPayrollServices.IOService.FILE_IO);
        long entries = employeePayrollService.countEntries(EmpPayrollServices.IOService.FILE_IO);
        assertEquals(3, entries);

    }

    @Test
    public void givenFile_WhenRead_ShouldReturnNumberOfEntries() {
        EmpPayrollServices employeePayrollService = new EmpPayrollServices();
        long entries = employeePayrollService.readDataFromFile(EmpPayrollServices.IOService.FILE_IO);
        assertEquals(3, entries);
    }

    @Test
    public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() {

        EmpPayrollServices employeePayrollService = new EmpPayrollServices();
        List<EmpPayrollData> employeePayrollData = employeePayrollService
                .readEmployeePayrollData(EmpPayrollServices.IOService.DB_IO);
        System.out.println(employeePayrollData.size());
        assertEquals(0, employeePayrollData.size());
    }

    @Test
    public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB() {

        EmpPayrollServices employeePayrollService = new EmpPayrollServices();
        List<EmpPayrollData> employeePayrollData = employeePayrollService
                .readEmployeePayrollData(EmpPayrollServices.IOService.DB_IO);
        employeePayrollService.updateEmployeeSalary("Bill", 7000000.00);
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Bill");
        Assert.assertTrue(result);

    }

    @Test
    public void givenName_WhenFound_ShouldReturnEmployeeDetails() {

        EmpPayrollServices employeePayrollService = new EmpPayrollServices();
        String name = "Rosa Diaz";
        List<EmpPayrollData> employeePayrollData = employeePayrollService
                .getEmployeeDetailsBasedOnName(EmpPayrollServices.IOService.DB_IO, name);
        String resultName = employeePayrollData.get(0).employeeName;
        Assert.assertEquals(name, resultName);
    }

    @Test
    public void givenStartDateRange_WhenMatches_ShouldReturnEmployeeDetails() {

        String startDate = "2013-01-01";
        EmpPayrollServices employeePayrollService = new EmpPayrollServices();
        List<EmpPayrollData> employeePayrollData = employeePayrollService
                .getEmployeeDetailsBasedOnStartDate(EmpPayrollServices.IOService.DB_IO, startDate);
        System.out.println(employeePayrollData.size());
        assertEquals(0, employeePayrollData.size());
    }
}
