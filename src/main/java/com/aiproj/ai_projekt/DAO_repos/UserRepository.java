package com.aiproj.ai_projekt.DAO_repos;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.aiproj.ai_projekt.POJO.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
@Repository
public class UserRepository {
	private JdbcTemplate jdbc;
	@Autowired
	public UserRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	public List<User> findAll() {
		return jdbc.query(
				"select imie, nazwisko, login " +
						"from users ", (rs, rowNum) ->
				{
					User user = new User();
					user.setImie(rs.getString(1));
					user.setNazwisko(rs.getString(2));
					user.setLogin(rs.getString(3));
					return user;
					
				});
	}
	public void save(User user) {
		jdbc.update(
				"insert into users " +
						"values (?, ?, ?,?,?,?)",
				user.getLogin(),user.getHaslo(),user.getImie(),user.getNazwisko(),true,null);
		jdbc.update(
				"insert into user_roles " +
						"values (?, ?)",
				user.getLogin(),"USER");
	}
	
	public boolean check(String login) {
		Integer cnt = jdbc.queryForObject(
				"SELECT count(*) FROM users WHERE user_login = ?",Integer.class,login);
		return cnt != null && cnt > 0 ;
	}
	
	
}
