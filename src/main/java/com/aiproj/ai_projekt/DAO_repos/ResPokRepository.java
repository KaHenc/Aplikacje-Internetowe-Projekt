package com.aiproj.ai_projekt.DAO_repos;

import com.aiproj.ai_projekt.POJO.pokoj;
//import com.mysql.jdbc.PreparedStatement;

import com.aiproj.ai_projekt.POJO.reshist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class ResPokRepository {
	private JdbcTemplate jdbc;
	
	@Autowired
	public ResPokRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	public List<pokoj> findfreerooms(String type, String data)
	{
		
		String sql="Select id_pokoju,ilo_osobowy,opis "+
				"from pokoj " +
				"where id_pokoju not in (Select id_pokoju from rezerwacja r where r.data=? ) and   ilo_osobowy=?";
		return jdbc.query(
				sql,
				
				preparedStatement ->
				{
					preparedStatement.setString(1, data);
					preparedStatement.setString(2,type);
				}, (rs, rowNum) ->
				{
					pokoj pokoj = new pokoj();
					pokoj.setId(rs.getInt(1));
					pokoj.setIl(rs.getString(2));
					pokoj.setOpis(rs.getString(3));
					return pokoj;
					
				});
	}
	
	public void MakeReservation(String data,int id_pokoju)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user =(User) auth.getPrincipal(); //user springowy a nie mój
		String user_login = user.getUsername();
		
		jdbc.update(
				"insert into rezerwacja " +
						"values (?, ?,?)",
				data,id_pokoju,user_login);
		
		
	}
	public List<reshist> myres()
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user =(User) auth.getPrincipal();
		String user_login = user.getUsername();
		String sql="select data,p.ilo_osobowy,p.opis from rezerwacja r inner join pokoj p on r.id_pokoju=p.id_pokoju "+
				"where r.user_login=?;";
		return jdbc.query(
				sql,
				
				preparedStatement -> preparedStatement.setString(1, user_login), (rs, rowNum) ->
				{
					reshist reshist = new reshist();
					reshist.setData(rs.getString(1));
					reshist.setRodzaj(rs.getString(2));
					reshist.setOpis(rs.getString(3));
					return reshist;
					
				});
		
	}
	
}
