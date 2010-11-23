/*
 * R : A Computer Language for Statistical Data Analysis
 * Copyright (C) 1995, 1996  Robert Gentleman and Ross Ihaka
 * Copyright (C) 1997--2008  The R Development Core Team
 * Copyright (C) 2003, 2004  The R Foundation
 * Copyright (C) 2010 bedatadriven
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package r;

import org.junit.Test;
import r.lang.EvalTestCase;
import r.lang.SEXP;
import r.lang.StringExp;
import r.parser.RParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class BasePackageTest extends EvalTestCase {


  @Test
  public void loadBase() throws IOException {

    loadBasePackage();

    StringExp letters = (StringExp) eval("letters");
    assertThat( letters.get(0),  equalTo( "a" ));
    assertThat( letters.get(25), equalTo( "z" ));

    eval( "assign('x', 42) ");
    assertThat( eval( "x" ) , equalTo( c(42) ));
  }


  @Test
  public void surveyPackage() throws Exception {

    loadBasePackage();

    System.out.println( eval( ".libPaths() "));

   // eval(" library(survey) ");

  }

  private void loadBasePackage() throws IOException {
    Reader reader = new InputStreamReader(getClass().getResourceAsStream("/r/library/base/R/base"));
    SEXP loadingScript = RParser.parseSource(reader).evaluate(global).getExpression();
    loadingScript.evaluate(global);
  }
}
