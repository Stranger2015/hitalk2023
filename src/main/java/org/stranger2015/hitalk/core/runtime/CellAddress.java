package org.stranger2015.hitalk.core.runtime;

import org.stranger2015.hitalk.core.ListTerm;
import org.stranger2015.hitalk.core.Term;

/**
 * Just a containter to point at some memory in the WAM.
 * I'm planning to replace all CellAddress occurrences with a long which
 * encodes the same information, in order to optimize memory usage.
 * 
 * @author Bas Testerink
 *
 */
public class CellAddress extends Term {
	private int domain; // Either heap, stack, register.
	private int frame; // In case the address is towards the environment, we need the environment nr.
	private int index; // Either the heap index, register number, or environment variable number.

	protected
	CellAddress ( ListTerm head ) {
		super(head);
	}

	/**
	 * @param type
	 */
	public
	CellAddress ( ETypeMemoryCells type ) {
		super(type);
	}

	public void set(int domain, int frame, int index){
		this.domain = domain;
		this.frame = frame;
		this.index = index;
	}
	
	public CellAddress(){}

	@Override
	public
	boolean isPredicateIndicator () {
		return false;
	}

	@Override
	public
	boolean isAtomic () {
		return false;
	}

	public CellAddress(int domain, int frame, int index){
//		super();
		this.domain = domain;
		this.frame = frame;
		this.index = index;
	}
	
	public void setIndex(int i){ this.index = i; }
	public int getDomain(){ return domain; }
	public int getFrame(){ return frame; }
	public void setFrame(int frame){ this.frame = frame; }
	public int getIndex(){ return index; }
	public void incrementIndex(){ index++; }
	public void copyFrom(CellAddress other){
		domain = other.domain;
		frame = other.frame;
		index = other.index;
	}
	
	/**
	 * Based on the original WAM ordering where heap < stack < registers.
	 * @param other
	 * @return True if the callee comes before the other. 
	 */
	public boolean lowerThan(CellAddress other){
		if(domain < other.getDomain()) {
			return true;
		}
		else {
			if(domain == STACK.ordinal()) {
				if(frame < other.getFrame()) {
					return true;
				}
			}

			return index < other.getIndex(); 
		}
	}
	
	public String toString(){
		return "%s,%s%d".formatted(targetToString(domain), domain == STACK.ordinal() ?
				("%d,".formatted(frame))
				: "",
				domain == REGISTERS.ordinal() ?
					(index + 1)
					: index);
	}
	
	public boolean equals(CellAddress other){
		return other.getDomain() == domain && other.getFrame() == frame && other.getIndex() == index; 
	}

	/**
	 * @return
	 */
	@Override
	public
	byte getKind () {
		return 0;
	}

	/**
	 * @return
	 */
	@Override
	public
	boolean isFree () {
		return false;
	}
}
