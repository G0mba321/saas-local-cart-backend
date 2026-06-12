INSERT INTO category(id, name, base_type, is_root, parent_id)
VALUES (100, 'Beer', 'ALCOHOL', true, null),
       (101, 'FreshMeat', 'MEAT', true, null),
       (102, 'Tobacco', 'TOBACCO_PRODUCTS', true, null),
       (103, 'Cigarettes', 'TOBACCO_PRODUCTS', false, 102);