Senario 1:

CREATE OR REPLACE PROCEDURE GenerateMonthlyStatements IS
    CURSOR cust_cur IS
        SELECT DISTINCT c.CustomerID, c.Name
        FROM Customers c
        JOIN Accounts a ON c.CustomerID = a.CustomerID
        JOIN Transactions t ON a.AccountID = t.AccountID
        WHERE EXTRACT(MONTH FROM t.TransactionDate) = EXTRACT(MONTH FROM SYSDATE)
        AND EXTRACT(YEAR FROM t.TransactionDate) = EXTRACT(YEAR FROM SYSDATE);
    
    CURSOR trans_cur(p_customer_id NUMBER) IS
        SELECT a.AccountID, t.TransactionDate, t.Amount, t.TransactionType
        FROM Accounts a
        JOIN Transactions t ON a.AccountID = t.AccountID
        WHERE a.CustomerID = p_customer_id
        AND EXTRACT(MONTH FROM t.TransactionDate) = EXTRACT(MONTH FROM SYSDATE)
        AND EXTRACT(YEAR FROM t.TransactionDate) = EXTRACT(YEAR FROM SYSDATE);
    
    v_total_balance NUMBER;
BEGIN
    FOR cust_rec IN cust_cur LOOP
        DBMS_OUTPUT.PUT_LINE('Monthly Statement for ' || cust_rec.Name);
        DBMS_OUTPUT.PUT_LINE('----------------------------------------');
        
        v_total_balance := 0;
        
        FOR trans_rec IN trans_cur(cust_rec.CustomerID) LOOP
            DBMS_OUTPUT.PUT_LINE('Account: ' || trans_rec.AccountID || 
                                 ', Date: ' || TO_CHAR(trans_rec.TransactionDate, 'DD-MON-YYYY') ||
                                 ', Amount: ' || trans_rec.Amount || 
                                 ', Type: ' || trans_rec.TransactionType);
            
            IF trans_rec.TransactionType = 'Deposit' THEN
                v_total_balance := v_total_balance + trans_rec.Amount;
            ELSIF trans_rec.TransactionType = 'Withdrawal' THEN
                v_total_balance := v_total_balance - trans_rec.Amount;
            END IF;
        END LOOP;
        
        DBMS_OUTPUT.PUT_LINE('Total Balance Change: ' || v_total_balance);
        DBMS_OUTPUT.PUT_LINE('----------------------------------------');
    END LOOP;
END GenerateMonthlyStatements;
/

-- Senario 2:

CREATE OR REPLACE PROCEDURE ApplyAnnualFee IS
    CURSOR acc_cur IS
        SELECT AccountID, Balance
        FROM Accounts
        FOR UPDATE OF Balance;
    
    v_annual_fee NUMBER := 50; -- Set your annual fee amount here
BEGIN
    FOR acc_rec IN acc_cur LOOP
        IF acc_rec.Balance >= v_annual_fee THEN
            UPDATE Accounts
            SET Balance = Balance - v_annual_fee
            WHERE CURRENT OF acc_cur;
            
            DBMS_OUTPUT.PUT_LINE('Annual fee applied to Account ' || acc_rec.AccountID);
        ELSE
            DBMS_OUTPUT.PUT_LINE('Insufficient balance in Account ' || acc_rec.AccountID || ' to apply annual fee');
        END IF;
    END LOOP;
    
    COMMIT;
END ApplyAnnualFee;
/

-- Senario 3:

CREATE OR REPLACE PROCEDURE UpdateLoanInterestRates IS
    CURSOR loan_cur IS
        SELECT LoanID, LoanAmount, InterestRate
        FROM Loans
        FOR UPDATE OF InterestRate;
    
    v_new_rate NUMBER;
BEGIN
    FOR loan_rec IN loan_cur LOOP
        -- Example policy: Increase rate by 0.5% for loans over 10000, decrease by 0.25% for others
        IF loan_rec.LoanAmount > 10000 THEN
            v_new_rate := loan_rec.InterestRate + 0.5;
        ELSE
            v_new_rate := loan_rec.InterestRate - 0.25;
        END IF;
        
        UPDATE Loans
        SET InterestRate = v_new_rate
        WHERE CURRENT OF loan_cur;
        
        DBMS_OUTPUT.PUT_LINE('Updated interest rate for Loan ' || loan_rec.LoanID || ' from ' || 
                             loan_rec.InterestRate || '% to ' || v_new_rate || '%');
    END LOOP;
    
    COMMIT;
END UpdateLoanInterestRates;
/
