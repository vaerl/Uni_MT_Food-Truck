package de.thm.foodtruckbe;

import de.thm.foodtruckbe.data.entities.Dish;
import de.thm.foodtruckbe.data.entities.Ingredient;
import de.thm.foodtruckbe.data.entities.Location;
import de.thm.foodtruckbe.data.entities.order.PreOrder;
import de.thm.foodtruckbe.data.entities.order.Reservation;
import de.thm.foodtruckbe.data.entities.user.Customer;
import de.thm.foodtruckbe.data.entities.user.Operator;
import de.thm.foodtruckbe.data.repos.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

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
			Location tor = new Location("Frankenberger Tor", operator, 25.0, 17.0, thm, Duration.ofHours(1));
			Location lasertag = new Location("Lasertag-Halle", operator, 30.0, 35.0, tor, Duration.ofHours(1));
			operator.addLocation(thm);
			operator.addLocation(tor);
			operator.addLocation(lasertag);

			Customer manuel = new Customer("Manuel", "leunam");
			Customer alex = new Customer("Alex", "xlea");
			Customer lukas = new Customer("Lukas", "sakul");

			List<Ingredient> lasagnaIngredients = new ArrayList<>();
			lasagnaIngredients.add(new Ingredient(Ingredient.IngredientName.NUDELN, 3));
			lasagnaIngredients.add(new Ingredient(Ingredient.IngredientName.TOMATEN, 2));
			lasagnaIngredients.add(new Ingredient(Ingredient.IngredientName.METT, 3));
			lasagnaIngredients.add(new Ingredient(Ingredient.IngredientName.KAESE, 2));
			Dish lasagna = new Dish("Lasagne", operator, 5.50, lasagnaIngredients);

			List<Ingredient> burgerIngredients = new ArrayList<>();
			burgerIngredients.add(new Ingredient(Ingredient.IngredientName.TOMATEN, 2));
			burgerIngredients.add(new Ingredient(Ingredient.IngredientName.GURKE, 3));
			burgerIngredients.add(new Ingredient(Ingredient.IngredientName.SALAT, 2));
			burgerIngredients.add(new Ingredient(Ingredient.IngredientName.BROETCHEN, 1));
			burgerIngredients.add(new Ingredient(Ingredient.IngredientName.BOULETTE, 2));
			burgerIngredients.add(new Ingredient(Ingredient.IngredientName.POMMES, 1));
			burgerIngredients.add(new Ingredient(Ingredient.IngredientName.KETCHUP, 1));
			Dish burger = new Dish("Burger", operator, 7d, burgerIngredients);

			List<Ingredient> pancakeIngredients = new ArrayList<>();
			pancakeIngredients.add(new Ingredient(Ingredient.IngredientName.EI, 3));
			pancakeIngredients.add(new Ingredient(Ingredient.IngredientName.MEHL, 2));
			pancakeIngredients.add(new Ingredient(Ingredient.IngredientName.SALZ, 1));
			pancakeIngredients.add(new Ingredient(Ingredient.IngredientName.ZUCKER, 2));
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
			String res = String.valueOf(check.getInputStream());
			log.info("Container exists: {}", res);
			check.getOutputStream().close();
			if (!res.contains("true")) {
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

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setMaxPayloadLength(64000);
        return loggingFilter;
    }

}
