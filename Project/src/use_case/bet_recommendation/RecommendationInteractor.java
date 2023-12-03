package use_case.bet_recommendation;

import data_access.PlayerDataAccessObject;
import data_access.TeamDataAccessObject;
import entity.Player;
import entity.Team;


import java.util.ArrayList;

public class RecommendationInteractor implements RecommendationInputBoundary{
    final RecommendationDataAccessInterface playerDataAccessObject;
    final RecommendationOutputBoundary recommendPresenter;

    public RecommendationInteractor(RecommendationDataAccessInterface recommendationDataAccessInterface,
                                    RecommendationOutputBoundary recommendationOutputBoundary) {
        this.playerDataAccessObject = recommendationDataAccessInterface;
        this.recommendPresenter = recommendationOutputBoundary;
    }
    @Override
    public void execute(RecommendationInputData recommendationInputData) {
        String name = recommendationInputData.getName();
        String error = "Error";
        if (!name.contains(" ")){
            RecommendationOutputData output = new RecommendationOutputData(error, recommendationInputData.panel);
            recommendPresenter.prepareFailView(output);

        }

        String[] names = name.split(" ");
        String first_name = names[0];
        String last_name = names[1];


        Player fav_player = PlayerDataAccessObject.getPlayerStats(PlayerDataAccessObject.getPlayerID(first_name, last_name));
        if (fav_player.getId() != -1) {

        ArrayList <Integer> points = fav_player.getPointsPerGame();

        Double over_avg = Math.round(fav_player.avg(points,points.size())) + 0.5;
        Double under_avg = Math.round(fav_player.avg(points,points.size())) - 0.5;
        Double over_avg1 = Math.round(fav_player.avg(points,points.size())) + 2.5;
        Double under_avg1 = Math.round(fav_player.avg(points,points.size())) - 2.5;

            String safe_bet = "Safe Bet : \n";
            String over = first_name + " " + last_name + " scoring over " + over_avg1 +" pts"  + "\n";
            String under = first_name + " " + last_name + " scoring under " + under_avg1 +" pts"  + "\n" + "\n";
            String risky_bet = "Risky Bet : \n";
            String over1 = first_name + " " + last_name + " scoring over " + over_avg +" pts"  + "\n";
            String under1 = first_name + " " + last_name + " scoring under " + under_avg +" pts"  + "\n";

            String out = safe_bet + over + under + risky_bet + over1 + under1;
            RecommendationOutputData output = new RecommendationOutputData(out, recommendationInputData.panel);
            recommendPresenter.prepareSuccessView(output);
        }
        else {RecommendationOutputData output = new RecommendationOutputData(error, recommendationInputData.panel);
            recommendPresenter.prepareFailView(output);

        }
    }}

