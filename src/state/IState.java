package state;

public interface IState {

    public void update(long currentNanoTime);

    public void render();

    public void onEnter();

    public void onExit();

}

class EmptyState implements IState {
	
	public void update(long currentTime) {
		//nothing
	}

    public void render() {
    	//nothing
    }

    public void onEnter() {
    	//nothing
    }

    public void onExit() {
    	//nothing
    }
}