package org.stranger2015.hitalk.core.runtime;

import org.stranger2015.hitalk.core.Term;

import java.util.ArrayList;
import java.util.List;

/**
 * The frame stack contains the environments and choice points (implemented as Frame objects) that are required at runtime.
 * The frame objects are reused between queries in order to prevent their mass instantiation. 
 * @author Bas Testerink
 *
 */
public class FrameStack {
	private final List<Frame> stack = new ArrayList<>(); // The stack itself
	private int topEnvironmentStack = -1;								// Points to the top of the stack
	private int topChoicePointStack = -1;
	
	/** Resets the stack. Does not remove all the frame objects but sets the top-poiner to 0. */
	public void reset(){
		topEnvironmentStack = -1;
		topChoicePointStack = -1;
	}
	
	/** Obtain a new frame index. */
	private int newFrameIndex(){
		int top;
		if(topChoicePointStack > topEnvironmentStack)
			top = topChoicePointStack + 1;
		else top = topEnvironmentStack + 1;
		if(top == stack.size()){
			stack.add(new Frame());
		}
		return top;
	}

	/** Obtain a new environment. This frame might be a reused frame. */
	public
    Frame newEnvironment(){
		int currentTop = topEnvironmentStack;
		topEnvironmentStack = newFrameIndex();
		Frame r = stack.get(topEnvironmentStack);
		r.setPrevious(currentTop); // This will function as setting the current E in the environment
		return r;
	}

	/** Obtain a new choicepoint. This frame might be a reused frame. */
	public
    Frame newChoicePoint(){
		int currentTop = topChoicePointStack;
		topChoicePointStack = newFrameIndex();
		Frame r = stack.get(topChoicePointStack);
		r.setPrevious(currentTop); // This will function as setting the current B in the choicepoint
		return r;
	}
	
	/** Obtain the top frame and remove it. */
	public
    Frame popTopEnvironment(){
		if(topEnvironmentStack < 0) return null;
		int top = topEnvironmentStack; 
		topEnvironmentStack = stack.get(top).getPrevious();
		return stack.get(top);
	}
	
	/** Obtain the top frame and remove it. */
	public
    Frame popTopChoicePoint(){
		if(topChoicePointStack < 0) return null;
		int top = topChoicePointStack;
		topChoicePointStack = stack.get(top).getPrevious();
		return stack.get(top);
	}

	/** Obtain the top frame. */
	public
    Frame peekTopChoicePoint(){
		if(topChoicePointStack < 0) return null;
		return stack.get(topChoicePointStack);
	}
	/** Obtain the top frame and remove it. */
	public
    Frame peekTopEnvironment(){
		if(topEnvironmentStack < 0) return null; 
		return stack.get(topEnvironmentStack);
	}
	
	public int getChoicePointTop(){ return topChoicePointStack; }
	public int getEnvironmentTop(){ return topEnvironmentStack; }
	public void setEnvironmentTop(int top){ topEnvironmentStack = top; }
	public void setChoicePointTop(int top){ topChoicePointStack = top; }
	public
    Frame getFrame( int i){ return stack.get(i); }
	
	/**
	 * Set a variable in the top frame. The variable will copy the cell's values, not point towards the input cell.
	 * @param nr The variable's index.
	 * @param value The cell from which the values are copied.
	 */
	public void setVariable(int nr, MemoryCell value){
		stack.get(topEnvironmentStack).getVar(nr).copyFrom(value);
	}
	
	/** Obtain the memory cell of a stack variable. */
	public
	Term getVariable( int nr){ return (Term) stack.get(topEnvironmentStack).getVar(nr); }
	
	/** Obtain the variables of the top frame. */
	public
	List<Term> getVariables(){ return stack.get(topEnvironmentStack).getVariables(); }
	
	public String toString(){
		StringBuilder b = new StringBuilder();
		b.append("Stack:\r\n");
		b.append("b: ").append(getChoicePointTop()).append("\r\n");
		b.append("e: ").append(getEnvironmentTop()).append("\r\n").append("\r\n");
		for(int i = stack.size()-1; i >= 0; i--){
			if(i <= topChoicePointStack || i <= topEnvironmentStack){
				b.append("(index=").append(i).append("){ ").append("\r\n");
				b.append(stack.get(i)).append("\r\n");
				b.append("}\r\n");
			}
		}

		return b.toString();
	}
}
