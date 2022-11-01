package deo.github.com.data.generator;

import deo.github.com.data.entity.Customer;
import deo.github.com.data.entity.Product;
import deo.github.com.data.entity.Status;
import deo.github.com.data.repository.ContactRepository;
import deo.github.com.data.repository.ProductRepository;
import deo.github.com.data.repository.StatusRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.vaadin.artur.exampledata.DataType;
import org.vaadin.artur.exampledata.ExampleDataGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(ContactRepository contactRepository, ProductRepository productRepository,
                                      StatusRepository statusRepository) {

        return args -> {
            int seed = 2910;

            if (contactRepository.count() != 0L) {
                return;
            }

            //Товары
            ExampleDataGenerator<Product> productExampleDataGenerator = new ExampleDataGenerator<>(Product.class, LocalDateTime.now());

            productExampleDataGenerator.setData(Product::setName, DataType.FOOD_PRODUCT_NAME);

            List<Product> product = productRepository.saveAll(productExampleDataGenerator.create(5, seed));

            //Статусы
            List<Status> statuses = statusRepository
                    .saveAll(Stream.of("Ordering", "Buying", "Contacted", "Shipping", "Lost", "Closed")
                            .map(Status::new).collect(Collectors.toList()));


            //Покупатели
            ExampleDataGenerator<Customer> contactGenerator = new ExampleDataGenerator<>(Customer.class,
                    LocalDateTime.now());
            contactGenerator.setData(Customer::setFirstName, DataType.FIRST_NAME);
            contactGenerator.setData(Customer::setLastName, DataType.LAST_NAME);
            contactGenerator.setData(Customer::setEmail, DataType.EMAIL);

            Random r = new Random(seed);
            List<Customer> contacts = contactGenerator.create(10, seed).stream().peek(contact -> {
                contact.setProduct(product.get(r.nextInt(product.size())));
                contact.setStatus(statuses.get(r.nextInt(statuses.size())));
            }).collect(Collectors.toList());

            contactRepository.saveAll(contacts);
        };
    }

}
