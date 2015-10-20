package org.renjin.gcc.codegen.var;

import com.google.common.base.Preconditions;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.renjin.gcc.codegen.Types;
import org.renjin.gcc.codegen.expr.AbstractExprGenerator;
import org.renjin.gcc.codegen.expr.ExprGenerator;
import org.renjin.gcc.codegen.expr.LValueGenerator;
import org.renjin.gcc.codegen.expr.ValueGenerator;
import org.renjin.gcc.gimple.type.GimplePrimitiveType;
import org.renjin.gcc.gimple.type.GimpleType;

import static org.objectweb.asm.Opcodes.ILOAD;
import static org.objectweb.asm.Opcodes.ISTORE;

public class ValueVarGenerator extends AbstractExprGenerator implements LValueGenerator, ValueGenerator, VarGenerator {
  private int index;
  private String name;
  private GimpleType type;

  public ValueVarGenerator(int index, String name, GimpleType type) {
    this.index = index;
    this.name = name;
    this.type = type;
  }

  @Override
  public void emitStore(MethodVisitor mv, ExprGenerator exprGenerator) {
    ValueGenerator valueGenerator = (ValueGenerator) exprGenerator;
    valueGenerator.emitPushValue(mv);

    if(Types.isInt(this) && Types.isLong(exprGenerator)) {
      mv.visitInsn(Opcodes.L2I);

    } else if(Types.isLong(this) && Types.isInt(exprGenerator)) {
      mv.visitInsn(Opcodes.I2L);
      
    } else {

      Preconditions.checkArgument(checkTypes(valueGenerator),
          "Type mismatch: Cannot assign %s of type %s to %s of type %s",
          valueGenerator,
          valueGenerator.getValueType(),
          this,
          getValueType());


    }

    mv.visitVarInsn(getValueType().getOpcode(ISTORE), index);

  }

  private boolean checkTypes(ValueGenerator valueGenerator) {
    Type varType = getValueType();
    Type valueType = valueGenerator.getValueType();
 
    return (isIntType(varType) && isIntType(valueType)) ||
           varType.equals(valueType);
  }

  private boolean isIntType(Type type) {
    return type.equals(Type.BOOLEAN_TYPE) ||
           type.equals(Type.BYTE_TYPE) ||
           type.equals(Type.INT_TYPE);
  }
  
  @Override
  public Type getValueType() {
    if(type instanceof GimplePrimitiveType) {
      return ((GimplePrimitiveType) type).jvmType();
    }
    throw new UnsupportedOperationException();
  }

  @Override
  public void emitPushValue(MethodVisitor mv) {
    mv.visitVarInsn(getValueType().getOpcode(ILOAD), index);
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public void emitDefaultInit(MethodVisitor mv) {
    
  }

  @Override
  public GimpleType getGimpleType() {
    return type;
  }
}
