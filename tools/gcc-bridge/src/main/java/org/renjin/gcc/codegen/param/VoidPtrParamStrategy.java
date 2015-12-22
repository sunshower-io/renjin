package org.renjin.gcc.codegen.param;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.renjin.gcc.codegen.LocalVarAllocator;
import org.renjin.gcc.codegen.expr.AbstractExprGenerator;
import org.renjin.gcc.codegen.expr.ExprGenerator;
import org.renjin.gcc.gimple.GimpleParameter;
import org.renjin.gcc.gimple.type.GimplePointerType;
import org.renjin.gcc.gimple.type.GimpleType;
import org.renjin.gcc.gimple.type.GimpleVoidType;
import org.renjin.gcc.runtime.Ptr;

import java.util.Collections;
import java.util.List;

/**
 * Strategy for {@code void*} parameters, implemented using a {@link Ptr} parameter.
 */
public class VoidPtrParamStrategy extends ParamStrategy {
  @Override
  public List<Type> getParameterTypes() {
    return Collections.singletonList(Type.getType(Ptr.class));
  }

  @Override
  public ExprGenerator emitInitialization(MethodVisitor methodVisitor, GimpleParameter parameter, int startIndex, LocalVarAllocator localVars) {
    return new Expr(startIndex);
  }

  @Override
  public void emitPushParameter(MethodVisitor mv, ExprGenerator parameterValueGenerator) {
    parameterValueGenerator.emitPushRecordRef(mv);
  }

  private class Expr extends AbstractExprGenerator {
    private int varIndex;

    public Expr(int varIndex) {
      this.varIndex = varIndex;
    }

    @Override
    public GimpleType getGimpleType() {
      return new GimplePointerType(new GimpleVoidType());
    }


    @Override
    public void emitPushRecordRef(MethodVisitor mv) {
      mv.visitVarInsn(Opcodes.ALOAD, varIndex);
    }
  }
}