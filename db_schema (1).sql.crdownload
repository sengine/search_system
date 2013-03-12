-- Schema: search_system

-- DROP SCHEMA search_system;

CREATE SCHEMA search_system
  AUTHORIZATION postgres;

-- Table: search_system.documents

-- DROP TABLE search_system.documents;

CREATE TABLE search_system.documents
(
  id bigint NOT NULL,
  content text NOT NULL,
  CONSTRAINT primary_key_documents PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE search_system.documents
  OWNER TO postgres;
-- Table: search_system.index

-- DROP TABLE search_system.index;

CREATE TABLE search_system.index
(
  object bytea,
  stamp timestamp with time zone NOT NULL DEFAULT now(),
  CONSTRAINT unique_constraint_index UNIQUE (stamp)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE search_system.index
  OWNER TO postgres;
-- Table: search_system.postings

-- DROP TABLE search_system.postings;

CREATE TABLE search_system.postings
(
  termid bigint NOT NULL,
  docid integer NOT NULL,
  CONSTRAINT unique_constraint_postings UNIQUE (termid, docid)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE search_system.postings
  OWNER TO postgres;
-- Table: search_system.terms

-- DROP TABLE search_system.terms;

CREATE TABLE search_system.terms
(
  id bigserial NOT NULL,
  value character varying NOT NULL,
  CONSTRAINT primary_key PRIMARY KEY (id),
  CONSTRAINT unique_constraint UNIQUE (value)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE search_system.terms
  OWNER TO postgres;
