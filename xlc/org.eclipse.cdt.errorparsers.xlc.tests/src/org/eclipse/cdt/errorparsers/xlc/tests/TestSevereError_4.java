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

public class TestSevereError_4 extends TestCase {
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
		assertEquals("temp9.c", aix.getFileName(0));
		assertEquals(12, aix.getLineNumber(0));
		assertEquals(IMarkerGenerator.SEVERITY_ERROR_RESOURCE, aix.getSeverity(0));
		assertEquals("Function argument assignment between types " + "\"int\" and \"char*\" is not allowed.",
				aix.getMessage(0));
	}

	public TestSevereError_4(String name) {
		super(name);
		err_msg = "\"temp9.c\", line 12.18: 1506-280 (S) " + "Function argument assignment between types "
				+ "\"int\" and \"char*\" is not allowed.";
	}
}