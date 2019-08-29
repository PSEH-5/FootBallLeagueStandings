package com.football.league.api.standings.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.football.league.api.standings.config.StandingsFBDataConfiguration;
import com.football.league.api.standings.pojo.Country;
import com.football.league.api.standings.pojo.League;
import com.football.league.api.standings.pojo.StandingsFBLeagueResponse;
import com.football.league.api.standings.service.exceptions.StandingsFBException;

@Component
public class StandingsFBServiceImpl implements StandingsFBService {

	@Autowired
	StandingsFBDataConfiguration config;

	@Override
	public String getCountryId(String countryName) throws StandingsFBException {
		// TODO Auto-generated method stub
		RestTemplate resttemplate = new RestTemplate();
		ResponseEntity<Country[]> responsecountryList = resttemplate.getForEntity(config.getGetCountriesUrl(),
				Country[].class);
		String country_Id = "";
		
		for (Country country : responsecountryList.getBody()) {
			System.out.println("country == " + country.toString());
			if (countryName.equalsIgnoreCase(country.getCountry_name())) {
				country_Id = country.getCountry_id();
			}

		}
		
		if (StringUtils.isEmpty(country_Id)) {
			throw new StandingsFBException("Country does not exist in league.");
		}
		return country_Id;

	}

	@Override
	public String getLeagueId(String country_Id) throws StandingsFBException {

		RestTemplate resttemplate = new RestTemplate();
		String league_Id = "";

		String leagueUrl = UriComponentsBuilder.fromUriString(config.getGetleaguesurl())
				.replaceQueryParam("country_id", country_Id).toUriString();

		ResponseEntity<League[]> responseLeagueList = resttemplate.getForEntity(leagueUrl, League[].class);
		
		for (League league : responseLeagueList.getBody()) {
			System.out.println("league == " + league.toString());
			league_Id = league.getLeague_id();
		}
		
		if (StringUtils.isEmpty(league_Id)) {
			throw new StandingsFBException("League does not exist.");
		}
		
		return league_Id;
	}


	@Override
	public List<StandingsFBLeagueResponse> getStandingsData(String countryName, String leagueName, String teamName,String leaguePosition)
			throws StandingsFBException {
		// TODO Auto-generated method stub
		RestTemplate resttemplate = new RestTemplate();
		
		String country_Id=getCountryId(countryName);
		String league_Id=getLeagueId(country_Id);

		
		// get standings
		String standingUrl = UriComponentsBuilder.fromUriString(config.getGetstandingsurl())
				.replaceQueryParam("league_id", league_Id).toUriString();

		ResponseEntity<StandingsFBLeagueResponse[]> standingresponse = resttemplate.getForEntity(standingUrl,
				StandingsFBLeagueResponse[].class);

		List<StandingsFBLeagueResponse> list = new ArrayList<>();
		StandingsFBLeagueResponse leagueResponse = null;
		
		
		for (StandingsFBLeagueResponse standings : standingresponse.getBody()) {
			System.out.println("standings ==  " + standings.toString());
			if (standings.getLeague_name().equalsIgnoreCase(leagueName)
					&& standings.getCountry_name().equalsIgnoreCase(countryName)
					&& standings.getTeam_name().equalsIgnoreCase(teamName)) {
				leagueResponse = new StandingsFBLeagueResponse();
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
