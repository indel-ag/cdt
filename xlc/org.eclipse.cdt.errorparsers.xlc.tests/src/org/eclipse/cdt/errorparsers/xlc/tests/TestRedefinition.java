/*******************************************************************************
 * Copyright (c) 2006, 2010 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.cdt.errorparsers.xlc.tests;

import junit.framework.TestCase;

import org.eclipse.cdt.core.IMarkerGenerator;

public class TestRedefinition extends TestCase {
	/**
	 * This function tests parseLine function of the
	 * XlcErrorParser class. The second message generated by
	 * xlc compiler for macro/variable redefinition problem is given as
	 * input for testing.
	 */
	public void testparseLine() {
		XlcErrorParserTester aix = new XlcErrorParserTester();
		// Macro redefinition warning generates 2 messages. First line is ignored.
		// Second line is re-parsed to 2 warnings to cross-reference both.
		String err_msg1 = "\"temp1.c\", line 5.9: 1506-236 (W) Macro name TEMP_1 has been redefined.";
		String err_msg2 = "\"temp1.c\", line 5.9: 1506-358 (I) \"TEMP_1\" is defined on line 3 of temp1.h.";
		// variation of the message
		String err_msg3 = "\"temp2.c\", line 17.9: 1506-358 (I) \"MACRO_2\" is defined on line 10 of \"temp2.c\".";
		aix.parseLine(err_msg1);
		aix.parseLine(err_msg2);
		aix.parseLine(err_msg3);
		assertEquals(4, aix.getNumberOfMarkers());

		assertEquals("\"TEMP_1\" has been redefined on line 5 of temp1.c", aix.getMessage(0));
		assertEquals("temp1.h", aix.getFileName(0));
		assertEquals(3, aix.getLineNumber(0));
		assertEquals(IMarkerGenerator.SEVERITY_WARNING, aix.getSeverity(0));

		assertEquals("\"TEMP_1\" redefines original definition on line 3 of temp1.h", aix.getMessage(1));
		assertEquals("temp1.c", aix.getFileName(1));
		assertEquals(5, aix.getLineNumber(1));
		assertEquals(IMarkerGenerator.SEVERITY_WARNING, aix.getSeverity(1));

		assertEquals("\"MACRO_2\" has been redefined on line 17 of temp2.c", aix.getMessage(2));
		assertEquals("temp2.c", aix.getFileName(2));
		assertEquals(10, aix.getLineNumber(2));
		assertEquals(IMarkerGenerator.SEVERITY_WARNING, aix.getSeverity(2));

		assertEquals("\"MACRO_2\" redefines original definition on line 10 of temp2.c", aix.getMessage(3));
		assertEquals("temp2.c", aix.getFileName(3));
		assertEquals(17, aix.getLineNumber(3));
		assertEquals(IMarkerGenerator.SEVERITY_WARNING, aix.getSeverity(3));
	}

	public TestRedefinition(String name) {
		super(name);
	}
}