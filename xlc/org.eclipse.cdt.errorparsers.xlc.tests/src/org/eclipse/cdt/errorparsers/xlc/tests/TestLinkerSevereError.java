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

public class TestLinkerSevereError extends TestCase {
	String err_msg;

	/**
	 * This function tests parseLine function of the
	 * XlcErrorParser class. Error message generated by
	 * xlc linker with "SEVERE ERROR" level is given as
	 * input for testing.
	 */
	public void testparseLine() {
		XlcErrorParserTester aix = new XlcErrorParserTester();
		aix.parseLine(err_msg);
		assertEquals("", aix.getFileName(0));
		assertEquals(0, aix.getLineNumber(0));
		assertEquals(IMarkerGenerator.SEVERITY_ERROR_RESOURCE, aix.getSeverity(0));
		assertEquals("EXEC binder commands nested too deeply.", aix.getMessage(0));
	}

	public TestLinkerSevereError(String name) {
		super(name);
		err_msg = "ld: 0711-634 SEVERE ERROR: EXEC binder commands nested too deeply.";
	}
}
