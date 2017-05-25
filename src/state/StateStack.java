package state;

import java.util.HashMap;
import java.util.Stack;

public class StateStack {

	private static HashMap<String, IState> mStates = new HashMap<>();
	private static Stack<IState> mStack = new Stack<>();
	
	public StateStack() {
		
	}
	
	// add states to the hash map.(initialize)
	public static void add(String name, IState state) {
		mStates.put(name, state);
	}
	
	public static void update(long currentNanoTime) {
		IState top = mStack.lastElement();
		top.update(currentNanoTime);
	}

	public static void render() {
		IState top = mStack.lastElement();
		top.render();
	}

	public static void push(String name) {
		IState state = mStates.get(name);
		mStack.push(state);
		state.onEnter();
	}

	public static IState pop() {
		// the git hub code多了一行 :
		mStack.lastElement().onExit();
		return mStack.pop();
		//why should I use IState for return type?(the tutorial and the code on github do the same)
	}
	
	public static IState getCurrentState() {
		return mStack.lastElement();
	}
}