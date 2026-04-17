CREATE TABLE loan_application (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name varchar(32) not null,
    last_name varchar(32) not null,
    personal_code varchar(11) not null unique,
    loan_period_months int not null,
    interest_margin numeric(10,3) not null,
    base_interest_rate numeric(10,3) not null,
    loan_amount numeric(15,2) not null,
    status varchar(30) not null,
    rejection_reason varchar(50),
    created_at timestamp not null
);

CREATE TABLE payment_schedule (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    loan_application_id BIGINT not null references loan_application(id),
    payment_number integer not null,
    payment_date date not null,
    principal numeric(15,2) not null,
    interest numeric(15,2) not null,
    total_payment numeric(15,2) not null,
    remaining_balance numeric(15,2) not null
);