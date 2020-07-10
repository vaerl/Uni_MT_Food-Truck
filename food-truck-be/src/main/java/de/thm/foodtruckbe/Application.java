package de.thm.foodtruckbe;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

import de.thm.foodtruckbe.data.entities.DishWrapper;
import de.thm.foodtruckbe.data.entities.Ingredient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import de.thm.foodtruckbe.data.entities.user.Customer;
import de.thm.foodtruckbe.data.entities.Dish;
import de.thm.foodtruckbe.data.entities.Location;
import de.thm.foodtruckbe.data.entities.user.Operator;
import de.thm.foodtruckbe.data.entities.order.PreOrder;
import de.thm.foodtruckbe.data.entities.order.Reservation;
import de.thm.foodtruckbe.data.repos.CustomerRepository;
import de.thm.foodtruckbe.data.repos.DishRepository;
import de.thm.foodtruckbe.data.repos.LocationRepository;
import de.thm.foodtruckbe.data.repos.OperatorRepository;
import de.thm.foodtruckbe.data.repos.OrderRepository;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

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

			Dish lasagna = new Dish("Lasagne", operator, 5.50, null);
			List<Ingredient> lasagnaIngredients = new ArrayList<>();
			lasagnaIngredients.add(new Ingredient(Ingredient.IngredientName.NUDELN.toString(), 3, lasagna, operator));
			lasagnaIngredients.add(new Ingredient(Ingredient.IngredientName.TOMATEN.toString(), 2, lasagna, operator));
			lasagnaIngredients.add(new Ingredient(Ingredient.IngredientName.METT.toString(), 3, lasagna, operator));
			lasagnaIngredients.add(new Ingredient(Ingredient.IngredientName.KAESE.toString(), 2, lasagna, operator));
			lasagna.setIngredients(lasagnaIngredients);

			Dish burger = new Dish("Burger", operator, 7d, null);
			List<Ingredient> burgerIngredients = new ArrayList<>();
			burgerIngredients.add(new Ingredient(Ingredient.IngredientName.TOMATEN.toString(), 2, burger, operator));
			burgerIngredients.add(new Ingredient(Ingredient.IngredientName.GURKE.toString(), 3, burger, operator));
			burgerIngredients.add(new Ingredient(Ingredient.IngredientName.SALAT.toString(), 2, burger, operator));
			burgerIngredients.add(new Ingredient(Ingredient.IngredientName.BROETCHEN.toString(), 1, burger, operator));
			burgerIngredients.add(new Ingredient(Ingredient.IngredientName.BOULETTE.toString(), 2, burger, operator));
			burgerIngredients.add(new Ingredient(Ingredient.IngredientName.POMMES.toString(), 1, burger, operator));
			burgerIngredients.add(new Ingredient(Ingredient.IngredientName.KETCHUP.toString(), 1, burger, operator));
			burger.setIngredients(burgerIngredients);

			Dish pancakes = new Dish("Pancakes", operator, 4d, null);
			List<Ingredient> pancakeIngredients = new ArrayList<>();
			pancakeIngredients.add(new Ingredient(Ingredient.IngredientName.EI.toString(), 3, pancakes, operator));
			pancakeIngredients.add(new Ingredient(Ingredient.IngredientName.MEHL.toString(), 2, pancakes, operator));
			pancakeIngredients.add(new Ingredient(Ingredient.IngredientName.SALZ.toString(), 1, pancakes, operator));
			pancakeIngredients.add(new Ingredient(Ingredient.IngredientName.ZUCKER.toString(), 2, pancakes, operator));
			pancakes.setIngredients(pancakeIngredients);

			ArrayList<DishWrapper> itemsManuel =new ArrayList<>();
			itemsManuel.add(new DishWrapper(burger, 1));
			itemsManuel.add(new DishWrapper(lasagna, 1));
			Reservation reservationManuel = new Reservation(manuel, tor, itemsManuel);
			ArrayList<DishWrapper> itemsAlex =new ArrayList<>();
			itemsAlex.add(new DishWrapper(lasagna, 2));
			Reservation reservationAlex = new Reservation(alex, tor, itemsAlex);
			ArrayList<DishWrapper> itemsLukas =new ArrayList<>();
			itemsLukas.add(new DishWrapper(pancakes, 4));
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
