-- Senario 1:

CREATE OR REPLACE FUNCTION CalculateAge(p_dob DATE) 
RETURN NUMBER IS
BEGIN
    RETURN TRUNC(MONTHS_BETWEEN(SYSDATE, p_dob) / 12);
END CalculateAge;
/

-- Senario 2:

CREATE OR REPLACE FUNCTION CalculateMonthlyInstallment(
    p_loan_amount NUMBER,
    p_interest_rate NUMBER,
    p_duration_years NUMBER
) RETURN NUMBER IS
    v_monthly_rate NUMBER;
    v_num_payments NUMBER;
BEGIN
    v_monthly_rate := p_interest_rate / (12 * 100);
    v_num_payments := p_duration_years * 12;
    
    RETURN (p_loan_amount * v_monthly_rate * POWER(1 + v_monthly_rate, v_num_payments)) /
           (POWER(1 + v_monthly_rate, v_num_payments) - 1);
END CalculateMonthlyInstallment;
/

-- Senario 3:

CREATE OR REPLACE FUNCTION HasSufficientBalance(
    p_account_id NUMBER,
    p_amount NUMBER
) RETURN BOOLEAN IS
    v_balance NUMBER;
BEGIN
    SELECT Balance INTO v_balance
    FROM Accounts
    WHERE AccountID = p_account_id;
    
    RETURN v_balance >= p_amount;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN FALSE;
END HasSufficientBalance;
/
