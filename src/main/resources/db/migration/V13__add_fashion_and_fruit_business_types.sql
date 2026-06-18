ALTER TABLE users
    DROP CONSTRAINT IF EXISTS users_business_type_check;

ALTER TABLE users
    ADD CONSTRAINT users_business_type_check
        CHECK (business_type IN ('SUPERMARKET', 'RESTAURANT', 'FASHION', 'FRUIT', 'BOTH'));
