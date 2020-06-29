package proit.test.controllers;

import org.jooq.util.maven.example.tables.pojos.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import proit.test.models.EmplListOnPage;
import proit.test.models.EmplNode;
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
    public EmplListOnPage showEmplPage(@RequestParam("offset") int offset, @RequestParam("limit") int limit) {
        return new EmplListOnPage(service.countEmpl(), service.selectPage(offset, limit));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/create")
    public List<Employee> showEmplOrg(@RequestParam UUID id_org) {
        return service.selectEmplOrg(id_org);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/create")
    public Employee insertEmpl(@RequestBody Employee empl) {
        return service.insert(empl);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/update")
    public Employee updateEmpl(@RequestBody Employee empl) {
        return service.update(empl);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/{id_empl}")
    public boolean deleteEmpl(@PathVariable("id_empl") UUID id_empl) {
        return service.delete(id_empl);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/tree")
    public List<EmplNode> allTree() {
        return service.getAllTree();
    }
}
