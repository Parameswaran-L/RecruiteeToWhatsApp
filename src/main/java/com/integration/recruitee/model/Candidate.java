package com.integration.recruitee.model;


import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("Candidate")
public class Candidate implements Serializable  {

    @Id
    public Integer id;
    public String name;
    public String phoneNumber;
    public String email;
    
}
