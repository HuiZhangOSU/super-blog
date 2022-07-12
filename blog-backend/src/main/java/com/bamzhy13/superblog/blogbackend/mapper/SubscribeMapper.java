package com.bamzhy13.superblog.blogbackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SubscribeMapper {

    void insert(String email);

    void delete(String email);
}
