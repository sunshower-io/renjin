/*
 * Renjin : JVM-based interpreter for the R language for the statistical analysis
 * Copyright © 2010-${$file.lastModified.year} BeDataDriven Groep B.V. and contributors
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, a copy is available at
 *  https://www.gnu.org/licenses/gpl-2.0.txt
 *
 */

package org.renjin.compiler.codegen.expr;

import org.renjin.compiler.codegen.ConstantBytecode;
import org.renjin.compiler.codegen.EmitContext;
import org.renjin.repackaged.asm.commons.InstructionAdapter;
import org.renjin.sexp.SEXP;
import org.renjin.sexp.Vector;

public class ConstantExpr implements CompiledSexp {

  private final SEXP sexp;

  public ConstantExpr(SEXP sexp) {
    this.sexp = sexp;
  }

  @Override
  public void loadSexp(EmitContext context, InstructionAdapter mv) {
    ConstantBytecode.pushConstant(mv, sexp);
  }

  @Override
  public void loadArray(EmitContext context, InstructionAdapter mv, VectorType vectorType) {
    throw new UnsupportedOperationException("TODO");
  }

  @Override
  public void loadLength(EmitContext context, InstructionAdapter mv) {
    mv.visitLdcInsn(sexp.length());
  }

  @Override
  public CompiledSexp elementAt(EmitContext context, CompiledSexp indexExpr) {
    throw new UnsupportedOperationException("TODO");
  }

  @Override
  public void loadScalar(EmitContext context, InstructionAdapter mv, VectorType type) {
    switch (type) {
      case BYTE:
        mv.iconst(((Vector) sexp).getElementAsByte(0));
        break;
      case INT:
        mv.iconst(((Vector) sexp).getElementAsInt(0));
        break;
      case DOUBLE:
        mv.dconst(((Vector) sexp).getElementAsDouble(0));
        break;
      case STRING:
        mv.aconst(((Vector) sexp).getElementAsString(0));
        break;
    }
  }
}
