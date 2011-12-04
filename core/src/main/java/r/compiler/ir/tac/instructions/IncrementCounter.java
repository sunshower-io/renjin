package r.compiler.ir.tac.instructions;

import r.compiler.ir.tac.operand.Temp;
import r.lang.Context;

/**
 * Increments a counter variable. Only used for the 
 * 'for' loop, will see if really need this
 * 
 */
public class IncrementCounter implements Statement {

  private Temp counter;

  public IncrementCounter(Temp counter) {
    this.counter = counter;
  }
  
  public Temp getCounter() {
    return counter;
  }
  
  @Override
  public Object interpret(Context context, Object[] temp) {
    Integer i = (Integer)temp[counter.getIndex()];
    temp[counter.getIndex()] = i+1;
    return null;
  }

  @Override
  public String toString() {
    return "increment counter " + counter;
  }
}
