package spring.project.apirestwebflux.implementations;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;import reactor.core.publisher.Flux;import reactor.core.publisher.Mono;import spring.project.apirestwebflux.documents.Category;import spring.project.apirestwebflux.repositories.ICategoryRepository;import spring.project.apirestwebflux.services.ICategoryService;@Servicepublic class CategoryServiceImpl implements ICategoryService {    @Autowired    private ICategoryRepository categoryRepository;    @Override    public Flux<Category> select() {        return categoryRepository.findAll();    }    @Override    public Mono<Category> get(String id) {        return categoryRepository.findById(id);    }    @Override    public Mono<Category> insert(Category category) {        return categoryRepository.save(category);    }}