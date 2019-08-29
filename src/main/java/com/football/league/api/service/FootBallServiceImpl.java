package com.football.league.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.football.league.api.config.FootBallDataConfiguration;
import com.football.league.api.pojo.Country;
import com.football.league.api.pojo.FootBallLeagueResponse;
import com.football.league.api.pojo.League;
import com.football.league.api.repository.CountryRepository;
import com.football.league.api.repository.LeagueRepostory;
import com.football.league.api.service.exceptions.FootBallException;


@Component
public class FootBallServiceImpl implements FootBallService {

	@Autowired
	FootBallDataConfiguration config;
	
	@Autowired
	CountryRepository countryRepo;
	
	@Autowired
	LeagueRepostory lrepo;
	
	/**
	 *
	 */
	@Override
	public String getCountryId(String countryName) throws FootBallException {
		String country_Id = "";
		ResponseEntity<Country[]> responsecountryList = countryRepo.getCountryId(countryName);
		
		
		for (Country country : responsecountryList.getBody()) {
			System.out.println("country == " + country.toString());
			if (countryName.equalsIgnoreCase(country.getCountry_name())) {
				country_Id = country.getCountry_id();
			}

		}
		
		if (StringUtils.isEmpty(country_Id)) {
			throw new FootBallException("Country does not exist in league.");
		}
		return country_Id;

	}

	/**
	 *
	 */
	@Override
	public String getLeagueId(String country_Id) throws FootBallException {

		String league_Id = "";
		ResponseEntity<League[]> responseLeagueList = lrepo.getLeagueId(country_Id);
		
		for (League league : responseLeagueList.getBody()) {
			System.out.println("league list == " + league.toString());
			league_Id = league.getLeague_id();
		}
		
		if (StringUtils.isEmpty(league_Id)) {
			throw new FootBallException("League does not exist.");
		}
		
		return league_Id;
	}


	/**
	 *
	 */
	@Override
	public List<FootBallLeagueResponse> getFootBallStandingsData(String countryName, String leagueName, String teamName,String leaguePosition)
			throws FootBallException {
		// TODO Auto-generated method stub
		RestTemplate resttemplate = new RestTemplate();
		
		String country_Id=getCountryId(countryName);
		String league_Id=getLeagueId(country_Id);

		
		// get standings
		String standingUrl = UriComponentsBuilder.fromUriString(config.getGetstandingsurl())
				.replaceQueryParam("league_id", league_Id).toUriString();

		ResponseEntity<FootBallLeagueResponse[]> standingresponse = resttemplate.getForEntity(standingUrl,
				FootBallLeagueResponse[].class);

		List<FootBallLeagueResponse> list = new ArrayList<>();
		FootBallLeagueResponse leagueResponse = null;
		
		
		for (FootBallLeagueResponse standings : standingresponse.getBody()) {
			System.out.println("standings ==  " + standings.toString());
			if (standings.getLeague_name().equalsIgnoreCase(leagueName)
					&& standings.getCountry_name().equalsIgnoreCase(countryName)
					&& standings.getTeam_name().equalsIgnoreCase(teamName) && standings.getOverall_league_position().equalsIgnoreCase(leaguePosition)) {
				leagueResponse = new FootBallLeagueResponse();
				leagueResponse.setCountry_id(country_Id);
				leagueResponse.setCountry_name(standings.getCountry_name());
				leagueResponse.setLeague_name(standings.getLeague_name());
				leagueResponse.setLegaue_id(league_Id);
				leagueResponse.setTeam_id(standings.getTeam_id());
				leagueResponse.setTeam_name(standings.getTeam_name());
				leagueResponse.setOverall_league_position(standings.getOverall_league_position());
				list.add(leagueResponse);
			}

		}
		return list;
	}

}
