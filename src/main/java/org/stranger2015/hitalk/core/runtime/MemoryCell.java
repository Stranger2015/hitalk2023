package org.stranger2015.hitalk.core.runtime;

import static org.stranger2015.hitalk.core.runtime.MemoryCell.ETypeMemoryCells.*;

/**
 * Memory cells have a type and accompanying values. To prevent the mass creation of memory cell objects
 * a construction is used where memory cells can be converted into other types. Hence if for instance a
 * reference cell is reused as a structure then the convert method can be used rather than having to create
 * a new memory cell.
 *
 * @author Bas Testerink
 */

public
class MemoryCell {
    private int type;

    public
    MemoryCell () {
        type= ETypeMemoryCells.REF.ordinal();
    }

    public
    boolean lowerThan ( CellAddress hb ) {
        return false;//FIXME
    }

    public
    enum ETypeMemoryCells {
        STR,
        FN,
        REF,
        CON,
        NUM,
        LIS,
    }

    private ETypeMemoryCells eType;                      // Type of an instance
    private String functor;        // For functor and constant cells
    private int argCount = -1;        // For functor cells
    private double number = 0;

    private CellAddress pointer = new CellAddress();    // For STR, LIS, and REF cells, target is heap/register/frame/trail etc

    /**
     * @param type
     */
    public
    MemoryCell ( ETypeMemoryCells type ) {
        this.eType = type;
    }

    /**
     * Resets the cell to its initial values.
     */
    public
    void reset () {
        eType = null; //parseInt("-1");
        functor = null;
        argCount = -1;
        number = 0;
        pointer.set(-1, -1, -1);
    }

    /**
     * Convert this cell to a structure cell
     */
    public
    void convertToStructureCell ( CellAddress ptrFN ) {
        this.type = STR.ordinal();
        pointer.set(ptrFN.getDomain(), ptrFN.getFrame(), ptrFN.getIndex());
    }

    public
    void convertToStructureCell ( int domain, int frame, int index ) {
        this.type = STR.ordinal();
        pointer.set(domain, frame, index);
    }

    /**
     * Convert this cell to a reference cell
     */
    public
    void convertToRefCell ( CellAddress ptr ) {
        this.type = REF.ordinal();
        pointer.set(ptr.getDomain(), ptr.getFrame(), ptr.getIndex());
    }

    public
    void convertToRefCell ( int domain, int frame, int index ) {
        this.type = REF.ordinal();
        pointer.set(domain, frame, index);
    }

    /**
     * Convert this cell to a list cell
     */
    public
    void convertToListCell ( CellAddress ptr ) {
        this.type = LIS.ordinal();
        pointer.set(ptr.getDomain(), ptr.getFrame(), ptr.getIndex());
    }

    public
    void convertToListCell ( int domain, int frame, int index ) {
        this.type = LIS.ordinal();
        pointer.set(domain, frame, index);
    }

    /**
     * Convert this cell to a functor cell
     */
    public
    void convertToFunctorCell ( String functor, int argCount ) {
        this.type = FN.ordinal();
        this.functor = functor;
        this.argCount = argCount;
    }

    /**
     * Convert this cell to a constant cell
     */
    public
    void convertToConstantCell ( String constant ) {
        this.type = CON.ordinal() ;
        this.functor = constant;
    }

    /**
     * Convert this cell to a number cell
     */
    public
    void convertToNumberCell ( double number ) {
        this.type = NUM.ordinal();
        this.number = number;
    }


    /**
     * Copy the values of another cell into this cell.
     */
    public
    void copyFrom ( MemoryCell other ) {
        setType(other.getType());
        setFunctor(other.getFunctor());
        setArgCount(other.getArgCount());
        setNumber(other.getNumber());
        pointer.set(other.getPointerDomain(), other.getPointerFrame(), other.getPointerIndex());
    }

    public
    void setPointer ( CellAddress pointer ) {
        this.pointer = pointer;
    }

    public
    ETypeMemoryCells getType () {
        return eType;
    }

    // Setters/getters
    public
    void setType ( ETypeMemoryCells type ) {
        this.eType = type;
    }

    public
    String getFunctor () {
        return functor;
    }

    public
    void setFunctor ( String functor ) {
        this.functor = functor;
    }

    public
    int getArgCount () {
        return argCount;
    }

    public
    void setArgCount ( int argCount ) {
        this.argCount = argCount;
    }

    public
    int getPointerDomain () {
        return pointer.getDomain();
    }

    public
    int getPointerFrame () {
        return pointer.getFrame();
    }

    public
    int getPointerIndex () {
        return pointer.getIndex();
    }

    public
    double getNumber () {
        return number;
    }

    public
    void setNumber ( double number ) {
        this.number = number;
    }

    public
    String toString () {
        String r = "";
        switch (eType) {
            case STR -> r += "<STR,%s>".formatted(pointer);
            case REF -> r += "<REF,%s>".formatted(pointer);
            case FN -> r += "<FN,%s,%d>".formatted(functor, argCount);
            case CON -> r += "<CON,%s>".formatted(functor);
            case NUM -> r += "<NUM,%s>".formatted(number);
            case LIS -> r += "<LIS,%s>".formatted(pointer);
//			case -1 -> r += "<null>";
            default -> throw new IllegalStateException("Unexpected value: %s".formatted(type));
        }

        return r;
    }
}
