create table gift_certificate(
                                 id serial primary key not null,
                                 name varchar(255) not null,
                                 description varchar(255) not null,
                                 price decimal(10,2) not null,
                                 duration int not null,
                                 create_date timestamp with time zone not null default current_timestamp,
                                 last_update_date timestamp with time zone not null default current_timestamp
);

create table tag(
                    id serial primary key not null,
                    name varchar(250)
);

-- Creating many-to-many-relationship with gift_certificate_tag

create table gift_certificate_tag(
                                     certificate_id integer references gift_certificate(id),
                                     tag_id integer references tag(id),
                                     primary key (certificate_id, tag_id)
);


-- Creating a trigger for last_update date in gift_certificate to update a new date, once the table changes.

CREATE OR REPLACE FUNCTION update_gift_certificate_last_update_date()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.last_update_date = current_timestamp;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER gift_certificate_last_update_date_trigger
    BEFORE INSERT OR UPDATE OR DELETE ON gift_certificate
    FOR EACH ROW
EXECUTE FUNCTION update_gift_certificate_last_update_date();


--The first block of code creates a PL/pgSQL function called "update_gift_certificate_last_update_date()" that sets the "last_update_date"
--column of the "gift_certificate" table to the current timestamp.

--The second block of code creates a trigger called "gift_certificate_last_update_date_trigger" that fires before each insert,
--update, or delete operation on the "gift_certificate" table. The trigger calls the "update_gift_certificate_last_update_date()" function,
--which updates the "last_update_date" column of the affected row.

--With this trigger in place, the "last_update_date" column will automatically be updated whenever a row in the "gift_certificate" table is modified.

