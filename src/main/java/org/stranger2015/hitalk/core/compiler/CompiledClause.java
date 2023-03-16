package  org.stranger2015.hitalk.core.compiler;

import org.stranger2015.hitalk.core.compiler.instructions.Instruction;

import java.util.List;

/**
 * This class is the result of the compilation process. It contains WAM instructions, a string representation of the code and the functor/arity. 
 * This class can be removed in the future as <code>CodeClause</code> instances can also be used instead of CompiledClause. 
 * 
 * @author Bas Testerink
 */
public class CompiledClause {
	private final String prologString;
	private final String functor;
	private final List<Instruction> instructions;
	
	public CompiledClause(String prologString, String functor, List <Instruction> instructions){
		this.prologString = prologString;
		this.functor = functor;
		this.instructions = instructions;
	}
	
	public String getPrologString(){ return prologString; }
	public String getFunctor(){ return functor; }
	public List<Instruction> getInstructions(){ return instructions; }
	
	public String toString(){
		return "%s\r\n%s\r\n%s\r\n".formatted(functor, prologString, instructions);
	}
}
