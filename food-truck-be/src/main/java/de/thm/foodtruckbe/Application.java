package de.thm.foodtruckbe;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import de.thm.foodtruckbe.entities.Customer;
import de.thm.foodtruckbe.entities.Dish;
import de.thm.foodtruckbe.entities.Location;
import de.thm.foodtruckbe.entities.Operator;
import de.thm.foodtruckbe.entities.Dish.Ingredient;
import de.thm.foodtruckbe.entities.order.Reservation;
import de.thm.foodtruckbe.repos.CustomerRepository;
import de.thm.foodtruckbe.repos.DishRepository;
import de.thm.foodtruckbe.repos.LocationRepository;
import de.thm.foodtruckbe.repos.OperatorRepository;
import de.thm.foodtruckbe.repos.OrderRepository;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);
	private static final String CONTAINER_NAME = "foodtruck";
	private static final String CONTAINER_DATABASE_PASSWORD = "food_truck123!";
	private static final String CONTAINER_DATABASE_NAME = "foodtruck";

	public static void main(String[] args) {
		createMySQLContainer(CONTAINER_NAME, CONTAINER_DATABASE_PASSWORD, CONTAINER_DATABASE_NAME);
		startMySQLContainer(CONTAINER_DATABASE_NAME);
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner loadData(LocationRepository locationRepository, CustomerRepository customerRepository,
			DishRepository dishRepository, OrderRepository orderRepository, OperatorRepository operatorRepository) {
		return args -> {

			Operator operator = new Operator("Truck-Food");

			Location location = new Location("Ikea", operator, 5.0, 7.0, LocalDateTime.now(), Duration.ofDays(2));

			Customer customer = new Customer("Lukas");

			EnumMap<Ingredient, Integer> ingredients = new EnumMap<>(Ingredient.class);
			ingredients.put(Ingredient.BREAD, 3);
			Dish dish = new Dish("Lasagne", operator, 5.50, ingredients);

			HashMap<Dish, Integer> items = new HashMap<>();
			items.put(dish, 4);
			Reservation reservation = new Reservation(customer, location, items);

			operatorRepository.save(operator);
			locationRepository.save(location);
			customerRepository.save(customer);
			dishRepository.save(dish);
			orderRepository.save(reservation);
			log.info("Saved exemplary data.");
		};
	}

	// TODO figure out how to check if the container already exists
	public static void createMySQLContainer(String containerName, String databasePassword, String databaseName) {
		try {
			log.info("Checking if container {} exists.", containerName);
			Process check = Runtime.getRuntime().exec("docker inspect -f '{{.State.Running}}' " + containerName);
			String res = String.valueOf(check.getInputStream().readAllBytes());
			log.info("Container exists: {}", res);
			check.getOutputStream().close();
			if (!res.contains("'true'")) {
				log.info("Creating container {}.", containerName);
				Process run = Runtime.getRuntime()
						.exec("docker run -p 3306:3306 --name " + containerName + " -e MYSQL_ROOT_PASSWORD="
								+ databasePassword + " -e MYSQL_DATABASE=" + databaseName + " -d mysql:latest");
				run.getOutputStream().close();
				log.info("Created docker-container with name: {}", containerName);
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Could not create docker-container with name: {}", containerName);
		}
	}

	private static void startMySQLContainer(String containerName) {
		try {
			Process start = Runtime.getRuntime().exec("docker start " + containerName);
			start.getOutputStream().close();
			log.info("Started docker-container with name: {}", containerName);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Could'nt start docker-container with name: {}", containerName);
		}
	}

}
