package proit.test.controllers;

import org.jooq.util.maven.example.tables.pojos.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import proit.test.models.EmplList;
import proit.test.services.EmplService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/employee")
public class EmplController {

    @Autowired
    private EmplService service;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public List<EmplList> showEmpl() {
        return service.selectAll();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/employee/create")
    public List<Employee> showEmplOrg(@RequestParam UUID id_org) {
        return service.selectEmplOrg(id_org);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/employee/create")
    public Employee insertEmpl(@RequestBody Employee empl) {
        return service.insert(empl);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/employee/update")
    public Employee updateEmpl(@RequestBody Employee empl) {
        return service.update(empl);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/employee/{id_empl}")
    public boolean deleteEmpl(@PathVariable("id_empl") UUID id_empl) {
        return service.delete(id_empl);
    }
}
