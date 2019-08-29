package com.football.league.api.standings.service;

import java.util.List;

import com.football.league.api.standings.pojo.StandingsFBLeagueResponse;
import com.football.league.api.standings.service.exceptions.StandingsFBException;

public interface StandingsFBService {
	String getCountryId(String countryName) throws StandingsFBException;

	String getLeagueId(String leagueName) throws StandingsFBException;

	List<StandingsFBLeagueResponse> getStandingsData(String countryName, String leagueName, String teamName,String leaguePosition)
			throws StandingsFBException;
}
