package org.stranger2015.hitalk.core.runtime;

import java.util.ArrayList;
import java.util.List;

import static org.stranger2015.hitalk.core.runtime.Frame.CHOICEPOINT;
import static org.stranger2015.hitalk.core.runtime.Frame.CONTEXT;

/**
 * The frame stack contains the environments and choice points (implemented as Frame objects)
 * that are required at runtime.
 * The frame objects are reused between queries in order to prevent their mass instantiation. 
 * @author Bas Testerink
 *
 */
public class FrameStack {
	protected final List<Frame> stack = new ArrayList<>(); // The stack itself
	protected int topEnvironmentStack = -1;// Points to the top of the stack
	protected int topChoicePointStack = -1;
	protected int topContextStack = -1;

	/** Resets the stack. Does not remove all the frame objects but sets the top-pointer to 0. */
	public void reset(){
		topEnvironmentStack = -1;//fixme = 0
		topChoicePointStack = -1;
		topContextStack = -1;
	}
	
	/** Obtain a new frame index. */
	private int newFrameIndex(){
		int top;
		int type;
		if(topChoicePointStack > topEnvironmentStack) {
			top = topChoicePointStack + 1;
			type= CHOICEPOINT;

		}
		else if(topEnvironmentStack > topContextStack ){
			top = topEnvironmentStack + 1;
			type=CHOICEPOINT;
					}
		else {
			top = topContextStack + 1;
			type=CONTEXT;
		}
		if(top == stack.size()){
			stack.add(createFrame(type));
		}

		return top;
	}

	/**
	 * @param type 
	 * @return
	 */
	Frame createFrame(int type, int cp_index, CodeClause cp_clause,int variableCount, int b0, Object ...params){
		switch (type) {
			case Frame.ENVIRONMENT -> {
				return new EnvironmentFrame(
						cp_index,
						cp_clause,
						variableCount,
						b0,
						(Integer) params[0]);
			}
			case CHOICEPOINT -> {//int e,int tr,int h,
				return new ChoicePointFrame(
						cp_index,
						cp_clause,
						variableCount,
						b0,
						(Integer) params[0],
						(Integer) params[1],
						(Integer) params[2]);
			}
			case Frame.CONTEXT -> {
				return new CtxFrame(
						cp_index,
						cp_clause,
						variableCount,
						b0);
			}
		}

		return null;
	}

	/** Obtain a new environment. This frame might be a reused frame. */
	public
    Frame newEnvironment( int cp_index,
						  CodeClause cp_clause,
						  int permVarCount,
						  int b0 ){
		int currentTop = topEnvironmentStack;
		topEnvironmentStack = newFrameIndex();
		Frame frame = stack.get(topEnvironmentStack);
		frame.setPrevious(currentTop); // This will function as setting the current E in the environment

		return frame;
	}

	/** Obtain a new choicepoint. This frame might be a reused frame. */
	public
    Frame newChoicePoint( int cp_index,
						  CodeClause cp_clause,
						  CodeClause p_clause,
						  int e,
						  int tr,
						  int index,
						  int b0,
						  int arity ){
		int currentTop = topChoicePointStack;
		topChoicePointStack = newFrameIndex();
		Frame frame = stack.get(topChoicePointStack);
		frame.setPrevious(currentTop);			 // This will function as setting the current B in the choicepoint

		return frame;
	}
	
	/** Obtain the top frame and remove it. */
	public
    Frame popTopEnvironment(){
		if(topEnvironmentStack < 0) {
			return null;
		}
		int top = topEnvironmentStack; 
		topEnvironmentStack = stack.get(top).getPrevious();

		return stack.get(top);
	}
	
	/** Obtain the top frame and remove it. */
	public
    Frame popTopChoicePoint(){
		if(topChoicePointStack < 0) {
			return null;
		}
		int top = topChoicePointStack;
		topChoicePointStack = stack.get(top).getPrevious();
		return stack.get(top);
	}

	/** Obtain the top frame. */
	public
    Frame peekTopChoicePoint(){
		return topChoicePointStack < 0 ? null : stack.get(topChoicePointStack);
	}
	/** Obtain the top frame and remove it. */
	public
    Frame peekTopEnvironment(){
		return topEnvironmentStack < 0 ? null : stack.get(topEnvironmentStack);
	}

	/**
	 * @return
	 */
	public int getChoicePointTop(){ return topChoicePointStack; }

	/**
	 * @return
	 */
	public int getEnvironmentTop(){ return topEnvironmentStack; }

	/**
	 * @param top
	 */
	public void setEnvironmentTop(int top){ topEnvironmentStack = top; }

	/**
	 * @param top
	 */
	public void setChoicePointTop(int top){ topChoicePointStack = top; }

	/**
	 * @param i
	 * @return
	 */
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
	MemoryCell getVariable( int nr){ return stack.get(topEnvironmentStack).getVar(nr); }
	
	/** Obtain the variables of the top frame. */
	public
	List<MemoryCell> getVariables(){ return stack.get(topEnvironmentStack).getVariables(); }

	/**
	 * @return
	 */
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

	/**
	 * @return
	 */
	public
	int getTopContextStack () {
		return topContextStack;
	}
}
