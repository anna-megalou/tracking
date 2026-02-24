DELETE FROM orders_tracking;

INSERT INTO orders_tracking (
    id, status, items, price, store, city, postal_code,
    store_lat, store_lng, destination_lat, destination_lng, destination_label,
    driver_lat, driver_lng, eta_minutes, progress, route
) VALUES

-- Order 1: Processing (not yet picked up)
('U44653', 'Processing', 3, 5.00, 'Politeia Bookstore', 'Athens', '10564',
 37.9795, 23.7325, 37.9680, 23.7695, 'Home',
 37.9795, 23.7325, 0, 0,
 '[]'),

-- Order 2: Delivering (driver en route)
('U44654', 'Delivering', 3, 5.00, 'Public Syntagma', 'Athens', '10563',
 37.9755, 23.7348, 37.9900, 23.7560, 'Office',
 37.9820, 23.7440, 12, 55,
 '[{"lat":37.9755,"lng":23.7348},{"lat":37.9770,"lng":23.7370},{"lat":37.9790,"lng":23.7400},{"lat":37.9820,"lng":23.7440},{"lat":37.9850,"lng":23.7480},{"lat":37.9875,"lng":23.7510},{"lat":37.9900,"lng":23.7560}]'),

-- Order 3: Completed
('U44655', 'Completed', 3, 5.00, 'Ianos Bookstore', 'Athens', '10672',
 37.9785, 23.7340, 37.9840, 23.7510, 'Home',
 37.9840, 23.7510, 0, 100,
 '[{"lat":37.9785,"lng":23.7340},{"lat":37.9800,"lng":23.7390},{"lat":37.9820,"lng":23.7450},{"lat":37.9840,"lng":23.7510}]'),

-- Order 4: Completed
('U44656', 'Completed', 3, 5.00, 'Papasotiriou Books', 'Athens', '10671',
 37.9810, 23.7290, 37.9750, 23.7450, 'Home',
 37.9750, 23.7450, 0, 100,
 '[{"lat":37.9810,"lng":23.7290},{"lat":37.9800,"lng":23.7330},{"lat":37.9780,"lng":23.7380},{"lat":37.9760,"lng":23.7420},{"lat":37.9750,"lng":23.7450}]');
