package com.integration.recruitee.controller;


import java.util.List;

import com.integration.recruitee.model.feedback.Candidate;
import com.integration.recruitee.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{contactNo}")
    public Candidate findCandidate(@PathVariable long contactNo) {
        return repo.findCandidateByContactNo(contactNo);
    }

    @DeleteMapping("/{contactNo}")
    public String remove(@PathVariable long contactNo) {
        return repo.deleteCandidate(contactNo);
    }

}
