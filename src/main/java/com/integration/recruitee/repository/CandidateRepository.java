package com.integration.recruitee.repository;


import java.util.List;

import com.integration.recruitee.model.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CandidateRepository {

    public static final String HASH_KEY = "Candidate";
    @Autowired
    private RedisTemplate template;

    public Candidate save(Candidate candidate) {
        template.opsForHash().put(HASH_KEY, candidate.getId(), candidate);
        return candidate;
    }

    public List<Candidate> findAll() {
        return template.opsForHash().values(HASH_KEY);
    }

    public Candidate findCandidateById(int id) {
        return (Candidate) template.opsForHash().get(HASH_KEY, id);
    }


    public String deleteCandidate(int id) {
        template.opsForHash().delete(HASH_KEY, id);
        return "candidate removed !!";
    }
}
