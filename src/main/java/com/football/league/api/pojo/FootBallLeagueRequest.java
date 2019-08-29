package com.football.league.api.pojo;

/**
 * @author Marimuthu
 *
 */
public class FootBallLeagueRequest {
	String countryName;
	@Override
	public String toString() {
		return "StandingsRequest [countryName=" + countryName + ", leagueName=" + leagueName + ", teamName=" + teamName
				+ "]";
	}
	String leagueName;
	String teamName;
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getLeagueName() {
		return leagueName;
	}
	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
}
