/*
 * Copyright 2015 Allette Systems (Australia)
 * http://www.allette.com.au
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.pageseeder.furi;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * A URI token wrapping a variable.
 *
 * <p>Variables follow the following expression:
 * <pre>
 * var         = varname [ &quot;=&quot; vardefault ]
 * varname     = (ALPHA / DIGIT)*(ALPHA / DIGIT / &quot;.&quot; / &quot;_&quot; / &quot;-&quot; )
 * vardefault  = *(unreserved / pct-encoded)
 * </pre>
 *
 * @author Christophe Lauret
 * @version 30 December 2008
 */
public class TokenVariable extends TokenBase implements Token, Matchable {

  /**
   * The variable for this token.
   */
  private Variable _var;

  /**
   * Creates a new variable token.
   *
   * @param exp The expression to create a new.
   *
   * @throws NullPointerException If the specified expression is <code>null</code>.
   * @throws URITemplateSyntaxException If the specified expression could not be parsed as a
   *           variable.
   */
  public TokenVariable(String exp) throws NullPointerException, URITemplateSyntaxException {
    this(Variable.parse(exp));
  }

  /**
   * Creates a new variable token.
   *
   * @param var The variable this token corresponds to.
   *
   * @throws NullPointerException If the specified text is <code>null</code>.
   */
  public TokenVariable(Variable var) throws NullPointerException {
    super('{' + var.toString() + "}");
    this._var = var;
  }

  /**
   * Returns the variable wrapped by this token.
   *
   * @return The variable wrapped by this token.
   */
  public Variable getVariable() {
    return this._var;
  }

  /**
   * {@inheritDoc}
   */
  public String expand(Parameters variables) {
    return URICoder.encode(this._var.value(variables));
  }

  /**
   * {@inheritDoc}
   */
  public boolean match(String value) {
    return Variable.isValidValue(value);
  }

  /**
   * {@inheritDoc}
   */
  public Pattern pattern() {
    return Variable.VALID_VALUE;
  }

  /**
   * {@inheritDoc}
   */
  public boolean resolve(String expanded, Map<Variable, Object> values) {
    values.put(this._var, URICoder.decode(expanded));
    return true;
  }

}
