package com.football.league.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.football.league.api.pojo.FootBallLeagueResponse;
import com.football.league.api.service.FootBallService;
import com.football.league.api.service.exceptions.FootBallException;

/**
 * @author Marimuthu
 *
 */
@RestController
public class FootBallLeagueController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FootBallLeagueController.class);

	@Autowired
	private FootBallService standingservice;

	/**
	 * @param countryName
	 * @param leagueName
	 * @param teamName
	 * @param leaguePosition
	 * @return
	 * @throws FootBallException
	 */
	@RequestMapping(value = "/football/league/{countryName}/{leagueName}/{teamName}/{leaguePosition}", method = RequestMethod.GET)
	public ResponseEntity<List<FootBallLeagueResponse>> getFootballTeamStanding(
			@PathVariable(value = "countryName", required = true) String countryName,
			@PathVariable(value = "leagueName", required = true) String leagueName,
			@PathVariable(value = "teamName", required = true) String teamName,
			@PathVariable(value = "leaguePosition", required = true) String leaguePosition)
			throws FootBallException {
		try {
			List<FootBallLeagueResponse> responselist = standingservice.getFootBallStandingsData(countryName, leagueName,
					teamName, leaguePosition);

			return new ResponseEntity<List<FootBallLeagueResponse>>(responselist, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error("Error occurred", ex);
			return (ResponseEntity<List<FootBallLeagueResponse>>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	// http://localhost:9000/football/standings/France/Ligue 2/Lorient

}
