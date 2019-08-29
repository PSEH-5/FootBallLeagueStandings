package com.football.league.api.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.football.league.api.config.FootBallDataConfiguration;
import com.football.league.api.pojo.League;
import com.football.league.api.service.exceptions.FootBallException;

@Component
public class LeagueRepostory {

	@Autowired
	FootBallDataConfiguration config;
	
	public ResponseEntity<League[]> getLeagueId(String country_Id) throws FootBallException {

		RestTemplate resttemplate = new RestTemplate();

		String leagueUrl = UriComponentsBuilder.fromUriString(config.getGetleaguesurl())
				.replaceQueryParam("country_id", country_Id).toUriString();

		ResponseEntity<League[]> responseLeagueList = resttemplate.getForEntity(leagueUrl, League[].class);
		return responseLeagueList;
	}

}
