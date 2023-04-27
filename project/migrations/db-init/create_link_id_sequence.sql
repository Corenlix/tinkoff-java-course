CREATE SEQUENCE link_id_seq;

ALTER TABLE link ALTER COLUMN id SET DEFAULT nextval('link_id_seq');