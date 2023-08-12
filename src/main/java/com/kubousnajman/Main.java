package com.kubousnajman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class Main  {

    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping
    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }

    record NewCustomerRequest (String name, String email, Integer age){}

    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request){
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setAge(request.age());
        customerRepository.save(customer);
    }

    @DeleteMapping("{id}")
    public void deleteCustomer(@PathVariable Integer id){
        customerRepository.deleteById(id);
    }

    @PutMapping("{id}")
    public boolean updateCustomer(@PathVariable Integer id, @RequestBody Customer updateCustomer){
        Optional<Customer> currentCustomer = customerRepository.findById(id);;
        if(currentCustomer.isPresent()){
            currentCustomer.get().setName(updateCustomer.getName());
            currentCustomer.get().setEmail(updateCustomer.getEmail());
            currentCustomer.get().setAge(updateCustomer.getAge());
            customerRepository.save(currentCustomer.get());
            return true;
        }
        return false;
    }

}
