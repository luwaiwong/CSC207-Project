package use_case.player_bet_predictor;

import data_access.PlayerDataAccessObject;
import entity.Player;
import use_case.player_bet_predictor.calculatePlayerAverage.Context;
import use_case.player_bet_predictor.calculatePlayerAverage.meanPlayerCalculator;
import use_case.player_bet_predictor.calculatePlayerAverage.rangePlayerCalculatorOverlap;

import java.util.ArrayList;

public class PlayerInteractor implements PlayerInputBoundary {

    final PlayerTeamDataAccessInterface playerDataAccessObject;
    final PlayerOutputBoundary playerPresenter;

    public PlayerInteractor(PlayerTeamDataAccessInterface playerDataAccessInterface,
                                    PlayerOutputBoundary playerOutputBoundary) {
        this.playerDataAccessObject = playerDataAccessInterface;
        this.playerPresenter = playerOutputBoundary;

    }
    @Override
    public void execute(PlayerInputData playerInputData) {
        String playerFirstName = playerInputData.getFirstName();
        String playerLastName = playerInputData.getLastName();

        ArrayList<Integer> player = PlayerDataAccessObject.getPlayerStats(PlayerDataAccessObject.getPlayerID(playerFirstName, playerLastName));

        Context context = new Context(new rangePlayerCalculatorOverlap());

        PlayerOutputData pointsWon = new PlayerOutputData(context.executeStrategy(player));
        playerPresenter.prepareSuccessView(pointsWon);

    }
}
