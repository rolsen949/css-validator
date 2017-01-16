//
// Author: Yves Lafon <ylafon@w3.org>
//
// (c) COPYRIGHT MIT, ERCIM, Keio, Beihang, 2016.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.css.properties.css3;

import org.w3c.css.util.ApplContext;
import org.w3c.css.util.InvalidParamException;
import org.w3c.css.values.CssExpression;
import org.w3c.css.values.CssTypes;
import org.w3c.css.values.CssValue;
import org.w3c.css.values.CssValueList;

import java.util.ArrayList;

import static org.w3c.css.values.CssOperator.SPACE;

/**
 * @spec https://www.w3.org/TR/2016/CR-css-grid-1-20160929/#propdef-grid-column
 */
public class CssGridColumn extends org.w3c.css.properties.css.CssGridColumn {

	/**
	 * Create a new CssGridColumn
	 */
	public CssGridColumn() {
		value = initial;
	}

	/**
	 * Creates a new CssGridColumn
	 *
	 * @param expression The expression for this property
	 * @throws org.w3c.css.util.InvalidParamException
	 *          Expressions are incorrect
	 */
	public CssGridColumn(ApplContext ac, CssExpression expression, boolean check)
			throws InvalidParamException {
		CssExpression exp;

		CssValue val;
		char op;
		exp = new CssExpression();
		int nb_switch = 0;

		ArrayList<CssValue> values = new ArrayList<>();

		while (!expression.end()) {
			val = expression.getValue();
			op = expression.getOperator();

			if (val.getType() == CssTypes.CSS_SWITCH) {
				if (nb_switch > 0) {
					throw new InvalidParamException("value", val,
							getPropertyName(), ac);
				}
				values.add(CssGridRowStart.checkGridLine(ac, exp, check, this));
				values.add(val);
				exp = new CssExpression();
				nb_switch++;
			} else {
				exp.addValue(val);
			}
			if (op != SPACE) {
				throw new InvalidParamException("operator", op,
						getPropertyName(), ac);
			}
			expression.next();
		}
		values.add(CssGridRowStart.checkGridLine(ac, exp, check, this));

		value = (values.size() == 1) ? values.get(0) : new CssValueList(values);
	}

	public CssGridColumn(ApplContext ac, CssExpression expression)
			throws InvalidParamException {
		this(ac, expression, false);
	}

}

