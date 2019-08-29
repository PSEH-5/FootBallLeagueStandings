package com.football.league.api.standings.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.football.league.api.standings.pojo.StandingsFBLeagueResponse;
import com.football.league.api.standings.service.StandingsFBService;
import com.football.league.api.standings.service.exceptions.StandingsFBException;

/**
 * @author Marimuthu
 *
 */
@RestController
public class StandingsFBLeagueController {

	@Autowired
	private StandingsFBService standingservice;

	/**
	 * @param countryName
	 * @param leagueName
	 * @param teamName
	 * @param leaguePosition
	 * @return
	 * @throws StandingsFBException
	 */
	@RequestMapping(value = "/football/league/standings/{countryName}/{leagueName}/{teamName}/{leaguePosition}", method = RequestMethod.GET)
	public ResponseEntity<List<StandingsFBLeagueResponse>> getTeamStandings(
			@PathVariable(value = "countryName", required = true) String countryName,
			@PathVariable(value = "leagueName", required = true) String leagueName,
			@PathVariable(value = "teamName", required = true) String teamName,
			@PathVariable(value = "leaguePosition", required = true) String leaguePosition)
			throws StandingsFBException {

		List<StandingsFBLeagueResponse> responselist = standingservice.getStandingsData(countryName, leagueName,
				teamName, leaguePosition);

		return new ResponseEntity<List<StandingsFBLeagueResponse>>(responselist, HttpStatus.OK);
	}
	// http://localhost:9000/football/standings/France/Ligue 2/Lorient

}
