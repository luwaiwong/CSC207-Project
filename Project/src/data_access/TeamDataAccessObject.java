package data_access;

import entity.Team;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.bet_predictor.BetTeamDataAccessInterface;

import java.io.IOException;
import java.util.ArrayList;

public class TeamDataAccessObject implements BetTeamDataAccessInterface {
    public static void main(String[] args) {
        Team team1 = getTeamStats(getTeamID("Hawks"));
        System.out.println(team1.getId());
        System.out.println(team1.getAvg_points());
        System.out.println(team1.getNumgames());
        System.out.println(team1.getPoints_per_game());
    }
    public static int getTeamID(String teamName) {
        int id = -1;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://www.balldontlie.io/api/v1/teams")
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject responseBody = new JSONObject(response.body().string());
            JSONArray responseArray = responseBody.getJSONArray("data");
            for (int i = 0; i < responseArray.length(); i ++) {
                JSONObject team = responseArray.getJSONObject(i);
                if (team.getString("name").equals(teamName)) {
                    id = team.getInt("id");
                    break; // Exit the loop once the team is found
                }
            }

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public static Team getTeamStats(int teamID) throws JSONException{
        ArrayList<Integer> points = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://balldontlie.io/api/v1/games?seasons[]=" + 2018 + "&team_ids[]=" + teamID)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject responseBody = new JSONObject(response.body().string());
            JSONArray responseArray = responseBody.getJSONArray("data");
            //System.out.println(responseArray);
            points = getTeamPoints(responseArray, teamID);

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(points);
        return new Team(teamID, points);
    }

    private static ArrayList<Integer> getTeamPoints(JSONArray games, int teamId) {
        ArrayList<Integer> points = new ArrayList<>();

        for (int i = 0; i < games.length(); i++) {
            JSONObject game = games.getJSONObject(i);
            JSONObject homeTeam = game.getJSONObject("home_team");
            JSONObject visitorTeam = game.getJSONObject("visitor_team");

            if (visitorTeam.getInt("id") == teamId) {
                points.add(game.getInt("visitor_team_score"));
            }
            else if (homeTeam.getInt("id") == teamId) {
                points.add(game.getInt("home_team_score"));
            }
        }
        return points;

    }
}
