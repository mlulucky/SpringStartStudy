package com.acorn.springstartstudy.mapper;

import com.acorn.springstartstudy.dto.UsersDto;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper // Mybatis db 컨테이너에서 객체 구현 후 관리
public interface UsersMapper { // == Dao
    // 함수를 호출. 실행하면 Mapper.xml 의 sql 문이 실행되고
    // resultMap(resultType)맵핑된 결과가 나온다.

    List<UsersDto> findAll();

    UsersDto findByUId(String uId); // #{uId} 파라미터 => UsersMapper.xml 에 파라미터로 전달
    // uId 는 UsersDto 의 필드명

    int deleteByUIdAndPw(String uId, String pw);

    int updateOne(UsersDto user);

    int insertOne(UsersDto user);


}
