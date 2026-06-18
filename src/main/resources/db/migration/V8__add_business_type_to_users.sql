ALTER TABLE users
    ADD COLUMN IF NOT EXISTS business_type VARCHAR(30) NOT NULL DEFAULT 'SUPERMARKET';

ALTER TABLE users
    DROP CONSTRAINT IF EXISTS users_business_type_check;

ALTER TABLE users
    ADD CONSTRAINT users_business_type_check
        CHECK (business_type IN ('SUPERMARKET', 'RESTAURANT', 'BOTH'));
