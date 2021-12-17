package com.integration.recruitee.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.integration.recruitee.model.Candidate;
import com.integration.recruitee.repository.CandidateRepository;

import java.util.List;

@RestController
@RequestMapping("/candidate")
public class RedisController {
    @Autowired
    private CandidateRepository repo;

    @PostMapping
    public Candidate save(@RequestBody Candidate candidate) {
        return repo.save(candidate);
    }

    @GetMapping
    public List<Candidate> getAllCandidates() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Candidate findCandidate(@PathVariable int id) {
        return repo.findCandidateById(id);
    }
    @DeleteMapping("/{id}")
    public String remove(@PathVariable int id)   {
    	return repo.deleteCandidate(id);
	}

}
