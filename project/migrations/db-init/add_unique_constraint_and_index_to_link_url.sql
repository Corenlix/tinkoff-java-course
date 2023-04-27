ALTER TABLE link ADD CONSTRAINT unique_url UNIQUE (url);
CREATE INDEX idx_url ON link(url);