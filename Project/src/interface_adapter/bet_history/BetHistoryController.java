package interface_adapter.bet_history;

import use_case.bet_history.BetHistoryInputBoundary;
import use_case.bet_history.BetHistoryInputData;
import use_case.bet_history.BetHistoryInteractor;

public class BetHistoryController {
    final BetHistoryInputBoundary betHistoryUseCaseInteractor;
    public BetHistoryController(BetHistoryInputBoundary betHistoryInteractor) {
        this.betHistoryUseCaseInteractor = betHistoryInteractor;
    }
    public void setUsername(String username){
        betHistoryUseCaseInteractor.setUsername(username);
    }

    public void backToMain() {betHistoryUseCaseInteractor.backToMain();}

    public void setHistory(String username) {
        betHistoryUseCaseInteractor.setHistory(username);
    }
    public String getHistory(String username) {
        return betHistoryUseCaseInteractor.getHistory(username);
    }
}
