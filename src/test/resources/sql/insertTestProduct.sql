INSERT INTO product(id, name, in_stock, price, description, extra_details,
                    category_id, origin_country_id, brand_id)
VALUES (100, 'Opillya', 100, 24.99, 'vey cool beer hell yeah',
        JSON '{"volume": "0.5L", "style": "Lagger", "alcohol": "0.5%"}',
        100, 100, 100),
       (101, 'SteakHouseMeat', 5, 50.00, 'fresh meath shit', null, 101, 101, 101);