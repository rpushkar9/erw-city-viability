INSERT INTO site (name, region, latitude, longitude, score) VALUES
  -- Madagascar Sites
  ('Ambanja West', 'Madagascar-NW', -13.675, 48.450, 0.62),
  ('Toamasina Portside', 'Madagascar-NE', -18.150, 49.400, 0.71),
  ('Mahajanga Inland', 'Madagascar-NW', -15.717, 46.317, 0.55),
  
  -- South India Sites
  ('Bangalore Rural', 'Karnataka-South', 12.970, 77.590, 0.78),
  ('Mysore Agricultural', 'Karnataka-South', 12.295, 76.639, 0.73),
  ('Coimbatore Industrial', 'Tamil Nadu-West', 11.016, 76.955, 0.69),
  ('Salem Granite Belt', 'Tamil Nadu-North', 11.664, 78.146, 0.65),
  ('Visakhapatnam Coastal', 'Andhra Pradesh-North', 17.686, 83.218, 0.72),
  ('Guntur Agricultural', 'Andhra Pradesh-Central', 16.306, 80.436, 0.67),
  ('Kochi Backwaters', 'Kerala-Central', 9.931, 76.267, 0.58),
  ('Thrissur Rice Fields', 'Kerala-Central', 10.527, 76.214, 0.63),
  
  -- Additional Global Sites
  ('Rio Grande Valley', 'Brazil-Southeast', -22.906, -43.172, 0.74),
  ('Minas Gerais Mining', 'Brazil-Southeast', -19.917, -43.934, 0.70),
  ('Victoria Highlands', 'Australia-Southeast', -37.814, 144.963, 0.76),
  ('Queensland Plains', 'Australia-Northeast', -27.469, 153.025, 0.68)
ON CONFLICT (name, region) DO NOTHING;
