/*******************************************************************************
 * Copyright (c) 2006, 2013 IBM Corporation and others.
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

public class TestSevereError_2 extends TestCase {
	String err_msg;

	/**
	 * This function tests parseLine function of the
	 * XlcErrorParser class. Error message generated by
	 * xlc compiler with high severity (S) is given as
	 * input for testing.
	 */
	public void testparseLine() {
		XlcErrorParserTester aix = new XlcErrorParserTester();
		aix.parseLine(err_msg);
		assertEquals("temp5.c", aix.getFileName(0));
		assertEquals(5, aix.getLineNumber(0));
		assertEquals(IMarkerGenerator.SEVERITY_ERROR_RESOURCE, aix.getSeverity(0));
		assertEquals("Undeclared identifier y.", aix.getMessage(0));
	}

	public TestSevereError_2(String name) {
		super(name);
		err_msg = "\"temp5.c\", line 5.9: 1506-045 (S) " + "Undeclared identifier y.";
	}
}