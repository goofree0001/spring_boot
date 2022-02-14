package com.example.demo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import com.example.demo.DemoEntity;

@Mapper
public interface DemoMapper {

	@Select("SELECT * FROM tourneys")
	public List<DemoEntity> findAll();

	@Select("SELECT * FROM tourneys WHERE pkid = #{pkid}")
	public DemoEntity findById(@Param("pkid") Integer pkid);

    @Select("SELECT * FROM tourneys ORDER BY pkid")
	public List<DemoEntity> findAllOrderById();

	@Select("INSERT INTO tourneys (name, wins, best, size) VALUES (" +
	"#{demoEntity.name} ," +
	"#{demoEntity.wins} ," +
	"#{demoEntity.best} ," +
	"#{demoEntity.size}" +
	");")
	void create(@Param("demoEntity") DemoEntity demoEntity);

	@Select("UPDATE tourneys SET " +
	"name = #{demoEntity.name} ," +
	"wins = #{demoEntity.wins} ," +
	"best = #{demoEntity.best} ," +
	"size = #{demoEntity.size} " +
	"WHERE pkid = #{demoEntity.pkid}" +
	";")
	void update(@Param("demoEntity") DemoEntity demoEntity);

	@Select("DELETE FROM tourneys WHERE pkid = #{pkid}")
	void delete(@Param("pkid") Integer pkid);

}
