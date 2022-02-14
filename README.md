# spring_boot

DROP TABLE public.tourneys;

CREATE TABLE public.tourneys (
	pkid serial PRIMARY KEY,
	name varchar(30) NULL,
	wins INTEGER NULL,
	best INTEGER NULL,
	"size" float4 NULL
);

INSERT INTO tourneys (name, wins, best, size)
VALUES 
('Dolly', '7', '245', '8.5'),
('Etta', '4', '283', '9'),
('Irma', '9', '266', '7'),
('Barbara', '2', '197', '7.5'),
('Gladys', '13', '273', '8');
