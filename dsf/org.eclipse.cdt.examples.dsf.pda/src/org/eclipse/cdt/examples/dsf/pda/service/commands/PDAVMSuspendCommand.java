/*******************************************************************************
 * Copyright (c) 2008, 2009 Wind River Systems and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Wind River Systems - initial API and implementation
 *******************************************************************************/
package org.eclipse.cdt.examples.dsf.pda.service.commands;

import org.eclipse.cdt.dsf.concurrent.Immutable;
import org.eclipse.cdt.examples.dsf.pda.service.PDAVirtualMachineDMContext;

/**
 * Suspends the execution of the whole virtual machine
 *
 * <pre>
 *    C: vmsuspend
 *    R: ok
 *    E: vmsuspended client
 *
 * Errors:
 *    error: thread already suspended
 * </pre>
 */
@Immutable
public class PDAVMSuspendCommand extends AbstractPDACommand<PDACommandResult> {

	public PDAVMSuspendCommand(PDAVirtualMachineDMContext context) {
		super(context, "vmsuspend");
	}

	@Override
	public PDACommandResult createResult(String resultText) {
		return new PDACommandResult(resultText);
	}
}
