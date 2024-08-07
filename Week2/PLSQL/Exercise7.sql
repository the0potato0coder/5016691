-- Senario 1:

CREATE OR REPLACE PACKAGE CustomerManagement AS
    PROCEDURE AddNewCustomer(
        p_name VARCHAR2,
        p_dob DATE,
        p_initial_balance NUMBER
    );
    
    PROCEDURE UpdateCustomerDetails(
        p_customer_id NUMBER,
        p_name VARCHAR2,
        p_dob DATE
    );
    
    FUNCTION GetCustomerBalance(p_customer_id NUMBER) RETURN NUMBER;
END CustomerManagement;
/

CREATE OR REPLACE PACKAGE BODY CustomerManagement AS
    PROCEDURE AddNewCustomer(
        p_name VARCHAR2,
        p_dob DATE,
        p_initial_balance NUMBER
    ) IS
        v_customer_id NUMBER;
    BEGIN
        INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
        VALUES (Customers_Seq.NEXTVAL, p_name, p_dob, p_initial_balance, SYSDATE)
        RETURNING CustomerID INTO v_customer_id;
        
        DBMS_OUTPUT.PUT_LINE('New customer added with ID: ' || v_customer_id);
    END AddNewCustomer;
    
    PROCEDURE UpdateCustomerDetails(
        p_customer_id NUMBER,
        p_name VARCHAR2,
        p_dob DATE
    ) IS
    BEGIN
        UPDATE Customers
        SET Name = p_name,
            DOB = p_dob,
            LastModified = SYSDATE
        WHERE CustomerID = p_customer_id;
        
        IF SQL%ROWCOUNT = 0 THEN
            RAISE_APPLICATION_ERROR(-20001, 'Customer not found');
        ELSE
            DBMS_OUTPUT.PUT_LINE('Customer details updated successfully');
        END IF;
    END UpdateCustomerDetails;
    
    FUNCTION GetCustomerBalance(p_customer_id NUMBER) RETURN NUMBER IS
        v_balance NUMBER;
    BEGIN
        SELECT Balance INTO v_balance
        FROM Customers
        WHERE CustomerID = p_customer_id;
        
        RETURN v_balance;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE_APPLICATION_ERROR(-20002, 'Customer not found');
    END GetCustomerBalance;
END CustomerManagement;
/

-- Senario 2:

CREATE OR REPLACE PACKAGE EmployeeManagement AS
    PROCEDURE HireNewEmployee(
        p_name VARCHAR2,
        p_position VARCHAR2,
        p_salary NUMBER,
        p_department VARCHAR2
    );
    
    PROCEDURE UpdateEmployeeDetails(
        p_employee_id NUMBER,
        p_position VARCHAR2,
        p_salary NUMBER,
        p_department VARCHAR2
    );
    
    FUNCTION CalculateAnnualSalary(p_employee_id NUMBER) RETURN NUMBER;
END EmployeeManagement;
/

CREATE OR REPLACE PACKAGE BODY EmployeeManagement AS
    PROCEDURE HireNewEmployee(
        p_name VARCHAR2,
        p_position VARCHAR2,
        p_salary NUMBER,
        p_department VARCHAR2
    ) IS
        v_employee_id NUMBER;
    BEGIN
        INSERT INTO Employees (EmployeeID, Name, Position, Salary, Department, HireDate)
        VALUES (Employees_Seq.NEXTVAL, p_name, p_position, p_salary, p_department, SYSDATE)
        RETURNING EmployeeID INTO v_employee_id;
        
        DBMS_OUTPUT.PUT_LINE('New employee hired with ID: ' || v_employee_id);
    END HireNewEmployee;
    
    PROCEDURE UpdateEmployeeDetails(
        p_employee_id NUMBER,
        p_position VARCHAR2,
        p_salary NUMBER,
        p_department VARCHAR2
    ) IS
    BEGIN
        UPDATE Employees
        SET Position = p_position,
            Salary = p_salary,
            Department = p_department
        WHERE EmployeeID = p_employee_id;
        
        IF SQL%ROWCOUNT = 0 THEN
            RAISE_APPLICATION_ERROR(-20003, 'Employee not found');
        ELSE
            DBMS_OUTPUT.PUT_LINE('Employee details updated successfully');
        END IF;
    END UpdateEmployeeDetails;
    
    FUNCTION CalculateAnnualSalary(p_employee_id NUMBER) RETURN NUMBER IS
        v_monthly_salary NUMBER;
    BEGIN
        SELECT Salary INTO v_monthly_salary
        FROM Employees
        WHERE EmployeeID = p_employee_id;
        
        RETURN v_monthly_salary * 12;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE_APPLICATION_ERROR(-20004, 'Employee not found');
    END CalculateAnnualSalary;
END EmployeeManagement;
/


-- Senario 3:

CREATE OR REPLACE PACKAGE AccountOperations AS
    PROCEDURE OpenNewAccount(
        p_customer_id NUMBER,
        p_account_type VARCHAR2,
        p_initial_balance NUMBER
    );
    
    PROCEDURE CloseAccount(p_account_id NUMBER);
    
    FUNCTION GetTotalCustomerBalance(p_customer_id NUMBER) RETURN NUMBER;
END AccountOperations;
/

CREATE OR REPLACE PACKAGE BODY AccountOperations AS
    PROCEDURE OpenNewAccount(
        p_customer_id NUMBER,
        p_account_type VARCHAR2,
        p_initial_balance NUMBER
    ) IS
        v_account_id NUMBER;
    BEGIN
        INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified)
        VALUES (Accounts_Seq.NEXTVAL, p_customer_id, p_account_type, p_initial_balance, SYSDATE)
        RETURNING AccountID INTO v_account_id;
        
        DBMS_OUTPUT.PUT_LINE('New account opened with ID: ' || v_account_id);
    END OpenNewAccount;
    
    PROCEDURE CloseAccount(p_account_id NUMBER) IS
        v_balance NUMBER;
    BEGIN
        SELECT Balance INTO v_balance
        FROM Accounts
        WHERE AccountID = p_account_id;
        
        IF v_balance <> 0 THEN
            RAISE_APPLICATION_ERROR(-20005, 'Account balance must be zero to close');
        ELSE
            DELETE FROM Accounts WHERE AccountID = p_account_id;
            
            IF SQL%ROWCOUNT = 0 THEN
                RAISE_APPLICATION_ERROR(-20006, 'Account not found');
            ELSE
                DBMS_OUTPUT.PUT_LINE('Account closed successfully');
            END IF;
        END IF;
    END CloseAccount;
    
    FUNCTION GetTotalCustomerBalance(p_customer_id NUMBER) RETURN NUMBER IS
        v_total_balance NUMBER;
    BEGIN
        SELECT SUM(Balance) INTO v_total_balance
        FROM Accounts
        WHERE CustomerID = p_customer_id;
        
        RETURN NVL(v_total_balance, 0);
    END GetTotalCustomerBalance;
END AccountOperations;
/

