ALTER TABLE link
ALTER COLUMN updated_at TYPE TIMESTAMP WITH TIME ZONE
USING updated_at AT TIME ZONE 'UTC';