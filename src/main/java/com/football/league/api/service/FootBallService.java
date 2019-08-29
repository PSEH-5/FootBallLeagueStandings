package com.football.league.api.service;

import java.util.List;

import com.football.league.api.pojo.FootBallLeagueResponse;
import com.football.league.api.service.exceptions.FootBallException;

public interface FootBallService {
	String getCountryId(String countryName) throws FootBallException;

	String getLeagueId(String leagueName) throws FootBallException;

	List<FootBallLeagueResponse> getStandingsData(String countryName, String leagueName, String teamName,String leaguePosition)
			throws FootBallException;
}
