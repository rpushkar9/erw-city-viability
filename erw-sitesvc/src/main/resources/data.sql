INSERT INTO site (name, region, latitude, longitude, score) VALUES
  -- Tier 1: Agricultural Megacities (Global Impact)
  ('Des Moines', 'Iowa-USA', 41.5868, -93.6250, 0.94),
  ('Fresno', 'California-USA', 36.7378, -119.7871, 0.91), 
  ('Winnipeg', 'Manitoba-Canada', 49.8951, -97.1384, 0.88),
  ('Toulouse', 'Occitanie-France', 43.6047, 2.4442, 0.85),
  ('Kansas City', 'Kansas-USA', 39.0997, -94.5786, 0.84),
  ('São Paulo Interior', 'São Paulo-Brazil', -22.3193, -49.0658, 0.81),
  
  -- Tier 2: Regional Agricultural Centers  
  ('Brisbane', 'Queensland-Australia', -27.4705, 153.0260, 0.79),
  ('Córdoba', 'Córdoba-Argentina', -31.4201, -64.1888, 0.76),
  ('Bangalore', 'Karnataka-India', 12.9716, 77.5946, 0.74),
  ('Calgary', 'Alberta-Canada', 51.0447, -114.0719, 0.72),
  ('Lincoln', 'Nebraska-USA', 40.8136, -96.7026, 0.78),
  ('Fargo', 'North Dakota-USA', 46.8772, -96.7898, 0.76),
  
  -- Tier 3: Emerging Agricultural Hubs
  ('Kiev', 'Kyiv-Ukraine', 50.4501, 30.5234, 0.70),
  ('Nairobi', 'Central-Kenya', -1.2921, 36.8219, 0.66),
  ('Hyderabad', 'Telangana-India', 17.3850, 78.4867, 0.68),
  ('Mendoza', 'Mendoza-Argentina', -32.8895, -68.8458, 0.64),
  ('Perth', 'Western Australia-Australia', -31.9505, 115.8605, 0.73),
  ('Saskatoon', 'Saskatchewan-Canada', 52.1579, -106.6702, 0.75),
  
  -- Major Agricultural Valleys/Plains
  ('Stockton', 'California-USA', 37.9577, -121.2908, 0.82),
  ('Bakersfield', 'California-USA', 35.3733, -119.0187, 0.80),
  ('Grand Island', 'Nebraska-USA', 40.9264, -98.3420, 0.77),
  ('Minot', 'North Dakota-USA', 48.2330, -101.2957, 0.74),
  ('Regina', 'Saskatchewan-Canada', 50.4452, -104.6189, 0.73),
  ('Lethbridge', 'Alberta-Canada', 49.6934, -112.8414, 0.71),
  
  -- International Agricultural Centers
  ('Rosario', 'Santa Fe-Argentina', -32.9442, -60.6505, 0.75),
  ('Palmerston North', 'North Island-New Zealand', -40.3523, 175.6082, 0.69),
  ('Piracicaba', 'São Paulo-Brazil', -22.7253, -47.6492, 0.72),
  ('Londrina', 'Paraná-Brazil', -23.3045, -51.1696, 0.70),
  ('Lleida', 'Catalonia-Spain', 41.6176, 0.6200, 0.67),
  ('Toulouse Rural', 'Midi-Pyrénées-France', 43.4643, 1.4437, 0.71),
  
  -- Asian Agricultural Hubs
  ('Ludhiana', 'Punjab-India', 30.9010, 75.8573, 0.69),
  ('Nashik', 'Maharashtra-India', 19.9975, 73.7898, 0.66),
  ('Mysore', 'Karnataka-India', 12.2958, 76.6394, 0.71)
ON CONFLICT (name, region) DO NOTHING;