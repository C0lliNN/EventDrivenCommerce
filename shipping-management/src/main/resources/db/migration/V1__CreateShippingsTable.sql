CREATE TABLE shippings
(
    id INT NOT NULL auto_increment PRIMARY KEY,
    upstream_external_identifier VARCHAR(36) NOT NULL,
    status VARCHAR(20) NOT NULL,
    last_updated TIMESTAMP NOT NULL
)