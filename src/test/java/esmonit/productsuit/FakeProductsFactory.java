package esmonit.productsuit;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import esmonit.product.domain.Product;

import java.util.*;
import java.util.function.*;

public final class FakeProductsFactory {
    private Multimap<String, String> database
                        = ArrayListMultimap.create();
    final String BOOKS =  "BOOKS";
    final String MOBILE = "MOBILE";
    final String EXAMS  = "EXAMS";
    final List<String> types = Arrays.asList(BOOKS,MOBILE,EXAMS);


    private FakeProductsFactory() {
        database.put(BOOKS,"Head First Design Patterns");
        // Bit heavy I would say, head first does better job
        //database.put(BOOKS,"Gang Of Four Design Patterns");
        database.put(BOOKS,"Effective Java");
        database.put(BOOKS,"Effective Kotlin");
        database.put(BOOKS,"Effective Kafka");
        database.put(BOOKS,"MicroServices Pattern");
        database.put(BOOKS,"Domain Driven Design");

        // Certifications
        database.put(EXAMS,"AWS Solution Architect Associated");
        database.put(EXAMS,"AWS Solution Architect Professional");
        database.put(EXAMS,"Kafka Certified  Developer");
        database.put(EXAMS,"Elastic Certified  Developer");
        database.put(EXAMS,"Java SE 11 Developer Certification");

        database.put(MOBILE,"IPhone-x");
        database.put(MOBILE,"Samsung");
    }

    public List<Product> someRandomProducts(int startCounter) {
        List<Product> products = new ArrayList<>();
        for(int i=startCounter;i<startCounter+100;i++) {
            String type = types.get((int) (Math.random() * types.size()));
            List<String> list = new ArrayList<>(database.get(type));
            String name = list.get((int) (Math.random() * list.size()));
            Product product = new Product();
            product.setId(startCounter + 1);
            product.setName(name + "(" + UUID.randomUUID() + ")");
            product.setCategory(type);
            products.add(product);
        }
        return products;
    }

    private static FakeProductsFactory productsFactory = new FakeProductsFactory();
    public static FakeProductsFactory get() {
        return productsFactory;
    }


}
