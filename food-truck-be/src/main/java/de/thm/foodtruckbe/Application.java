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

import de.thm.foodtruckbe.entities.user.Customer;
import de.thm.foodtruckbe.entities.Dish;
import de.thm.foodtruckbe.entities.Location;
import de.thm.foodtruckbe.entities.user.Operator;
import de.thm.foodtruckbe.entities.Dish.Ingredient;
import de.thm.foodtruckbe.entities.order.PreOrder;
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

			Operator operator = new Operator("Truck-Food", "1234");

			Location thm = new Location("THM", operator, 5.0, 7.0, LocalDateTime.now(), Duration.ofHours(1));
			Location tor = new Location("Frankenberger Tor", operator, 5.0, 7.0, thm, Duration.ofHours(1));
			Location lasertag = new Location("Lasertag-Halle", operator, 5.0, 7.0, tor, Duration.ofHours(1));
			operator.addLocation(thm);
			operator.addLocation(tor);
			operator.addLocation(lasertag);

			Customer manuel = new Customer("Manuel", "leunam");
			Customer alex = new Customer("Alex", "xlea");
			Customer lukas = new Customer("Lukas", "sakul");

			EnumMap<Ingredient, Integer> lasagnaIngredients = new EnumMap<>(Ingredient.class);
			lasagnaIngredients.put(Ingredient.NUDELN, 3);
			lasagnaIngredients.put(Ingredient.TOMATEN, 2);
			lasagnaIngredients.put(Ingredient.METT, 3);
			lasagnaIngredients.put(Ingredient.KAESE, 2);
			Dish lasagna = new Dish("Lasagne", operator, 5.50, lasagnaIngredients);

			EnumMap<Ingredient, Integer> burgerIngredients = new EnumMap<>(Ingredient.class);
			burgerIngredients.put(Ingredient.BROETCHEN, 1);
			burgerIngredients.put(Ingredient.TOMATEN, 2);
			burgerIngredients.put(Ingredient.GURKE, 3);
			burgerIngredients.put(Ingredient.SALAT, 2);
			burgerIngredients.put(Ingredient.BOULETTE, 2);
			burgerIngredients.put(Ingredient.POMMES, 1);
			burgerIngredients.put(Ingredient.KETCHUP, 1);
			Dish burger = new Dish("Burger", operator, 7d, burgerIngredients);

			EnumMap<Ingredient, Integer> pancakeIngredients = new EnumMap<>(Ingredient.class);
			pancakeIngredients.put(Ingredient.EI, 3);
			pancakeIngredients.put(Ingredient.MEHL, 2);
			pancakeIngredients.put(Ingredient.SALZ, 1);
			pancakeIngredients.put(Ingredient.ZUCKER, 2);
			Dish pancakes = new Dish("Pancakes", operator, 4d, pancakeIngredients);

			HashMap<Dish, Integer> itemsManuel = new HashMap<>();
			itemsManuel.put(burger, 1);
			itemsManuel.put(lasagna, 1);
			Reservation reservationManuel = new Reservation(manuel, tor, itemsManuel);
			HashMap<Dish, Integer> itemsAlex = new HashMap<>();
			itemsAlex.put(lasagna, 2);
			Reservation reservationAlex = new Reservation(alex, tor, itemsAlex);
			HashMap<Dish, Integer> itemsLukas = new HashMap<>();
			itemsLukas.put(pancakes, 4);
			PreOrder preOrderLukas = new PreOrder(lukas, thm, itemsLukas);

			operatorRepository.save(operator);
			locationRepository.save(thm);
			locationRepository.save(tor);
			locationRepository.save(lasertag);
			customerRepository.save(manuel);
			customerRepository.save(alex);
			customerRepository.save(lukas);

			dishRepository.save(lasagna);
			dishRepository.save(burger);
			dishRepository.save(pancakes);
			orderRepository.save(reservationManuel);
			orderRepository.save(reservationAlex);
			orderRepository.save(preOrderLukas);
			log.info("Saved exemplary data.");
		};
	}

	// TODO figure out how to check if the container already exists
	public static void createMySQLContainer(String containerName, String databasePassword, String databaseName) {
		try {
			log.info("Checking if container {} exists.", containerName);
			Process check = Runtime.getRuntime().exec("docker inspect -f '{{.State.Running}}' " + containerName);
			String res = "true";
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
