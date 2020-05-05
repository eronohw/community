package hw.learning.first.mapper;

import hw.learning.first.model.UserModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {
    @Insert("INSERT INTO test (account_id,name,token,gmt_create,gmt_modified) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    void insert(UserModel user);
    @Select("SELECT *FROM test WHERE TOKEN=#{token}")
    UserModel findByToken(@Param("token") String token);
}
