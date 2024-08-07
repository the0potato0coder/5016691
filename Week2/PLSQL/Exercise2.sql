-- Senario 1:

CREATE OR REPLACE PROCEDURE SafeTransferFunds(
    p_from_account NUMBER,
    p_to_account NUMBER,
    p_amount NUMBER
)
AS
    v_from_balance NUMBER;
    insufficient_funds EXCEPTION;
    PRAGMA EXCEPTION_INIT(insufficient_funds, -20001);
BEGIN
    -- Start transaction
    SAVEPOINT start_transfer;

    -- Check if source account has sufficient funds
    SELECT Balance INTO v_from_balance
    FROM Accounts
    WHERE AccountID = p_from_account
    FOR UPDATE;

    IF v_from_balance < p_amount THEN
        RAISE insufficient_funds;
    END IF;

    -- Update balances
    UPDATE Accounts
    SET Balance = Balance - p_amount
    WHERE AccountID = p_from_account;

    UPDATE Accounts
    SET Balance = Balance + p_amount
    WHERE AccountID = p_to_account;

    -- Commit transaction
    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Transfer completed successfully.');

EXCEPTION
    WHEN insufficient_funds THEN
        ROLLBACK TO start_transfer;
        DBMS_OUTPUT.PUT_LINE('Error: Insufficient funds in source account.');
    WHEN NO_DATA_FOUND THEN
        ROLLBACK TO start_transfer;
        DBMS_OUTPUT.PUT_LINE('Error: One or both account IDs are invalid.');
    WHEN OTHERS THEN
        ROLLBACK TO start_transfer;
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END SafeTransferFunds;
/

-- Senario 2:

CREATE OR REPLACE PROCEDURE UpdateSalary(
    p_employee_id NUMBER,
    p_increase_percentage NUMBER
)
AS
    v_current_salary NUMBER;
    employee_not_found EXCEPTION;
    PRAGMA EXCEPTION_INIT(employee_not_found, -20002);
BEGIN
    -- Check if employee exists
    SELECT Salary INTO v_current_salary
    FROM Employees
    WHERE EmployeeID = p_employee_id
    FOR UPDATE;

    -- Update salary
    UPDATE Employees
    SET Salary = Salary * (1 + p_increase_percentage / 100)
    WHERE EmployeeID = p_employee_id;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Salary updated successfully.');

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE employee_not_found;
    WHEN employee_not_found THEN
        DBMS_OUTPUT.PUT_LINE('Error: Employee ID ' || p_employee_id || ' not found.');
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END UpdateSalary;
/

-- Senario 3:

CREATE OR REPLACE PROCEDURE AddNewCustomer(
    p_customer_id NUMBER,
    p_name VARCHAR2,
    p_dob DATE,
    p_balance NUMBER
)
AS
    duplicate_customer EXCEPTION;
    PRAGMA EXCEPTION_INIT(duplicate_customer, -20003);
BEGIN
    -- Check if customer already exists
    IF EXISTS (SELECT 1 FROM Customers WHERE CustomerID = p_customer_id) THEN
        RAISE duplicate_customer;
    END IF;

    -- Insert new customer
    INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
    VALUES (p_customer_id, p_name, p_dob, p_balance, SYSDATE);

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('New customer added successfully.');

EXCEPTION
    WHEN duplicate_customer THEN
        DBMS_OUTPUT.PUT_LINE('Error: Customer with ID ' || p_customer_id || ' already exists.');
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END AddNewCustomer;
/


