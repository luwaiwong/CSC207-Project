package interface_adapter.player;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PlayerViewModel extends ViewModel {
    public final String TITLE_LABEL = "SPORTSMART";
    public final String SUBTITLE_LABEL = "Player Point Prediction";
    public final String INPUT_A_LABEL = "First Name";
    public final String INPUT_B_LABEL = "Last Name";
    public final String BACK_BUTTON_LABEL = "Back";
    public final String PREDICT_BUTTON_LABEL = "Predict";
    private PlayerState state = new PlayerState();

    public PlayerViewModel(){
        super("player");
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void setState (PlayerState state){
        this.state = state;
    }

    public PlayerState getState(){
        return state;
    }
}
