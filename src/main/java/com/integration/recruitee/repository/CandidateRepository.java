package com.integration.recruitee.repository;


import java.util.List;

import com.integration.recruitee.model.feedback.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CandidateRepository {

    public static final String HASH_KEY = "Candidate";
    @Autowired
    private RedisTemplate template;

    public Candidate save(Candidate candidate) {
        template.opsForHash().put(HASH_KEY, candidate.getContactNo(), candidate);
        return candidate;
    }

    public List<Candidate> findAll() {
        return template.opsForHash().values(HASH_KEY);
    }

    public Candidate findCandidateByContactNo(long contactNo) {
        return (Candidate) template.opsForHash().get(HASH_KEY, contactNo);
    }


    public String deleteCandidate(long contactNo) {
        template.opsForHash().delete(HASH_KEY, contactNo);
        return "candidate removed !!";
    }
}
