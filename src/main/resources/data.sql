INSERT INTO site (name, region, latitude, longitude, score) VALUES
  -- Tier 1: Global Metropolitan ERW Leaders (Highest Potential)
  ('Des Moines', 'Iowa-USA', 41.5868, -93.6250, 0.94),
  ('Fresno', 'California-USA', 36.7378, -119.7871, 0.91), 
  ('Winnipeg', 'Manitoba-Canada', 49.8951, -97.1384, 0.88),
  ('Toulouse', 'Occitanie-France', 43.6047, 2.4442, 0.85),
  ('Kansas City', 'Kansas-USA', 39.0997, -94.5786, 0.84),
  ('São Paulo Interior', 'São Paulo-Brazil', -22.3193, -49.0658, 0.81),
  
  -- Tier 2: Regional Metropolitan ERW Hubs  
  ('Brisbane', 'Queensland-Australia', -27.4705, 153.0260, 0.79),
  ('Córdoba', 'Córdoba-Argentina', -31.4201, -64.1888, 0.76),
  ('Bangalore', 'Karnataka-India', 12.9716, 77.5946, 0.74),
  ('Calgary', 'Alberta-Canada', 51.0447, -114.0719, 0.72),
  ('Lincoln', 'Nebraska-USA', 40.8136, -96.7026, 0.78),
  ('Fargo', 'North Dakota-USA', 46.8772, -96.7898, 0.76),
  
  -- Tier 3: Emerging Metropolitan ERW Centers
  ('Kiev', 'Kyiv-Ukraine', 50.4501, 30.5234, 0.70),
  ('Nairobi', 'Central-Kenya', -1.2921, 36.8219, 0.66),
  ('Hyderabad', 'Telangana-India', 17.3850, 78.4867, 0.68),
  ('Mendoza', 'Mendoza-Argentina', -32.8895, -68.8458, 0.64),
  ('Perth', 'Western Australia-Australia', -31.9505, 115.8605, 0.73),
  ('Saskatoon', 'Saskatchewan-Canada', 52.1579, -106.6702, 0.75),
  
  -- Metropolitan Areas in Major Agricultural Valleys/Plains
  ('Stockton', 'California-USA', 37.9577, -121.2908, 0.82),
  ('Bakersfield', 'California-USA', 35.3733, -119.0187, 0.80),
  ('Grand Island', 'Nebraska-USA', 40.9264, -98.3420, 0.77),
  ('Minot', 'North Dakota-USA', 48.2330, -101.2957, 0.74),
  ('Regina', 'Saskatchewan-Canada', 50.4452, -104.6189, 0.73),
  ('Lethbridge', 'Alberta-Canada', 49.6934, -112.8414, 0.71),
  
  -- International Metropolitan ERW Centers
  ('Rosario', 'Santa Fe-Argentina', -32.9442, -60.6505, 0.75),
  ('Palmerston North', 'North Island-New Zealand', -40.3523, 175.6082, 0.69),
  ('Piracicaba', 'São Paulo-Brazil', -22.7253, -47.6492, 0.72),
  ('Londrina', 'Paraná-Brazil', -23.3045, -51.1696, 0.70),
  ('Lleida', 'Catalonia-Spain', 41.6176, 0.6200, 0.67),
  ('Toulouse Rural', 'Midi-Pyrénées-France', 43.4643, 1.4437, 0.71),
  
  -- Asian Metropolitan ERW Hubs
  ('Ludhiana', 'Punjab-India', 30.9010, 75.8573, 0.69),
  ('Nashik', 'Maharashtra-India', 19.9975, 73.7898, 0.66),
  ('Mysore', 'Karnataka-India', 12.2958, 76.6394, 0.71),
  
  -- East Asian Metropolitan ERW Centers
  ('Jinan', 'Shandong-China', 36.6512, 117.1201, 0.78),
  ('Beijing Rural', 'Hebei-China', 39.9042, 116.4074, 0.75),
  ('Sendai', 'Tohoku-Japan', 38.2682, 140.8694, 0.72),
  ('Suwon', 'Gyeonggi-South Korea', 37.2636, 127.0286, 0.70),
  
  -- African Metropolitan ERW Expansion  
  ('Johannesburg Rural', 'Gauteng-South Africa', -26.2041, 28.0473, 0.68),
  ('Casablanca Rural', 'Grand Casablanca-Morocco', 33.5731, -7.5898, 0.65),
  ('Kano', 'Kano State-Nigeria', 12.0022, 8.5920, 0.63),
  
  -- Russian Federation Agricultural Zones
  ('Krasnodar', 'Krasnodar Krai-Russia', 45.0328, 38.9769, 0.76),
  ('Novosibirsk Rural', 'Novosibirsk Oblast-Russia', 55.0084, 82.9357, 0.74),
  
  -- Global Strategic Metropolitan Areas
  ('Ankara Rural', 'Central Anatolia-Turkey', 39.9334, 32.8597, 0.67),
  ('Munich Rural', 'Bavaria-Germany', 48.1351, 11.5820, 0.73),
  ('Bangkok Rural', 'Central Plains-Thailand', 13.7563, 100.5018, 0.69)
ON CONFLICT (name, region) DO NOTHING;