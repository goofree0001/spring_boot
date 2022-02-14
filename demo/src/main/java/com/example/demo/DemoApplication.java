/*
https://start.spring.io/
./gradlew bootRun

localhost:8080

killall -s KILL java

https://www.digitalocean.com/community/tutorials/introduction-to-queries-postgresql
CREATE TABLE tourneys (
pkid real PRIMARY KEY,
name varchar(30),
wins real,
best real,
size real
);

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

*/

package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
