package spring.project.apirestwebflux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;
import spring.project.apirestwebflux.documents.Category;
import spring.project.apirestwebflux.documents.Product;
import spring.project.apirestwebflux.services.ICategoryService;
import spring.project.apirestwebflux.services.IProductService;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ApiRestWebFluxApplication implements CommandLineRunner {

	@Autowired
	private IProductService productService;

	@Autowired
	private ICategoryService categoryService;

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(ApiRestWebFluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mongoTemplate.dropCollection("products").subscribe();
		mongoTemplate.dropCollection("categories").subscribe();

		Category computer=new Category("computer");
		Category phone=new Category("phone");
		Category tv=new Category("tv");

		List<Product> products=new ArrayList<>();
		products.add(new Product("dell",2999.99, computer));
		products.add(new Product("huawei",3999.99, phone));
		products.add(new Product("samsung",4999.99, tv));

		Flux.just(computer,phone,tv)
				.flatMap(categoryService::insert)
				.thenMany(
						Flux.fromIterable(products)
						.flatMap(product -> {
							return productService.insert(product);
						})
				).subscribe();
	}
}
