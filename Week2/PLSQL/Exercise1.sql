-- Senario 1:

DECLARE
    v_age NUMBER;
    v_discount NUMBER := 0.01; -- 1% discount
BEGIN
    FOR customer IN (SELECT CustomerID, DOB FROM Customers) LOOP
        v_age := TRUNC(MONTHS_BETWEEN(SYSDATE, customer.DOB) / 12);
        
        IF v_age > 60 THEN
            UPDATE Loans
            SET InterestRate = InterestRate - v_discount
            WHERE CustomerID = customer.CustomerID;
        END IF;
    END LOOP;
    
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Interest rate discounts applied for customers over 60.');
END;
/

-- Senario 2:

ALTER TABLE Customers ADD IsVIP VARCHAR2(5);

DECLARE
    v_vip_threshold NUMBER := 10000;
BEGIN
    UPDATE Customers
    SET IsVIP = CASE 
                    WHEN Balance > v_vip_threshold THEN 'TRUE'
                    ELSE 'FALSE'
                END;
    
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('VIP status updated for all customers.');
END;
/

Senario 3:

DECLARE
    v_reminder_days NUMBER := 30;
BEGIN
    FOR loan IN (
        SELECT c.Name, l.LoanID, l.EndDate
        FROM Loans l
        JOIN Customers c ON l.CustomerID = c.CustomerID
        WHERE l.EndDate BETWEEN SYSDATE AND SYSDATE + v_reminder_days
    ) LOOP
        DBMS_OUTPUT.PUT_LINE('Reminder: Dear ' || loan.Name || 
                             ', your loan (ID: ' || loan.LoanID || 
                             ') is due on ' || TO_CHAR(loan.EndDate, 'DD-MON-YYYY') || 
                             '. Please arrange for repayment.');
    END LOOP;
END;
/
